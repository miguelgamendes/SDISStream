package HttpSecure;


import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;

/**
 * Created by danfergo on 27-05-2015.
 */
public abstract class HttpSecureServer implements HttpHandler {
    HttpServer httpServer;


    public HttpSecureServer(int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", this);
        httpServer.setExecutor(null); // creates a default executor
        httpServer.start();
        System.out.println(httpServer.getAddress().getPort());

        // Google it -> https://www.google.pt/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=com.sun.net.HttpServer

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //TODO work encryption/decryption
        /** System.out.println("Conecçao recebida.");

        String response = "This is the response";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();**/
        secureHandle(httpExchange);
    }

    public abstract void secureHandle(HttpExchange httpExchange);
}