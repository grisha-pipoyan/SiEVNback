package com.brutus.sievn.rest.data;

import com.brutus.sievn.MyUtils;
import com.brutus.sievn.exception.BadRequestException;
import com.brutus.sievn.exception.NotAuthorized;
import com.brutus.sievn.exception.NotFoundException;
import com.brutus.sievn.persistance.model.addData.*;
import com.brutus.sievn.persistance.model.user.AppUser;
import com.brutus.sievn.rest.data.model.HouseInfoPicture;
import com.brutus.sievn.rest.data.model.request.DeleteModel;
import com.brutus.sievn.rest.data.model.request.RequestModel;
import com.brutus.sievn.rest.data.model.request.RequestModelUpdate;
import com.brutus.sievn.rest.data.model.response.ResponseModelAdmin;
import com.brutus.sievn.services.HouseDataService;
import com.brutus.sievn.services.UserService;
import com.google.common.collect.Lists;
import com.opencsv.CSVReader;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.FileDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/sievn/management")
@CrossOrigin("*")
@Validated
public class AdminDataController {

    private final CacheManager cacheManager;

    private final HouseDataService houseDataService;

    private final UserService userService;

    public AdminDataController(CacheManager cacheManager,
                               HouseDataService houseDataService,
                               UserService userService) {
        this.cacheManager = cacheManager;
        this.houseDataService = houseDataService;
        this.userService = userService;
    }


    @Scheduled(fixedRateString = "${clear.all.cache.fixed.rate}",
            initialDelayString = "${clear.all.cache.init.delay}")
    // reset cache every hr, with delay of 1hr after app start
    public void reportCurrentTime() {
        log.info("Clearing all cache");
        cacheManager.getCacheNames().parallelStream().forEach(
                name -> Objects.requireNonNull(cacheManager.getCache(name)).clear());
    }

    @PostMapping("/addHouseDataJson")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addHouseDataJson(@RequestParam("pictures") MultipartFile pictures,
                                              @RequestParam("infos") MultipartFile infos) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AppUser applicationUser = (AppUser) userService.loadUserByUsername(
                (String) authentication.getPrincipal());

//        String bioru = "В квартире есть все удобства";
//        String bioam = "Բնակարանում կան բոլոր հարմարությունները";
//        String bioen = "The apartment has all the amenities";

//        DSSDocument picturesDoc = new FileDocument(new File("C:\\Users\\grish\\Desktop\\house_pictures_202301182007.csv"));
//        DSSDocument infosDoc = new FileDocument(new File("C:\\Users\\grish\\Desktop\\01.18.2023.json"));

        DSSDocument picturesDoc = new FileDocument(new File("/opt/sievn/houses.csv"));
        DSSDocument infosDoc = new FileDocument(new File("/opt/sievn/info.json"));

        List<HouseInfoPicture> houseInfoPictures = new ArrayList<>();
        int counter = 0;
        try {
            try (CSVReader reader = new CSVReader(new InputStreamReader(picturesDoc.openStream()))) {
                List<String[]> r = reader.readAll();

                for (var obj :
                        r) {
                    if (counter == 0) {
                        counter += 1;
                        continue;
                    }
                    houseInfoPictures.add(new HouseInfoPicture(Long.valueOf(obj[0]), obj[2], Utils.fromBase64(obj[1])));
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

        List<HouseInfo> houseInfoList = new ArrayList<>();

        try {

            JSONParser jsonParser = new JSONParser();
            org.jose4j.json.internal.json_simple.JSONArray jsonArray = (org.jose4j.json.internal.json_simple.JSONArray)
                    jsonParser.parse(
                            new InputStreamReader(infosDoc.openStream(), StandardCharsets.UTF_8));

            //JSONObject jsonObject = new JSONObject();

            for (Object object :
                    jsonArray) {
                JSONObject house = (JSONObject) object;

                Long id = (Long) house.get("houseId");
                LinkedHashMap<String, byte[]> linkedHashMap = new LinkedHashMap<>();

                for (HouseInfoPicture h :
                        houseInfoPictures) {
                    if (h.getId().longValue() == id.longValue()) {
                        linkedHashMap.put(h.getName(), h.getBytes());
                    }
                }

                Animals animals = returnJsonValueAnimals(house);
                Double area = (Double) house.get("area");
                Balcony balcony = returnJsonValueBalcony(house);
                BuildingType buildingType = returnJsonValueBuild(house);
                Cities city = returnJsonValueCity(house);
                CommercialType commercialType = returnJsonValueCommercial(house);
                YesNo elevator = returnJsonValueYesNo(house, "elevator");

                Long floor1 = (Long) house.get("floor");
                int floor = 1;
                if(floor1!=null){
                    floor = floor1.intValue();
                }

                Long floor_number1 = (Long) house.get("floorNumber");
                int floor_number = 1;
                if(floor_number1!=null){
                    floor_number = floor_number1.intValue();
                }

                YesNo isTopic = returnJsonValueYesNo(house, "isTopic");
                YesNo newBuilt = returnJsonValueYesNo(house, "newBuilt");
                PaymentMethod paymentMethod = returnJsonValuePaymentMethod(house);
                Double price = (Double) house.get("price");
                RepairType repairType = returnJsonValueRepairType(house);

                int rooms = 1;
                Long rooms1 = (Long) house.get("rooms");
                if(rooms1!=null){
                    rooms = rooms1.intValue();
                }

                int toilets = 1;
                Long toilets1 = (Long) house.get("toilets");
                if(toilets1!=null){
                    toilets = toilets1.intValue();
                }

                String bioam = returnJsonValue(house,"bioAM");
                String bioru = returnJsonValue(house,"bioRU");
                String bioen = returnJsonValue(house,"bioEN");

                String streetam = returnJsonValue(house, "streetAM");
                String streetru = returnJsonValue(house, "streetRU");
                String streeten = returnJsonValue(house, "streetEN");
                String titleam = returnJsonValue(house, "titleAM");
                String titleru = returnJsonValue(house, "titleRU");
                String titleen = returnJsonValue(house, "titleEN");

                YerevanRegions yerevanRegions = returnJsonValueCityYerevanRegions(house);
                String currencyType1 = (String) house.get("currencyType");
                CurrencyType currencyType = CurrencyType.valueOf(currencyType1);
                Property property = returnJsonValueProperty(house);


                HouseInfo data = getHouseInfoFromHouseData(isTopic, titleru, titleen, titleam,
                        bioru, bioen, bioam, property, paymentMethod, commercialType, city, yerevanRegions,
                        streetru, streeten, streetam, buildingType, newBuilt, area, elevator, floor, floor_number,
                        repairType, rooms, toilets, animals, balcony,
                        price, currencyType, linkedHashMap);

                //data.setId(id);
                data.setAppUser(applicationUser);
                houseInfoList.add(data);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

        List<HouseInfo> reverse = Lists.reverse(houseInfoList);
        houseDataService.addData(reverse);

        return ResponseEntity.ok("");

    }

    private String returnJsonValue(JSONObject jsonObject, String value) {
        return (String) jsonObject.get(value);
    }

    private Animals returnJsonValueAnimals(JSONObject jsonObject) {
        String s = (String) jsonObject.get("animals");
        if (s == null) {
            return null;
        } else {
            return Animals.forRussian(s);
        }
    }

    private Balcony returnJsonValueBalcony(JSONObject jsonObject) {
        String s = (String) jsonObject.get("balcony");
        if (s == null) {
            return null;
        } else {
            return Balcony.forRussian(s);
        }
    }

    private BuildingType returnJsonValueBuild(JSONObject jsonObject) {
        String s = (String) jsonObject.get("buildingType");
        if (s == null) {
            return null;
        } else {
            return BuildingType.forRussian(s);
        }
    }

    private Cities returnJsonValueCity(JSONObject jsonObject) {
        String s = (String) jsonObject.get("city");
        if (s == null) {
            return null;
        } else {
            return Cities.forRussian(s);
        }
    }

    private CommercialType returnJsonValueCommercial(JSONObject jsonObject) {
        String s = (String) jsonObject.get("commercialType");
        if (s == null) {
            return null;
        } else {
            return CommercialType.forRussian(s);
        }
    }

    private PaymentMethod returnJsonValuePaymentMethod(JSONObject jsonObject) {
        String s = (String) jsonObject.get("paymentMethod");
        if (s == null) {
            return null;
        } else {
            return PaymentMethod.forRussian(s);
        }
    }

    private RepairType returnJsonValueRepairType(JSONObject jsonObject) {
        String s = (String) jsonObject.get("repairType");
        if (s == null) {
            return null;
        } else {
            return RepairType.forRussian(s);
        }
    }

    private YerevanRegions returnJsonValueCityYerevanRegions(JSONObject jsonObject) {
        String s = (String) jsonObject.get("yerevanRegion");
        if (s == null) {
            return null;
        } else {
            return YerevanRegions.forRussian(s);
        }
    }

    private Property returnJsonValueProperty(JSONObject jsonObject) {
        String s = (String) jsonObject.get("property");
        if (s == null) {
            return null;
        } else {
            return Property.forRussian(s);
        }
    }

    private YesNo returnJsonValueYesNo(JSONObject jsonObject, String value) {
        String s = (String) jsonObject.get(value);
        if (s == null) {
            return null;
        } else {
            return YesNo.forRussian(s);
        }
    }


    @Operation(summary = "Adding house info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/addHouseData1")
    @Caching(evict = {
            @CacheEvict(value = "houseInfos", allEntries = true),
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void addHouseDta1(@Valid @RequestBody RequestModel requestModel){

        log.info("Request has been obtained...");

        LinkedHashMap<String, byte[]> picturesHashMap = new LinkedHashMap<>();

        int counter = 0;
        try {
            DSSDocument mainDoc = new InMemoryDocument(Utils.fromBase64(requestModel.getMainPicture()));
            String mainHousePicture = String.format(
                    "%d.%s", counter, FilenameUtils.getExtension(
                            mainDoc.getName()));
            picturesHashMap.put(mainHousePicture, Utils.fromBase64(requestModel.getMainPicture()));

            counter++;

        } catch (Exception e) {
            String error = String.format("Can not set main picture. Reason: %s", e.getMessage());
            log.error(error);
            throw new BadRequestException(error);
        }

        for (String multipartFile :
                requestModel.getPictures()) {

            try {
                DSSDocument currentPic = new InMemoryDocument(Utils.fromBase64(multipartFile));
                String housePicturesId = String.format(
                        "%d.%s", counter, FilenameUtils.getExtension(currentPic.getName()));

                picturesHashMap.put(housePicturesId, Utils.fromBase64(multipartFile));

                counter++;

            } catch (Exception e) {
                String error = String.format("An error has been occurred while setting " +
                        "optional pictures. Reason: %s", e.getMessage());
                log.error(error);
                throw new BadRequestException(error);
            }
        }


        log.info("Preparing request for adding in database...");
        HouseInfo data = getHouseInfoFromHouseData(requestModel.getIsTopic(), requestModel.getTitleRU(), requestModel.getTitleEN(),
                requestModel.getTitleAM(),
                requestModel.getBioRU(), requestModel.getBioEN(), requestModel.getBioAM(),
                requestModel.getProperty(), requestModel.getPaymentMethod(), requestModel.getCommercialType(),
                requestModel.getCity(), requestModel.getYerevanRegion(),
                requestModel.getStreetRU(), requestModel.getStreetEN(), requestModel.getStreetAM(),
                requestModel.getBuildingType(), requestModel.getNewBuilt(), requestModel.getArea(),
                requestModel.getElevator(), requestModel.getFloor(), requestModel.getFloorNumber(),
                requestModel.getRepairType(), requestModel.getRooms(), requestModel.getToilets(),
                requestModel.getAnimals(), requestModel.getBalcony(),
                requestModel.getPrice(), requestModel.getCurrencyType(), picturesHashMap);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AppUser applicationUser = (AppUser) userService.loadUserByUsername(
                (String) authentication.getPrincipal());

        data.setAppUser(applicationUser);

        houseDataService.addData(data);
        log.info("House data has been successfully added to database.");


    }


    @Operation(summary = "Updating house info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/updateHouseData1")
    @Caching(evict = {
            @CacheEvict(value = "houseInfos", allEntries = true)
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void updateHouseData1(@Valid @RequestBody RequestModelUpdate requestModel){

        log.info("Request has been obtained...");

        HouseInfo oldData = houseDataService.getDataById(requestModel.getId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("HouseInfo with ID %s not found", requestModel.getId())
                ));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser applicationUser = (AppUser) userService.loadUserByUsername(
                (String) authentication.getPrincipal()
        );

        if (!oldData.getAppUser().equals(applicationUser)) {
            throw new NotAuthorized("No credentials.");
        }

        log.info("Preparing request for adding in database...");

        Map<String, byte[]> house_pictures = oldData.getHouse_pictures();
        List<String> ids = new ArrayList<>(house_pictures.keySet());
        List<String> idsList = new ArrayList<>(ids);
        java.util.Collections.sort(idsList);

        LinkedHashMap<String, byte[]> picturesHashMap = new LinkedHashMap<>();
        idsList.forEach(id -> picturesHashMap.put(id, house_pictures.get(id)));

        HouseInfo newData = getHouseInfoFromHouseData(requestModel.getIsTopic(), requestModel.getTitleRU(), requestModel.getTitleEN(),
                requestModel.getTitleAM(),
                requestModel.getBioRU(), requestModel.getBioEN(), requestModel.getBioAM(),
                requestModel.getProperty(), requestModel.getPaymentMethod(), requestModel.getCommercialType(),
                requestModel.getCity(), requestModel.getYerevanRegion(),
                requestModel.getStreetRU(), requestModel.getStreetEN(), requestModel.getStreetAM(),
                requestModel.getBuildingType(), requestModel.getNewBuilt(), requestModel.getArea(),
                requestModel.getElevator(), requestModel.getFloor(), requestModel.getFloorNumber(),
                requestModel.getRepairType(), requestModel.getRooms(), requestModel.getToilets(),
                requestModel.getAnimals(), requestModel.getBalcony(),
                requestModel.getPrice(), requestModel.getCurrencyType(), picturesHashMap);

        newData.setId(requestModel.getId());
        newData.setAppUser(applicationUser);

        houseDataService.updateHouseData(newData);


    }


    private HouseInfo getHouseInfoFromHouseData(YesNo isTopic, String titleRU, String titleEN, String titleAM,
                                                String bioRU, String bioEN, String bioAM,
                                                Property property, PaymentMethod paymentMethod,
                                                CommercialType commercialType, Cities city, YerevanRegions yerevanRegion,
                                                String streetRU, String streetEN, String streetAM,
                                                BuildingType buildingType, YesNo newBuilt, Double area,
                                                YesNo elevator, Integer floor, Integer floorNumber,
                                                RepairType repairType, Integer rooms, Integer toilets,
                                                Animals animals, Balcony balcony, Double price, CurrencyType currencyType,
                                                LinkedHashMap<String, byte[]> hashMap) {

        HouseInfo houseInfo = new HouseInfo();

        houseInfo.setIsTopic(isTopic);
        houseInfo.setTitleRU(titleRU);
        houseInfo.setTitleEN(titleEN);
        houseInfo.setTitleAM(titleAM);

        houseInfo.setBioRU(bioRU);
        houseInfo.setBioEN(bioEN);
        houseInfo.setBioAM(bioAM);

        houseInfo.setPaymentMethod(paymentMethod);
        if (paymentMethod.equals(PaymentMethod.Commercial)) {
            Optional.ofNullable(commercialType).orElseThrow(() -> {
                log.error("Type must be provided for Commercial.");
                throw new BadRequestException("Type must be provided for Commercial.");
            });
            houseInfo.setCommercialType(commercialType);
        }

        houseInfo.setCity(city);
        if (city.equals(Cities.Yerevan)) {
            Optional.ofNullable(yerevanRegion).orElseThrow(
                    () -> {
                        log.error("Region must be provided for Yerevan city.");
                        throw new BadRequestException("Region must be provided for Yerevan city.");
                    }
            );
            houseInfo.setYerevanRegion(yerevanRegion);
        }

        houseInfo.setStreetRU(streetRU);
        houseInfo.setStreetEN(streetEN);
        houseInfo.setStreetAM(streetAM);

        if (!paymentMethod.equals(PaymentMethod.Commercial)) {

            Optional.ofNullable(property).orElseThrow(
                    () -> {
                        log.error("Property must be provided for non commercial.");
                        throw new BadRequestException("Property must be provided for non commercial.");
                    }
            );
            houseInfo.setProperty(property);

            Optional.ofNullable(rooms).orElseThrow(
                    () -> {
                        log.error("Rooms must be provided for non commercial.");
                        throw new BadRequestException("Rooms must be provided for non commercial.");
                    }
            );
            houseInfo.setRooms(rooms);

            Optional.ofNullable(toilets).orElseThrow(
                    () -> {
                        log.error("Toilets must be provided for non commercial.");
                        throw new BadRequestException("Toilets must be provided for non commercial.");
                    }
            );
            houseInfo.setToilets(toilets);

            Optional.ofNullable(balcony).orElseThrow(
                    () -> {
                        log.error("Balcony must be provided for non commercial.");
                        throw new BadRequestException("Balcony must be provided for non commercial.");
                    }
            );
            houseInfo.setBalcony(balcony);

        }


        houseInfo.setRepairType(repairType);
        houseInfo.setArea(area);
        houseInfo.setFloor(floor);
        houseInfo.setFloorNumber(floorNumber);

        if (!paymentMethod.equals(PaymentMethod.Sale) && !paymentMethod.equals(PaymentMethod.Commercial)) {
            Optional.ofNullable(animals).orElseThrow(
                    () -> {
                        log.error("Animals field must be provided for non sale.");
                        throw new BadRequestException("Animals field must be provided for non sale.");
                    }
            );
            houseInfo.setAnimals(animals);
        }

        houseInfo.setPrice(price);

        Optional.ofNullable(currencyType).orElseThrow(
                () -> {
                    log.error("Currency field must be provided.");
                    throw new BadRequestException("Currency field must be provided.");
                }
        );
        houseInfo.setCurrencyType(currencyType);

        houseInfo.setHouse_pictures(hashMap);

        if (!paymentMethod.equals(PaymentMethod.Commercial)) {
            if (houseInfo.getProperty().equals(Property.Private)) {
                houseInfo.setBuildingType(null);
                houseInfo.setNewBuilt(null);
                houseInfo.setElevator(null);
                houseInfo.setFloor(null);
                houseInfo.setFloorNumber(null);

            } else {

                Optional.ofNullable(buildingType).orElseThrow(
                        () -> {
                            log.error("BuildingType must be provided for non private houses.");
                            throw new BadRequestException("BuildingType must be provided for non private houses.");
                        }
                );
                houseInfo.setBuildingType(buildingType);

                Optional.ofNullable(newBuilt).orElseThrow(
                        () -> {
                            log.error("NewBuilt must be provided for non private houses.");
                            throw new BadRequestException("NewBuilt must be provided for non private houses.");
                        }
                );
                houseInfo.setNewBuilt(newBuilt);

                Optional.ofNullable(elevator).orElseThrow(
                        () -> {
                            log.error("Elevator must be provided for non private houses.");
                            throw new BadRequestException("Elevator must be provided for non private houses.");
                        }
                );
                houseInfo.setElevator(elevator);
            }
        } else {

            Optional.ofNullable(newBuilt).orElseThrow(
                    () -> {
                        log.error("NewBuilt must be provided for non private houses.");
                        throw new BadRequestException("NewBuilt must be provided for non private houses.");
                    }
            );
            houseInfo.setNewBuilt(newBuilt);

            Optional.ofNullable(elevator).orElseThrow(
                    () -> {
                        log.error("Elevator must be provided for non private houses.");
                        throw new BadRequestException("Elevator must be provided for non private houses.");
                    }
            );
            houseInfo.setElevator(elevator);
        }

        return houseInfo;
    }


    @Operation(summary = "Deleting house info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404")
    })
    @DeleteMapping("/deleteDataById")
    @Caching(
            evict = {
                    @CacheEvict(value = "houseInfo", allEntries = true),
                    @CacheEvict(value = "houseInfos", allEntries = true)
            })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void deleteDataById(@Valid @RequestBody DeleteModel deleteModel) {

        log.info("Request has been obtained for deleting HouseData...");

        HouseInfo houseInfo = houseDataService.getDataById(deleteModel.getHouseId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("HouseInfo with ID %s not found", deleteModel.getHouseId().toString())
                ));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser applicationUser = (AppUser) userService.loadUserByUsername(
                (String) authentication.getPrincipal());

        if (houseInfo.getAppUser().equals(applicationUser)) {
            houseDataService.deleteDataById(houseInfo);
        } else {
            throw new NotAuthorized("No credentials.");
        }

    }

    @Operation(summary = "Get full house info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/getDataAdminById")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ResponseModelAdmin> getAdminDataById(@RequestParam("houseId") Long houseId){

        log.info(String.format("Request has been obtained for getting House Data %s.", houseId.toString()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser applicationUser = (AppUser) userService.loadUserByUsername(
                (String) authentication.getPrincipal()
        );

        List<HouseInfo> houseInfoList = houseDataService.getAllDataByUser(applicationUser);

        HouseInfo first =
                houseInfoList.stream().filter(houseInfo -> houseInfo.getId().longValue() == houseId.longValue()).findFirst()
                        .orElseThrow(()->new NotFoundException(String.format("House data with id %s not found for user %s.",
                                houseId, applicationUser.getUsername())));

        return ResponseEntity.ok(MyUtils.converter(first));

    }

    @Operation(summary = "Get full house info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/getAllDataAdmin")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<ResponseModelAdmin>> getAllData() {

        log.info("Request has been obtained for getting all House Data.");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser applicationUser = (AppUser) userService.loadUserByUsername(
                (String) authentication.getPrincipal()
        );

        List<HouseInfo> houseInfoList = houseDataService.getAllDataByUser(applicationUser);

        return ResponseEntity.ok(MyUtils.getResponseModelForAdmin(houseInfoList));

    }


    @Operation(summary = "Adding house info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/addHouseData")
    @Caching(evict = {
            @CacheEvict(value = "houseInfos", allEntries = true),
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String addHouseData(
            @RequestParam(value = "isTopic") YesNo isTopic,
            @RequestParam(value = "titleRU") @NonNull @NotEmpty @NotBlank String titleRU,
            @RequestParam(value = "titleEN") @NonNull @NotEmpty @NotBlank String titleEN,
            @RequestParam(value = "titleAM") @NonNull @NotEmpty @NotBlank String titleAM,
            @RequestParam(value = "bioRU") @NonNull @NotEmpty @NotBlank String bioRU,
            @RequestParam(value = "bioEN") @NonNull @NotEmpty @NotBlank String bioEN,
            @RequestParam(value = "bioAM") @NonNull @NotEmpty @NotBlank String bioAM,
            @RequestParam(value = "property", required = false) Property property,
            @RequestParam(value = "paymentMethod") PaymentMethod paymentMethod,
            @RequestParam(value = "commercialType", required = false) CommercialType commercialType,
            @RequestParam(value = "city") Cities city,
            @RequestParam(value = "yerevanRegion", required = false) YerevanRegions yerevanRegion,
            @RequestParam(value = "streetRU") @NonNull @NotEmpty @NotBlank String streetRU,
            @RequestParam(value = "streetEN") @NonNull @NotEmpty @NotBlank String streetEN,
            @RequestParam(value = "streetAM") @NonNull @NotEmpty @NotBlank String streetAM,
            @RequestParam(value = "buildingType", required = false) BuildingType buildingType,
            @RequestParam(value = "newBuilt", required = false) YesNo newBuilt,
            @RequestParam(value = "area") @Min(0) Double area,
            @RequestParam(value = "elevator", required = false) YesNo elevator,
            @RequestParam(value = "floor", required = false) @Min(1) Integer floor,
            @RequestParam(value = "floorNumber", required = false) @Min(1) Integer floorNumber,
            @RequestParam(value = "repairType") RepairType repairType,
            @RequestParam(value = "rooms", required = false) Integer rooms,
            @RequestParam(value = "toilets", required = false) Integer toilets,
            @RequestParam(value = "animals", required = false) Animals animals,
            @RequestParam(value = "balcony", required = false) Balcony balcony,
            @RequestParam(value = "price") @Min(1) Double price,
            @RequestParam(value = "currencyType") CurrencyType currencyType,
            @RequestParam(value = "mainPicture") MultipartFile mainPicture,
            @RequestParam(value = "pictures", required = false) MultipartFile[] pictures) {

        log.info("Request has been obtained...");

        LinkedHashMap<String, byte[]> picturesHashMap = new LinkedHashMap<>();

        int counter = 0;
        try {
            String mainHousePicture = String.format(
                    "%d.%s", counter, FilenameUtils.getExtension(
                            mainPicture.getOriginalFilename()));
            picturesHashMap.put(mainHousePicture, mainPicture.getBytes());

            counter++;

        } catch (IOException e) {
            String error = String.format("Can not set main picture. Reason: %s", e.getMessage());
            log.error(error);
            throw new BadRequestException(error);
        }

        for (MultipartFile multipartFile :
                pictures) {

            try {
                String housePicturesId = String.format(
                        "%d.%s", counter, FilenameUtils.getExtension(multipartFile.getOriginalFilename()));

                picturesHashMap.put(housePicturesId, multipartFile.getBytes());

                counter++;

            } catch (IOException e) {
                String error = String.format("An error has been occurred while setting " +
                        "optional pictures. Reason: %s", e.getMessage());
                log.error(error);
                throw new BadRequestException(error);
            }
        }

        log.info("Preparing request for adding in database...");
        HouseInfo data = getHouseInfoFromHouseData(isTopic, titleRU, titleEN, titleAM,
                bioRU, bioEN, bioAM, property, paymentMethod, commercialType, city, yerevanRegion,
                streetRU, streetEN, streetAM, buildingType, newBuilt, area, elevator, floor, floorNumber,
                repairType, rooms, toilets, animals, balcony,
                price, currencyType, picturesHashMap);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AppUser applicationUser = (AppUser) userService.loadUserByUsername(
                (String) authentication.getPrincipal());

        data.setAppUser(applicationUser);

        houseDataService.addData(data);
        log.info("House data has been successfully added to database.");

        return "ok";
    }

    @Operation(summary = "Updating house info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/updateHouseData")
    @Caching(evict = {
            @CacheEvict(value = "houseInfos", allEntries = true)
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> updateHouseData(
            @RequestParam(value = "houseId") Long houseId,
            @RequestParam(value = "isTopic") YesNo isTopic,
            @RequestParam(value = "titleRU") @NonNull @NotEmpty @NotBlank String titleRU,
            @RequestParam(value = "titleEN") @NonNull @NotEmpty @NotBlank String titleEN,
            @RequestParam(value = "titleAM") @NonNull @NotEmpty @NotBlank String titleAM,
            @RequestParam(value = "bioRU") @NonNull @NotEmpty @NotBlank String bioRU,
            @RequestParam(value = "bioEN") @NonNull @NotEmpty @NotBlank String bioEN,
            @RequestParam(value = "bioAM") @NonNull @NotEmpty @NotBlank String bioAM,
            @RequestParam(value = "property", required = false) Property property,
            @RequestParam(value = "paymentMethod") PaymentMethod paymentMethod,
            @RequestParam(value = "commercialType", required = false) CommercialType commercialType,
            @RequestParam(value = "city") Cities city,
            @RequestParam(value = "yerevanRegion", required = false) YerevanRegions yerevanRegion,
            @RequestParam(value = "streetRU") @NonNull @NotEmpty @NotBlank String streetRU,
            @RequestParam(value = "streetEN") @NonNull @NotEmpty @NotBlank String streetEN,
            @RequestParam(value = "streetAM") @NonNull @NotEmpty @NotBlank String streetAM,
            @RequestParam(value = "buildingType", required = false) BuildingType buildingType,
            @RequestParam(value = "newBuilt", required = false) YesNo newBuilt,
            @RequestParam(value = "area") @Min(0) Double area,
            @RequestParam(value = "elevator", required = false) YesNo elevator,
            @RequestParam(value = "floor", required = false) @Min(1) Integer floor,
            @RequestParam(value = "floorNumber", required = false) @Min(1) Integer floorNumber,
            @RequestParam(value = "repairType") RepairType repairType,
            @RequestParam(value = "rooms", required = false) Integer rooms,
            @RequestParam(value = "toilets", required = false) Integer toilets,
            @RequestParam(value = "animals", required = false) Animals animals,
            @RequestParam(value = "balcony", required = false) Balcony balcony,
            @RequestParam(value = "price") @Min(1) Double price,
            @RequestParam(value = "currencyType") CurrencyType currencyType) {

        log.info("Request has been obtained...");

        HouseInfo oldData = houseDataService.getDataById(houseId).orElseThrow(
                () -> new NotFoundException(
                        String.format("HouseInfo with ID %s not found", houseId)
                ));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser applicationUser = (AppUser) userService.loadUserByUsername(
                (String) authentication.getPrincipal()
        );

        if (!oldData.getAppUser().equals(applicationUser)) {
            throw new NotAuthorized("No credentials.");
        }

        log.info("Preparing request for adding in database...");

        Map<String, byte[]> house_pictures = oldData.getHouse_pictures();
        List<String> ids = new ArrayList<>(house_pictures.keySet());
        List<String> idsList = new ArrayList<>(ids);
        java.util.Collections.sort(idsList);

        LinkedHashMap<String, byte[]> picturesHashMap = new LinkedHashMap<>();
        idsList.forEach(id -> picturesHashMap.put(id, house_pictures.get(id)));

        HouseInfo newData = getHouseInfoFromHouseData(isTopic, titleRU, titleEN, titleAM,
                bioRU, bioEN, bioAM, property, paymentMethod, commercialType, city, yerevanRegion,
                streetRU, streetEN, streetAM, buildingType, newBuilt, area, elevator, floor, floorNumber,
                repairType, rooms, toilets, animals, balcony,
                price, currencyType, picturesHashMap);

        newData.setId(houseId);
        newData.setAppUser(applicationUser);

        houseDataService.updateHouseData(newData);

        return ResponseEntity.ok().build();

    }




}
