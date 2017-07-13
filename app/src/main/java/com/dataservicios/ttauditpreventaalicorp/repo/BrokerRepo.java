package com.dataservicios.ttauditpreventaalicorp.repo;

import android.content.Context;

import com.dataservicios.ttauditpreventaalicorp.db.DatabaseHelper;
import com.dataservicios.ttauditpreventaalicorp.db.DatabaseManager;
import com.dataservicios.ttauditpreventaalicorp.model.Broker;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 28/06/2017.
 */

public class BrokerRepo implements Crud {
    private DatabaseHelper helper;

    public BrokerRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        Broker object = (Broker) item;
        try {
            index = helper.getBrokerDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        Broker object = (Broker) item;

        try {
            helper.getBrokerDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        Broker object = (Broker) item;

        try {
            helper.getBrokerDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<Broker> items = null;
        int counter = 0;
        try {
            items = helper.getBrokerDao().queryForAll();

            for (Broker object : items) {
                // do something with object
                helper.getBrokerDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        Broker wishList = null;
        try {
            wishList = helper.getBrokerDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<Broker> items = null;

        try {
            items = helper.getBrokerDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getBrokerDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getBrokerDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Busca una lista de Brokers por su distrito
     * @param district
     * @return Retorna lista de stores
     */
    public List<Broker> findByDistrict(String district) {

        List<Broker> wishList = null;
        try {
            wishList = helper.getBrokerDao().queryBuilder().where().eq("district",district).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }



}