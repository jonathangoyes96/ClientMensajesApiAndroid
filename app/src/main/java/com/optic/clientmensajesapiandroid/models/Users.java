package com.optic.clientmensajesapiandroid.models;

import java.util.ArrayList;

/**
 * Created by Optic on 1/04/2018.
 * MODELO QUE SERVIRA PARA MOSTRAR LA LISTA DE USUARIO CREADOS EN EL RECYCLER VIEW
 */

public class Users {

    private ArrayList<User> users;

    public Users() {

    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
