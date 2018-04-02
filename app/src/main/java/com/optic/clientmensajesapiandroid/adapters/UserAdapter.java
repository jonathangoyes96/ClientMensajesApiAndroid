package com.optic.clientmensajesapiandroid.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.optic.clientmensajesapiandroid.R;
import com.optic.clientmensajesapiandroid.api.APIService;
import com.optic.clientmensajesapiandroid.api.APIUrl;
import com.optic.clientmensajesapiandroid.helper.SharedPrefManager;
import com.optic.clientmensajesapiandroid.models.MessageResponse;
import com.optic.clientmensajesapiandroid.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Optic on 1/04/2018.
 * Adaptador para mostrar todos los usuarios registrados en la DB a traves de recyclerView
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context mCtx;

    public UserAdapter(List<User> users, Context mCtx) {
        this.users = users;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_users, parent, false);
        return new ViewHolder(v);
    }

    /*
     * METODO DONDE SE SETEAN LOS VALORES DE LOS VIEWS
     */
    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        final User user = users.get(position);
        holder.textViewName.setText(user.getName());

        // MOSTRANDO ALERTDIALOG PARA ENVIAR MENSAJE
        holder.imageButtonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(mCtx);
                View promptsView = li.inflate(R.layout.dialog_send_messages, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mCtx);
                alertDialogBuilder.setView(promptsView);

                // VIEWS ALERT DIALOG INSTANCES
                final EditText editTextTitle = (EditText) promptsView.findViewById(R.id.editTextTitle);
                final EditText editTextMessage = (EditText) promptsView.findViewById(R.id.editTextMessage);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Enviar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        // OBTENIENDO LOS VALORES
                                        String title = editTextTitle.getText().toString().trim();
                                        String message = editTextMessage.getText().toString().trim();

                                        // ENVIANDO MENSAJE
                                        sendMessage(user.getId(), title, message);
                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


    }


    /*
     * METODO QUE PERMITE ENVIAR UN MENSAJE A OTRO USUARIO
     */
    private void sendMessage(int id, String title, String message){
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        progressDialog.setMessage("Sending Message...");
        progressDialog.show();

        // RETROFIT INSTANCE

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // API SERVICE INSTANCE
        APIService service = retrofit.create(APIService.class);

        // CONFIGURANDO LOS PAREMETROS QUE DEFINIMOS EN APIServices PARA HACER LA PETICION
        // SharedPrefManager.getInstance(mCtx).getUser().getId() -> OBTENIENDO EL ID QUE DEFINIMOS CON LA CRECION DE LA SESION A TRAVES DE SHAREDPREFERENCES
        Call<MessageResponse> call = service.sendMessage(
                SharedPrefManager.getInstance(mCtx).getUser().getId(),
                id,
                title,
                message
        );

        // EJECUTANDO LA PETICION
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                progressDialog.dismiss();
                Toast.makeText(mCtx, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mCtx, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    /*
     * INSTANCIAR CADA UNA DE LAS VISTAS PERTENECIENTES AL CARDVIEW
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageButton imageButtonMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageButtonMessage = (ImageButton) itemView.findViewById(R.id.imageButtonMessage);
        }
    }
}
