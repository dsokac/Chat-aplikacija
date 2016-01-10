package hr.foi.air.t18.chatup.Conversation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.core.User;

/**
 * Adapter class used to list all available conversations.
 * Created by Danijel on 25.11.2015..
 */
public class ConversationListAdapter extends BaseAdapter
{
    private ArrayList<Conversation> conversations;
    private Activity activity;

    /**
     * Constructor for ConversationListAdapter class.
     * @param conversations List of Conversation objects
     * @param activity Activity that calls this class
     */
    public ConversationListAdapter(ArrayList<Conversation> conversations, Activity activity)
    {
        this.conversations = conversations;
        this.activity = activity;
    }

    @Override
    public int getCount()
    {
        return conversations.size();
    }

    @Override
    public Object getItem(int position)
    {
        return conversations.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.conversation_list_item_layout, null);
        }

        TextView conversationItem = (TextView) view.findViewById(R.id.conversation_list_item);
        ArrayList<User> participants = conversations.get(position).getParticipants();

        String currentUserUsername = SharedPreferencesClass.getDefaults("UserUsername", activity.getApplicationContext());
        for (int i = 0; i < participants.size(); i++)
        {
            String username = participants.get(i).getUsername();
            if(username.equals(currentUserUsername))
            {
                participants.remove(i);
            }
        }

        StringBuilder builder = new StringBuilder();
        String delimiter = "";
        for (int i = 0; i < participants.size(); i++)
        {
            builder.append(delimiter);
            String username = participants.get(i).getUsername();
            builder.append(username);
            delimiter = ", ";
        }
        conversationItem.setText(builder.toString());

        return view;
    }
}
