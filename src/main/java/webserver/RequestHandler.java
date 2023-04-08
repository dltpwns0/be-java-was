package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import configure.ResolverConfigure;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseResolver;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpServlet httpServlet;
    private HttpResponseResolver httpResponseResolver;
    private ResolverConfigure resolverConfigure;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpServlet = new HttpServlet();
        this.httpResponseResolver = new HttpResponseResolver();
        // TODO : 이러한 설정은 메인 함수에서 하는 것이 맞을 것 같다.
        this.resolverConfigure = new ResolverConfigure();
        resolverConfigure.addMimeType(this.httpResponseResolver);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader( in, StandardCharsets.UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            String requestHead = readRequestHead(br);

            HttpRequest httpRequest = new HttpRequest(requestHead);
            HttpResponse httpResponse = new HttpResponse();

            httpServlet.service(httpRequest, httpResponse);

            byte[] response = httpResponseResolver.resolve(httpResponse);
            dos.write(response);
            dos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static String readRequestHead(BufferedReader br) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        readRequestLine(br, stringBuilder);
        readRequestHeader(br, stringBuilder);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private static void readRequestHeader(BufferedReader br, StringBuilder stringBuilder) throws IOException {
        String line = null;
        while (!(line = br.readLine()).equals("")) {
            stringBuilder.append(line).append("\n");
        }
    }

    private static void readRequestLine(BufferedReader br, StringBuilder stringBuilder) throws IOException {
        String line = br.readLine();
        String requestLine = URLDecoder.decode(line, StandardCharsets.UTF_8);
        stringBuilder.append(requestLine).append("\n");
    }
}
