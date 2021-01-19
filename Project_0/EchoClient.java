import java.io.*;
import java.net.*;
import java.util.*;

//more comments to come
public class EchoClient 
{
    public static void main(String[] args)
    {
        System.out.println("(Client) Echo Client ...");

        try
        {
        Socket socket = new Socket(InetAddress.getLocalHost(),8000);
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Report success of client server connection
        System.out.println("(Client) We are connected to server.");
        Scanner scanner = new Scanner(System.in);

        while(true)
        {
            System.out.println("Please Enter message: ");
            String input = scanner.nextLine();

            //valid alphabetical array 
            char[] alphArray = new char[input.length()];
            int alphIndex = 0;

            //check for valid inpuit
            for(int index = 0; index < input.length(); index++)
            {
                // if lowercase of uppercase letters
                if((input.charAt(index) >= 'a' && input.charAt(index) <= 'z')  || (input.charAt(index) >= 'A' && input.charAt(index) <= 'Z'))
                {
                    alphArray[alphIndex] = input.charAt(index);
                    alphIndex++;
                    //System.out.print(alphArray[index]);
                }
            }

            //check for quit causing termination of client connection and thread
            if(alphArray[0] == 'q' && alphArray[1] == 'u' && alphArray[2] == 'i' && alphArray[3] == 't')
            {
                toServer.println("quit");
                socket.close();
                break;
            }

            // Send message to server and wait for feedback from server
            else
            {
                System.out.println("(Client) Prininting to Server");
                //Send user input to the server
                toServer.println(alphArray);
                String response = fromServer.readLine();
                System.out.println("(Client) Server response is: " + response);
                
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


