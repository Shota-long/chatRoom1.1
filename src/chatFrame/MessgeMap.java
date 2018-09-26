package chatFrame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MessgeMap {
    private static MessgeMap instance;
    private Map msg;

    private MessgeMap(){
        msg = new HashMap();
    }

    public Queue<String> getMessageQueue(String sender)
    {
        Queue<String> queue = (Queue<String>) msg.get(sender);
        return queue;
    }

    public void addMessage(String sender, String data)
    {
        if (msg.containsKey(sender))
        {
            Queue<String> queue = (Queue<String>) msg.get(sender);
            queue.offer(data);
        }
        else
        {
            Queue<String> queue = new LinkedList<>();
            queue.offer(data);
            msg.put(sender, queue);
        }
    }

    public static MessgeMap getInstance(){
        if (instance == null)
            instance = new MessgeMap();
        return instance;
    }
}
