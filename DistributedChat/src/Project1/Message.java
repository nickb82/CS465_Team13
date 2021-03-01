package Project1;


public class Message 
{
    String type;
    NodeInfo node;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NodeInfo getNode()
    {
        return node;
    }

    public void setNode(NodeInfo localNode)
    {
        this.node = localNode;
    }
}
