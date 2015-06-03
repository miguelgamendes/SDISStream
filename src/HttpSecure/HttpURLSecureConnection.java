package HttpSecure;

import sun.net.www.protocol.http.Handler;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by danfergo on 27-05-2015.
 */
public class HttpURLSecureConnection {

    public HttpURLSecureConnection(URL url, int port) throws IOException{
        //String url = "http://localhost:8000/test";
        //URL obj = new URL(url);
        String fatherInfo = "";
        while (fatherInfo == "") {
            url = new URL(url.toString()+"?port="+port);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                String[] res = response.toString().split(":");
                if (res[0] == "CON") {
                    fatherInfo = res[1];
                } else if (res[0] == "URL") {
                    url = new URL(res[1]);
                }
            }
            in.close();
        }
        //return fatherInfo;

    }

    //TODO HERE. implement secure http connection.
}
