package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import process.RequestProcess;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestProcess requestProcess;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.requestProcess = new RequestProcess();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader( in, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while (!(line = br.readLine()).equals("")) {
                stringBuilder.append(line).append("\n");
            }
            stringBuilder.append("\n");

            DataOutputStream dos = new DataOutputStream(out);
            System.out.println(stringBuilder);

            byte[] body = requestProcess.process(stringBuilder.toString());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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
