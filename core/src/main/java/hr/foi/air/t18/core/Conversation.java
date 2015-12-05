package hr.foi.air.t18.core;

import java.util.ArrayList;

/**
 * Created by Danijel on 25.11.2015..
 */
public class Conversation
{
    private ArrayList<User> participants;
    private ArrayList<IMessage> messages;

    public Conversation()
    {
        participants = new ArrayList<User>();
        messages = new ArrayList<IMessage>();
    }

    public ArrayList<User> getParticipants()
    {
        return participants;
    }

    public ArrayList<IMessage> getMessages()
    {
        return messages;
    }

    public void addParticipant(User participant)
    {
        participants.add(participant);
    }

    public void addMessage(IMessage message)
    {
        messages.add(message);
    }
}
