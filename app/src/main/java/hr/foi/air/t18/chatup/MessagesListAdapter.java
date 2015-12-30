package hr.foi.air.t18.chatup;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hr.foi.air.t18.core.Message;


/**
 * An adapter that lists all available messages in one conversation.
 *
 * Created by Danijel on 3.12.2015..
 */
public class MessagesListAdapter extends BaseAdapter
{
    private ArrayList<Message> messages;
    private Activity activity;

    /**
     * Constrictor for MessagesListAdapter class.
     * @param messages List of Message objects
     * @param activity Activity that calls this class
     */
    public MessagesListAdapter(ArrayList<Message> messages, Activity activity)
    {
        this.messages = messages;
        this.activity = activity;
    }

    @Override
    public int getCount()
    {
        return messages.size();
    }

    @Override
    public Object getItem(int position)
    {
        return messages.get(position);
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

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.message_list_item_layout_2, null);
        }

        TextView msgUsername = (TextView) view.findViewById(R.id.msgUsername);
        TextView msgTimeSent = (TextView) view.findViewById(R.id.msgTimeSent);
        TextView msgContent = (TextView) view.findViewById(R.id.msgContent);

        Message message = messages.get(position);
        msgUsername.setText(message.getSender());
        msgContent.setText((String) message.getContent());

        Date date = new Date(Long.parseLong(message.getTimeSend()));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd.MM.yyyy.");
        msgTimeSent.setText(sdf.format(date));

        return view;
    }
}
