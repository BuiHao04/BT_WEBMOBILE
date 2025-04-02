package com.example.danhb.add;

public class DonVi {
    private String id, address, name, sdt;
    private int avatar;

    public DonVi() {
    }

    public DonVi(String id, String address, String name, int avatar, String sdt) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.avatar = avatar;
        this.sdt = sdt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
