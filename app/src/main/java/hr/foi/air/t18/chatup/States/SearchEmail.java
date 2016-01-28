package hr.foi.air.t18.chatup.States;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hr.foi.air.t18.chatup.RegisteredUsersListAdapter;
import hr.foi.air.t18.state.Context;
import hr.foi.air.t18.state.IState;
import hr.foi.air.t18.core.User;

/**
 * Created by Danijel on 27.1.2016..
 */
public class SearchEmail implements IState {

    ArrayList<User> search;
    ArrayList<User> reg_users;
    ListView lv;
    TextView search_text;
    Activity activity;

    public SearchEmail(ArrayList<User> list, ListView lv, TextView search_text, Activity activity, ArrayList<User> reg_users)
    {
        this.search = new ArrayList<>();
        this.reg_users = new ArrayList<>();
        this.lv = lv;
        this.activity = activity;

        this.search_text = search_text;
        this.search = list;
        this.reg_users = reg_users;
    }

    @Override
    public void applyChange(final Context context, View view) {
        Button btn = (Button)view;

        btn.setText("Find e-mail");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_users.clear();
                for (int i = 0; i < search.size(); i++) {
                    if(search.get(i).getEmail().contains(search_text.getText().toString())){
                        reg_users.add(search.get(i));
                    }

                }
                lv.setAdapter(new RegisteredUsersListAdapter(activity, reg_users));
            }
        });
    }

    @Override
    public Object getData() {
        return this.reg_users;
    }
}
