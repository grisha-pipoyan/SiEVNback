package com.brutus.sievn.rest.filedownload;

import com.brutus.sievn.exception.NotFoundException;
import com.brutus.sievn.persistance.model.addData.HouseInfo;
import com.brutus.sievn.services.HouseDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sievn/public")
@CrossOrigin("*")
public class FileDownloadController {

    private final HouseDataService houseDataService;

    public FileDownloadController(HouseDataService houseDataService) {
        this.houseDataService = houseDataService;
    }


    @Operation(summary = "Download picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    @GetMapping("/files")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@RequestParam("houseId") Long houseId,
                                              @RequestParam("fileName") String fileName) {


        HouseInfo houseInfo = houseDataService.getDataById(houseId).
                orElseThrow(() -> new NotFoundException(
                        String.format("HouseInfo with ID %s not found",houseId)
                ));

        if(houseInfo.getHouse_pictures().containsKey(fileName)){
            ByteArrayResource resource = new ByteArrayResource(houseInfo.getHouse_pictures().get(fileName));

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileName + "\"").body(resource);
        }else{
            throw new NotFoundException("Picture not found");
        }

    }

}
