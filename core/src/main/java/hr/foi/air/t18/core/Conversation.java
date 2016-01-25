package hr.foi.air.t18.core;

import java.util.ArrayList;

import hr.foi.air.t18.socketnotifications.SocketNotificationsManager;

/**
 * Class that represents a conversation.
 *
 * Created by Danijel on 25.11.2015..
 */
public class Conversation
{
    private ArrayList<User> participants;
    private ArrayList<Message> messages;
    private String id;
    /**
     * Constructor for Conversation class.
     */
    public Conversation()
    {
        participants = new ArrayList<User>();
        messages = new ArrayList<Message>();
    }

    /**
     * Returns list of User objects that participate
     * in a conversation.
     * @return List of User objects
     */
    public ArrayList<User> getParticipants()
    {
        return participants;
    }

    /**
     * Returns list of Message objects.
     * @return List of Message objects
     */
    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    /**
     * Adds a User object as a participant in
     * conversation
     * @param participant User object
     */
    public void addParticipant(User participant)
    {
        participants.add(participant);
    }

    /**
     * Adds a Message object into the Conversation.
     * @param message Message object
     */
    public void addMessage(Message message)
    {
        messages.add(message);
    }

    /**
     * Deletes all Message objects.
     */
    public void clearMessages()
    {
        messages.clear();
    }

    /**
     * Returns the ID of Conversation. The ID is created
     * in database.
     * @return Conversation ID
     */
    public String getID()
    {
        return id;
    }

    /**
     * Sets new Conversation ID
     * @param id Conversation ID
     */
    public void setID(String id)
    {
        this.id = id;
    }
}
