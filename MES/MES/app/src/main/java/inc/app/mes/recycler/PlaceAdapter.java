package inc.app.mes.recycler;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import inc.app.mes.DTO.PlaceDAO;
import inc.app.mes.R;
import inc.app.mes.ui.home.HomeFragment;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private ArrayList<PlaceDAO> items = new ArrayList<>();
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private Context context;
    private int color;
    public PlaceAdapter(Context context, ArrayList<PlaceDAO> items) {
        this.context = context;
        this.items = items;
        this.color=context.getResources().getColor(R.color.colorPrimaryDark);
        mSelectedItems.put(0,true);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CardView cardView;
        Context context;
        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            cardView =itemView.findViewById(R.id.item_cardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    toggleItemSelected(pos);
                    if(mListener!=null){
                        mListener.onItemClick(v, pos);
                    }
                }
            });//end click listener
        }
    }
    public ArrayList<PlaceDAO> getItems() {
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
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        PlaceAdapter.ViewHolder holder = new PlaceAdapter.ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getPlace());
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

    public void addItem(PlaceDAO placeDAO)
    {
        items.add(placeDAO);
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