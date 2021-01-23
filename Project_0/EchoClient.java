import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient 
{
    public static void main(String[] args)
    {
        System.out.println("(Client) Echo Client ...");

        try
        {
        Socket socket = new Socket(InetAddress.getLocalHost(),8000);
        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
        DataInputStream fromServer = new DataInputStream(socket.getInputStream());
        boolean clientOn = true;

        //Report success of client server connection
        System.out.println("(Client) We are connected to server.");

        while(clientOn)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please Enter message: ");
            String input = scanner.nextLine();

            System.out.println("(Client) Write to Server");

            //Send user input to the server
            toServer.writeChars(input);

            char[] response = new char[input.length()];
            System.out.print("(Client) Server response is: ");
            System.out.println("");
            int index = 0;

            //Read from thread on server side
            while(fromServer.available() > 0)
            {
                response[index] = Character.toLowerCase(fromServer.readChar());
                System.out.print(response[index]);
                index++;
            }

            //Used to clean display for return of server input
            System.out.println();


            //check for quit causing termination of client
            if(response[0] == 'q' && response[1] == 'u' && response[2] == 'i'  && response[3] == 't')
            {

                System.out.println("Client Session Terminated");
                toServer.close();
                fromServer.close();
                socket.close();
                clientOn = false;
            }
        }
    }
    
    //Report if client socket has errors connecting
    catch(Exception e)
    {
        e.printStackTrace();
    }

        
    }
}


