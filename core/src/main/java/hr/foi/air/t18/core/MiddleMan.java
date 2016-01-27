package hr.foi.air.t18.core;

import java.util.Objects;

/**
 * Class used to temporarily store required data
 * when switching between activities.
 *
 * Created by Danijel on 3.12.2015..
 */
public class MiddleMan
{
    private static Object _object;
    private static Object _conversationObject;
    private static Object _notificationObject;
    private static Object _userObject;

    /**
     * Stores the object needed to transfer.
     * @param object Object needed to transfer
     */
    public static void setObject(Object object)
    {
        _object = object;
    }

    /**
     * Gets the stored object.
     * @return Stored object
     */
    public static Object getObject()
    {
        return _object;
    }

    public static void setConversationObject(Object object){_conversationObject = object;}

    public static Object getConversationObject(){return _conversationObject;}

    public static void setNotificationObject(Object object){_notificationObject = object;}

    public static Object getNotificationObject(){return _notificationObject;}

    public static void setUserObject(Object object){_userObject = object;}

    public static Object getUserObject(){return _userObject;}
}
