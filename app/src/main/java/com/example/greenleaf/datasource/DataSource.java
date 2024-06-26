package com.example.greenleaf.datasource;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.greenleaf.entity.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSource {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private MutableLiveData<List<Product>> liveData = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<Product>> liveDataProductSet = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<List<Product>> getProductsByCategory(String category){
        List<Product> products = new ArrayList<>();
        firestore.collection(category).get().addOnSuccessListener(result ->{
            for(int i = 0; i< result.size(); i++){
                products.add(new Product((Integer.valueOf(String.valueOf(result.getDocuments().get(i).getData().get("id")))),
                        String.valueOf(result.getDocuments().get(i).getData().get("image")),
                        String.valueOf(result.getDocuments().get(i).getData().get("name")),
                        String.valueOf(result.getDocuments().get(i).getData().get("mass")),
                        (int) (long) result.getDocuments().get(i).getData().get("price"),
                        (int) (long) result.getDocuments().get(i).getData().get("priceWithoutDiscount")
                ));
            }
            liveData.postValue(products);
        });
        return liveData;
    }

    public MutableLiveData<List<Product>> getAllProducts(){
        List<Product> products = new ArrayList<>();

        firestore.collection("all_products").get().addOnSuccessListener(result ->{

            for(int i = 0; i< result.size(); i++){
                products.add(new Product((Integer.valueOf(String.valueOf(result.getDocuments().get(i).getData().get("id")))),
                        String.valueOf(result.getDocuments().get(i).getData().get("image")),
                        String.valueOf(result.getDocuments().get(i).getData().get("name")),
                        String.valueOf(result.getDocuments().get(i).getData().get("mass")),
                        (int) (long) result.getDocuments().get(i).getData().get("price"),
                        (int) (long) result.getDocuments().get(i).getData().get("priceWithoutDiscount")
                ));
            }

            liveData.postValue(products);
        });
        return liveData;
    }

    public MutableLiveData<List<Product>> getProductSets(){
        List<Product> products = new ArrayList<>();

        firestore.collection("product_set").get().addOnSuccessListener(result ->{

            for(int i = 0; i< result.size(); i++){
                products.add(new Product((Integer.valueOf(String.valueOf(result.getDocuments().get(i).getData().get("id")))),
                        String.valueOf(result.getDocuments().get(i).getData().get("image")),
                        String.valueOf(result.getDocuments().get(i).getData().get("name")),
                        String.valueOf(result.getDocuments().get(i).getData().get("mass")),
                        (int) (long) result.getDocuments().get(i).getData().get("price"),
                        (int) (long) result.getDocuments().get(i).getData().get("priceWithoutDiscount")
                ));
            }

            liveDataProductSet.postValue(products);
        });
        return liveDataProductSet;
    }

    public void saveProducts(List<Product> list, String category){
        for (Product p : list) {
            Map<String, Object> product = new HashMap<>();
            product.put("id", p.getId());
            product.put("image", p.getImage());
            product.put("mass", p.getMass());
            product.put("name", p.getName());
            product.put("price", p.getPrice());
            product.put("priceWithoutDiscount", p.getPriceWithoutDiscount());

            firestore.collection("all_products").add(product).addOnSuccessListener(result ->{

            }) .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("DataSource", "Error writing document", e);
                }
            });

        }



    }
}
