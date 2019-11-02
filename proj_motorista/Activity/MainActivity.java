package com.example.patrick.proj_motorista.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void souMotorista(View view) {
        ConfigFirebase.setTipoUserLogado("motorista");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void souEmpresa(View view) {
        ConfigFirebase.setTipoUserLogado("empresa");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }



}
