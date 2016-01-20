import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Main {
    static Set<Socket> clients = new HashSet<>();

    static synchronized void addClient(Socket client) {
        clients.add(client);
    }

    static synchronized void removeClient(Socket client) {
        clients.remove(client);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8083);

        while (true) {
            Socket accept = ss.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        addClient(accept);

                        InputStream inputStream = accept.getInputStream();
                        OutputStream outputStream = accept.getOutputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                        while (true) {
                            String line = br.readLine();
                            if (line == null || "exit".equals(line))
                                break;
                            for (Socket s : clients) {
                                System.out.println("Got line:\n" + line);
                                try {
                                    s.getOutputStream().write((line+"\n").getBytes());
                                } catch (IOException e) {
                                }
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
                    removeClient(accept);
                }
            }).start();

        }
    }
}
