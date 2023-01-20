package com.brutus.sievn;

import com.brutus.sievn.enums.Language;
import com.brutus.sievn.persistance.model.addData.HouseInfo;
import com.brutus.sievn.rest.data.model.response.ResponseModelAdmin;
import com.brutus.sievn.rest.main.model.response.ResponseModel;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyUtils {


    public static byte[] resizeImage(byte[] imageBytes) throws IOException {

        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

        if(imageBytes.length<=1000000){
            param.setCompressionQuality(0.7f);
        }else if(imageBytes.length>1000000 && imageBytes.length <= 2000000){
            param.setCompressionQuality(0.5f);
        }else if(imageBytes.length>2000000 && imageBytes.length <= 3000000){
            param.setCompressionQuality(0.4f);
        }else if(imageBytes.length>3000000 && imageBytes.length <= 4000000) {
            param.setCompressionQuality(0.3f);
        }else{
            param.setCompressionQuality(0.1f);
        }

        // Change the quality value you prefer
        writer.write(null, new IIOImage(originalImage, null, null), param);

        out.close();
        ios.close();
        writer.dispose();

        return out.toByteArray();
    }

    public static BufferedImage convertToBufferedImage(Image img) {

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bi;
    }

    public static List<ResponseModelAdmin> getResponseModelForAdmin(List<HouseInfo> houseInfoList){

        List<ResponseModelAdmin> responseModels = new ArrayList<>();
        for (HouseInfo houseInfo :
                houseInfoList) {
            responseModels.add(converter(houseInfo));
        }

        return responseModels;
    }

    public static ResponseModelAdmin converter(HouseInfo houseInfo){

        ResponseModelAdmin responseModel = new ResponseModelAdmin();

        responseModel.setHouseId(houseInfo.getId());
        responseModel.setIsTopic(houseInfo.getIsTopic().getRussian());
        responseModel.setTitleRU(houseInfo.getTitleRU());
        responseModel.setTitleEN(houseInfo.getTitleEN());
        responseModel.setTitleAM(houseInfo.getTitleAM());
        responseModel.setBioRU(houseInfo.getBioRU());
        responseModel.setBioEN(houseInfo.getBioEN());
        responseModel.setBioAM(houseInfo.getBioAM());

        if(houseInfo.getProperty()!=null){
            responseModel.setProperty(houseInfo.getProperty().getRussian());
        }

        responseModel.setPaymentMethod(houseInfo.getPaymentMethod().getRussian());

        if (houseInfo.getCommercialType() != null) {
            responseModel.setCommercialType(houseInfo.getCommercialType().getRussian());
        }

        responseModel.setCity(houseInfo.getCity().getRussian());

        if (houseInfo.getYerevanRegion() != null) {
            responseModel.setYerevanRegion(houseInfo.getYerevanRegion().getRussian());
        }

        responseModel.setStreetRU(houseInfo.getStreetRU());
        responseModel.setStreetEN(houseInfo.getStreetEN());
        responseModel.setStreetAM(houseInfo.getStreetAM());

        if(houseInfo.getBuildingType()!=null){
            responseModel.setBuildingType(houseInfo.getBuildingType().getRussian());
        }

        if(houseInfo.getNewBuilt()!=null){
            responseModel.setNewBuilt(houseInfo.getNewBuilt().getRussian());
        }

        responseModel.setArea(houseInfo.getArea());

        if(houseInfo.getElevator()!=null){
            responseModel.setElevator(houseInfo.getElevator().getRussian());
        }

        responseModel.setFloor(houseInfo.getFloor());
        responseModel.setFloorNumber(houseInfo.getFloorNumber());
        responseModel.setRepairType(houseInfo.getRepairType().getRussian());

        if(houseInfo.getRooms()!=null){
            responseModel.setRooms(houseInfo.getRooms());
        }
        if(houseInfo.getToilets()!=null){
            responseModel.setToilets(houseInfo.getToilets());
        }

        if (houseInfo.getAnimals() != null) {
            responseModel.setAnimals(houseInfo.getAnimals().getRussian());
        }

        if(houseInfo.getBalcony()!=null){
            responseModel.setBalcony(houseInfo.getBalcony().getRussian());
        }

        responseModel.setPrice(houseInfo.getPrice());
        responseModel.setCurrencyType(houseInfo.getCurrencyType().name());

        List<String> ids = new ArrayList<>(houseInfo.getHouse_pictures().keySet());
        List<String> idsList = new ArrayList<>(ids);
        java.util.Collections.sort(idsList);
        responseModel.setPictures(idsList);

        return responseModel;
    }

    public static List<ResponseModel> getResponseModelListByLanguage(List<HouseInfo> houseInfoList,
                                                                     Language language) {

        List<ResponseModel> responseModels = new ArrayList<>();

        switch (language) {
            case ru: {
                for (HouseInfo houseInfo :
                        houseInfoList) {
                    responseModels.add(getResponseModelRussian(houseInfo));
                }
                break;
            }
            case en: {
                for (HouseInfo houseInfo :
                        houseInfoList) {
                    responseModels.add(getResponseModelEnglish(houseInfo));
                }
                break;
            }
            case am: {
                for (HouseInfo houseInfo :
                        houseInfoList) {
                    responseModels.add(getResponseModelArmenian(houseInfo));
                }
                break;
            }
        }

        return responseModels;
    }

    public static ResponseModel getResponseModelRussian(HouseInfo houseInfo) {

        ResponseModel responseModel = new ResponseModel();
        responseModel.setHouseId(houseInfo.getId());
        responseModel.setIsTopic(houseInfo.getIsTopic().getRussian());
        responseModel.setTitle(houseInfo.getTitleRU());
        responseModel.setBio(houseInfo.getBioRU());

        if(houseInfo.getProperty()!=null){
            responseModel.setProperty(houseInfo.getProperty().getRussian());
        }

        responseModel.setPaymentMethod(houseInfo.getPaymentMethod().getRussian());

        if (houseInfo.getCommercialType() != null) {
            responseModel.setCommercialType(houseInfo.getCommercialType().getRussian());
        }

        responseModel.setCity(houseInfo.getCity().getRussian());

        if (houseInfo.getYerevanRegion() != null) {
            responseModel.setYerevanRegion(houseInfo.getYerevanRegion().getRussian());
        }

        responseModel.setStreet(houseInfo.getStreetRU());

        if(houseInfo.getBuildingType()!=null){
            responseModel.setBuildingType(houseInfo.getBuildingType().getRussian());
        }

        if(houseInfo.getNewBuilt()!=null){
            responseModel.setNewBuilt(houseInfo.getNewBuilt().getRussian());
        }

        responseModel.setArea(houseInfo.getArea());

        if(houseInfo.getElevator()!=null){
            responseModel.setElevator(houseInfo.getElevator().getRussian());
        }

        responseModel.setFloor(houseInfo.getFloor());
        responseModel.setFloorNumber(houseInfo.getFloorNumber());
        responseModel.setRepairType(houseInfo.getRepairType().getRussian());

        if(houseInfo.getRooms()!=null){
            responseModel.setRooms(houseInfo.getRooms());
        }

        if(houseInfo.getToilets()!=null){
            responseModel.setToilets(houseInfo.getToilets());
        }

        if (houseInfo.getAnimals() != null) {
            responseModel.setAnimals(houseInfo.getAnimals().getRussian());
        }

        if(houseInfo.getBalcony()!=null){
            responseModel.setBalcony(houseInfo.getBalcony().getRussian());
        }

        responseModel.setPrice(houseInfo.getPrice());
        responseModel.setCurrencyType(houseInfo.getCurrencyType().name());

        List<String> ids = new ArrayList<>(houseInfo.getHouse_pictures().keySet());
        List<String> idsList = new ArrayList<>(ids);
        java.util.Collections.sort(idsList);
        responseModel.setPictures(idsList);

        return responseModel;
    }

    public static ResponseModel getResponseModelEnglish(HouseInfo houseInfo) {

        ResponseModel responseModel = new ResponseModel();
        responseModel.setHouseId(houseInfo.getId());
        responseModel.setIsTopic(houseInfo.getIsTopic().getEnglish());
        responseModel.setTitle(houseInfo.getTitleEN());
        responseModel.setStreet(houseInfo.getStreetEN());

        if(houseInfo.getProperty()!=null){
            responseModel.setProperty(houseInfo.getProperty().getEnglish());
        }

        responseModel.setPaymentMethod(houseInfo.getPaymentMethod().getEnglish());

        if (houseInfo.getCommercialType() != null) {
            responseModel.setCommercialType(houseInfo.getCommercialType().getEnglish());
        }

        responseModel.setCity(houseInfo.getCity().getEnglish());

        if (houseInfo.getYerevanRegion() != null) {
            responseModel.setYerevanRegion(houseInfo.getYerevanRegion().getEnglish());
        }

        responseModel.setBio(houseInfo.getBioEN());

        if(houseInfo.getBuildingType()!=null){
            responseModel.setBuildingType(houseInfo.getBuildingType().getEnglish());
        }

        if(houseInfo.getNewBuilt()!=null){
            responseModel.setNewBuilt(houseInfo.getNewBuilt().getEnglish());
        }

        responseModel.setArea(houseInfo.getArea());

        if(houseInfo.getElevator()!=null){
            responseModel.setElevator(houseInfo.getElevator().getEnglish());
        }

        responseModel.setFloor(houseInfo.getFloor());
        responseModel.setFloorNumber(houseInfo.getFloorNumber());
        responseModel.setRepairType(houseInfo.getRepairType().getEnglish());

        if(houseInfo.getRooms()!=null){
            responseModel.setRooms(houseInfo.getRooms());
        }

        if(houseInfo.getToilets()!=null){
            responseModel.setToilets(houseInfo.getToilets());
        }

        if (houseInfo.getAnimals() != null) {
            responseModel.setAnimals(houseInfo.getAnimals().getEnglish());
        }

        if(houseInfo.getBalcony()!=null){
            responseModel.setBalcony(houseInfo.getBalcony().getEnglish());
        }

        responseModel.setPrice(houseInfo.getPrice());
        responseModel.setCurrencyType(houseInfo.getCurrencyType().name());

        List<String> ids = new ArrayList<>(houseInfo.getHouse_pictures().keySet());
        List<String> idsList = new ArrayList<>(ids);
        java.util.Collections.sort(idsList);
        responseModel.setPictures(idsList);

        return responseModel;
    }

    public static ResponseModel getResponseModelArmenian(HouseInfo houseInfo) {

        ResponseModel responseModel = new ResponseModel();
        responseModel.setHouseId(houseInfo.getId());
        responseModel.setIsTopic(houseInfo.getIsTopic().getUri());
        responseModel.setTitle(houseInfo.getTitleAM());
        responseModel.setBio(houseInfo.getBioAM());

        if(houseInfo.getProperty()!=null){
            responseModel.setProperty(houseInfo.getProperty().getUri());
        }

        responseModel.setPaymentMethod(houseInfo.getPaymentMethod().getUri());

        if (houseInfo.getCommercialType() != null) {
            responseModel.setCommercialType(houseInfo.getCommercialType().getUri());
        }

        responseModel.setCity(houseInfo.getCity().getUri());

        if (houseInfo.getYerevanRegion() != null) {
            responseModel.setYerevanRegion(houseInfo.getYerevanRegion().getUri());
        }

        responseModel.setStreet(houseInfo.getStreetAM());

        if(houseInfo.getBuildingType()!=null){
            responseModel.setBuildingType(houseInfo.getBuildingType().getUri());
        }

        if(houseInfo.getNewBuilt()!=null) {
            responseModel.setNewBuilt(houseInfo.getNewBuilt().getUri());
        }

        responseModel.setArea(houseInfo.getArea());

        if(houseInfo.getElevator()!=null){
            responseModel.setElevator(houseInfo.getElevator().getUri());
        }

        responseModel.setFloor(houseInfo.getFloor());
        responseModel.setFloorNumber(houseInfo.getFloorNumber());
        responseModel.setRepairType(houseInfo.getRepairType().getUri());

        if(houseInfo.getRooms()!=null){
            responseModel.setRooms(houseInfo.getRooms());
        }
        if(houseInfo.getToilets()!=null){
            responseModel.setToilets(houseInfo.getToilets());
        }

        if (houseInfo.getAnimals() != null) {
            responseModel.setAnimals(houseInfo.getAnimals().getUri());
        }

        if(houseInfo.getBalcony()!=null){
            responseModel.setBalcony(houseInfo.getBalcony().getUri());
        }

        responseModel.setPrice(houseInfo.getPrice());
        responseModel.setCurrencyType(houseInfo.getCurrencyType().name());

        List<String> ids = new ArrayList<>(houseInfo.getHouse_pictures().keySet());
        List<String> idsList = new ArrayList<>(ids);
        java.util.Collections.sort(idsList);
        responseModel.setPictures(idsList);

        return responseModel;
    }

}
