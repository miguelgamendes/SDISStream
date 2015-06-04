package BinaryStreamTree;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by danfergo on 27-05-2015.
 */
public class BinaryStreamTreeServer extends BinaryStreamTreeNode {

    public BinaryStreamTreeServer(int BS3PPort) throws IOException {
        super(BS3PPort);
    }

    public void send(byte [] data, int n) {
        super.send(data,n);
    }

}
