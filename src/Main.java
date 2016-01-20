import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8083);

        while (true) {
            Socket accept = ss.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            accept.getOutputStream().write("Hello".getBytes());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
