package com.optic.clientmensajesapiandroid.api;



import com.optic.clientmensajesapiandroid.models.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by User on 1/04/2018.
 */

public interface APIService {

    // LLAMADA PARA REGISTRO DE USUARIO
    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender);

    // LLAMADA PARA LOGIN DE USUARIO
    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

}
