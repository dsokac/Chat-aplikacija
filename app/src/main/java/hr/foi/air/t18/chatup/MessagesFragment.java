package hr.foi.air.t18.chatup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Simple Fragment where you can view messages
 * Created by Laptop on 9.11.2015..
 */
public class MessagesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_messages, container, false);
        View root = inflater.inflate(R.layout.tab_fragment_messages, container, false);
        ArrayList<String> single_conversations = new ArrayList<>();
        single_conversations.add("Goran Drmencic");
        single_conversations.add("Danijel Sokač");
        single_conversations.add("Matija Jurman");
        single_conversations.add("Danijel Filipović");
        single_conversations.add("posao ante");
        single_conversations.add("susjed");
        single_conversations.add("drugi susjed");

        ListView lv =  (ListView)root.findViewById(R.id.single_conversationListView);
        lv.setAdapter(new UserListAdapter(getActivity(),single_conversations ));
        return root;
    }
}
