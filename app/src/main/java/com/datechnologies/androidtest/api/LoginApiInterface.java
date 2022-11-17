package com.datechnologies.androidtest.api;

import com.datechnologies.androidtest.api.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> loginVerify(@Field("email") String email, @Field("password") String password);


}
