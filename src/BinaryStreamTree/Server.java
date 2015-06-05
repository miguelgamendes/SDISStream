package BinaryStreamTree;

import java.io.IOException;

/**
 * Created by danfergo on 27-05-2015.
 */
public class Server extends Node {

    public Server(int BS3PPort) throws IOException {
        super(BS3PPort);
    }

    public void send(byte [] data, int n) {
        super.send(data,n);
    }

}
