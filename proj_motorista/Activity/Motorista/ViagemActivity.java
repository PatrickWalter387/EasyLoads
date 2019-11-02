package com.example.patrick.proj_motorista.Activity.Motorista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Viagens;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ViagemActivity extends AppCompatActivity {
    DatabaseReference referencia = ConfigFirebase.getFirebase();
    Viagens viagem = new Viagens();
    FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
    Button btnFinalizarViagem;
    Button btnFinalizarCarregamento;
    Button btnFinalizarDescarregamento ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagem);
        btnFinalizarViagem = findViewById(R.id.btnFinalizarViagem);
        btnFinalizarCarregamento = findViewById(R.id.btnChegarCarregamento);
        btnFinalizarDescarregamento = findViewById(R.id.btnChegarDescarregamento);

        btnFinalizarCarregamento.setVisibility(View.INVISIBLE);
        btnFinalizarDescarregamento.setVisibility(View.INVISIBLE);
        btnFinalizarViagem.setVisibility(View.INVISIBLE);



        referencia.child("Viagens").orderByChild("id_Motorista_status").equalTo(userAtual.getUid()+"_em andamento")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Viagens viag;
                        for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                            viag = objSnapshot.getValue(Viagens.class);
                            viagem = viag;

                            if(viagem.getSituacaoEmAndamento().equals("EsperandoCarregamento")){
                                btnFinalizarCarregamento.setVisibility(View.VISIBLE);
                                btnFinalizarDescarregamento.setVisibility(View.INVISIBLE);
                                btnFinalizarViagem.setVisibility(View.INVISIBLE);
                            }
                            else{
                                if(viagem.getSituacaoEmAndamento().equals("EsperandoDescarregamento")){
                                    btnFinalizarCarregamento.setVisibility(View.INVISIBLE);
                                    btnFinalizarDescarregamento.setVisibility(View.VISIBLE);
                                    btnFinalizarViagem.setVisibility(View.INVISIBLE);
                                }
                                else{
                                    if(viagem.getSituacaoEmAndamento().equals("EsperandoFinalizacao")){
                                        btnFinalizarCarregamento.setVisibility(View.INVISIBLE);
                                        btnFinalizarDescarregamento.setVisibility(View.INVISIBLE);
                                        btnFinalizarViagem.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        if(viagem.getSituacaoEmAndamento().equals("EsperandoCarregamento")){
                            btnFinalizarCarregamento.setVisibility(View.VISIBLE);
                            btnFinalizarDescarregamento.setVisibility(View.INVISIBLE);
                            btnFinalizarViagem.setVisibility(View.INVISIBLE);
                        }
                        else{
                            if(viagem.getSituacaoEmAndamento().equals("EsperandoDescarregamento")){
                                btnFinalizarCarregamento.setVisibility(View.INVISIBLE);
                                btnFinalizarDescarregamento.setVisibility(View.VISIBLE);
                                btnFinalizarViagem.setVisibility(View.INVISIBLE);
                            }
                            else{
                                if(viagem.getSituacaoEmAndamento().equals("EsperandoFinalizacao")){
                                    btnFinalizarCarregamento.setVisibility(View.INVISIBLE);
                                    btnFinalizarDescarregamento.setVisibility(View.INVISIBLE);
                                    btnFinalizarViagem.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });


    }






    public void ReportarProblema(View view) {
        Intent intent = new Intent(ViagemActivity.this, ReportarProblemasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("viagem", viagem);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void FinalizarViagem(View view) {
        referencia.child("Usuario").child(userAtual.getUid()).child("viagem")
                .setValue("livre");

        referencia.child("Viagens").child(viagem.getId()).child("status")
                .setValue("concluido");

        referencia.child("Viagens").child(viagem.getId()).child("id_Motorista_status")
                .setValue(userAtual.getUid()+"_concluido");

        referencia.child("Viagens").child(viagem.getId()).child("situacaoEmAndamento")
                .setValue("Finalizada");
        viagem.setSituacaoEmAndamento("Finalizada");

        Toast.makeText(ViagemActivity.this, "Viagem concluida com sucesso", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ViagemActivity.this, InicialPosLoginActivity.class);
        startActivity(intent);
    }

    public void IrParaMaps(View view) {
        Intent intent = new Intent(ViagemActivity.this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("viagem", viagem);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void FinalizarCarregamento(View view) {
        referencia.child("Viagens").child(viagem.getId()).child("situacaoEmAndamento")
                .setValue("EsperandoDescarregamento");
        viagem.setSituacaoEmAndamento("EsperandoDescarregamento");

        btnFinalizarCarregamento.setVisibility(View.INVISIBLE);
        btnFinalizarDescarregamento.setVisibility(View.VISIBLE);
        btnFinalizarViagem.setVisibility(View.INVISIBLE);
    }

    public void FinalizarDescarregamento(View view) {
        referencia.child("Viagens").child(viagem.getId()).child("situacaoEmAndamento")
                .setValue("EsperandoFinalizacao");
        viagem.setSituacaoEmAndamento("EsperandoFinalizacao");

        btnFinalizarCarregamento.setVisibility(View.INVISIBLE);
        btnFinalizarDescarregamento.setVisibility(View.INVISIBLE);
        btnFinalizarViagem.setVisibility(View.VISIBLE);
    }
}