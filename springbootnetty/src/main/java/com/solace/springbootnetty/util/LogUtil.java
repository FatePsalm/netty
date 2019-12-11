package com.solace.springbootnetty.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 /**
   * 作者 CG
   * 时间 2019/12/11 11:13
   * 注释 统一日志工具
   */
public class LogUtil {
   public static Logger logger(Class<?> logClass) {
        return LoggerFactory.getLogger(logClass);
    }
}
