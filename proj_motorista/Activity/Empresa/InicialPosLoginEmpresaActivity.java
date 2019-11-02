package com.example.patrick.proj_motorista.Activity.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Empresa;
import com.example.patrick.proj_motorista.Entidades.Gastos;
import com.example.patrick.proj_motorista.Entidades.Motorista;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InicialPosLoginEmpresaActivity extends AppCompatActivity {
    Empresa empresa = new Empresa();
    String StatusViagem;
    String teste2;
    DatabaseReference referencia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_pos_login_empresa);
        Button btnPerfil = (Button) findViewById(R.id.btnPerfil);
        Button btnAtribuirViagem = (Button) findViewById(R.id.btnViagens);


        btnAtribuirViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialPosLoginEmpresaActivity.this, ListaMotoristasViagemEmpresaActivity.class);
                startActivity(intent);
            }
        });

    }




    //INUTILIZAVEL
    //APENAS_EXEMPLO
    private void eventDatabase() {
        referencia = ConfigFirebase.getFirebase();
        final String IDATUAL = ConfigFirebase.getFirebaseAutentificacao().getCurrentUser().getUid();
        referencia.child("Empresa").child("cGF0cmlja0Bob3RtYWlsLmNvbQ==").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("telefone").getValue();
                System.out.println(post_title);
                System.out.println("Ola riot");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });
    }
    //INUTILIZAVEL
    //APENAS_EXEMPLO






        public void eventDatabase2(){
        final boolean StatsViag;
        referencia = ConfigFirebase.getFirebase();
            referencia.child("Empresa").orderByChild("id").equalTo("cGF0cmlja0Bob3RtYWlsLmNvbQ==").addValueEventListener(new ValueEventListener() {
            Empresa user;
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                        user = objSnapshot.getValue(Empresa.class);

                        System.out.println("Viagem: " + StatusViagem);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            }

}

