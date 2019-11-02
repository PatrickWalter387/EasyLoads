package com.example.patrick.proj_motorista.Activity.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Motorista;
import com.example.patrick.proj_motorista.Entidades.Viagens;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class StatsViagemEmpresaActivity extends AppCompatActivity {
    ListaMotoristasViagemEmpresaActivity telamotoristas = new ListaMotoristasViagemEmpresaActivity();
    DatabaseReference referencia;
    Viagens viagens;
    String StatusViagem;
    Motorista motorist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_viagem_empresa);
        Intent intent = getIntent();
        Motorista motorista = (Motorista) intent.getSerializableExtra("motorista");
        motorist = motorista;
        eventDatabase2(motorista);

        referencia.child("Viagens").orderByChild("id_Motorista_status").equalTo(motorist.getId()+"_em andamento")
                .addValueEventListener(new ValueEventListener() {
                    Viagens viag;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot objSnapshot:dataSnapshot.getChildren()) {
                            viag = objSnapshot.getValue(Viagens.class);
                            viagens = viag;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void eventDatabase2(final Motorista motorista){

        final Button btnAtribuirViagem = (Button) findViewById(R.id.btnAtribuirViagem);
        final Button btnViagemEmEspera = (Button) findViewById(R.id.btnViagemEmEspera);
        final Button btnViagemEmAndamento = (Button) findViewById(R.id.btnViagemEmAndamento);
        FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
        referencia = ConfigFirebase.getFirebase();
        referencia.child("Usuario")
                .orderByChild("id").equalTo(motorista.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Motorista user;
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    user = objSnapshot.getValue(Motorista.class);
                    StatusViagem = user.getViagem();
                    if (StatusViagem.equals("em andamento")){
                        btnAtribuirViagem.setVisibility(View.GONE);
                        btnViagemEmAndamento.setVisibility(View.VISIBLE);
                        btnViagemEmEspera.setVisibility(View.GONE);
                    }
                    else{
                        if(StatusViagem.equals("em espera")){
                            btnAtribuirViagem.setVisibility(View.GONE);
                            btnViagemEmAndamento.setVisibility(View.GONE);
                            btnViagemEmEspera.setVisibility(View.VISIBLE);
                        }
                        else{
                            btnAtribuirViagem.setVisibility(View.VISIBLE);
                            btnViagemEmAndamento.setVisibility(View.GONE);
                            btnViagemEmEspera.setVisibility(View.GONE);
                        }
                    }
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void EnviarViagem(View view) {
        Intent intent = new Intent(StatsViagemEmpresaActivity.this, AtribuirEmpresaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("motorista", motorist);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void VerViagensConcluidas(View view) {
        Intent intent = new Intent(StatsViagemEmpresaActivity.this, ListaViagensConcluidasEmpresaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("motorista", motorist);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void VerInfViagem(View view) {
        Intent intent = new Intent(StatsViagemEmpresaActivity.this, InfoViagAtualEmpresaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("viagem", (Serializable) viagens);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
