package com.zypvv.eventbusdemo;

/**
 * Created by zhang on 2017/4/1.
 */

public enum  ThreadMode {
    /**
     * 发布线程与接受处在同一线程
     */
    PostThread,
    /**
     * 接受发生在主线程
     */
    MainThread,
    /**
     * 接收发生在子线程
     */

    /**
     * 发生在子线程
     */

    BackgroundThread,

    /**
     * 发生在子线程
     */
    asny
}
