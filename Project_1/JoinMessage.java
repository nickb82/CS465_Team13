public class JoinMessage extends Message 
{
    NodeInfo localNodeInfo;

    JoinMessage(NodeInfo nodeInfo)
    {
        this.localNodeInfo = nodeInfo;
    }
}
