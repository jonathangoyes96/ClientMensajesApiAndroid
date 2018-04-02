package com.optic.clientmensajesapiandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.optic.clientmensajesapiandroid.R;
import com.optic.clientmensajesapiandroid.models.User;

import java.util.List;

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
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
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
