package inc.app.mes.recycler;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import inc.app.mes.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private ArrayList<String> items = new ArrayList<>();
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private Context context;
    private int color;
    public MenuAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<String>();
        this.color=context.getResources().getColor(R.color.colorPrimaryDark);
        mSelectedItems.put(0,true);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CardView cardView;
        LinearLayout layout;
        Context context;
        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            cardView =itemView.findViewById(R.id.item_cardview);
//            layout=itemView.find

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    toggleItemSelected(pos);
//                    Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                    if(mListener!=null){
                        mListener.onItemClick(v, pos);
                    }
                }
            });//end click listener
        }
    }
    public ArrayList<String> getItems() {
        return items;
    }
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    private OnItemClickListener mListener=null;


    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(items.get(position));
        if (isItemSelected(position)) {
            holder.itemView.setBackgroundColor(color);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(String string)
    {
        items.add(string);
    }

    private void toggleItemSelected(int position) {
        clearSelectedItem();
        if (mSelectedItems.get(position, false) == true) {
            mSelectedItems.delete(position);
            notifyItemChanged(position);
        } else {
            mSelectedItems.put(position, true);
            notifyItemChanged(position);
        }
    }

    private boolean isItemSelected(int position) {
        return mSelectedItems.get(position, false);
    }

    public void clearSelectedItem() {
        int position;
        for (int i = 0; i < mSelectedItems.size(); i++) {
            position = mSelectedItems.keyAt(i);
            mSelectedItems.put(position, false);
            notifyItemChanged(position);
        }
        mSelectedItems.clear();
    }
}
