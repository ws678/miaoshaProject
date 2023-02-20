package com.miaoshaproject.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESUserPlus {

    private String info;

    private Integer age;

    private String email;

    private HashMap<String, String> name;
}
