
package com.example.thaimongkieu_2123110013;

import com.example.thaimongkieu_2123110013.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("products")
    Call<List<Product>> getProducts();
    @GET("products")
    Call<List<Product>> getProductsByCategory (@Query("category") String category);

    @GET("products")
    Call<List<Product>> searchProducts(@Query("search") String keyword);


    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);


}
