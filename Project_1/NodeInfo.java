public class NodeInfo
{
    int portNum;
    String clientName, messageToSend, IP_Adress;

    NodeInfo(String name, String ipAdress, int portNum)
    {
        this.clientName = name;
        this.IP_Adress = ipAdress;
        this.portNum = portNum;
    }

    public String getIPAdress() 
    {
        return IP_Adress;
    }

    public String getName() {
        return clientName;
    }

    public int getPortNum()
    {
        return portNum;
    }

}