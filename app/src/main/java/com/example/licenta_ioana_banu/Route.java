package com.example.licenta_ioana_banu;

public class Route {
    private String orgName, destName, owner;
    private double orgLong, orgLat, destLong, destLat;

    public Route(String orgName, String destName, String owner, double orgLong, double orgLat, double destLong, double destLat) {
        this.orgName = orgName;
        this.destName = destName;
        this.owner = owner;
        this.orgLong = orgLong;
        this.orgLat = orgLat;
        this.destLong = destLong;
        this.destLat = destLat;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getOrgLong() {
        return orgLong;
    }

    public void setOrgLong(double orgLong) {
        this.orgLong = orgLong;
    }

    public double getOrgLat() {
        return orgLat;
    }

    public void setOrgLat(double orgLat) {
        this.orgLat = orgLat;
    }

    public double getDestLong() {
        return destLong;
    }

    public void setDestLong(double destLong) {
        this.destLong = destLong;
    }

    public double getDestLat() {
        return destLat;
    }

    public void setDestLat(double destLat) {
        this.destLat = destLat;
    }
}
