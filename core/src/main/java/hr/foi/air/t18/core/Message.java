package hr.foi.air.t18.core;

import java.util.Comparator;

/**
 * Class that represents a message.
 * Created by Danijel on 25.11.2015..
 */
public class Message
{
    private String content;
    private String sender;
    private String timeSend;
    private String location;
    private String type;

    public static final String TEXT = "text";
    public static final String IMAGE = "image";

    /**
     * Constructor for Message class.
     * @param content Text of base63-encoded image
     * @param sender Sender's username
     * @param timeSend Timestamp of the sent message
     * @param location Location from where the message was sent
     * @param type Type of message - text or image
     */
    public Message(String content, String sender, String timeSend, String location, String type)
    {
        this.content = content;
        this.sender = sender;
        this.timeSend = timeSend;
        this.location = location;
        this.type = type;
    }

    /**
     * Returns content of the message - text or base64-encoded image.
     * @return Content of the message
     */
    public String getContent()
    {
        return content;
    }

    /**
     * Returns sender's username.
     * @return Sender's username
     */
    public String getSender()
    {
        return sender;
    }

    /**
     * Timestamp that represents the moment the message was sent.
     * @return Timestamp of the message
     */
    public String getTimeSend()
    {
        return timeSend;
    }

    /**
     * Location from where the message was sent in real world.
     * @return Location of the sent message
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Returns type of message - text or image
     * @return Type of message
     */
    public String getType()
    {
        return type;
    }
}
