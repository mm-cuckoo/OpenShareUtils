package com.cfox.openshareutils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cfox.openshare.parse.IParse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonParser  implements IParse {
    /**
     * 用fastjson 将json字符串解析为一个 JavaBean
     *
     * @param jsonString jsonString
     * @param cls        cls
     * @return T
     */
    public static <T> T getParseBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            Log.d("JsonParser", "getParseListMap: " + e);
            e.printStackTrace();

        }
        return t;
    }

    /**
     * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
     *
     * @param jsonString jsonString
     * @param cls        cls
     * @return List
     */
    public static <T> List<T> getParseListBean(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            Log.d("JsonParser", "getParseListMap: " + e);
        }
        return list;
    }

    /**
     * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
     *
     * @param jsonString jsonString
     * @return List
     */
    public static List<Map<String, Object>> getParseListMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            Log.d("JsonParser", "getParseListMap: " + e);
        }
        return list;

    }

    @Override
    public Map<String, String> parseToMap(String str) {
        return JSON.parseObject(str,new TypeReference<Map<String, String>>(){});
    }
}
