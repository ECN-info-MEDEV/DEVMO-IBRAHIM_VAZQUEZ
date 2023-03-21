package com.ecn.vintedapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class}, version = 9, exportSchema = false)
public abstract class ProductRoomDatabase extends RoomDatabase {



    public abstract ProductDAO productDao();

    private static volatile ProductRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ProductRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ProductRoomDatabase.class, "product_database").fallbackToDestructiveMigration().
                    addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ProductDAO dao = INSTANCE.productDao();
                dao.deleteAll();

                Product product = new Product("Hello");
                product.setDescription("test");
                product.setName("product1");
                product.setPrice("10");
                Log.d ("productRoomDatabase", "Trying to insert a product in the database") ;
                dao.insert(product);
                product = new Product("World");
                product.setPrice("100");
                product.setDescription("description2");
                product.setName("product2");
                dao.insert(product);
            });
        }

    };
}

