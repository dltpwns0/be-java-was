package util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class MyObjectMapperByMap {
    public static Optional<?> readValue(Map<String, String> requestParams, Class<?> userClass) throws Exception {

        Map<String, Class<?>> mappedFields = getMappedFields(userClass);
        List<Object> constructorArguments = getConstructorArguments(requestParams, mappedFields);
        List<Class<?>> constructorArgumentsType = getConstructorArgumentTypes(constructorArguments);
        Constructor<?> constructor = userClass.getConstructor(constructorArgumentsType.toArray(new Class<?>[0]));
        
        return Optional.of(constructor.newInstance(constructorArguments.toArray()));
    }

    private static List<Class<?>> getConstructorArgumentTypes(List<Object> constructorArguments) {
        return constructorArguments.stream()
                .map(Object::getClass)
                .collect(Collectors.toList());
    }

    private static Map<String, Class<?>> getMappedFields(Class<?> userClass) throws IntrospectionException {
        // TODO  : 복잡하다.. 하지만 구현체는 복잡해도 되지 않을까?
        Map<String, Class<?>> mappedFields = new HashMap<>();
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(userClass).getPropertyDescriptors();
        for (PropertyDescriptor pd : propertyDescriptors) {
            String propertyName = pd.getName();
            Class<?> propertyType = pd.getPropertyType();
            mappedFields.put(propertyName, propertyType);
        }
        return mappedFields;
    }

    private static List<Object> getConstructorArguments(Map<String, String> requestParams, Map<String, Class<?>> mappedFields) {
        List<Object> constructorArguments = new ArrayList<>();

        // TODO : 복잡하다..
        requestParams.forEach((key, value) -> {
            Class<?> clazz = mappedFields.get(key);
            if (clazz != null) {
                Object obj = convertType(clazz, value);
                constructorArguments.add(obj);
            }
        });
        return constructorArguments;
    }

    private static Object convertType(Class<?> clazz, String value) {
        Object object = null;
        if (clazz == String.class) {
            return value;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return Integer.parseInt(value);
        }
        return null;
    }
}
