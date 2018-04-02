package com.optic.clientmensajesapiandroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optic.clientmensajesapiandroid.R;
import com.optic.clientmensajesapiandroid.adapters.UserAdapter;
import com.optic.clientmensajesapiandroid.api.APIService;
import com.optic.clientmensajesapiandroid.api.APIUrl;
import com.optic.clientmensajesapiandroid.models.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Optic on 1/04/2018.
 * DESCRIPCCION:
 * 1. CREA EL RECYCLERVIEW QUE MOSTRARA LA LISTA DE TODOS LOS USUARIO REGISTRADOS EN LA DB
 * 2. UTILIZA RETROFIT PARA HACER UNA PETICION GET Y OBTENER TODOS LOS USUARIOS DEL WEB SERVICES
 *
 */

public class HomeFragment extends Fragment{

    private RecyclerView recyclerViewUsers;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");

        // RECYCLER VIEW CONFIGURACIONES
        recyclerViewUsers = (RecyclerView) view.findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));

        // RETROFIT INSTANCES
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Users> call = service.getUsers();

        // MOSTRANDO TODOS LOS USUARIOS PROVENIENTES DEL WEB SERVICES EN LA VISTA UTILIZANDO RECYCLER VIEW
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                adapter = new UserAdapter(response.body().getUsers(), getActivity());
                recyclerViewUsers.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }

}
