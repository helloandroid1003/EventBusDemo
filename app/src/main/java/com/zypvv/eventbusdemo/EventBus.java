package com.zypvv.eventbusdemo;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhang on 2017/4/1.
 */
public class EventBus {
    private static EventBus ourInstance = new EventBus();
    /**
     * 缓存所有的订阅者里面的处理函数
     */
    private Map<Object,List<SubscibleMethod>> cacheMap;
    private final Handler handler;
    /**
     * 线程池
     */
    private ExecutorService executorService;

    public static EventBus getInstance() {
        return ourInstance;
    }

    private EventBus() {
        cacheMap=new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService= Executors.newCachedThreadPool();
    }

    /**
     * 注册
     * 事件订阅

     */
      public void register(Activity activity){

        List<SubscibleMethod> list=cacheMap.get(activity);
        if(list==null){
            List<SubscibleMethod> methods=getSubscrbleMethods(activity);
            cacheMap.put(activity,methods);
        }
    }

    /**
     * 寻找activity 里面所有处理的响应函数 并且封装到集合里面去
     * @param activity
     * @return
     */
    private List<SubscibleMethod> getSubscrbleMethods(Activity activity) {
        List<SubscibleMethod> list=new CopyOnWriteArrayList<>();
        Class<?> clazz=activity.getClass();
        //得到activity 所有的方法

        while(clazz!=null){

                String name=clazz.getName();

                if(name.startsWith("java.")||name.startsWith("javax.")||name.startsWith("anddrodi."))
                    break;
                 Method[] methods= clazz.getDeclaredMethods();
            /**
             * 遍历每一个订阅者里面的方法， 找出含有注解的响应函数
             */

               for(Method method:methods){

                /**
                 * 找到含有 Subscribe 代表他是一个响应函数
                 */
                Subseribe subseribe=method.getAnnotation(Subseribe.class);
                if(subseribe==null){
                    continue;
                }
                /**
                 * 拿到method 里面的参数类型
                 */
                Class<?>[] paraters = method.getParameterTypes();

                    if(paraters.length==1){

                    // 得到参数 class 类型   Friend
                       Class<?> paramClass=paraters[0];

                        //保存当前响应函数发生的线程  ThreadMode.MainThread
                        ThreadMode threadMode=subseribe.threadMode();

                        SubscibleMethod subscibleMethod=new SubscibleMethod(method,threadMode,paramClass);

                        list.add(subscibleMethod);
                }else {
                    throw new RuntimeException("eventbusa methods not support more paramter");
                }
            }
            //遍历父类含有的 响应方法
            clazz=clazz.getSuperclass();
        }
        return list;
    }

    /**
     * 注销
     * @param object
     */
    public void unRegister(Object object){

    }

    /**
     * 发送事件
     * @param object
     */
    public void post(final Object object){

        Set<Object> set = cacheMap.keySet();

        Iterator<Object> iterator = set.iterator();

        while(iterator.hasNext()){

            final Object activity = iterator.next();

            List<SubscibleMethod> list = cacheMap.get(activity);

            for (final SubscibleMethod subscriblMethod :list){

                /**
                 * 根据响应函数的参数类型 是否与post  事件类型是否一致
                 */
                
                if(object.getClass().isAssignableFrom(subscriblMethod.getEnvenType())){

                    switch (subscriblMethod.getThreadMode()){

                        case PostThread:
                            invoke(subscriblMethod,activity,object);
                            break;

                        case MainThread:
                            //发布事件 发生在主线程  接收事件实在主线程
                            if(Looper.myLooper()==Looper.getMainLooper()) {
                                invoke(subscriblMethod, activity, object);
                            }else {
                                //发布时间 发生在子线程  接收事件实在主线程
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscriblMethod, activity, object);
                                    }
                                });
                            }
                            break;
                        //  接受在子线程
                        case BackgroundThread:
                            if(Looper.getMainLooper()==Looper.myLooper()){
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            invoke(subscriblMethod, activity, object);
                                        }
                                    });
                            }else {
                                invoke(subscriblMethod, activity, object);
                            }
                            break;

                    }


                }
                
            }
        }


    }

    private void invoke(SubscibleMethod subscriblMethod, Object activity, Object object) {

        try {
            subscriblMethod.getMethod().invoke(activity,object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
