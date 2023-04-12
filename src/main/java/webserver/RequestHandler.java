package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import configure.ResolverConfigure;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.HttpServlet;
import servlet.HttpServletContainer;
import util.HttpRequestParser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpServletContainer httpServletContainer;
    private HttpResponseResolver httpResponseResolver;
    private ResolverConfigure resolverConfigure;

    private HttpRequestParser httpRequestParser;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpServletContainer = new HttpServletContainer();
        this.httpResponseResolver = new HttpResponseResolver();
        this.resolverConfigure = new ResolverConfigure();
        resolverConfigure.addMimeType(httpResponseResolver);
        this.httpRequestParser = new HttpRequestParser();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader( in, StandardCharsets.UTF_8));

            HttpRequest httpRequest = httpRequestParser.parse(br);
            HttpResponse httpResponse = new HttpResponse();

            String httpMethod = httpRequest.getMethod();
            HttpServlet httpServlet = httpServletContainer.getServlet(httpMethod);

            httpServlet.service(httpRequest, httpResponse);

            byte[] response =  httpResponseResolver.resolve(httpResponse);


            DataOutputStream dos = new DataOutputStream(out);
            dos.write(response);
            dos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
