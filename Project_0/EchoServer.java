import java.io.*;
import java.net.*;

public class EchoServer {

    public static void main(String args[]) {
        System.out.println("Echo Server is started!");
        try (ServerSocket echoServer = new ServerSocket(8000);) {
            while (true) {
                Socket clientSocket = echoServer.accept();
                new Thread(new EchoThread(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class EchoThread implements Runnable {
    private Socket socket;
    private long threadId;
    private BufferedReader fromClient;
    private char charFromClient;
    private PrintWriter toClient;

    EchoThread(Socket socket) {
        this.socket = socket;
    }

    public long getThreadId() {
        threadId = Thread.currentThread().getId();
        return threadId;
    }

    @Override
    public void run() {
        try {
            int i;
            // sets up the input and output streams
            fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toClient = new PrintWriter(socket.getOutputStream(), true);
            while ((i = fromClient.read()) != -1) {
                charFromClient = (char) i; // converts an int to a char
                toClient.println(charFromClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
