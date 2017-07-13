package com.dataservicios.ttauditpreventaalicorp.repo;

import android.content.Context;

import com.dataservicios.ttauditpreventaalicorp.db.DatabaseHelper;
import com.dataservicios.ttauditpreventaalicorp.db.DatabaseManager;
import com.dataservicios.ttauditpreventaalicorp.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 29/06/2017.
 */

public class ProductRepo  implements Crud {
    private DatabaseHelper helper;

    public ProductRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        Product object = (Product) item;
        try {
            index = helper.getProductDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        Product object = (Product) item;

        try {
            helper.getProductDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        Product object = (Product) item;

        try {
            helper.getProductDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<Product> items = null;
        int counter = 0;
        try {
            items = helper.getProductDao().queryForAll();

            for (Product object : items) {
                // do something with object
                helper.getProductDao().deleteById(object.getId());
                counter ++ ;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        Product wishList = null;
        try {
            wishList = helper.getProductDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<Product> items = null;

        try {
            items = helper.getProductDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    @Override
    public Object findFirstReg() {

        Object wishList = null;
        try {
            wishList = helper.getProductDao().queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public long countReg() {
        long count = 0;
        try {
            count = helper.getProductDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     *
     * @param competity
     * @param region
     * @return
     */
    public List<Product> findByCompetityAndRegion(int competity, String region) {

        List<Product> wishList = null;
        try {
            wishList = helper.getProductDao().queryBuilder().where().eq("competity",competity).and().eq("region",region).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }



}