import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8083);

        Set<Socket> clients = new HashSet<>();

        while (true) {
            Socket accept = ss.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream = accept.getInputStream();
                        OutputStream outputStream = accept.getOutputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                        while (true) {
                            String line = br.readLine();
                            if ("exit".equals(line))
                                break;
                            for (Socket s : clients) {
                                s.getOutputStream().write(line.getBytes());
                            }

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

            clients.add(accept);

        }
    }
}
