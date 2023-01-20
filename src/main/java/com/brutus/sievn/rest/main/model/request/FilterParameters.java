package com.brutus.sievn.rest.main.model.request;

import com.brutus.sievn.enums.Language;
import com.brutus.sievn.persistance.model.addData.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterParameters {

    @Nullable
    private PaymentMethod paymentMethod;

    @Nullable
    private CommercialType commercialType;

    @Nullable
    private Cities region;

    @Nullable
    private YerevanRegions inYerevan;

    @Nullable
    private BuildingType buildingType;

    @Nullable
    private YesNo newBuilt;

    @Min(value = 0)
    private Double minArea;

    @Min(value = 0)
    private Double maxArea;

    @Nullable
    private RepairType repairType;

    @Min(value = 1)
    private Integer rooms;

    @Min(value = 1)
    private Integer toilets;

    @Nullable
    private Animals animals;

    @Nullable
    private Balcony balcony;

    @Min(value = 0)
    private Double minPrice;

    @Min(value = 0)
    private Double maxPrice;

    @NonNull
    private Language language;


}
