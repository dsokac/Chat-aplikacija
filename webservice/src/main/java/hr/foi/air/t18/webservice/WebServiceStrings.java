package hr.foi.air.t18.webservice;

/**
 * String resource class for 'webservice' module.
 */
public class WebServiceStrings
{
    private WebServiceStrings()
    {
        throw new RuntimeException("Class WebServiceStrings cannot be instantiated.");
    }

    public static final String SERVER = "http://104.236.58.50:8080";

    public static final String ADD_PARTICIPANTS = "/addParticipantsToConversation";
    public static final String CREATE_CONVERSATION = "/createConversation";
    public static final String GET_MESSAGES = "/getMessages";
    public static final String GET_NEW_MESSAGES = "/getNewMessages";
    public static final String SEND_MESSAGE = "/sendMessage";
    public static final String FORGOT_PASSWORD = "/forgotPassword";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String ADD_FRIENDS = "/addFriends";
    public static final String GET_USER_DATA = "/getUserData";
    public static final String GET_FRIENDS = "/getFriends";
    public static final String SAVE_PROFILE_PIC = "/saveProfilePic";
    public static final String REGISTERED_USERS_2 = "/registeredUsers2";
    public static final String EDIT_PROFILE = "/editProfile";
    public static final String GET_USER_DATA_EDIT_PROFILE = "/getUserDataEditProfile";
    public static final String LOGOUT = "/logout";
}
