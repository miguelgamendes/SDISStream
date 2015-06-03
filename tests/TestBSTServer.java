import BinaryStreamTree.BinaryStreamTreePeer;
import BinaryStreamTree.BinaryStreamTreeServer;

import java.io.IOException;

/**
 * Created by danfergo on 03-06-2015.
 */
public class TestBSTServer {

    public static void main(String args[]) throws IOException {

        BinaryStreamTreeServer server = new BinaryStreamTreeServer();

        server.send("Hello world!".getBytes());
        server.send("This is a simple BinaryStreamTree test.".getBytes());
        server.send("We hope you like this stuff.".getBytes());
        server.send("Use it for a lot of things,.".getBytes());
        server.send("things that use stream., ".getBytes());
        server.send("Tree Stream.".getBytes());
    }
}
