import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String[] a) throws Exception {

        Socket s = new Socket("127.0.0.1", 4000);
        System.out.println("Server ready for Connection");

        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println("Enter the filename");
        out.println(kb.readLine());

        String line;
        while ((line = in.readLine()) != null)
            System.out.println(line);

        s.close();
    }
}

