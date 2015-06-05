package HttpSecure;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by danfergo on 05-06-2015.
 */
public class SecureDataOutputStream extends DataOutputStream {

    public SecureDataOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public void encryptAndWrite(byte[] data, int i, int n) throws IOException {
        super.write(data, i, n);
    }
}
