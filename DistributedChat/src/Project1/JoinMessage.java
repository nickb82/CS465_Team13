package Project1;

import java.io.Serializable;

public class JoinMessage extends Message implements Serializable
{
    public void joinAlert()
    {
        System.out.printf("Accessing list of participants");
    }
}
