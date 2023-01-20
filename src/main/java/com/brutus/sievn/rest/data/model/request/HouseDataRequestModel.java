package com.brutus.sievn.rest.data.model.request;

import com.brutus.sievn.persistance.model.addData.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseDataRequestModel {

    @NonNull
    private YesNo isTopic;

    @NonNull
    private String titleRU;

    @NonNull
    private String titleEN;

    @NonNull
    private String titleAM;

    @NonNull
    private String bioRU;

    @NonNull
    private String bioEN;

    @NonNull
    private String bioAM;

    @NonNull
    private PaymentMethod paymentMethod;

    @Nullable
    private CommercialType commercialType;

    @NonNull
    private Cities city;

    @Nullable
    private YerevanRegions yerevanRegion;

    @NonNull
    private String streetRU;

    @NonNull
    private String streetEN;

    @NonNull
    private String streetAM;

    @NonNull
    private BuildingType buildingType;

    @NonNull
    private YesNo newBuilt;

    @NonNull
    @Min(value = 0)
    private Double area;

    @NonNull
    private YesNo elevator;

    @NonNull
    @Min(value = 1)
    private Integer floor;

    @NonNull
    @Min(value = 1)
    private Integer floorNumber;

    @NonNull
    private RepairType repairType;

    @NonNull
    @Min(value = 1)
    private Integer rooms;

    @NonNull
    @Min(value = 1)
    private Integer toilets;

    @Nullable
    private Animals animals;

    @NonNull
    private Balcony balcony;

    @NonNull
    @Min(value = 0)
    private Double price;

    @NonNull
    private CurrencyType currencyType;

    @NonNull
    private ArrayList<MultipartFile> pictures;

    @NonNull
    private String token;

}
