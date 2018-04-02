package com.optic.clientmensajesapiandroid.api;



import com.optic.clientmensajesapiandroid.models.MessageResponse;
import com.optic.clientmensajesapiandroid.models.Messages;
import com.optic.clientmensajesapiandroid.models.Result;
import com.optic.clientmensajesapiandroid.models.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    // LLAMADA PARA OBTENER TODOS LOS USURIOS REGISTRADOS EN LA DB
    @GET("users")
    Call<Users> getUsers();


    // LLAMADA PARA ENVIAR MENSAJE
    @FormUrlEncoded
    @POST("sendmessage")
    Call<MessageResponse> sendMessage(
            @Field("from") int from,
            @Field("to") int to,
            @Field("title") String title,
            @Field("message") String message);

    // LLAMADA PRA ACTUALIZAR UN USUARIO POR ID
    @FormUrlEncoded
    @POST("update/{id}")
    Call<Result> updateUser(
            @Path("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender
    );


    // LLAMADA PARA OBTENER TODOS LOS MENSAJES
    @GET("messages/{id}")
    Call<Messages> getMessages(@Path("id") int id);
}
