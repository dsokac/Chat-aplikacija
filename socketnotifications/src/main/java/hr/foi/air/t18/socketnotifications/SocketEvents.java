package hr.foi.air.t18.socketnotifications;

/**
 * Created by Danijel on 23.1.2016..
 */
public enum SocketEvents {
    friendRequest("friendRequest"),
    newMessage("newMessage"),
    registration("registration"),
    userDisconnected("userDisconnected"),
    serverDisconnected("serverDisconnected");

    private String eventString;

    SocketEvents(String event) {
        this.eventString = event;
    }

    protected String getEvent()
    {
        return this.eventString;
    }
}
