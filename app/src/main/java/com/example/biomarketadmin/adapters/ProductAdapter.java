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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.ProductResume;
import com.example.biomarketadmin.R;
import com.example.biomarketadmin.activities.EditProductActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    ArrayList<Product> ProductArrayList;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        ProductArrayList = productArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_template,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product p = ProductArrayList.get(position);

        String name, category, describe, classify, imgUrl;
        Double price;
        int qtStock;
        Boolean avaible,visible;

        name = p.getTitle();  category = p.getCategory(); price = p.getPrice(); qtStock = p.getQuantityInStock(); describe = p.getDescription();
        classify = p.getClassification(); avaible = p.getInStock(); visible = p.getVisible(); imgUrl = p.getImageUrl();

        Glide.with(context).load(p.getImageUrl()).into(holder.image);
        holder.name.setText(p.getTitle());
//        holder.category.setText(p.getCategory());
        holder.price.setText(Double.toString(p.getPrice()));


        holder.quantityInStock.setText(String.valueOf(p.getQuantityInStock()));
//        holder.describe.setText(p.getDescription());
//        holder.classify.setText(p.getClassification());
        holder.avaible.setText(p.getInStock()?"Available":"Unavailable");
        holder.visible.setText(p.getVisible()?"Visible":"Invisible");


        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                FirebaseFirestore baseStore = FirebaseFirestore.getInstance();



                System.out.println(p.getID());
                baseStore.collection("Products").document("bioMarketStore").collection("products")
                        .document(p.getID()).delete();
                Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                ProductArrayList.remove(position);
                notifyDataSetChanged();
            }
        });


        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditProductActivity.class);
//                i.putExtra("name", name);
//                i.putExtra("category", category);
//                i.putExtra("price", price);
//                i.putExtra("quantityInStock", qtStock);
//                i.putExtra("description", describe);
//                i.putExtra("classification", classify);
//                i.putExtra("avaible",avaible);
//                i.putExtra("visible", visible);
//                i.putExtra("imageUrl",imgUrl);
//                i.putExtra("ProductID",ProductArrayList.get(position).getID());
                i.putExtra("object", (Serializable) ProductArrayList.get(position));
//                System.out.println("price  :: " + price);

                context.startActivity(i);
            }
        });


    }

    @SuppressLint("NotifyDataSetChanged")
    public void addProduct(Product product) {
        if (!ProductArrayList.contains(product)) {
            ProductArrayList.add(product);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return ProductArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,category,price,describe,classify,avaible,visible, quantityInStock;
        ImageView image;
        TextView Edit, Delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
//            category = itemView.findViewById(R.id.tvcat);
            price = itemView.findViewById(R.id.price);
            quantityInStock = itemView.findViewById(R.id.quantityInStock);
//            describe = itemView.findViewById(R.id.tvdesc);
//            classify = itemView.findViewById(R.id.tvclass);
            avaible = itemView.findViewById(R.id.availability);
            visible = itemView.findViewById(R.id.visibility);
            image = itemView.findViewById(R.id.imageProduct);
            Edit = itemView.findViewById(R.id.edit);
            Delete = itemView.findViewById(R.id.delete);
        }
    }
}
