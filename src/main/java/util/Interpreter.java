package util;

import model.Model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class Interpreter {

    private Collection<Model> models;

    public Interpreter(Collection<Model> models) {
        this.models = models;
    }

    public Interpreter() {
        models = new ArrayList<>();
    };
    public Interpreter(Model model) {
        models = new ArrayList<>();
        models.add(model);
    }

    public void addModel(Model model) {
        this.models.add(model);
    }


    public String interpretValue(String input) {
        String name = getObjectName(input);
        Object object = findObjectBy(name);
        return object.toString();
    }

    public Object interpretObject(String name) {
        return findObjectBy(name);
    }

    public String interpretObjectAndMethodName(String objectNameAndMethod) {
        String objectName = getObjectName(objectNameAndMethod);
        String methodName = getMethodName(objectNameAndMethod);
        Object object = findObjectBy(objectName);
        return interpretObjectAndMethodName(object, methodName);
    }

    public String interpretObjectAndMethodName(Object object, String methodName) {
        assert object != null;
        Class<?> clazz = object.getClass();

        String output = null;
        try {
            Method method = clazz.getMethod(methodName);
            Object invoke = method.invoke(object);

            output = invoke.toString();
        } catch (Exception e) {
        }

        return output;
    }

    private Object findObjectBy(String name) {
        for (Model model : models) {
            if (model.getName().equals(name)) {
                return model.getObject();
            }
        }
        return null;
    }

    private String getObjectName(String input) {
        return input.split("\\.")[0];
    }
    private String getMethodName(String input) {
        return input.split("\\.")[1];
    }
}
