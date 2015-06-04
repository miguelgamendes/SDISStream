package BinaryStreamTree;

import HttpSecure.HttpURLSecureConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by danfergo on 03-06-2015.
 */
public class BinaryStreamTreeRemoteUpperNode extends BinaryStreamTreeRemoteNode{

    ServerSocket socket;
    BufferedReader feed;


    BinaryStreamTreeRemoteUpperNode(String address) throws IOException {
        super(address);
        //HttpURLSecureConnection con = new HttpURLSecureConnection(new URL(address),port); //after this, there is a socket opened in a server peer
        socket = new ServerSocket(0);
        connect(address, socket.getLocalPort());
        Socket conn = socket.accept();
        feed = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    }

    public void connect(String address, int myDataSocketPort) throws IOException {
        URL url;
        //String url = "http://localhost:8000/test";
        //URL obj = new URL(url);
        String fatherInfo = "";
        //while (fatherInfo == "") {
            url = new URL("http://"+address+"/?port="+myDataSocketPort);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();
            System.out.println("asdsad");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            /**while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                String[] res = response.toString().split(":");
                if (res[0] == "CON") {
                    fatherInfo = res[1];
                    return;
                } else if (res[0] == "URL") {
                    url = new URL(res[1]);
                    //TODO connect(url,port);
                    return;
                }
            }**/
            in.close();
        //}
        //return fatherInfo;

    }


    public byte[] receive() {

        try {
            return feed.readLine().getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
