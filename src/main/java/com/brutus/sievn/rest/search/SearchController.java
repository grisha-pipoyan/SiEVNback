package com.brutus.sievn.rest.search;

import com.brutus.sievn.enums.Language;
import com.brutus.sievn.persistance.model.addData.HouseInfo;
import com.brutus.sievn.services.HouseDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.brutus.sievn.MyUtils.getResponseModelListByLanguage;

@RestController
@RequestMapping("/sievn/public/search")
@CrossOrigin("*")
public class SearchController {

    private final HouseDataService houseDataService;

    public SearchController(HouseDataService houseDataService) {
        this.houseDataService = houseDataService;
    }


    @GetMapping
    public ResponseEntity<?> searchDataByTitle(@RequestParam("parameter")String parameter,
                                               @RequestParam("language")Language language){

        List<HouseInfo> houseInfoList = houseDataService.searchByParameter(parameter,language);

        return ResponseEntity.ok(getResponseModelListByLanguage(houseInfoList,language));
    }

}
