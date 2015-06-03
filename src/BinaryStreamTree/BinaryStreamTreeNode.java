package BinaryStreamTree;

import HttpSecure.HttpSecureServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

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
    }

    public void disconnect(){

    }

    void connectRequest(){

    }

}
