package com.dataservicios.ttauditpreventaalicorp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dataservicios.ttauditpreventaalicorp.R;
import com.dataservicios.ttauditpreventaalicorp.model.Product;
import com.dataservicios.ttauditpreventaalicorp.repo.ProductRepo;
import com.dataservicios.ttauditpreventaalicorp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jcdia on 23/06/2017.
 */

public class ProductAdapterRecyclerView extends RecyclerView.Adapter<ProductAdapterRecyclerView.ProductViewHolder> {
    private ArrayList<Product>  products;
    private int                         resource;
    private Activity                    activity;
    private int                         store_id;
    private int                         audit_id;
    private ProductRepo         productRepo;

    public ProductAdapterRecyclerView(ArrayList<Product> products, int resource, Activity activity, int store_id, int audit_id) {
        this.products           = products;
        this.resource           = resource;
        this.activity           = activity;
        this.store_id           = store_id;
        this.audit_id           = audit_id;
        productRepo     = new ProductRepo(activity);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        ProductViewHolder vh = new ProductViewHolder(view, new ProductViewHolder.ITextWatcher() {
            @Override
            public void beforeTextChanged(int position, CharSequence s, int start, int count, int after) {
                // do something
            }

            @Override
            public void onTextChanged(int position, CharSequence s, int start, int before, int count) {

                String cantidad = s.toString().trim();

                if(cantidad.length() > 0){
                    products.get(position).setCantidad(String.valueOf(cantidad));
                    productRepo.update(products.get(position));
                }
            }

            @Override
            public void afterTextChanged(int position, Editable s) {
                // do something
            }
        });

       // return new ProductViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Product product = products.get(position);

        holder.tvFullName.setText(product.getFullname());
        holder.imgStatus.setVisibility(View.GONE);
        Picasso.with(activity)
                .load(R.drawable.thumbs_ttaudit)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.thumbs_ttaudit)
                .into(holder.imgPhoto);

        holder.etCantidad.setText(String.valueOf(product.getCantidad()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private  TextView         tvFullName;
        private  EditText         etCantidad;
        private  ImageView        imgPhoto;
        private  ImageView        imgStatus;
        private ITextWatcher      mTextWatcher;

        public interface ITextWatcher {
            // you can add/remove methods as you please, maybe you dont need this much
            void beforeTextChanged(int position, CharSequence s, int start, int count, int after);

            void onTextChanged(int position, CharSequence s, int start, int before, int count);

            void afterTextChanged(int position, Editable s);
        }

        public ProductViewHolder(View itemView, ITextWatcher textWatcher) {
            super(itemView);
            tvFullName      = (TextView) itemView.findViewById(R.id.tvFullName);
            etCantidad         = (EditText)  itemView.findViewById(R.id.etCantidad);
            imgPhoto        = (ImageView)  itemView.findViewById(R.id.imgPhoto);
            imgStatus       = (ImageView)  itemView.findViewById(R.id.imgStatus);

            mTextWatcher = textWatcher;
            this.etCantidad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mTextWatcher.beforeTextChanged(getAdapterPosition(), s, start, count, after);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mTextWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mTextWatcher.afterTextChanged(getAdapterPosition(), s);
                }
            });
        }
    }

    public void setFilter(ArrayList<Product> products){
        this.products = new ArrayList<>();
        this.products.addAll(products);
        notifyDataSetChanged();
    }
}
