package hr.foi.air.t18.chatup.Conversation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hr.foi.air.t18.chatup.R;

/**
 * Created by Jurman_Lap on 15.11.2015..
 */
public class SingleConversationListAdapter extends BaseAdapter {
    private ArrayList<String> single_conversations;
    private LayoutInflater inflater;

    public  SingleConversationListAdapter(Context fragment, ArrayList<String> results)
    {
        this.single_conversations = results;
        this.inflater = LayoutInflater.from(fragment);
    }

    @Override
    public int getCount() {
        return this.single_conversations.size();
    }

    @Override
    public Object getItem(int position) {
        return this.single_conversations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView single_conversation_name;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.single_conversation_list_item_layout, null);
            single_conversation_name = (TextView)convertView.findViewById(R.id.conversationName);

            convertView.setTag(single_conversation_name);
        }
        else
        {
            single_conversation_name = (TextView) convertView.getTag();
        }

        single_conversation_name.setText(single_conversations.get(position).toString());

        return convertView;
    }
}