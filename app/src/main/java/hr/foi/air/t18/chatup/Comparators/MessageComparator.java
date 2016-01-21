package hr.foi.air.t18.chatup.Comparators;

import java.util.Comparator;

import hr.foi.air.t18.core.Message;

/**
 * Comparator class used for comparing the timestamp
 * of two messages.
 *
 * Created by Danijel on 5.12.2015..
 */
public class MessageComparator implements Comparator<Message>
{
    /**
     * Compares two messages by their timestamp
     * @param msg1 First Message object
     * @param msg2 Second Message object
     * @return Comparison between two messages
     */
    @Override
    public int compare(Message msg1, Message msg2)
    {
        String timeSend1 = msg1.getTimeSend();
        String timeSend2 = msg2.getTimeSend();
        return timeSend1.compareTo(timeSend2);
    }
}
