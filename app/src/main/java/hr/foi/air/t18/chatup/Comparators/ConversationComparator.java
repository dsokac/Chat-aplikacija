package hr.foi.air.t18.chatup.Comparators;

import java.util.Comparator;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.Message;

/**
 * Class that compares two conversations.
 */
public class ConversationComparator implements Comparator<Conversation>
{
    /**
     * Compares two conversations depending on their latest messages.
     * @param conv1 First conversation
     * @param conv2 Second conversation
     * @return comparison between two conversations
     */
    @Override
    public int compare(Conversation conv1, Conversation conv2)
    {
        Message newestMessage1 = newestMessage(conv1);
        Message newestMessage2 = newestMessage(conv2);
        int returnValue;

        if (newestMessage1 == null)
            returnValue = -1;
        else if (newestMessage2 == null)
            returnValue = 1;
        else
        {
            String timeSend1 = newestMessage1.getTimeSend();
            String timeSend2 = newestMessage2.getTimeSend();
            returnValue = timeSend2.compareTo(timeSend1);
        }

        return returnValue;
    }

    /**
     * Returns newest message from conversation.
     * @param conversation Conversation object
     * @return newest message from conversation
     */
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
