package com.brutus.sievn.services;

import com.brutus.sievn.enums.Language;
import com.brutus.sievn.persistance.model.addData.*;
import com.brutus.sievn.persistance.model.user.AppUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HouseDataService {

    private final HouseInfoRepo houseInfoRepo;

    public HouseDataService(HouseInfoRepo houseInfoRepo) {
        this.houseInfoRepo = houseInfoRepo;
    }

    public void addData(HouseInfo data) {
        houseInfoRepo.saveAndFlush(data);
    }

    public <S extends HouseInfo> void addData(Iterable<S> data) {
        houseInfoRepo.saveAll(data);
    }

    public void deleteDataById(HouseInfo entity) {
        houseInfoRepo.delete(entity);
    }

    public Optional<HouseInfo> getDataById(Long id) {
        return houseInfoRepo.findById(id);
    }

    public List<HouseInfo> getAllData() {
        return houseInfoRepo.findAllByOrderByIdDesc();
    }

    public List<HouseInfo> getAllDataByUser(AppUser appUser) {
        return houseInfoRepo.findAllByAppUser(appUser);
    }

    public List<HouseInfo> filter(PaymentMethod paymentMethod, CommercialType commercialType,
                                  Cities city, YerevanRegions yerevanRegions,
                                  BuildingType buildingType, YesNo newBuilt,
                                  Double minArea, Double maxArea,
                                  RepairType repairType, Integer rooms, Integer toilets,
                                  Animals animals, Balcony balcony,
                                  Double minPrice, Double maxPrice, Property property) {


        if(rooms!=null){
            if(rooms == 0){
                rooms = null;
            }
        }

        if(toilets!=null){
            if(toilets == 0){
                toilets = null;
            }
        }

        if (minArea == null) minArea = (double) 0;
        if (maxArea == null) maxArea = Double.MAX_VALUE;

        if (minPrice == null) minPrice = (double) 0;
        if (maxPrice == null) maxPrice = Double.MAX_VALUE;

//        if (rooms == null || rooms > 8) rooms = 100;
//        if (toilets == null || toilets > 8) toilets = 100;

        if(property!=null){
            if(property.equals(Property.Private)){
                return houseInfoRepo.findHouseInfosByFilter(
                        paymentMethod, commercialType, city,
                        yerevanRegions, null, null,
                        minArea, maxArea, repairType, rooms,
                        toilets, animals, balcony, minPrice,
                        maxPrice, property);
            }
        }

        return houseInfoRepo.findHouseInfosByFilter(
                paymentMethod, commercialType, city, yerevanRegions, buildingType, newBuilt,
                minArea, maxArea, repairType, rooms, toilets, animals, balcony, minPrice, maxPrice, property);

    }


    public List<HouseInfo> searchByParameter(String parameter, Language language) {

        List<HouseInfo> infoRepoAll = houseInfoRepo.findAll();
        Set<HouseInfo> houseInfoList = new HashSet<>();

        String[] strArray = parameter.split(" ");

        switch (language) {
            case ru: {

                for (HouseInfo houseInfo :
                        infoRepoAll) {

                    for (String s : strArray) {
                        if (houseInfo.getBioRU().contains(s)) {
                            houseInfoList.add(houseInfo);
                        }
                    }
                }
                break;
            }
            case en: {
                for (HouseInfo houseInfo :
                        infoRepoAll) {
                    for (String s : strArray) {
                        if (houseInfo.getBioEN().contains(s)) {
                            houseInfoList.add(houseInfo);
                        }
                    }
                }
                break;
            }
            case am: {
                for (HouseInfo houseInfo :
                        infoRepoAll) {
                    for (String s : strArray) {
                        if (houseInfo.getBioAM().contains(s)) {
                            houseInfoList.add(houseInfo);
                        }
                    }
                }
                break;
            }
        }

        return new ArrayList<>(houseInfoList);
    }

    public void updateHouseData(HouseInfo newHouseData) {
        houseInfoRepo.save(newHouseData);
    }


}
