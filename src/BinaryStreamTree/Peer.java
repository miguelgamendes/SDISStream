package BinaryStreamTree;

import BinaryStreamTree.remote.UpperNode;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import javax.swing.plaf.basic.BasicTreeUI;
import java.io.IOException;

/**
 * Created by danfergo on 27-05-2015.
 */
public class Peer extends Node implements Runnable {

    UpperNode parent = null;
    UpperNode godfather = null;


    public Peer(int BS3PPort, String BS3PParentAddress) throws IOException {
        super(BS3PPort);
        parent = new UpperNode(BS3PParentAddress, BS3PPort);
        godfather = parent.connect(BS3PParentAddress, parent.getSocketPort());
        parent.accept();
        new Thread(this).start();
    }

    @Override
    public void handleConnectRequest(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();

        if(olderSon == null){
            httpExchange.getResponseHeaders().add("Godfather", parent.getAddress() + ":" + parent.getPort());
        } else if(youngerSon == null){
            httpExchange.getResponseHeaders().add("Godfather", olderSon.getAddress() + ":" + olderSon.getPort());
        }

        super.handleConnectRequest(httpExchange);

    }

    public void handleDisconnection() throws AbortedConnectionException {
        if(godfather == null){
            System.out.println("Does not exist a godfather, so it's impossible to reconnect.");
            throw new AbortedConnectionException();
        }else {
            parent = godfather;
            String address = parent.getAddress() + ":" + parent.getPort();
            try {
                System.out.println("Connecting to godfather");
                godfather = parent.connect(address, parent.getSocketPort());
                parent.accept();
                System.out.println("Connected to godfather");
            } catch (IOException e) {
                System.out.println("Not connected");
                e.printStackTrace();
                throw new AbortedConnectionException();
            }
        }
    }

    /**
     * Requests video chunks from RemoteNode
     * parent' socket resend it to it's children and return the upper layer.
     */
    public byte [] receive(int bytes) throws AbortedConnectionException {
        byte [] data = new byte[0];
        try {
            data = parent.receive(bytes);
            if(data == null){
                handleDisconnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void run() {
        while(true) {
        byte[] data = new byte[15000];
        int n;
            try {
                n = parent.readEncrypted(data,0,15000);
                send(data,n);
            } catch (NullPointerException e) {
                // this should append because of concurrency.
            } catch (IOException e) {
                try {
                    handleDisconnection();
                } catch (AbortedConnectionException e1) {
                    e1.printStackTrace();
                }
            }


        }
    }

}
