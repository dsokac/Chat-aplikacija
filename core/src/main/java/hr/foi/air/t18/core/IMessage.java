package hr.foi.air.t18.core;

/**
 * Created by Danijel on 25.11.2015..
 */
public interface IMessage
{
    Object getContent();
    String getSender();
    String getTimeSend();
    String getLocation();
}
