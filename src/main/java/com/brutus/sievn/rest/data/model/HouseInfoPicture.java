package com.brutus.sievn.rest.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseInfoPicture {
    private Long id;
    private String name;
    private byte[] bytes;
}
