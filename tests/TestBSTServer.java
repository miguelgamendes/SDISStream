import BinaryStreamTree.BinaryStreamTreePeer;
import BinaryStreamTree.BinaryStreamTreeServer;

import java.io.IOException;

/**
 * Created by danfergo on 03-06-2015.
 */
public class TestBSTServer {

    public static void main(String args[]) throws IOException, InterruptedException {

        BinaryStreamTreeServer server = new BinaryStreamTreeServer(15001);
        for (int i =0; i >= 0; i++) {
           // server.send("Hello world!\n".getBytes());
           // server.send("This is a simple BinaryStreamTree test.\n".getBytes());
           // server.send("We hope you like this stuff.\n".getBytes());
            //server.send("Use it for a lot of things,.\n".getBytes());
            //server.send("things that use stream.\n".getBytes());
            //server.send("Tree Stream.\n".getBytes());
            byte xx[] = new byte[1];
            xx[0] = (byte)i;
            server.send(xx);
            System.out.println(xx[0]);
            Thread.sleep(100);
        }
    }
}
