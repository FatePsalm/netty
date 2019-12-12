package com.solace.springbootnetty.enums;

 /**
   * 作者 CG
   * 时间 2019/12/12 9:48
   * 注释 聊天类型区分
   */
public class MsgEnum {
    public enum Type{
        普通消息(1),
        上线消息(2),
        下线消息(3);
        private Integer type;

        Type(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }
}
