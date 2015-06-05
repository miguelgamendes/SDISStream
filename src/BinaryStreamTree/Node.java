package BinaryStreamTree;

import BinaryStreamTree.remote.LowerNode;
import HttpSecure.HttpSecureServer;
import HttpSecure.HttpURLSecureConnection;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by danfergo on 27-05-2015.
 */
public abstract class Node extends HttpSecureServer{
    protected LowerNode olderSon = null;
    protected LowerNode youngerSon = null;


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

        if(params.get("socket_port") == null ||  params.get("http_port") == null ){
            throw new NumberFormatException();
        }

        int socketPort = Integer.parseInt(params.get("socket_port"));
        int httpPort = Integer.parseInt(params.get("http_port"));
        String reconn = params.get("reconn");
        if(olderSon == null || reconn.equals("true")){
            olderSon = new LowerNode(httpExchange.getRemoteAddress().getHostName(),
                    httpPort,socketPort);
            httpExchange.sendResponseHeaders(200, "Sucess".length());
        } else if(youngerSon == null){
            youngerSon = new LowerNode(httpExchange.getRemoteAddress().getHostName(),
                    httpPort,socketPort);
            httpExchange.sendResponseHeaders(200, "Sucess".length());
        } else {
            //response = "URL:"+olderSon.getAddress();
            String url = "http://" + olderSon.getAddress()+":"+olderSon.getPort() + "/?" + httpExchange.getRequestURI().getQuery();
            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Location", url);
            System.out.println("LOcation url"+ url);
            httpExchange.sendResponseHeaders(300, "Redirect".length());
        }
    }



    protected void send(byte [] data, int n) {
        try { //TODO improve handlers.
            if(youngerSon != null) youngerSon.send(data, n);
        }catch (IOException e){
            youngerSon = null;
            System.out.println("Youngest son, disconnected without notify it.");
        }

        try {
            if(olderSon != null) olderSon.send(data, n);
        }catch (IOException e){
            olderSon = null;
            System.out.println("Oldest son, disconnected without notify it.");
        }
    }




}
