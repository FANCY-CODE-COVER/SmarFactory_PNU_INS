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

import inc.app.mes.DTO.FacilityDAO;
import inc.app.mes.DTO.PlaceDAO;
import inc.app.mes.R;
import inc.app.mes.ui.home.FacilityDetailActivity;

public class FacilityListAdapter extends RecyclerView.Adapter<FacilityListAdapter.ViewHolder> {

    private ArrayList<FacilityDAO> items = new ArrayList<>();
    public FacilityListAdapter(Context context, ArrayList<FacilityDAO> items) {
        this.items = items;
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
                    Intent intent = new Intent(v.getContext(), FacilityDetailActivity.class);
                    intent.putExtra("facility_cd", items.get(pos).getFacility_no());
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
        holder.title.setText(items.get(position).getFacility_nm());
        holder.state.setText(items.get(position).getFacility_no());
        
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(FacilityDAO facilityDAO)
    {
        items.add(facilityDAO);
    }
    public void setClear(){
        items.clear();
    }


}