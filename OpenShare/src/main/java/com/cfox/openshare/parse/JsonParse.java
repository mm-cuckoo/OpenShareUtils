package com.cfox.openshare.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.parse
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class JsonParse implements IParse {
    @Override
    public Map<String, String> parseToMap(String str) {
        return JSON.parseObject(str,new TypeReference<Map<String, String>>(){});
    }
}
