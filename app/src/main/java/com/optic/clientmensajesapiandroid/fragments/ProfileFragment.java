package com.optic.clientmensajesapiandroid.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.optic.clientmensajesapiandroid.R;
import com.optic.clientmensajesapiandroid.api.APIService;
import com.optic.clientmensajesapiandroid.api.APIUrl;
import com.optic.clientmensajesapiandroid.helper.SharedPrefManager;
import com.optic.clientmensajesapiandroid.models.Result;
import com.optic.clientmensajesapiandroid.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Optic on 1/04/2018.
 */

public class ProfileFragment extends Fragment {

    private Button buttonUpdate;
    private EditText editTextName, editTextEmail, editTextPassword;
    private RadioGroup radioGender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");

        buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);

        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);

        radioGender = (RadioGroup) view.findViewById(R.id.radioGender);



        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());
        editTextPassword.setText("0000");

        if (user.getGender().equalsIgnoreCase("male")) {
            radioGender.check(R.id.radioMale);
        } else {
            radioGender.check(R.id.radioFemale);
        }

        // ONCLICK ACTUALIZAR INFORMACION DEL USUARIO
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

    }

    /*
     * METODO QUE PERMITE ACTUALIZAR LOS DATOS DE UN USUARIO MEDIANTE PETICION PUT REALIZADA A TRAVES DE RETROFIT
     */
    private void updateUser() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        final RadioButton radioSex = (RadioButton) getActivity().findViewById(radioGender.getCheckedRadioButtonId());

        // OBTENIENDO VALORES DE LOS CAMPOS
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String gender = radioSex.getText().toString();

        // RETROFIT INSTANCES
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // APIServices INSTANCES
        APIService service = retrofit.create(APIService.class);

        // OBTENIENDO EL USUARIO CON LOS DATOS DE LA SESION CREADA A TRAVES DE SharedPreferences
        User user = new User(SharedPrefManager.getInstance(getActivity()).getUser().getId(), name, email, password, gender);

        // MAPEANDO DATOS DE LA LLAMADA
        Call<Result> call = service.updateUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender()
        );

        // EJECUTANDO LA PETICION
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (!response.body().getError()) {
                    SharedPrefManager.getInstance(getActivity()).userLogin(response.body().getUser());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
