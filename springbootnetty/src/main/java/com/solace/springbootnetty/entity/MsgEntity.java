package com.solace.springbootnetty.entity;
 /**
   * 作者 CG
   * 时间 2019/12/12 9:46
   * 注释 消息类型
   */
public class MsgEntity {
     private Integer type;
     private String msg;
     private String sendId;
     private String acceptId;

     public String getSendId() {
         return sendId;
     }

     public void setSendId(String sendId) {
         this.sendId = sendId;
     }

     public String getAcceptId() {
         return acceptId;
     }

     public void setAcceptId(String acceptId) {
         this.acceptId = acceptId;
     }

     public Integer getType() {
         return type;
     }

     public void setType(Integer type) {
         this.type = type;
     }

     public String getMsg() {
         return msg;
     }

     public void setMsg(String msg) {
         this.msg = msg;
     }

     @Override
     public String toString() {
         return "MsgEntity{" +
                 "type=" + type +
                 ", msg='" + msg + '\'' +
                 ", sendId='" + sendId + '\'' +
                 ", acceptId='" + acceptId + '\'' +
                 '}';
     }
 }
