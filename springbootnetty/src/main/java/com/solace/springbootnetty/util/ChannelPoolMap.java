package com.solace.springbootnetty.util;

import com.solace.springbootnetty.enums.ParamEnum;
import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
 /**
   * 作者 CG
   * 时间 2019/12/11 20:35
   * 注释 主要用于管理socket连接
   */
public class ChannelPoolMap {
    private static ConcurrentMap<String, ConcurrentMap<String, Channel>> map = new ConcurrentHashMap<>();
    static {
        ParamEnum.ChatType[] values = ParamEnum.ChatType.values();
        for (ParamEnum.ChatType index:values
             ) {
            map.put(index.getType(), new ConcurrentHashMap<String, Channel>());
        }
    }
    /**
       * 作者 CG
       * 时间 2019/12/11 15:17
       * 注释 返回指定的map
       */
     public static ConcurrentMap<String, Channel> getType(ParamEnum.ChatType type){
         return map.get(type.getType());
     }
}
