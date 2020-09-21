package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Map<String, Controller> controllerMapper;


    public RequestHandler(Socket connectionSocket, Map<String, Controller> controllerMapper) {
        this.connection = connectionSocket;
        this.controllerMapper = controllerMapper;

        controllerMapper.put("/index.html", new IndexController());
        controllerMapper.put("/user/create", new CreateUserController());
        controllerMapper.put("/user/list.html", new ListUserController());
        controllerMapper.put("/user/login", new LoginController());
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(bufferedReader);
            HttpResponse httpResponse = new HttpResponse(out);

            Controller controller = controllerMapper.getOrDefault(httpRequest.getPath(), new IndexController());
            controller.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
