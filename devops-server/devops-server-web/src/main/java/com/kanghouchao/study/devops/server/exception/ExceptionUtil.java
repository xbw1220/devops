package com.kanghouchao.study.devops.server.exception;

public class ExceptionUtil {

    public static void handleException() {
        throw new RuntimeException("请求错误");
    }

}
