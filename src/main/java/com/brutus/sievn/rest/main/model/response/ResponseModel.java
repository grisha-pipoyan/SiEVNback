package com.brutus.sievn.rest.main.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {

    private Long houseId;

    private String isTopic;

    private String title;

    private String bio;

    private String property;

    private String paymentMethod;

    private String commercialType;

    private String city;

    private String yerevanRegion;

    private String street;

    private String buildingType;

    private String newBuilt;

    private Double area;

    private String elevator;

    private Integer floor;

    private Integer floorNumber;

    private String repairType;

    private Integer rooms;

    private Integer toilets;

    private String animals;

    private String balcony;

    private Double price;

    private String currencyType;

    private List<String> pictures;

}
