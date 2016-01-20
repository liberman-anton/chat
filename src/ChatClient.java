import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 8083);
        Scanner sc = new Scanner(System.in);

        OutputStream outputStream = s.getOutputStream();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = s.getInputStream();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(inputStream));
                    while (true){
                        String s1 = br.readLine();
                        System.out.println("NET: " + s1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println("Sending line:\n"+line);
                outputStream.write((line+"\n").getBytes());
            }

        }
    }
}
