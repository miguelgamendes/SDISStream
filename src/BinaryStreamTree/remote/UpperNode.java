package BinaryStreamTree.remote;

import HttpSecure.SecureDataInputStream;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created by danfergo on 03-06-2015.
 */
public class UpperNode extends RemoteNode {
    PublicKey pubKey;
    ServerSocket socket;
    SecureDataInputStream feed;
    int httpPort;

    public int getHttpPort(){
        return httpPort;
    }

    public UpperNode(String address, int httpport) throws IOException {

        super(address, httpport);
        this.httpPort = httpport;
        socket = new ServerSocket(0);
    }

    public int getSocketPort(){

       return socket.getLocalPort();
    }

    public void accept() throws IOException {

        Socket conn = socket.accept();
        feed = new SecureDataInputStream(conn.getInputStream());
    }

    public UpperNode connect(String address, int myDataSocketPort) throws IOException {
        URL url;
        UpperNode godfather = null;
        try{
            url = new URL("http://"+address+"/connect/?socket_port="+myDataSocketPort+"&http_port="+httpPort);
            System.out.println("Try address: "+url);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
            con.connect();
            System.out.println("Success address: " + con.getURL());

            String key,ln;
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            StringBuilder sb = new StringBuilder();
            while ((ln = br.readLine()) != null) {
                sb.append(ln);
            }


            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            pubKey = keyFactory.generatePublic(keySpec);


            String gf = con.getHeaderField("Godfather");
            if(gf != null){
                String add = gf.split(":")[0];
                int port = Integer.parseInt(gf.split(":")[1]);
                godfather = new UpperNode(add,port);
                System.out.println("My godfather is: "+godfather.getAddress()+":"+godfather.getPort());
            } else {
                System.out.println("I have no godfather.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return godfather;
    }


    public int readEncrypted(byte b[], int off, int len) throws IOException {
        return feed.readEncrypted(b, off, len);
    }

    public byte[] receive(int bytes) throws IOException{

        byte buf[]  = new byte[bytes];
        int missingBytes = bytes, n;

        do {

            //n = feed.read(buf,bytes-missingBytes,missingBytes); #Uncomment_This_To_Turn_On_Encryption
            n = feed.readDecrypted(buf,bytes-missingBytes,missingBytes,pubKey); // comment this then
            if(n == -1) {
                if(missingBytes == bytes) return null;
                byte reBuf[] = new byte[bytes - missingBytes];
                System.arraycopy(buf,0,reBuf,0,bytes - missingBytes);
            }
            missingBytes -=  n;
        }while(missingBytes > 0);

        return buf;


    }

}
