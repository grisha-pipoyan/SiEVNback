package com.brutus.sievn.rest.data.model.request;

import com.brutus.sievn.persistance.model.addData.*;

import java.util.List;

public class RequestModel {

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

    private String mainPicture;

    private List<String> pictures;

    public YesNo getIsTopic() {
        return isTopic;
    }

    public void setIsTopic(YesNo isTopic) {
        this.isTopic = isTopic;
    }

    public String getTitleRU() {
        return titleRU;
    }

    public void setTitleRU(String titleRU) {
        this.titleRU = titleRU;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTitleAM() {
        return titleAM;
    }

    public void setTitleAM(String titleAM) {
        this.titleAM = titleAM;
    }

    public String getBioRU() {
        return bioRU;
    }

    public void setBioRU(String bioRU) {
        this.bioRU = bioRU;
    }

    public String getBioEN() {
        return bioEN;
    }

    public void setBioEN(String bioEN) {
        this.bioEN = bioEN;
    }

    public String getBioAM() {
        return bioAM;
    }

    public void setBioAM(String bioAM) {
        this.bioAM = bioAM;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CommercialType getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(CommercialType commercialType) {
        this.commercialType = commercialType;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public YerevanRegions getYerevanRegion() {
        return yerevanRegion;
    }

    public void setYerevanRegion(YerevanRegions yerevanRegion) {
        this.yerevanRegion = yerevanRegion;
    }

    public String getStreetRU() {
        return streetRU;
    }

    public void setStreetRU(String streetRU) {
        this.streetRU = streetRU;
    }

    public String getStreetEN() {
        return streetEN;
    }

    public void setStreetEN(String streetEN) {
        this.streetEN = streetEN;
    }

    public String getStreetAM() {
        return streetAM;
    }

    public void setStreetAM(String streetAM) {
        this.streetAM = streetAM;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public YesNo getNewBuilt() {
        return newBuilt;
    }

    public void setNewBuilt(YesNo newBuilt) {
        this.newBuilt = newBuilt;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public YesNo getElevator() {
        return elevator;
    }

    public void setElevator(YesNo elevator) {
        this.elevator = elevator;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public RepairType getRepairType() {
        return repairType;
    }

    public void setRepairType(RepairType repairType) {
        this.repairType = repairType;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getToilets() {
        return toilets;
    }

    public void setToilets(Integer toilets) {
        this.toilets = toilets;
    }

    public Animals getAnimals() {
        return animals;
    }

    public void setAnimals(Animals animals) {
        this.animals = animals;
    }

    public Balcony getBalcony() {
        return balcony;
    }

    public void setBalcony(Balcony balcony) {
        this.balcony = balcony;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}
