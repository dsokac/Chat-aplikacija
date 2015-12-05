package hr.foi.air.t18.core;

/**
 * Created by Danijel on 25.11.2015..
 */
public class MessageText implements IMessage
{
    private String content;
    private String sender;
    private String timeSend;
    private String location;

    public MessageText(String content, String sender, String timeSend, String location)
    {
        this.content = content;
        this.sender = sender;
        this.timeSend = timeSend;
        this.location = location;
    }

    @Override
    public String getContent()
    {
        return content;
    }

    @Override
    public String getSender()
    {
        return sender;
    }

    @Override
    public String getTimeSend()
    {
        return timeSend;
    }

    @Override
    public String getLocation()
    {
        return location;
    }
}
