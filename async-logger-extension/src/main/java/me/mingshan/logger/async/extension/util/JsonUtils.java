package me.mingshan.logger.async.extension.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class JsonUtils {

    public static String arrayToString(Object[] source) {
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(source);
        return jsonArray.toJSONString();
    }

    public static String objectToString(Object source) {
        return JSON.toJSONString(source);
    }
}
