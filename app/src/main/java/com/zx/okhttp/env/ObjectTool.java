package com.zx.okhttp.env;


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectTool {

    /**
     * 将任意对象转换成为application/x-www-form-urlencoded的Content
     * 即：key1=value1&key2=value2的形式 主要用于转换成URL参数等
     */
    public static String toContent(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj != null) {
            // 获得对象中的所有字段
            Field[] fields = obj.getClass().getFields();
            for (Field field : fields) {
                // 获得DataContentTag Annotation
                ObjectTag tag = field.getAnnotation(ObjectTag.class);
                // 仅在标记存在时才转换此字段
                if (tag != null) {
                    try {
                        // 获得数据载体Obj
                        Object dataContainer = field.get(obj);
                        // 转换成content
                        if (dataContainer != null) {
                            if (sb.length() != 0) {
                                sb.append("&");
                            }
                            sb.append(String.format("%s=%s", tag.value(),
                                    URLEncoder.encode(dataContainer.toString(),
                                            "utf-8")));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将任意Map对象转换成为application/x-www-form-urlencoded的Content
     * 即：key1=value1&key2=value2的形式 主要用于转换成URL参数等
     */
    public static String toContent(Map<?, ?> data) {
        StringBuilder sb = new StringBuilder();
        for (Entry<?, ?> item : data.entrySet()) {
            try {
                Object key = item.getKey();
                Object val = item.getValue();
                if (val != null) {
                    if (sb.length() != 0) {
                        sb.append("&");
                    }
                    sb.append(String.format("%s=%s", key.toString(),
                            URLEncoder.encode(val.toString(), "utf-8")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 非null的字段都会被转换到map中
     */
    public static Map<String, Object> toMap(Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        Map<String, Object> map = new HashMap<>();
        if (obj != null) {
            // 获得对象中的所有字段
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                ObjectTag tag = field.getAnnotation(ObjectTag.class);
                // 仅在标记存在时才转换此字段
                if (tag != null) {
                    try {
                        field.setAccessible(true);
                        // 获得数据载体Obj
                        Object dataContainer = field.get(obj);
                        // 转换成Map
                        if (dataContainer != null) {
                            map.put(tag.value(), dataContainer);
                        }
                    } catch (Exception e) {
                        // do nothing
                    }
                }
            }
        }
        return map;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Inherited
    public @interface ObjectTag {
        String value();
    }
}
