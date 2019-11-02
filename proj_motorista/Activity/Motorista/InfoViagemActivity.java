package com.example.patrick.proj_motorista.Activity.Motorista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Viagens;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class InfoViagemActivity extends AppCompatActivity {
    DatabaseReference referencia;
    Viagens viagem = new Viagens();
    private EditText edtCadTipoCarga;
    private EditText edtCadLocCarregamento;
    private EditText edtCadLocDescarregamento;
    private EditText edtCadHorarioSaida;
    private EditText edtCadHorarioChegada;
    private EditText edtCadInfoCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viagem);
        eventDatabase();
        edtCadTipoCarga = (EditText)findViewById(R.id.edtTipodeCarga2);
        edtCadLocCarregamento = (EditText)findViewById(R.id.edtLocCarregamento2);
        edtCadLocDescarregamento = (EditText)findViewById(R.id.edtLocDescarregamento2);
        edtCadHorarioSaida = (EditText)findViewById(R.id.edtHorarioSaida2);
        edtCadHorarioChegada = (EditText)findViewById(R.id.edtHorarioChegada2);
        edtCadInfoCarga = (EditText)findViewById(R.id.edtInfoCarga);

        edtCadTipoCarga.setEnabled(false);
        edtCadLocCarregamento.setEnabled(false);
        edtCadLocDescarregamento.setEnabled(false);
        edtCadHorarioSaida.setEnabled(false);
        edtCadHorarioChegada.setEnabled(false);
        edtCadInfoCarga.setEnabled(false);
    }

    public void eventDatabase() {
        FirebaseUser userAtual = ConfigFirebase.getFirabaseUser();
        referencia = ConfigFirebase.getFirebase();
        referencia.child("Viagens").orderByChild("id_Motorista_status").equalTo(userAtual.getUid()+"_em espera")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Viagens viag;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    viag = objSnapshot.getValue(Viagens.class);
                    viagem = viag;

                    edtCadTipoCarga.setText(viagem.getTipoCarga());
                    edtCadLocCarregamento.setText(viagem.getNomeLocalCarregamento());
                    edtCadLocDescarregamento.setText(viagem.getNomeLocalDescarga());
                    edtCadHorarioSaida.setText(viagem.getHorario_maximo_do_saida());
                    edtCadHorarioChegada.setText(viagem.getHorario_maximo_chegada());
                    edtCadInfoCarga.setText(viagem.getInfoCarga());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void AceitarViagem(View view) {
        FirebaseUser userAtual = ConfigFirebase.getFirabaseUser();
        referencia.child("Usuario").child(userAtual.getUid()).child("viagem")
                .setValue("em andamento");

        referencia.child("Viagens").child(viagem.getId()).child("status")
                .setValue("em andamento");

        referencia.child("Viagens").child(viagem.getId()).child("id_Motorista_status")
                .setValue(userAtual.getUid()+"_em andamento");

        Intent intent = new Intent(InfoViagemActivity.this, InicialPosLoginActivity.class);
        startActivity(intent);
        finish();

    }


}
