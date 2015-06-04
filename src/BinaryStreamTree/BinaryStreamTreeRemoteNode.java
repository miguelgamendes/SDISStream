package BinaryStreamTree;

import HttpSecure.HttpSecureServer;
import HttpSecure.HttpURLSecureConnection;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import sun.net.www.protocol.http.Handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by danfergo on 27-05-2015.
 */
public abstract class BinaryStreamTreeRemoteNode {
    //HttpURLSecureConnection httpConnection = null;

    String address;

    BinaryStreamTreeRemoteNode(String address){
        this.address = address;
    }

    /*** NOTIFICATIONS **/
    public void disconnect(){
        //TODO to implement nice exit from the Tree. Send it
    }


    public String getAddress() {
        return address;
    }
}
