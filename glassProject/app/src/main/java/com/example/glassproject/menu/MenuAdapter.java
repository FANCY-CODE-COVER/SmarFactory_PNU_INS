package com.example.glassproject.menu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.glassproject.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter for the menu horizontal recycler view.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final List<GlassMenuItem> menuItems;

    MenuAdapter(List<GlassMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public class MenuItemBinding extends RecyclerView.ViewHolder {

        public MenuItemBinding(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(GlassMenuItem glassMenuItem) {
        }
    }
    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int position) {
        menuViewHolder.bind(menuItems.get(position));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private MenuItemBinding binding;

        MenuViewHolder(View itemView) {
            super(itemView);
            this.binding = binding;
        }

        public void bind(GlassMenuItem glassMenuItem) {
            binding.setItem(glassMenuItem);
            //binding.executePendingBindings();
        }
    }
}