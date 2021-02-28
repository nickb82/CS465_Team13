import java.io.Serializable;

public class NoteMessage extends Message implements Serializable
{
    String localMessage;

    NoteMessage(String message)
    {
        this.localMessage = message;
    }

    public String getMessage()
    {
        return localMessage;
    }
    
}
