package BinaryStreamTree;

import BinaryStreamTree.remote.LowerNode;
import HttpSecure.HttpSecureServer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by danfergo on 27-05-2015.
 */
public abstract class Node extends HttpSecureServer{
    volatile protected LowerNode olderSon = null;
    volatile protected LowerNode youngerSon = null;
    volatile protected boolean olderSonConfirmed, youngerSonConfirmed;
    volatile protected PublicKey key;

    Node(int BS3PPort) throws IOException {
        super(BS3PPort);

    }
    /**
     * thanks to http://stackoverflow.com/questions/11640025/java-httpserver-httpexchange-get
     * copy-past from http://www.rgagnon.com/javadetails/java-get-url-parameters-using-jdk-http-server.html
     * returns the url parameters in a map
     * @param query
     * @return map
     */
    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public void secureHandle(HttpExchange httpExchange) throws IOException {
        //TODO implement treatment of client's requests. Switch case sort of stuff.
        switch (httpExchange.getRequestURI().getPath()) {
            case "/connect/":
                handleConnectRequest(httpExchange);
                break;
            case "/status/":
                handleStatusRequest(httpExchange);
        }

    }

    private void handleStatusRequest(HttpExchange httpExchange) {
        System.out.println(httpExchange.getRequestMethod());
        /* switch (httpExchange.getRequestMethod()){

        }*/
    }


    public void handleConnectRequest(HttpExchange httpExchange) throws IOException {
        Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());

        //ensuring that: if a peer exists, we need to wait for its confirmation.
        if(params.get("socket_port") == null ||  params.get("http_port") == null ){
            throw new NumberFormatException();
        }

        int socketPort = Integer.parseInt(params.get("socket_port"));
        int httpPort = Integer.parseInt(params.get("http_port"));

        olderSonConfirmed = false;
        youngerSonConfirmed = false;
        while (olderSon != null && !olderSonConfirmed && !youngerSonConfirmed && youngerSon != null){
            try {Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace();}
        }

        byte [] encKey = Base64.getEncoder().encode(key.getEncoded());
            if (olderSon == null) {
                System.out.println("New child is OlderSon");
                olderSon = new LowerNode(httpExchange.getRemoteAddress().getHostName(),
                        httpPort, socketPort);

                httpExchange.sendResponseHeaders(200, encKey.length);
                OutputStream out = httpExchange.getResponseBody();
                out.write(encKey);
                out.close();

            } else if (youngerSon == null) {
                System.out.println("New child is youngerSon");
                youngerSon = new LowerNode(httpExchange.getRemoteAddress().getHostName(),
                        httpPort, socketPort);

                httpExchange.sendResponseHeaders(200, encKey.length);
                OutputStream out = httpExchange.getResponseBody();
                out.write(encKey);
                out.close();

            } else {
                String url = "http://" + olderSon.getAddress() + ":" + olderSon.getPort() + httpExchange.getRequestURI();
                Headers headers = httpExchange.getResponseHeaders();
                headers.add("Location", url);
                System.out.println("New child request redirected to url Location " + url);
                httpExchange.sendResponseHeaders(300, "Redirect".length());
            }

    }



    public void send(byte[] data, int n) {
            try { //TODO improve handlers.
                if(youngerSon != null) youngerSon.send(data, n);
                olderSonConfirmed = true;
            }catch (IOException e){
                youngerSon = null;
                System.out.println("Youngest son, disconnected without notify it.");
            }

            try {
                if(olderSon != null) olderSon.send(data, n);
                olderSonConfirmed = true;
            }catch (IOException e){
                olderSon = youngerSon;
                youngerSon = null;
                System.out.println("Oldest son, disconnected without notify it.");
            }

    }

}
