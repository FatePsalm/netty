package com.solace.springbootnetty.util;

import com.solace.springbootnetty.enums.ParamEnum;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者 CG
 * 时间 2019/12/11 11:19
 * 注释 处理请求工具类
 */
public class RequestUtil {
    /**
     * 作者 CG
     * 时间 2019/12/11 11:19
     * 注释 拆分URL
     */
    public static Map<String, String> paramMap(String url) {
        Map<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(url)) {
            if (!url.contains("?"))
                return map;
            String[] split = url.split("\\?");
            if (split.length > 1) {
                String[] param = split[1].replaceAll("&&", "&").split("&");
                for (String index : param
                ) {
                    String key = index.split("=")[0];
                    String value = index.split("=")[1];
                    map.put(key, value);
                }
            }
            map.put(ParamEnum.Param.URL.getValue(), split[0]);
        }
        return map;
    }
}
