package hr.foi.air.t18.core;

import java.util.Comparator;

/**
 * Created by Danijel on 5.12.2015..
 */
public class MessageComparator implements Comparator<Message>
{
    @Override
    public int compare(Message msg1, Message msg2)
    {
        String timeSend1 = msg1.getTimeSend();
        String timeSend2 = msg2.getTimeSend();
        return timeSend1.compareTo(timeSend2);
    }
}
