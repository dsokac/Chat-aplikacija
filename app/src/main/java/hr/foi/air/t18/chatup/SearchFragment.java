package hr.foi.air.t18.chatup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Simple Fragment for searching other users
 * Created by Laptop on 9.11.2015..
 */
public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View search_view = inflater.inflate(R.layout.tab_fragment_search, container, false);
        Button search_button = (Button) search_view.findViewById(R.id.searchButton);
        search_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //search logic here
                Toast.makeText(getContext(),"You clicked search" , Toast.LENGTH_LONG).show();
            }
        });
        return search_view;
        // return inflater.inflate(R.layout.tab_fragment_search, container, false);
    }
}