package com.example.patrick.proj_motorista.Activity.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.patrick.proj_motorista.Entidades.RelatorioProblemas;
import com.example.patrick.proj_motorista.Entidades.Viagens;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RelatoProbEmpresaActivity extends AppCompatActivity {
    TextView txtRelatoProblemas;
    DatabaseReference referencia;
    Viagens viagem;
    RelatorioProblemas relatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relato_prob_empresa);
        Intent intent = getIntent();
        Viagens viag = (Viagens) intent.getSerializableExtra("viagem");
        viagem = viag;
        txtRelatoProblemas = (TextView) findViewById(R.id.txtRelatErro);
        eventDatabase();
    }

    private void eventDatabase() {
        referencia = FirebaseDatabase.getInstance().getReference();
        referencia.child("RelatorioProblemas").orderByChild("id_Viagem").equalTo(viagem.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    RelatorioProblemas relat = objSnapshot.getValue(RelatorioProblemas.class);
                    relatorio = relat;
                    txtRelatoProblemas.setText(relatorio.getConteudo_msg());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
