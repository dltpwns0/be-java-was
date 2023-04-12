package configure;

import webserver.HttpResponseResolver;

public class ResolverConfigure {

    public void addMimeType(HttpResponseResolver responseResolver) {
        // TODO : 흠.... 실제로 이렇게 동작할까? 아닐 것 같다..
        responseResolver.addSupportedMimeType("html", "text/html");
        responseResolver.addSupportedMimeType("css", "text/css");
        responseResolver.addSupportedMimeType("js", "application/javascript");
        responseResolver.addSupportedMimeType("png", "image/png");
        responseResolver.addSupportedMimeType("ico", "image/x-icon");
    }
}
