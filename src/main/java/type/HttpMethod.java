package type;

public enum HttpMethod {
    GET("GET"), POST("POST");

    String method;
    HttpMethod(String method) {
        this.method = method;
    }

    public String toString() {
        return this.method;
    }

    public static HttpMethod getMethod(String method) {
        if (method.equals("GET")) {
            return GET;
        }
        if (method.equals("POST")) {
            return POST;
        }
        return null;
    }
}
