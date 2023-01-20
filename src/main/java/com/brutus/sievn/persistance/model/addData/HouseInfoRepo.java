package com.brutus.sievn.persistance.model.addData;

import com.brutus.sievn.persistance.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
@Repository
public interface HouseInfoRepo extends JpaRepository<HouseInfo,Long> {

    List<HouseInfo> findHouseInfosByIsTopicEquals(YesNo yesNo);

    List<HouseInfo> findAllByOrderByIdAsc();

    List<HouseInfo> findAllByOrderByIdDesc();

    List<HouseInfo> findAllByAppUser(AppUser appUser);


    @Query("SELECT h FROM HouseInfo h where " +
            "(h.paymentMethod = ?1 or ?1 is null ) " +
            "and (h.commercialType=?2 or ?2 is null ) "+
            "and (h.city = ?3 or ?3 is null ) " +
            "and (h.yerevanRegion = ?4 or ?4 is null )" +
            "and (h.buildingType = ?5 or ?5 is null ) " +
            "and (h.newBuilt = ?6 or ?6 is null ) " +
            "and (h.area>=?7 and h.area<=?8)" +
            "and (h.repairType = ?9 or ?9 is null ) " +
            "and (h.rooms = ?10 or ?10 is null) " +
            "and (h.toilets = ?11 or ?11 is null) " +
            "and (h.animals = ?12 or ?12 is null ) " +
            "and (h.balcony = ?13 or ?13 is null )" +
            "and (h.price>=?14 and h.price<=?15)" +
            "and (h.property = ?16 or ?16 is null) ")
    List<HouseInfo> findHouseInfosByFilter(PaymentMethod paymentMethod, CommercialType commercialType,
                                           Cities city, YerevanRegions yerevanRegion,
                                           BuildingType buildingType, YesNo newBuilt,
                                           Double minArea, Double maxArea,
                                           RepairType repairType,
                                           Integer roomsQuantity, Integer toiletQuantity,
                                           Animals withAnimals, Balcony withBalcony,
                                           Double minPrice, Double maxPrice, Property property);

}
