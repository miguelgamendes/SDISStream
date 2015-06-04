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
    int port;

    BinaryStreamTreeRemoteNode(String address, int port){
        this.address = address;
        this.port = port;
    }

    /*** NOTIFICATIONS **/
    public void disconnect(){
        //TODO to implement nice exit from the Tree. Send it
    }


    public String getAddress() {
        return address;
    }
    public int getPort() { return port; }

    public void setPort(int httpport) {  this.port = httpport; }
}
