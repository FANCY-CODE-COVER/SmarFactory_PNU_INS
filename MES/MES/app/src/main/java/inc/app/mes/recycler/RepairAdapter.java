package inc.app.mes.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import inc.app.mes.DTO.FacilityDAO;
import inc.app.mes.DTO.RepairDAO;
import inc.app.mes.R;
import inc.app.mes.ui.edit.RepairDetailActivity;
import inc.app.mes.ui.home.FacilityDetailActivity;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder> {

    private ArrayList<RepairDAO> items ;
    private Context context;
    public RepairAdapter(Context context) {
        this.context = context;
        this.items = new  ArrayList<RepairDAO> ();
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
                    context=v.getContext();
                    Intent intent = new Intent(v.getContext(), RepairDetailActivity.class);
                    intent.putExtra("repair_no", items.get(pos).getRepair_no());
                    context.startActivity(intent);
                }

            });

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getRepair_no());
        holder.state.setText(items.get(position).getRepair_type());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(RepairDAO repairDAO)
    {
        items.add(repairDAO);
//        mListData.add(String.valueOf(ItemRecyclerView));
    }
    public void setClear(){
        items.clear();
    }


}
