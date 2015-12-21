package hr.foi.air.t18.chatup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import hr.foi.air.t18.core.User;

/**
 * Created by JurmanLap on 20.12.2015..
 */
public class RegisteredUsersListAdapter extends BaseAdapter {
    private ArrayList<User> reg_users;
    private LayoutInflater inflater;

    public RegisteredUsersListAdapter(Context fragment, ArrayList<User> results)
    {
        this.reg_users = results;
        this.inflater = LayoutInflater.from(fragment);
    }

    @Override
    public int getCount() {
        return this.reg_users.size();
    }

    @Override
    public Object getItem(int position) {
        return this.reg_users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomListItem list = new CustomListItem();
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.registered_users_list_item_layout, null);
            list.txtUsername = (TextView)convertView.findViewById(R.id.registeredUserUsername);
            convertView.setTag(list);
        }
        else
        {
            list = (CustomListItem) convertView.getTag();
        }
        list.txtUsername.setText(reg_users.get(position).getUsername());
        return convertView;
    }
}