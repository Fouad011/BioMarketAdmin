package com.example.biomarketadmin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductResume implements Serializable {
    String id, commandId, userId;
    int quantity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ProductResume() {
    }

    public ProductResume(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public List<ProductResume> toProductResume(List<Product> ProductList){
        List<ProductResume> productResumes = new ArrayList<>();
        for (Product product : ProductList){
            ProductResume productResume = new ProductResume(product.getID(), product.getQuantity());
            productResumes.add(productResume);
        }

        return productResumes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResume that = (ProductResume) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(commandId, that.commandId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commandId, userId, quantity);
    }

    public boolean isPresent(List<ProductResume> productResumeList){

        for(ProductResume pr : productResumeList){
            if(pr.equals(this)){
                return true;
            }
        }
        return false;
    }




}
