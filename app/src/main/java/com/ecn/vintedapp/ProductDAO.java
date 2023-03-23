package com.ecn.vintedapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDAO {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product product);

    @Query("DELETE FROM product_table")
    void deleteAll();



    @Query("SELECT * FROM product_table")
    LiveData<List<Product>> getAllProducts();

    @Query("UPDATE product_table set Description=:description,Price=:price,Category=:category ,name=:name where id=:id  ")
    void update(String description,String price,String category,String name,int id);
}