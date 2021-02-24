import java.io.Socket;
import java.io.IOException;



class Send_Thread implements Runnable
{

    Scanner scanInput = new Scanner(System.in);
    System.out.println("To join the chat pleae enter: JOIN, followed by an IP Address and port number");
    
    String userInput = scanInput.nextline();
    String argParse[] = userInput.split(" ");
    
    String joinState = argParse[0];
    int port = Integer.parseInt(argParse[2]);
    String ipAdress = (argParse[1]);
    

    // if statement // jpoinStet....something else
    //socket created

    Socket sock = new Socket(ipAdress, port);

    // Scanner input = new Scanner(sock.getInputStream());

    PrintStream pr = new PrintStream(sock.getOutputStream());

    //pass it to server
    pr.println( );


    //receieve the result from the server
    temp = input.next();

    
}