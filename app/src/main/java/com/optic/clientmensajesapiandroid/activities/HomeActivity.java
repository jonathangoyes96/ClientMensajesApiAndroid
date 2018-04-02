package com.optic.clientmensajesapiandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.optic.clientmensajesapiandroid.R;
import com.optic.clientmensajesapiandroid.fragments.HomeFragment;
import com.optic.clientmensajesapiandroid.fragments.MessageFragment;
import com.optic.clientmensajesapiandroid.fragments.ProfileFragment;
import com.optic.clientmensajesapiandroid.helper.SharedPrefManager;

/*
 * DESCRIPCCION:
 *
 * 1. MUESTRA EL NAVIGATION DRAWER
 * 2. PERMITE MOSTRAR EL FRAGMENT QUE EL USURIO ELIJA SEGUN LAS OPCIONES DEL MENU
 * 3. PERMITE CERRAR SESION
 */

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // TEXTVIEW PERTENECIENTE AL NAVIGATIONDRAWER
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // SI EL USUARIO NO ESTA LOGEADO LO ENVIA A LA VISTA DE SignInActivity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        // VIEWS NAVIGATION DRAWER INTANCES
        View headerView = navigationView.getHeaderView(0);
        textViewName = (TextView) headerView.findViewById(R.id.textViewName);
        textViewName.setText(SharedPrefManager.getInstance(this).getUser().getName());

        // MOSTRANDO HomeFragment por defecto
        displaySelectedScreen(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_messages:
                fragment = new MessageFragment();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        // REEMPLAZAR EL FRAGMENT SEGUN EL QUE ELIJA EL USUARIO EN EL MENU
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /*
     * METODO QUE PERMITE CERRAR SESION EN LA APLICACION
     */
    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }

    /*
     * METODO QUE PERMITE MOSTRAR EL FRAGMENT QUE EL USUARIO ELIJA
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }
}
