package hr.foi.air.t18.chatup.Comparators;

import java.util.Comparator;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.Message;

public class ConversationComparator implements Comparator<Conversation>
{
    @Override
    public int compare(Conversation conv1, Conversation conv2)
    {
        String timeSend1 = newestMessage(conv1).getTimeSend();
        String timeSend2 = newestMessage(conv2).getTimeSend();
        return timeSend2.compareTo(timeSend1);
    }

    private Message newestMessage(Conversation conversation)
    {
        Message message = null;
        String maxTimestamp = "0";

        for (int i = 0; i < conversation.getMessages().size(); i++)
        {
            String messageTimestamp = conversation.getMessages().get(i).getTimeSend();
            if (messageTimestamp.compareTo(maxTimestamp) > 0)
            {
                message = conversation.getMessages().get(i);
                maxTimestamp = messageTimestamp;
            }
        }

        return message;
    }
}
