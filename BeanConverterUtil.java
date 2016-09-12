package cn.thinkjoy.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * map与bean互转
 * <p/>
 * 创建时间: 16/2/22<br/>
 */
public class BeanConverterUtil {
    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, Object> bean2Map(Object javaBean, boolean includeNull) {
        Map<String, Object> result = new HashMap<>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    if(!includeNull &&  null != value){
                        result.put(field, value);
                    }else {
                        result.put(field, null == value ? "" : value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Map<String, Object> bean2Map(Object javaBean) {
        return bean2Map(javaBean, false);
    }

    /**
     * 将map转换成Javabean
     * date类型字段不支持，需要自行转换
     *
     * @param clazz    javaBean
     * @param data     map数据
     */
    public static Object map2Bean(Class clazz, Map<String, Object> data) {
        Object bean = null;
        try {
            Method[] methods = clazz.getMethods();
            bean = clazz.newInstance();
            for (Method method : methods) {
                try {
                    if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
                        String field = method.getName();
                        field = field.substring(field.indexOf("set") + 3);
                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        if(data.containsKey(field) && data.get(field) != null){
                            Class paramType = method.getParameterTypes()[0];
                            if(paramType == Integer.class || paramType == int.class){
                                method.invoke(bean, new Object[]{Integer.valueOf(data.get(field).toString())});
                            }else if(paramType == Boolean.class || paramType == boolean.class){
                                method.invoke(bean, new Object[]{Boolean.valueOf(data.get(field).toString())});
                            }else if(paramType == Byte.class || paramType == byte.class){
                                method.invoke(bean, new Object[]{Byte.valueOf(data.get(field).toString())});
                            }else if(paramType == Short.class || paramType == short.class){
                                method.invoke(bean, new Object[]{Short.valueOf(data.get(field).toString())});
                            }else if(paramType == Character.class || paramType == char.class){
                                method.invoke(bean, new Object[]{Character.valueOf(data.get(field).toString().charAt(0))});
                            }else if(paramType == Long.class || paramType == long.class){
                                method.invoke(bean, new Object[]{Long.valueOf(data.get(field).toString())});
                            }else if(paramType == Float.class || paramType == float.class){
                                method.invoke(bean, new Object[]{Float.valueOf(data.get(field).toString())});
                            }else if(paramType == Double.class || paramType == double.class){
                                method.invoke(bean, new Object[]{Double.valueOf(data.get(field).toString())});
                            }else {
                                method.invoke(bean, new Object[]{data.get(field)});
                            }
                        }
                    }
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
