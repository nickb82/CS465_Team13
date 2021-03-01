package Project1;

import java.io.Serializable;

public class JoinedMessage extends Message implements Serializable
{
    public void joinedAlert()
    {
        System.out.printf("Adding node to list of participants");
    }
}
