import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8083);

        Socket accept = ss.accept();

        accept.getOutputStream().write("Hello".getBytes());

        accept.close();
    }
}
