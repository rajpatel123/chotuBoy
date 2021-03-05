package com.chotupartner.modelClass.forOutLet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outlet {

    @SerializedName("outlet_id")
    @Expose
    private String outletId;
    @SerializedName("associated_delivery_id")
    @Expose
    private String associatedDeliveryId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("assigned_city")
    @Expose
    private String assignedCity;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("reviews")
    @Expose
    private String reviews;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("outlet_image")
    @Expose
    private String outletImage;
    @SerializedName("publication_status")
    @Expose
    private String publicationStatus;
    @SerializedName("deletion_status")
    @Expose
    private String deletionStatus;
    @SerializedName("verify_number")
    @Expose
    private Object verifyNumber;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("last_updated")
    @Expose
    private Object lastUpdated;
    @SerializedName("lastlogin")
    @Expose
    private Object lastlogin;

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public String getAssociatedDeliveryId() {
        return associatedDeliveryId;
    }

    public void setAssociatedDeliveryId(String associatedDeliveryId) {
        this.associatedDeliveryId = associatedDeliveryId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAssignedCity() {
        return assignedCity;
    }

    public void setAssignedCity(String assignedCity) {
        this.assignedCity = assignedCity;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getOutletImage() {
        return outletImage;
    }

    public void setOutletImage(String outletImage) {
        this.outletImage = outletImage;
    }

    public String getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(String publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public String getDeletionStatus() {
        return deletionStatus;
    }

    public void setDeletionStatus(String deletionStatus) {
        this.deletionStatus = deletionStatus;
    }

    public Object getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(Object verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Object getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Object lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Object getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Object lastlogin) {
        this.lastlogin = lastlogin;
    }

}