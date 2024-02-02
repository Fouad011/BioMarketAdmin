package com.example.biomarketadmin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.biomarketadmin.DeliveryModel;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.ProductResume;
import com.example.biomarketadmin.R;
import com.example.biomarketadmin.activities.CommandsActivity;
import com.example.biomarketadmin.activities.HomeActivity;
import com.example.biomarketadmin.firebase.DBDelivery;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    Context context;
    List<DeliveryModel> deliveryModelList;
    DeliveryAdapter deliveryAdapter;
    String userId, commandId;
    TextView priceSubTotal, textViewPriceTotal;
    Double priceTotal = 0.0;
    int i=0;
    Double livPrice;

    DecimalFormat df = new DecimalFormat("0.00");


    public DeliveryAdapter(Context context, List<DeliveryModel> deliveryModelList, DeliveryAdapter deliveryAdapter, String userId, String commandId) {
        this.context = context;
        this.deliveryModelList = deliveryModelList;
        this.deliveryAdapter = deliveryAdapter;
        this.userId = userId;
        this.commandId = commandId;
//        this.priceSubTotal = priceSubTotal;
//        this.textViewPriceTotal = textViewPriceTotal;
//        this.livPrice = livPrice;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(context).load(deliveryModelList.get(position).getImageUrl()).into(holder.imageViewDelivery);
        holder.deliveryName.setText(deliveryModelList.get(position).getFullName());
        holder.deliveryState.setText(deliveryModelList.get(position).isState()?"Available":"Unavailable");

//        int qnt = deliveryModelList.get(position).getQuantity();
//        holder.quantity.setText(String.valueOf(qnt));
//
//
//        Double price = deliveryModelList.get(position).getPrice();
//
//        Double total = qnt*price;
//        String totalStr = Double.toString(total);
//
//        holder.priceForAll.setText(totalStr);
//
//
//
//        priceTotal += price*qnt;
////
////        ++i;
////        if(position==0 && i!=0){
////            priceTotal = price;
////            i=0;
////        }
//
//
//        priceSubTotal.setText(PointToComma(priceTotal));
//        textViewPriceTotal.setText(PointToComma(priceTotal+livPrice));


        holder.cardView.setOnClickListener(v -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Commands");

            DBDelivery dbDelivery = new DBDelivery();
            // move Command to Delivery
            dbDelivery.moveCommand(context, deliveryModelList.get(position).getId(), userId, commandId);
//            deliveryModelList.clear();
//            deliveryAdapter.notifyDataSetChanged();

//            context.startActivity(new Intent(context, HomeActivity.class));

            // this command --> valider
            // ....

        });

    }



    public String PointToComma(Double price){
        String priceStr = Double.toString(price);
        priceStr += "00";
        String [] priceList = priceStr.split("\\.");
        return priceList[0]+","+priceList[1].substring(0, 2);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void addProduct(DeliveryModel deliveryModel) {
        if (!deliveryModelList.contains(deliveryModel)) {
            deliveryModelList.add(deliveryModel);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return deliveryModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDelivery;
        TextView deliveryName, deliveryState;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cartShopCV);


            imageViewDelivery = itemView.findViewById(R.id.imageView_delivery_pic);
            deliveryName = itemView.findViewById(R.id.deliveryName);
            deliveryState= itemView.findViewById(R.id.deliveryState);


        }
    }
}
