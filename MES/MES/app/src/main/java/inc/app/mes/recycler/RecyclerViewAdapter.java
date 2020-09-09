package inc.app.mes.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import inc.app.mes.MainActivity;
import inc.app.mes.R;
import inc.app.mes.ui.notifications.NotificationsFragment;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mListData = new ArrayList<>();
    private ArrayList<String> mListState = new ArrayList<>();

    public RecyclerViewAdapter(ArrayList<String> listData, ArrayList<String> context) {
        mListData = listData;
        mListState = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView state;
        Context context;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            state = itemView.findViewById(R.id.state);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Toast.makeText(v.getContext(), pos+"클릭됨", Toast.LENGTH_SHORT).show();
                    NotificationsFragment NF = new NotificationsFragment();

//                    Intent intent = new Intent(v.getContext(), MainActivity.class);
////                    intent.putExtra("context",context);
//                    intent.putExtra("pos",pos);
//                    if(pos != RecyclerView.NO_POSITION) {
//                        v.getContext().startActivity(intent);
//                    }

                }
            });

        }
    }
//        void onBind(ItemRecyclerVertical data) {
//            date.setText(data.getTitle());
//            if (data.getTitle().length() > 15) {
//                String first = data.getTitle().substring(0, 15);
//                String second = data.getTitle().substring(15, data.getTitle().length());
//                data.setTitle(first + "\n" + second);
//            }
//            if (data.getDate() != "") {
//                data.setTitle(data.getTitle() + "\n" + data.getDate());
//            }
//            title.setText(data.getTitle());
//            date.setText(data.getDate());
//       }

//
//    public RecyclerViewAdapter(ArrayList<ItemRecyclerView> listData) {
//        this.mListData = listData;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mListData.get(position));
        holder.state.setText(mListState.get(position));
//        String item = mListData.get(position);

//        holder.title.setText(mListData.get(position));
//        holder.state.setText(mListState.get(position));
        //holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void addItem(ItemRecyclerView ItemRecyclerView)
    {
        mListData.add(ItemRecyclerView.getTitle());
        mListState.add(ItemRecyclerView.getDate());
//        mListData.add(String.valueOf(ItemRecyclerView));
    }

}