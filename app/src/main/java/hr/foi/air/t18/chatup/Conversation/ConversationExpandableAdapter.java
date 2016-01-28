package hr.foi.air.t18.chatup.Conversation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.ChatUpPreferences;
import hr.foi.air.t18.core.User;

/**
 * Expandable list adapter for conversations. It divides the conversations into
 * single and group conversations.
 */
public class ConversationExpandableAdapter extends BaseExpandableListAdapter
{
    private Activity activity;
    private HashMap<String, ArrayList<Conversation>> conversations;

    public ConversationExpandableAdapter
    (HashMap<String, ArrayList<Conversation>> conversations, Activity activity)
    {
        this.activity = activity;
        this.conversations = conversations;
    }

    @Override
    public int getGroupCount()
    {
        return conversations.keySet().size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        String key = (String) conversations.keySet().toArray()[groupPosition];
        return conversations.get(key).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        String key = (String) conversations.keySet().toArray()[groupPosition];
        return conversations.get(key);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        String key = (String) conversations.keySet().toArray()[groupPosition];
        Conversation conversation = conversations.get(key).get(childPosition);
        return conversation;
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.conversation_group_layout, null);
        }

        String key = (String) conversations.keySet().toArray()[groupPosition];

        TextView txtGroupName = (TextView) convertView.findViewById(R.id.conversation_group_item);
        txtGroupName.setText(key);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.conversation_list_item_layout, null);
        }

        String key = (String) conversations.keySet().toArray()[groupPosition];

        TextView conversationItem = (TextView) convertView.findViewById(R.id.conversation_list_item);
        ArrayList<User> participants = conversations.get(key).get(childPosition).getParticipants();

        String currentUserUsername = ChatUpPreferences.getDefaults("UserUsername", activity.getApplicationContext());
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

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
