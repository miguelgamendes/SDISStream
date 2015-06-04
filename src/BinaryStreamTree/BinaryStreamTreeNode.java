package BinaryStreamTree;

import HttpSecure.HttpSecureServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by danfergo on 27-05-2015.
 */
public abstract class BinaryStreamTreeNode extends HttpSecureServer{
    protected BinaryStreamTreeRemoteLowerNode olderSon = null;
    protected BinaryStreamTreeRemoteLowerNode youngerSon = null;

    BinaryStreamTreeNode(int BS3PPort) throws IOException {
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

    public void secureHandle(HttpExchange httpExchange) {
        //TODO implement treatment of client's requests. Switch case sort of stuff.
        String response = "";
        try {
            int port = Integer.parseInt(queryToMap(httpExchange.getRequestURI().getQuery()).get("port"));

            if(olderSon == null){
                olderSon = new BinaryStreamTreeRemoteLowerNode(httpExchange.getRemoteAddress().getHostName(), port);
                response = "CON";
            } else if(youngerSon == null){
                youngerSon = new BinaryStreamTreeRemoteLowerNode(httpExchange.getRemoteAddress().getHostName(),port);
                response = "CON:";
            } else {
                response = "URL:"+olderSon.getAddress();
            }
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void send(byte [] data) {
        if(youngerSon != null) youngerSon.send(data);
        if(olderSon != null) olderSon.send(data);
    }

    public void disconnect(){

    }

}
