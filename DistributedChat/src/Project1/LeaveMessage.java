package Project1;

import java.io.Serializable;

public class LeaveMessage extends Message implements Serializable
{
    public void leaveAlert()
    {
        System.out.printf("Attempting to leave chat");
    }
    
}
