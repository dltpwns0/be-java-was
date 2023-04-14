package type;

import java.util.Objects;

public enum RequestMethod {
    GET("GET"), POST("POST");

    String method;
    RequestMethod(String method) {
        this.method = method;
    }

    public String toString() {
        return this.method;
    }

    public static RequestMethod getMethod(String method) {
        if (method.equals("GET")) {
            return GET;
        }
        if (method.equals("POST")) {
            return POST;
        }
        return null;
    }
}
