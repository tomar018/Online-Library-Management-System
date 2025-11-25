package com.thesquad.models;

import java.time.LocalDateTime;

public class AddressModel {

    private int addressId;
    private int houseNumber;
    private int districtId;
    private String street;
    private String neighborhood;
    private LocalDateTime createdAt;

    // Default constructor
    public AddressModel() {
    }

    // Parameterized constructor
    public AddressModel(int addressId, String street, int houseNumber, String neighborhood, int districtId, LocalDateTime createdAt) {
        this.addressId = addressId;
        this.street = street;
        this.houseNumber = houseNumber;
        this.neighborhood = neighborhood;
        this.districtId = districtId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDistrict(String parameter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setHouseNum(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}