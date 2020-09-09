package inc.app.mes.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import inc.app.mes.R;
import inc.app.mes.recycler.ItemRecyclerView;
import inc.app.mes.recycler.RecyclerViewAdapter;

public class HomeFragment extends Fragment {

    private RecyclerView homeRecycler;
    private RecyclerViewAdapter homeAdapter;
    public ArrayList<String> title = new ArrayList<String>();
    public ArrayList<String> state = new ArrayList<String>();
    public static ArrayList<String> context = new ArrayList<String>();
    public int itemSize;
    public List<ItemRecyclerView> ItemViews;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeRecycler = (RecyclerView)root.findViewById(R.id.HomeRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        homeAdapter = new RecyclerViewAdapter(title, state);

        homeRecycler.setLayoutManager(linearLayoutManager);
        ItemViews = new ArrayList<>();
        for( int i=0; i<12;++i){
            ItemRecyclerView temp = new ItemRecyclerView("ddd"+(i+1), "date"+(i+1));
            ItemViews.add(temp);
        }

        for( int i=0; i<12;++i){
            homeAdapter.addItem(ItemViews.get(i));
        }

        homeRecycler.setAdapter(homeAdapter);

        return root;
    }
}