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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
//            while (!(line = br.readLine()).equals("")) {
                stringBuilder.append(line);
                logger.info("값이 들어왔어용 : {} ", line);
            }

            String postData = stringBuilder.toString();
            logger.info("{}", postData);
            logger.info("이건 뭥미?");

            logger.info("이건 뭥미?");

//            HttpRequest httpRequest = new HttpRequest(br);
//            HttpResponse httpResponse = new HttpResponse();
//
//            httpServlet.service(httpRequest, httpResponse);
//
//            byte[] response = httpResponseResolver.resolve(httpResponse);
//
//            DataOutputStream dos = new DataOutputStream(out);
//            dos.write(response);
//            dos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
