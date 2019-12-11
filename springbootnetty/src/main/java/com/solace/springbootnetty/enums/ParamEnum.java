package com.solace.springbootnetty.enums;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
   * 作者 CG
   * 时间 2019/12/11 14:49
   * 注释 连接参数枚举
   */
public class ParamEnum {
     /**
       * 作者 CG
       * 时间 2019/12/11 15:20
       * 注释 创建socket聊天分类
       */
     public enum ChatType{
         WEB("web");
         private String type;

         ChatType(String type) {
             this.type = type;
         }

         public String getType() {
             return type;
         }

         public void setType(String type) {
             this.type = type;
         }
         public static ChatType findType(Map<String,String> param){
             String type = param.get(Param.TYPE.getValue());
             if (!StringUtils.isEmpty(type)) {
                 ChatType[] values = ChatType.values();
                 for (ChatType index:values
                      ) {
                     if (index.getType().equals(type))
                         return index;
                 }
             }
             return null;
         }
     }
     /**
       * 作者 CG
       * 时间 2019/12/11 14:49
       * 注释 param参数
       */
    public enum Param{
         UID("uid"),
         URL("url"),
         TYPE("type"),
         ;
         private String value;

         Param(String value) {
             this.value = value;
         }

         public String getValue() {
             return value;
         }

         public void setValue(String value) {
             this.value = value;
         }
     }
}
