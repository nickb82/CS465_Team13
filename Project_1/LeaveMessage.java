public class LeaveMessage extends Message
{

    NodeInfo localNodeInfo;

    LeaveMessage(NodeInfo nodeInfo)
    {
        this.localNodeInfo = nodeInfo;
    }
    
}
