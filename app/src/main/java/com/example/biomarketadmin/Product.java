package com.example.biomarketadmin;

import java.io.Serializable;

public class Product implements Serializable {
    String ID, title,category,classification,description, imageUrl;
    Double price;
    Boolean visible;
    Boolean inStock;
    private int quantityInStock;
    public int quantity;


    public Product() {
    }

    public Product(String title, String category, String classification, Double price, String description, String imageUrl, Boolean visible, Boolean inStock, int quantityInStock) {
        this.title = title;
        this.category = category;
        this.classification = classification;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.visible = visible;
        this.inStock = inStock;
        this.quantityInStock = quantityInStock;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
