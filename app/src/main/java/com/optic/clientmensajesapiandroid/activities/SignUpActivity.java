package com.optic.clientmensajesapiandroid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class SignUpActivity extends AppCompatActivity {

    private Button buttonSignUp;
    private EditText editTextName, editTextEmail, editTextPassword;
    private RadioGroup radioGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignUp = (Button) findViewById(R.id.buttonSignUpRegister);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        radioGender = (RadioGroup) findViewById(R.id.radioGender);



        // ONCLICK REGISTRAR USUARIO
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });
    }
    /*
     * METODO QUE PERMITE REGISTRAR UN NUEVO USUARIO EN LA DB USANDO RETROFIT PARA ENVIAR PETICION POST
     */
    private void userSignUp(){
        // DIALOG QUE SE MOSTRARA MIENTRAS SE REALIZA EL REGISTRO EN LA DB
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        // OBTENIENDO LOS VALORES DE LOS CAMPOS
        final RadioButton radioSex = (RadioButton) findViewById(radioGender.getCheckedRadioButtonId());

        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String gender = radioSex.getText().toString();


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // RETROFIT SERVICE INSTANCE
        APIService service = retrofit.create(APIService.class);

        // DEFINIENDO EL OBJETO USUARIO QUE SE VA A CREAR EN LA DB
        User user = new User(name, email, password, gender);

        // DEFINIENDO LA LLAMADA
        Call<Result> call = service.createUser(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender()
        );

        // EJECUTANDO LA PETICION POST
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                // MOSTRANDO EL MENSAJE DE LA RESPUESTA
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                // SI NO HAY ERROR EN LA PETICION SE REDIRIGE AL USUARIO A HomeActivity
                if (!response.body().getError()) {
                    //starting profile activity
                    finish();
                    // CREANDO UNA SESION AL USUARIO QUE SE REGISTRO
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
