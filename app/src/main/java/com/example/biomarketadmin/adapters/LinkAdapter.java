package com.example.biomarketadmin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biomarketadmin.LinkModel;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.ProductResume;
import com.example.biomarketadmin.R;
import com.example.biomarketadmin.activities.CommandDetailsActivity;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {
    Context context;
    List<LinkModel> list;
    LinkAdapter linkAdapter;

    public LinkAdapter(Context context, List<LinkModel> list, LinkAdapter linkAdapter) {
        this.context = context;
        this.list = list;
        this.linkAdapter = linkAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.link_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dateOfCommand.setText(list.get(position).getId());

        holder.cardViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommandDetailsActivity.class);
                intent.putExtra("commandId", list.get(position).getId());
                intent.putExtra("userId", list.get(position).getUserId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewLink;
        TextView dateOfCommand;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewLink = itemView.findViewById(R.id.cardViewLink);
            dateOfCommand = itemView.findViewById(R.id.dateOfCommand);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addProduct(LinkModel product) {
        boolean cond = true;
        for (LinkModel productResume : list){
            if (productResume.equals(product)) {
                cond = false;
            }
        }

        if (cond) {
            list.add(product);
            notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeProduct(LinkModel product) {
        list.remove(product);
        notifyDataSetChanged();
    }
}
