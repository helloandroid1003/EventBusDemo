package com.zypvv.eventbusdemo;

import java.lang.reflect.Method;

/**
 * Created by zhang on 2017/4/1.
 */

public class SubscibleMethod {

    private Method  method;

    private ThreadMode threadMode;

    private Class<?> envenType;

    public SubscibleMethod(Method method, ThreadMode threadMode, Class<?> envenType) {
        this.method = method;
        this.threadMode = threadMode;
        this.envenType = envenType;
    }


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Class<?> getEnvenType() {
        return envenType;
    }

    public void setEnvenType(Class<?> envenType) {
        this.envenType = envenType;
    }
}
