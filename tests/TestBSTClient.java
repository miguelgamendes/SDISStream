import BinaryStreamTree.AbortedConnectionException;
import BinaryStreamTree.Peer;

import java.io.IOException;

/**
 * Created by danfergo on 03-06-2015.
 */
public class TestBSTClient {

    public static void main(String args[]) throws IOException, AbortedConnectionException {

        Peer client = new Peer(15010,"localhost:15000");

        while(true){
            System.out.println(client.receive(1)[0]);
        }

    }
}
