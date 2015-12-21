package hr.foi.air.t18.core;

import java.util.ArrayList;

/**
 * Created by Danijel on 25.11.2015..
 */
public class Conversation
{
    private ArrayList<User> participants;
    private ArrayList<Message> messages;
    private String id;

    public Conversation()
    {
        participants = new ArrayList<User>();
        messages = new ArrayList<Message>();
    }

    public ArrayList<User> getParticipants()
    {
        return participants;
    }

    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    public void addParticipant(User participant)
    {
        participants.add(participant);
    }

    public void addMessage(Message message)
    {
        messages.add(message);
    }

    public void clearMessages()
    {
        messages.clear();
    }

    public String getID()
    {
        return id;
    }

    public void setID(String id)
    {
        this.id = id;
    }
}
