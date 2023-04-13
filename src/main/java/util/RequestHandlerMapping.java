package util;

import annotation.RequestMapping;
import type.RequestMethod;

import java.lang.reflect.Method;

public class RequestHandlerMapping {

    // TODO : 코드 정리가 필요한 시점!
    public static Method requestHandlerMapping(Class<?> controller, String url, RequestMethod requestMethod) {
        Method[] methods = controller.getMethods();
        RequestMapping controllerAnnotation = controller.getAnnotation(RequestMapping.class);
        String[] basePath = controllerAnnotation.path();

        for (Method method : methods) {
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            // TODO : methodAnnotation 은 null 일 수 있다! ( 즉, 컨트롤러의 모든 메서드에 어노테이션을 붙여줘야 함) (컨트롤러의 어노테이션을 이용하여 해결해보자!)
            if (methodAnnotation.method() != requestMethod) {
                continue;
            }

            String[] methodPaths = methodAnnotation.path();
            for (String controllerPath : basePath) {
                for (String methodPath : methodPaths) {
                    String path = controllerPath + methodPath;
                    if (url.startsWith(path)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }
}
