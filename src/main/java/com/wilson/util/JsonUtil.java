package com.wilson.util;

import com.alibaba.fastjson.JSON;

/**
 * Created by lsm on 2017/6/20.
 */
public class JsonUtil {

    public static <T> T getObject(String jsonString, Class<T> cls)
    {
        T t = null;
        try
        {
            t = JSON.parseObject(jsonString, cls);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }



}
