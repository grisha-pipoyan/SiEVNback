package com.brutus.sievn.rest.data.model.request;

import com.brutus.sievn.persistance.model.addData.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestModelUpdate {

    private Long id;

    private YesNo isTopic;

    private String titleRU;

    private String titleEN;

    private String titleAM;

    private String bioRU;

    private String bioEN;

    private String bioAM;

    private Property property;

    private PaymentMethod paymentMethod;

    private CommercialType commercialType;

    private Cities city;

    private YerevanRegions yerevanRegion;

    private String streetRU;

    private String streetEN;

    private String streetAM;

    private BuildingType buildingType;

    private YesNo newBuilt;

    private Double area;

    private YesNo elevator;

    private Integer floor;

    private Integer floorNumber;

    private RepairType repairType;

    private Integer rooms;

    private Integer toilets;

    private Animals animals;

    private Balcony balcony;

    private Double price;

    private CurrencyType currencyType;


}
