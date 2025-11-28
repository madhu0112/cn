import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String args[]) throws Exception {

        // Connect to server at localhost:4000
        Socket sock = new Socket("127.0.0.1", 4000);
        System.out.println("Enter the filename");

        // To read filename from keyboard
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        String fname = keyRead.readLine();

        // Send filename to the server
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        pwrite.println(fname);

        // Read server response (file content)
        InputStream istream = sock.getInputStream();
        BufferedReader socketRead = new BufferedReader(new InputStreamReader(istream));

        String str;
        while ((str = socketRead.readLine()) != null) {
            System.out.println(str);
        }

        pwrite.close();
        socketRead.close();
        keyRead.close();
        sock.close();
    }
}

