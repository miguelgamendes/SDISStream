package HttpSecure;

import sun.net.www.protocol.http.Handler;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.URL;

/**
 * Created by danfergo on 27-05-2015.
 */
public class HttpURLSecureConnection extends HttpURLConnection {

    protected HttpURLSecureConnection(URL url, Handler handler) throws IOException {
        super(url, handler);
    }

    //TODO HERE. implement secure http connection.
}
