package com.example.biomarketadmin;

import android.os.Build;

import androidx.annotation.NonNull;

import com.google.type.DateTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DeliveryModel {
    public String id, fullName, cni, mobile, imageUrl;
    public boolean state;
    public Address address;
//    public String dateTime;

    public DeliveryModel() {
    }

    public DeliveryModel(String fullName, String cni, String mobile, String imageUrl, boolean state) {
        this.fullName = fullName;
        this.cni = cni;
        this.mobile = mobile;
        this.imageUrl = imageUrl;
        this.state = state;

//        ZonedDateTime eventInCasablanca = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            eventInCasablanca = ZonedDateTime.now(ZoneId.of("Africa/Casablanca"));
//        }
//        DateTimeFormatter formatter = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a z");
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.dateTime = eventInCasablanca.format(formatter);
//        }


//        System.out.println(formatted); // Output: Wednesday, January 24, 2024 at 4:59 PM WET

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
