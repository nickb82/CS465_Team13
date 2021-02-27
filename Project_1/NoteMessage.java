public class NoteMessage extends Message
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
