package BinaryStreamTree;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by danfergo on 27-05-2015.
 */
public class BinaryStreamTreePeer extends BinaryStreamTreeNode {

    BinaryStreamTreeRemoteNode parent = null;
    BinaryStreamTreeRemoteNode godfather = null;


    public BinaryStreamTreePeer(InetAddress address) throws IOException {
        super();
        //TODO connect to parent. if it fails, throw some crappy exception. probably implement it in a separated method.
    }

    public BinaryStreamTreePeer(String address) throws IOException {
        this(InetAddress.getByName(address));
    }

    public void receive(byte [] packet) {
        //TODO request video chunks from BinaryStreamTreeRemoteNode parent's socket and throw it true packet paramther to the upper layer.
    }

}
