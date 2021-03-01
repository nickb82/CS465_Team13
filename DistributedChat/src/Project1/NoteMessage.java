package Project1;

import java.io.Serializable;

public class NoteMessage extends Message implements Serializable
{
    String message;

    NoteMessage(String localMessage)
    {
        this.message = localMessage;
    }

    public String getMessage()
    {
        return message;
    }
    
}
