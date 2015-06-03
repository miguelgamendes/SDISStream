package BinaryStreamTree;

import HttpSecure.HttpSecureServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by danfergo on 27-05-2015.
 */
public abstract class BinaryStreamTreeNode extends HttpSecureServer{
    protected BinaryStreamTreeRemoteNode olderSon = null;
    protected BinaryStreamTreeRemoteNode youngerSon = null;

    BinaryStreamTreeNode() throws IOException {
        super();
    }

    public void secureHandle(HttpExchange httpExchange) {
        //TODO implement treatment of client's requests. Switch case sort of stuff.
        String response = "";
        try {
            if(olderSon == null){
                olderSon = new BinaryStreamTreeRemoteNode(httpExchange.getRequestURI().toString(), (int)httpExchange.getAttribute("port"));
                response = "CON";
            } else if(youngerSon == null){
                youngerSon = new BinaryStreamTreeRemoteNode(httpExchange.getRequestURI().toString(), (int)httpExchange.getAttribute("port"));
                response = "CON:";
            } else {
                response = "URL:"+olderSon.getAddress();
            }
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void send(byte [] data) {
        if(youngerSon != null){
            youngerSon.send(data);
        }

        if(olderSon != null){
            olderSon.send(data);
        }
    }

    public void disconnect(){

    }

}
