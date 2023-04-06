package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import process.RequestProcess;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpServlet httpServlet;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpServlet = new HttpServlet();
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


            byte[] body = httpResponse.getResponseBody();

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static String readRequestHead(BufferedReader br) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        readRequestLine(br, stringBuilder);
        readRequestMIME(br, stringBuilder);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private static void readRequestMIME(BufferedReader br, StringBuilder stringBuilder) throws IOException {
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

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
