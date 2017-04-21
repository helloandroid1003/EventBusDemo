package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Fanshe {
    public static void main(String[] arg0) {
        //对象名。个体Class()   ?代表 我这个类型可以接收任意类型的class 对象
//        Employee zhangsan = new Employee("zhangsan", 30);
//
//        Class<? extends Employee> classType = zhangsan.getClass();
//
//         System.out.println(classType.getName());
//         System.out.println(classType.getSuperclass());


        //通过类名。class

//        Class<? extends Employee> classType =  Employee.class;
//        System.out.println(classType.getName());
//        System.out.println(classType.getSuperclass());


        //获取基本数据类型的class对象
//
//
//        Class<?> classType =  int.class;;
//
//        System.out.println(classType.getName());
//        System.out.println(classType.getSuperclass());

        // 通过基本数据类型的包装类来获取对应的基本数据类型所对应的Class对象

              Class<?> classType3 =  Double.TYPE;;

        System.out.println(classType3.getName());
        System.out.println(classType3.getSuperclass());

//        Class<Double> doubleClass = Double.class;
//
//        System.out.println(doubleClass.getName());
//        System.out.println(doubleClass.getSuperclass());//class java.lang.Number


        //通过 class。forname()


        try {
            Class<?> classType = Class.forName("com.example.Employee");
            //通过反射机制来构造一个Employee 的实例对象(默认调用无参数的构造方法
            // )
//           Employee employee=(Employee) classType.newInstance();
//
//            //调用制定的构造方法来构造对象
//
//            Constructor constructor = classType.getConstructor(new Class[]{});
//            Employee employee1=(Employee) constructor.newInstance(new Object[]{});


            //调用带参数构造方法来构造对象

            Constructor constructor1 = classType.getConstructor(new Class[]{String.class,int.class});
            Employee employee2=(Employee) constructor1.newInstance(new Object[]{"zhangsan",18});
           

//            //获取class对象所制定的方法，包括私有的
//
//            Method method = classType.getDeclaredMethod("toString", new Class[]{});
//           String desc= (String) method.invoke(employee2, new Object[]{});
//
//            System.out.println(desc);

//            //获取class 对象 所制定的所有方法 包括私有的
//            Method[] declaredMethods = classType.getDeclaredMethods();
//            for(Method method:declaredMethods){
//
//                System.out.println(method.getName()+ "   "+method.getModifiers()+"   "+method.getReturnType());
//            }


            //私有方法能被访问到 但是不能被调用  必须  sayMethod.setAccessible(true);  但是破坏了面向对象的封装
            Method sayMethod = classType.getDeclaredMethod("say", new Class[]{});
            sayMethod.setAccessible(true);
            sayMethod.invoke(employee2, new Object[]{});


            //获取class 对象 所制定的属性 包括私有的
             Field field = classType.getDeclaredField("name");
             field.setAccessible(true);
             field.set(employee2,"lisi");
             System.out.println(field.get(employee2));

        } catch ( Exception e) {
            e.printStackTrace();
        }


    }

}

    class Employee{

        private  String name;
        public Employee( ) {
            System.out.println("wucan");
        }
        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        private  int age;

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }


        private void say(){
            System.out.println(" hello wold");
        }
    }

