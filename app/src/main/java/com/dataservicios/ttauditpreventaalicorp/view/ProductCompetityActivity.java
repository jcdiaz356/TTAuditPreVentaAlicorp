package com.dataservicios.ttauditpreventaalicorp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.ttauditpreventaalicorp.R;
import com.dataservicios.ttauditpreventaalicorp.adapter.ProductAdapterRecyclerView;
import com.dataservicios.ttauditpreventaalicorp.adapter.ProductCompetityAdapterRecyclerView;
import com.dataservicios.ttauditpreventaalicorp.db.DatabaseManager;
import com.dataservicios.ttauditpreventaalicorp.model.Audit;
import com.dataservicios.ttauditpreventaalicorp.model.Company;
import com.dataservicios.ttauditpreventaalicorp.model.Poll;
import com.dataservicios.ttauditpreventaalicorp.model.PollDetail;
import com.dataservicios.ttauditpreventaalicorp.model.Product;
import com.dataservicios.ttauditpreventaalicorp.model.Route;
import com.dataservicios.ttauditpreventaalicorp.model.Store;
import com.dataservicios.ttauditpreventaalicorp.repo.AuditRepo;
import com.dataservicios.ttauditpreventaalicorp.repo.CompanyRepo;
import com.dataservicios.ttauditpreventaalicorp.repo.PollRepo;
import com.dataservicios.ttauditpreventaalicorp.repo.ProductRepo;
import com.dataservicios.ttauditpreventaalicorp.repo.StoreRepo;
import com.dataservicios.ttauditpreventaalicorp.util.AuditUtil;
import com.dataservicios.ttauditpreventaalicorp.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductCompetityActivity extends AppCompatActivity {
    private static final String LOG_TAG = ProductActivity.class.getSimpleName();
    private SessionManager                          session;
    private Activity                                activity =  this;
    private ProgressDialog                          pDialog;
    private int                                     user_id;
    private int                                     store_id;
    private int                                     audit_id;
    private TextView                                tvTotal;
    private Button                                  btSave;
    private ProductRepo                             productRepo;
    private StoreRepo                               storeRepo ;
    private CompanyRepo                             companyRepo ;
    private AuditRepo                               auditRepo ;
    private PollRepo                                pollRepo;
    private ProductCompetityAdapterRecyclerView     productCompetityAdapterRecyclerView;
    private RecyclerView                            productRecyclerView;
    private Audit                                   audit ;
    private Product                                 product;
    private Company                                 company ;
    private Route                                   route ;
    private Store                                   store ;
    private Poll                                    poll;
    private PollDetail                              pollDetail;
    private ArrayList<Product>                      products;
    private int                                     product_propietary=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_competity);

        tvTotal                 = (TextView) findViewById(R.id.tvTotal);
        btSave                  = (Button) findViewById(R.id.btSave);

        DatabaseManager.init(this);

        storeRepo           = new StoreRepo(activity);
        productRepo         = new ProductRepo(activity);
        auditRepo           = new AuditRepo(activity);
        companyRepo         = new CompanyRepo(activity);
        pollRepo            = new PollRepo(activity);

        Bundle bundle = getIntent().getExtras();
        store_id            = bundle.getInt("store_id");
        audit_id            = bundle.getInt("audit_id");

        company             = (Company)companyRepo.findFirstReg();
        store               = (Store) storeRepo.findById(store_id);
        //poll              = (Poll)            pollRepo.findByCompanyAuditIdAndOrder(auditRoadStore.getList().getCompany_audit_id(),13);
        poll                = (Poll)            pollRepo.findByOrder(2);

        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        audit = (Audit) auditRepo.findById(audit_id);
        showToolbar(audit.getFullname().toString(),false);
        //showToolbar(getString(R.string.title_activity_Stores_Audit),true);

        productRecyclerView = (RecyclerView) findViewById(R.id.stock_product_sood__recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productRecyclerView.setLayoutManager(linearLayoutManager);

        products = (ArrayList<Product>) productRepo.findByCompetityAndRegion(product_propietary,store.getDistrict());

        productCompetityAdapterRecyclerView =  new ProductCompetityAdapterRecyclerView(products, R.layout.cardview_product_competity, activity,store_id,audit_id);
        productRecyclerView.setAdapter(productCompetityAdapterRecyclerView);

        int total               = products.size();
        int productsAudits      = 0;

        for(Product p: products){
            if(p.getStatus()==1) productsAudits ++;
        }

        tvTotal.setText(String.valueOf(productsAudits) + " de " + String.valueOf(total));
        if(products.size() == 0) {
            btSave.setVisibility(View.INVISIBLE);
        }
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                for(int i=0;i<productAdapterRecyclerView.getItemCount();i++){
//                    ProductAdapterRecyclerView.ProductViewHolder  viewHolder= (ProductAdapterRecyclerView.ProductViewHolder)
//                            productRecyclerView.findViewHolderForAdapterPosition(i);
//                            EditText editText= viewHolder.etStock;
//
//                    String datos =  editText.getText().toString();
//                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.message_save);
                builder.setMessage(R.string.message_save_information);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        products = (ArrayList<Product>) productRepo.findAll();
                        for(Product m: products){
                            m.setStatus(0);
                            productRepo.update(m);
                        }

                        poll.setOrder(6);
                        PollActivity.createInstance(activity, store_id,audit_id,poll);
                        finish();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                builder.setCancelable(false);
            }
        });


    }

    private ArrayList<Product> filter(ArrayList<Product> models, String query) {

        query = query.toLowerCase();
        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product s : models) {
            final String fullName = s.getFullname().toLowerCase().trim();
            if (fullName.contains(query) ) {
                filteredModelList.add(s);
            }
        }
        return filteredModelList;
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //storeAdapterRecyclerView.getFilter().filter(newText.toString());
                final ArrayList<Product> filteredMStoreList = filter(products, newText);
                //adapter.setFilter(filteredModelList);
                productCompetityAdapterRecyclerView.setFilter(filteredMStoreList);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            onBackPressed();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    @Override
    public void onBackPressed() {

        if(products.size() == 0 ) {
            super.onBackPressed ();
        } else {
            alertDialogBasico(getString(R.string.message_save_audit_products));
        }
        //super.onBackPressed ();
    }

    private void alertDialogBasico(String message) {

        // 1. Instancia de AlertDialog.Builder con este constructor
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        // 2. Encadenar varios métodos setter para ajustar las características del diálogo
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });
        builder.show();

    }
}