package me.chiontang.wechatmomentexport.utils;

import android.util.Log;

import org.json.JSONObject;

import java.lang.reflect.Field;


/**
 * Created by Berkeley on 10/9/16.
 */
public class JSONUtils {

    private static final String TAG="JSONUtils";
    /**
     * 反射得到对象的json字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> JSONObject reflect(T obj) {
        JSONObject jo = new JSONObject();
        if (null == obj)
            return jo;


        try {
            Field[] fields = obj.getClass().getDeclaredFields();

            String[] types = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
            String[] value = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character", "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte"};
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                // 字段名
//                System.out.print(fields[j].getName() + ":");

                // 字段值
                for (int i = 0; i < types.length; i++) {
                    if (fields[j].getType().getName()
                            .equalsIgnoreCase(types[i]) || fields[j].getType().getName().equalsIgnoreCase(value[i])) {
                        try {
//                            System.out.print(fields[j].get(obj) + "");
                            jo.put(fields[j].getName()+"",fields[j].get(obj)+"");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        return jo;
    }



    /**
     *
     * 获取对象中某个字段的值
     * @param obj
     * @param str
     * @param <T>
     * @return
     */
    public static <T>  String  reflectValue(T obj,String str) {

        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);

                if (fields[j].getName().equals(str))
                {
                    return fields[j].get(obj)+"";
                }
            }
        } catch (Exception e) {

        }
        return null;
    }


    public static <T> void reflectValue(T obj) {

        try {
            Field[] fields = obj.getClass().getDeclaredFields();

            String[] types = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
            String[] value = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character", "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte"};
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                // 字段名
//                System.out.print(fields[j].getName() + ":");

                // 字段值
                for (int i = 0; i < types.length; i++) {
                    if (fields[j].getType().getName()
                            .equalsIgnoreCase(types[i]) || fields[j].getType().getName().equalsIgnoreCase(value[i])) {
                        try {
//                            System.out.print(fields[j].get(obj) + "");
                            Log.v(TAG,fields[j].getName()+"="+fields[j].get(obj));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }



}
