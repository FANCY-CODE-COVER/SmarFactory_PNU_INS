package inc.app.mes.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import inc.app.mes.R;
import inc.app.mes.recycler.ItemRecyclerView;
import inc.app.mes.recycler.RecyclerViewAdapter;

public class NotificationsFragment extends Fragment {

    private RecyclerView notificationRecycler;
    private RecyclerViewAdapter notificationAdapter;
    public ArrayList<String> title = new ArrayList<String>();
    public ArrayList<String> state = new ArrayList<String>();
    public static ArrayList<String> context = new ArrayList<String>();
    //    NotificationData notidata;
    public int itemSize;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationRecycler = (RecyclerView)root.findViewById(R.id.NotificationRecycler);
//        notificationRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        notificationAdapter = new RecyclerViewAdapter(title, state);

        notificationRecycler.setLayoutManager(linearLayoutManager);

        ItemRecyclerView data1 = new ItemRecyclerView("title1", "date");
        notificationAdapter.addItem(data1);

        ItemRecyclerView data2 = new ItemRecyclerView("title2", "date");
        notificationAdapter.addItem(data2);

        ItemRecyclerView data3 = new ItemRecyclerView("title3", "date");
        notificationAdapter.addItem(data3);

        ItemRecyclerView data4 = new ItemRecyclerView("title4", "date");
        notificationAdapter.addItem(data4);

        ItemRecyclerView data5 = new ItemRecyclerView("title5", "date");
        notificationAdapter.addItem(data5);

        ItemRecyclerView data6 = new ItemRecyclerView("title6", "date");
        notificationAdapter.addItem(data6);

        ItemRecyclerView data7 = new ItemRecyclerView("title7", "date");
        notificationAdapter.addItem(data7);

        ItemRecyclerView data8 = new ItemRecyclerView("title8", "date");
        notificationAdapter.addItem(data8);

        ItemRecyclerView data9 = new ItemRecyclerView("title9", "date");
        notificationAdapter.addItem(data9);

        ItemRecyclerView data10 = new ItemRecyclerView("title10", "date");
        notificationAdapter.addItem(data10);

        ItemRecyclerView data11 = new ItemRecyclerView("title11", "date");
        notificationAdapter.addItem(data11);

        ItemRecyclerView data12 = new ItemRecyclerView("title12", "date");
        notificationAdapter.addItem(data12);

        notificationRecycler.setAdapter(notificationAdapter);

        return root;
    }
}