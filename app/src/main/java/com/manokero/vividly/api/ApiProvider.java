package com.manokero.vividly.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {

    public static final  String BASE_URL = "https://api.unsplash.com/";
    public static final  String API_KEY = "KhDD-X4iq5r6OQROJgYLUFhgib5XTTQzdx6ahfX58oI";

    public static Retrofit retrofit = null;
    public static ApiInterface getApiInterface(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
