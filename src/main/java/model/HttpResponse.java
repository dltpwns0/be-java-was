package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

public class HttpResponse {

    private String path;
    private String redirectUrl;

    private String contentType;


    public String getContentType() {
        return contentType;
    }

    public void redirect(String url) {
        this.redirectUrl = url;
    }

    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean hasRedirectUrl() {
        return redirectUrl != null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }




}
