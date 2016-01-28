package hr.foi.air.t18.chatup.Conversation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.Message;


/**
 * An adapter that lists all available messages in one conversation.
 */
public class MessagesListAdapter extends BaseAdapter
{
    private ArrayList<Message> messages;
    private Activity activity;
    private TreeSet imagePositions;

    private final int TYPE_TEXT = 0;
    private final int TYPE_IMAGE = 1;

    /**
     * Constrictor for MessagesListAdapter class.
     * @param messages List of Message objects
     * @param activity Activity that calls this class
     */
    public MessagesListAdapter(ArrayList<Message> messages, Activity activity)
    {
        this.messages = messages;
        this.activity = activity;

        imagePositions = new TreeSet();
        for (int i = 0; i < messages.size(); i++)
        {
            String currentMessageType = messages.get(i).getType();
            if (currentMessageType.equals(Message.IMAGE))
                imagePositions.add(i);
        }
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
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public int getItemViewType(int position)
    {
        return imagePositions.contains(position) ? TYPE_IMAGE : TYPE_TEXT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int type = getItemViewType(position);

        if (convertView == null)
        {
            if (type == TYPE_TEXT)
                convertView = createTextView(messages.get(position));
            else if (type == TYPE_IMAGE)
                convertView = createImageView(messages.get(position));
        }

        return convertView;
    }

    private View createTextView(Message message)
    {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.message_list_item_layout_2, null);

        TextView msgUsername = (TextView) view.findViewById(R.id.msgUsername);
        TextView msgTimeSent = (TextView) view.findViewById(R.id.msgTimeSent);
        TextView msgContent = (TextView) view.findViewById(R.id.msgContent);

        msgUsername.setText(message.getSender());
        msgContent.setText(message.getContent());

        Date date = new Date(Long.parseLong(message.getTimeSend()));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd.MM.yyyy.");
        msgTimeSent.setText(sdf.format(date));

        return view;
    }

    private View createImageView(Message message)
    {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.message_list_item_layout_1, null);

        TextView msgUsername = (TextView) view.findViewById(R.id.msgUsername);
        TextView msgTimeSent = (TextView) view.findViewById(R.id.msgTimeSent);
        ImageView msgContent = (ImageView) view.findViewById(R.id.msgContent);

        msgUsername.setText(message.getSender());

        byte[] imageBytes = Base64.decode(message.getContent(), Base64.NO_WRAP | Base64.URL_SAFE);
        Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        msgContent.setImageBitmap(image);

        Date date = new Date(Long.parseLong(message.getTimeSend()));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd.MM.yyyy.");
        msgTimeSent.setText(sdf.format(date));

        return view;
    }
}
