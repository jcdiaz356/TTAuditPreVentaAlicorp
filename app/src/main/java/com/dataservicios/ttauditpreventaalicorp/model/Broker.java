package com.dataservicios.ttauditpreventaalicorp.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 28/06/2017.
 */

public class Broker  {

    @DatabaseField(id = true)
    private int         id;
    @DatabaseField
    private int      district_id;
    @DatabaseField
    private String      district;
    @DatabaseField
    private String      fullname;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString () {
        return fullname;
    }
}
