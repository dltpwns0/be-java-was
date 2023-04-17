package view;

import model.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 실제의 뷰 리졸버랑은 다르다. 단순히 뷰 클래스에 정보를 넘겨주기 전, 전처리 느낌으로 사용했음
public class ViewResolver {

    private View view;

    private final String ROOT_PATH = "src/main/resources/";
    private final String[] BASE_PATH = {"static", "templates"};
    private final String WELCOME_PAGE = "templates/index.html";

    // TODO : 더 효율적인 리다이렉트 방법을 생각해 보자
    private final String redirectPage = "src/main/resources/static/redirectFile.txt";

    private final String CONTENT_TYPE = "Content-Type";

    private final String CONTENT_LENGTH = "Content-Length";

    private final String LOCATION = "Location";


    private static Map<String, String> mime = new HashMap<>();

    public ViewResolver(View view) {
        this.view = view;

        // TODO : MIME 타입을 초기화하는 것을 다시 해보자 (나중에)
        mime.put("html", "text/html");
        mime.put("css", "text/css");
        mime.put("js", "application/javascript");
        mime.put("png", "image/png");
        mime.put("ico", "image/x-icon");
    }
    public void resolve(String viewName, HttpResponse httpResponse) throws IOException {
        File file = resolveFile(viewName, httpResponse);

        resolveLocation(httpResponse);
        resolveContentLength(httpResponse, file);
        resolveContentType(httpResponse, file);

        view.view(file, httpResponse);
    }

    private void resolveContentType(HttpResponse response, File file) {
        if (response.containHeader(CONTENT_TYPE)) {
            return;
        }

        // TODO : 아래의 코드가 마음에 들지 않는다. (확장자 명을 이용한 contentType? 이럼 안되~)
        String extension = getExtension(file);
        response.addHeader(CONTENT_TYPE, mime.get(extension));
    }

    private void resolveLocation(HttpResponse response) {
        // TODO : 만약 리다이렉트할 상태코드의 양이 많아진다면? (수정 필요)
        if (response.getStatus() == HttpResponse.SC_FOUND || response.getStatus() == HttpResponse.SC_UNAUTHORIZED) {
            response.addHeader(LOCATION, response.getRedirectURL());
        }
    }

    private void resolveContentLength(HttpResponse response, File file) {
        response.addHeader(CONTENT_LENGTH, Long.toString(file.length()));
    }


    private File resolveFile(String viewName, HttpResponse response) {
        if (response.getStatus() == HttpResponse.SC_FOUND) {
            return new File(redirectPage);
        }
        return getFileAt(viewName);
    }

    private File getFileAt(String requestUrl) {
        for (String basePath : BASE_PATH) {
            String filePath = ROOT_PATH + basePath + requestUrl;
            if (!fileExistsAt(filePath)) {
                continue;
            }
            return new File(filePath);
        }

        return new File(ROOT_PATH + WELCOME_PAGE);
    }

    private boolean fileExistsAt(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return false;
        }
        return f.isFile();
    }

    private static String getExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }
}
