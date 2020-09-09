package inc.app.mes.ui.edit;

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

public class EditFragment extends Fragment {

    private RecyclerView editRecycler;
    private RecyclerViewAdapter editAdapter;
    public ArrayList<String> title = new ArrayList<String>();
    public ArrayList<String> state = new ArrayList<String>();
//    public static ArrayList<String> context = new ArrayList<String>();
//    public int itemSize;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        editRecycler = (RecyclerView)root.findViewById(R.id.EditRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        editAdapter = new RecyclerViewAdapter(title, state);

        editRecycler.setLayoutManager(linearLayoutManager);

        ItemRecyclerView data1 = new ItemRecyclerView("title1", "date");
        editAdapter.addItem(data1);

        ItemRecyclerView data2 = new ItemRecyclerView("title2", "date");
        editAdapter.addItem(data2);

        ItemRecyclerView data3 = new ItemRecyclerView("title3", "date");
        editAdapter.addItem(data3);

        editRecycler.setAdapter(editAdapter);

        return root;
    }


}