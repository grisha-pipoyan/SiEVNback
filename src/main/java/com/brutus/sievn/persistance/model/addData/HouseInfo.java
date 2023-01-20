package com.brutus.sievn.persistance.model.addData;

import com.brutus.sievn.persistance.model.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "houses")
public class HouseInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private YesNo isTopic;

    private String titleRU;

    private String titleEN;

    private String titleAM;

    @Lob
    private String bioRU;

    @Lob
    private String bioEN;

    @Lob
    private String bioAM;

    @Enumerated(EnumType.STRING)
    private Property property;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private CommercialType commercialType;

    @Enumerated(EnumType.STRING)
    private Cities city;

    @Enumerated(EnumType.STRING)
    private YerevanRegions yerevanRegion;

    private String streetRU;

    private String streetEN;

    private String streetAM;

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    @Enumerated(EnumType.STRING)
    private YesNo newBuilt;

    private Double area;

    @Enumerated(EnumType.STRING)
    private YesNo elevator;

    private Integer floor;

    private Integer floorNumber;

    @Enumerated(EnumType.STRING)
    private RepairType repairType;

    private Integer rooms;

    private Integer toilets;

    @Enumerated(EnumType.STRING)
    private Animals animals;

    @Enumerated(EnumType.STRING)
    private Balcony balcony;

    private Double price;

    private CurrencyType currencyType;

//    @ElementCollection
//    @CollectionTable(name = "house_pictures_ids", joinColumns = @JoinColumn(name = "house_id"))
//    @Column(name = "pictures_ids")
//    private Set<String> housePictursIds = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "house_pictures",
            joinColumns = {
            @JoinColumn(name = "house_id", referencedColumnName = "Id")
    })
    @MapKeyColumn(name = "image_name")
    @Column(name = "image_bytes")
    private Map<String, byte[]> house_pictures = new LinkedHashMap<>();

    @ManyToOne
    private AppUser appUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HouseInfo houseInfo = (HouseInfo) o;
        return id != null && Objects.equals(id, houseInfo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
