package com.brutus.sievn.rest.main;

import com.brutus.sievn.enums.Language;
import com.brutus.sievn.exception.NotFoundException;
import com.brutus.sievn.persistance.model.addData.*;
import com.brutus.sievn.rest.main.model.response.ResponseModel;
import com.brutus.sievn.services.HouseDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

import static com.brutus.sievn.MyUtils.*;

@RestController
@RequestMapping("/sievn/public")
@CrossOrigin("*")
@Validated
public class MainController {

    private final HouseDataService houseDataService;

    public MainController(HouseDataService houseDataService) {
        this.houseDataService = houseDataService;
    }


    @Operation(summary = "Get all house infos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/getAllData")
    @Cacheable("houseInfos")
    public ResponseEntity<?> getAllData(@RequestParam("language") Language language) {

        List<HouseInfo> houseInfoList = houseDataService.getAllData();

        return ResponseEntity.ok(getResponseModelListByLanguage(houseInfoList, language));

    }

    @Operation(summary = "Get house info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    @GetMapping("/getDataById")
    @Cacheable(value = "houseInfo")
    public ResponseEntity<?> getDataById(@RequestParam("houseId") Long houseId,
                                         @RequestParam("language") Language language) {

        Optional<HouseInfo> houseInfo = houseDataService.getDataById(houseId);
        ResponseModel responseModel = new ResponseModel();

        if (houseInfo.isEmpty()) {
            throw new NotFoundException(String.format("HouseInfo with ID %s not found",houseId));
        }

        switch (language) {
            case ru:
                responseModel = getResponseModelRussian(houseInfo.get());
                break;
            case en:
                responseModel = getResponseModelEnglish(houseInfo.get());
                break;
            case am:
                responseModel = getResponseModelArmenian(houseInfo.get());
                break;
        }

        return ResponseEntity.ok(responseModel);
    }


    @Operation(summary = "Get filtered house infos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredData(
            @RequestParam(value = "property", required = false) Property property,
            @RequestParam(value = "paymentMethod", required = false) PaymentMethod paymentMethod,
            @RequestParam(value = "commercialType", required = false) CommercialType commercialType,
            @RequestParam(value = "city", required = false) Cities city,
            @RequestParam(value = "yerevanRegion", required = false) YerevanRegions yerevanRegion,
            @RequestParam(value = "buildingType", required = false) BuildingType buildingType,
            @RequestParam(value = "newBuilt", required = false) YesNo newBuilt,
            @RequestParam(value = "minArea", required = false) @Min(value = 0) Double minArea,
            @RequestParam(value = "maxArea", required = false) @Min(value = 0) Double maxArea,
            @RequestParam(value = "repairType", required = false) RepairType repairType,
            @RequestParam(value = "rooms", required = false)  Integer rooms,
            @RequestParam(value = "toilets", required = false) Integer toilets,
            @RequestParam(value = "animals", required = false) Animals animals,
            @RequestParam(value = "balcony", required = false) Balcony balcony,
            @RequestParam(value = "minPrice", required = false) @Min(value = 0) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) @Min(value = 0) Double maxPrice,
            @RequestParam("language") Language language) {

        List<HouseInfo> houseInfoList = houseDataService.filter(
                paymentMethod, commercialType,
                city, yerevanRegion, buildingType, newBuilt,
                minArea, maxArea, repairType,
                rooms, toilets,
                animals, balcony, minPrice, maxPrice,property);

        return ResponseEntity.ok(getResponseModelListByLanguage(houseInfoList,language));
    }

}
