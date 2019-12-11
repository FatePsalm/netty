package com.solace.springbootnetty.util;

import io.netty.util.AttributeKey;

import java.util.Map;

public class AttributeMap {
     /**
       * 作者 CG
       * 时间 2019/12/11 11:41
       * 注释 保存URL连接参数
       */
    public static final AttributeKey<Map<String,String>> PARAMMAP = AttributeKey.valueOf("paramMap");

}
