import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by danfergo on 21-05-2015.
 */
public class TCPServer {

    public static  void main(String args[]) throws IOException {

        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while(true)
        {
            Socket connectionSocket = welcomeSocket.accept();


            FileInputStream file = new FileInputStream(new File("demo.mp4"));
            int content;
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            byte[] bytes = new byte[64000];
            while ((content = file.read(bytes)) != -1) {
                // convert to char and display it
                // System.out.print((char) content);
                outToClient.write(bytes,0,content);
            }



/**
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence); **/
        }
    }
}
