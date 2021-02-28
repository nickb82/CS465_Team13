import java.util.Scanner;

public class testScanner 
{

    public static void main(String[] args)
    {
        System.out.println("Enter a message:");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();

        System.out.println("Scanner 1 returns: " + input);

        System.out.println("Enter a message:");
        userInput = new Scanner(System.in);
        String input2 = userInput.nextLine();

        System.out.println("Scanner 2 returns: " + input2);
    }
    
}
