package hr.foi.air.t18.chatup;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.zip.Inflater;

import hr.foi.air.t18.core.User;

/**
 * Created by Danijel on 14.11.2015..
 */
public class UserListAdapter extends BaseAdapter {
    private ArrayList<String> friends;
    private LayoutInflater inflater;

    public UserListAdapter(Context fragment, ArrayList<String> results)
    {
        this.friends = results;
        this.inflater = LayoutInflater.from(fragment);
    }

    @Override
    public int getCount() {
        return this.friends.size();
    }

    @Override
    public Object getItem(int position) {
        return this.friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView username;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.friend_list_item_layout, null);
            username = (TextView)convertView.findViewById(R.id.friendUsername);

            convertView.setTag(username);
        }
        else
        {
            username = (TextView) convertView.getTag();
        }

        username.setText(friends.get(position).toString());

        return convertView;
    }
}
