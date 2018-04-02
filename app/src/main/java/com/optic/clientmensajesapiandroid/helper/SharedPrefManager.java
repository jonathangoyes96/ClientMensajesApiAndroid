package com.optic.clientmensajesapiandroid.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.optic.clientmensajesapiandroid.models.User;

/**
 * Created by Optic on 1/04/2018.
 */


public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedprefretrofit";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_GENDER = "keyusergender";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    // INSTANCIANDO LA CLASE
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    /*
     * METODO QUE PERMITE OBTENER LOS DATOS DEL USUARIO PARA PASARLOS A OTRA CLASE
     */
    public boolean userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_GENDER, user.getGender());
        editor.apply();
        return true;
    }

    /*
     * METODO QUE VERIFICA SI EL USUARIO YA SE LOGEO EN LA APLICACION (ES COMO SI EL USURIO TUVIERA UNA SESION)
     */
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
            return true;
        return false;
    }

    /*
     * METODO QUE RETORNA UN USUARIO CON LOS DATOS QUE SE CREARON EN EL REGISTRO
     */
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_GENDER, null)
        );
    }

    /*
     * METODO QUE RECETEA TODAS LAS CONFIGURACION DEL SharedPreferences
     */
    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
