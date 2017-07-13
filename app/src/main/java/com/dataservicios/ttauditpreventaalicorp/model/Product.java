package com.dataservicios.ttauditpreventaalicorp.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 7/06/2017.
 */

public class Product {

    @DatabaseField(generatedId = true)
    private int    id;
    @DatabaseField
    private int    product_id;
    @DatabaseField
    private int    competity;
    @DatabaseField
    private String fullname;
    @DatabaseField
    private int    company_id;
    @DatabaseField
    private String region;
    @DatabaseField
    private String imagen;
    @DatabaseField
    private String precio;
    @DatabaseField
    private String cantidad;
    @DatabaseField
    private int    status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCompetity() {
        return competity;
    }

    public void setCompetity(int competity) {
        this.competity = competity;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}