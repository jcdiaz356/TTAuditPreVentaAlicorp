package com.dataservicios.ttauditpreventaalicorp.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.dataservicios.ttauditpreventaalicorp.model.Audit;
import com.dataservicios.ttauditpreventaalicorp.model.AuditRoadStore;
import com.dataservicios.ttauditpreventaalicorp.model.Broker;
import com.dataservicios.ttauditpreventaalicorp.model.Company;
import com.dataservicios.ttauditpreventaalicorp.model.Departament;
import com.dataservicios.ttauditpreventaalicorp.model.District;
import com.dataservicios.ttauditpreventaalicorp.model.Media;
import com.dataservicios.ttauditpreventaalicorp.model.Poll;
import com.dataservicios.ttauditpreventaalicorp.model.PollOption;
import com.dataservicios.ttauditpreventaalicorp.model.Product;
import com.dataservicios.ttauditpreventaalicorp.model.Route;
import com.dataservicios.ttauditpreventaalicorp.model.RouteStoreTime;
import com.dataservicios.ttauditpreventaalicorp.model.Store;
import com.dataservicios.ttauditpreventaalicorp.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "db_alicorp_pre_v";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 9;
    private Context myContext;
	// the DAO object we use to access the SimpleData table
    //pressure
	private Dao<User, Integer>                  UserDao             = null;
	private Dao<Departament, Integer>           DepartamentDao      = null;
	private Dao<District, Integer>              DistrictDao         = null;
	private Dao<Route, Integer>                 RouteDao            = null;
	private Dao<Company, Integer>               CompanyDao          = null;
	private Dao<Store, Integer>                 StoreDao            = null;
	private Dao<Audit, Integer>                 AuditDao            = null;
	private Dao<AuditRoadStore,Integer>         AuditRoadStoreDao   = null;
	private Dao<Poll, Integer>                  PollDao             = null;
	private Dao<PollOption, Integer>            PollOptionDao       = null;
	private Dao<Media, Integer>                 MediaDao            = null;
	private Dao<RouteStoreTime, Integer>        RouteStoreTimeDao   = null;
	private Dao<Broker, Integer>                BrokerDao           = null;
	private Dao<Product, Integer>               ProductDao           = null;


	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db,ConnectionSource connectionSource) {
		try {
			
			TableUtils.createTable(connectionSource, User.class                 );
			TableUtils.createTable(connectionSource, Departament.class          );
			TableUtils.createTable(connectionSource, District.class             );
			TableUtils.createTable(connectionSource, Route.class                );
			TableUtils.createTable(connectionSource, Company.class              );
			TableUtils.createTable(connectionSource, Store.class                );
			TableUtils.createTable(connectionSource, Audit.class                );
			TableUtils.createTable(connectionSource, AuditRoadStore.class       );
			TableUtils.createTable(connectionSource, Poll.class                 );
			TableUtils.createTable(connectionSource, PollOption.class           );
			TableUtils.createTable(connectionSource, Media.class                );
			TableUtils.createTable(connectionSource, RouteStoreTime.class       );
			TableUtils.createTable(connectionSource, Broker.class               );
			TableUtils.createTable(connectionSource, Product.class               );


            Log.i(LOG_TAG, "execute method onCreate: Can't create Tables");
            preloadData(db,myContext);

		} catch (SQLException e) {
			Log.e(LOG_TAG, "Can't create database", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			List<String> allSql = new ArrayList<String>();

			switch(oldVersion)
			{
				case 1:
				  //allSql.add("alter table AdData add column `new_col` VARCHAR");
				  //allSql.add("alter table AdData add column `new_col2` VARCHAR");

			}
			for (String sql : allSql) {
				db.execSQL(sql);
			}

            TableUtils.dropTable(connectionSource,User.class,true           );
            TableUtils.dropTable(connectionSource, Departament.class,true   );
            TableUtils.dropTable(connectionSource, District.class,true      );
            TableUtils.dropTable(connectionSource, Route.class,true         );
            TableUtils.dropTable(connectionSource, Company.class,true       );
            TableUtils.dropTable(connectionSource, Store.class,true         );
            TableUtils.dropTable(connectionSource, Audit.class,true         );
            TableUtils.dropTable(connectionSource, AuditRoadStore.class,true);
            TableUtils.dropTable(connectionSource, Poll.class,true          );
            TableUtils.dropTable(connectionSource, PollOption.class,true    );
            TableUtils.dropTable(connectionSource, Media.class,true         );
            TableUtils.dropTable(connectionSource, RouteStoreTime.class,true);
            TableUtils.dropTable(connectionSource, Broker.class,true        );
            TableUtils.dropTable(connectionSource, Product.class,true        );
            onCreate(db,connectionSource);

            Log.i(LOG_TAG, "execute method onUpgrade: drop Tables");

		} catch (SQLException e) {
			Log.e(LOG_TAG, "exception during onUpgrade", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }


	public Dao<User, Integer> getUserDao() {
		if (null == UserDao) {
			try {
				UserDao = getDao(User.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return UserDao;
	}

    public Dao<Departament, Integer> getDepartamentDao() {
        if (null == DepartamentDao) {
            try {
                DepartamentDao = getDao(Departament.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return DepartamentDao;
    }

    public Dao<District, Integer> getDistrictDao() {
        if (null == DistrictDao) {
            try {
                DistrictDao = getDao(District.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return DistrictDao;
    }

    public Dao<Route, Integer> getRouteDao() {
        if (null == RouteDao) {
            try {
                RouteDao = getDao(Route.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return RouteDao;
    }

    public Dao<Company, Integer> getCompanyDao() {
        if (null == CompanyDao) {
            try {
                CompanyDao = getDao(Company.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return CompanyDao;
    }

    public Dao<Store, Integer> getStoreDao() {
        if (null == StoreDao) {
            try {
                StoreDao = getDao(Store.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return StoreDao;
    }

    public Dao<Audit, Integer> getAuditDao() {
        if (null == AuditDao) {
            try {
                AuditDao = getDao(Audit.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return AuditDao;
    }

    public Dao<AuditRoadStore, Integer> getAuditRoadStoreDao() {
        if (null == AuditRoadStoreDao) {
            try {
                AuditRoadStoreDao = getDao(AuditRoadStore.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return AuditRoadStoreDao;
    }
    public Dao<Poll, Integer> getPollDao() {
        if (null == PollDao) {
            try {
                PollDao = getDao(Poll.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return PollDao;
    }

    public Dao<PollOption, Integer> getPollOptionDao() {
        if (null == PollOptionDao) {
            try {
                PollOptionDao = getDao(PollOption.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return PollOptionDao;
    }
    public Dao<Media, Integer> getMediaDao() {
            if (null == MediaDao) {
                try {
                    MediaDao = getDao(Media.class);
                }catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
            return MediaDao;
    }
    public Dao<RouteStoreTime, Integer> getRouteStoreTimeDao() {
                if (null == RouteStoreTimeDao) {
                    try {
                        RouteStoreTimeDao = getDao(RouteStoreTime.class);
                    }catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                }
                return RouteStoreTimeDao;
    }
    public Dao<Broker, Integer> getBrokerDao() {
                    if (null == BrokerDao) {
                        try {
                            BrokerDao = getDao(Broker.class);
                        }catch (java.sql.SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return BrokerDao;
    }

    public Dao<Product, Integer> getProductDao() {
                    if (null == ProductDao) {
                        try {
                            ProductDao = getDao(Product.class);
                        }catch (java.sql.SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return ProductDao;
    }


    private void preloadData(SQLiteDatabase db, Context context) {

        InputStream is = null;
        try {

            is = context.getAssets().open("insert.sql");
            if (is != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();

                }
                db.setTransactionSuccessful();
            }

            is.close();

            Log.i(LOG_TAG,"Insert rows");
        } catch (IOException e) {
            // Muestra log
            Log.e(LOG_TAG, "Error in File insert.sql", e);

        } catch (Exception e) {
            // Muestra log
            Log.e(LOG_TAG, "Error preloadData", e);
        } finally {
            db.endTransaction();
        }
    }
}
