package view;

import model.HttpResponse;

import java.io.IOException;

public interface View {

    public String getName();
    public void render(String viewName, HttpResponse httpResponse) throws IOException;
}
