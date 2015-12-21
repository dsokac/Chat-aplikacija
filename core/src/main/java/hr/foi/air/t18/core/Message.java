package hr.foi.air.t18.core;

import java.util.Comparator;

/**
 * Created by Danijel on 25.11.2015..
 */
public class Message
{
    private String content;
    private String sender;
    private String timeSend;
    private String location;

    public Message(String content, String sender, String timeSend, String location)
    {
        this.content = content;
        this.sender = sender;
        this.timeSend = timeSend;
        this.location = location;
    }

    public String getContent()
    {
        return content;
    }

    public String getSender()
    {
        return sender;
    }

    public String getTimeSend()
    {
        return timeSend;
    }

    public String getLocation()
    {
        return location;
    }
}
