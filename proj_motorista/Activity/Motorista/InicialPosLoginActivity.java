package com.example.patrick.proj_motorista.Activity.Motorista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Usuario;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class InicialPosLoginActivity extends AppCompatActivity {
    Usuario usuario = new Usuario();
   String StatusViagem;
    String teste2;
    DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_pos_login);
        Button btnViagDisponivel = (Button) findViewById(R.id.btnViagemDisponivel);
        Button btnViagIndisponivel = (Button) findViewById(R.id.btnViagemIndisponivel);
        Button btnViagAndamento = (Button)findViewById(R.id.btnViagemEmAndamento);
        Button btnPerfil = (Button) findViewById(R.id.btnPerfil);

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(InicialPosLoginActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });

        btnViagIndisponivel.setVisibility(View.GONE);
        btnViagDisponivel.setVisibility(View.GONE);
        btnViagAndamento.setVisibility(View.GONE);



        eventDatabase2();




    }


    //INUTILIZAVEL
    //APENAS_EXEMPLO
    private void eventDatabase() {
        referencia = ConfigFirebase.getFirebase();
        referencia.child("Usuario").child( ConfigFirebase.getFirebaseAutentificacao().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("telefone").getValue();
                System.out.println(post_title);
                System.out.println("Ola riot");
                System.out.println();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }

        });
    }
    //INUTILIZAVEL
    //APENAS_EXEMPLO






        public void eventDatabase2(){
        final boolean StatsViag;
        final Button btnViagDisponivel = (Button) findViewById(R.id.btnViagemDisponivel);
        final Button btnViagIndisponivel = (Button) findViewById(R.id.btnViagemIndisponivel);
            final Button btnViagAndamento = (Button)findViewById(R.id.btnViagemEmAndamento);
            FirebaseUser userAtual = ConfigFirebase.getFirabaseUser();
        referencia = ConfigFirebase.getFirebase();
            referencia.child("Usuario").orderByChild("id").equalTo(userAtual.getUid()).addValueEventListener(new ValueEventListener() {
                Usuario user;
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                        user = objSnapshot.getValue(Usuario.class);
                        StatusViagem = user.getViagem();
                        System.out.println("Viagem: " + StatusViagem);
                        if(StatusViagem.equals("em espera")){
                            btnViagDisponivel.setVisibility(View.VISIBLE);
                            btnViagIndisponivel.setVisibility(View.GONE);
                            btnViagAndamento.setVisibility(View.GONE);
                        }
                        else{
                            if(StatusViagem.equals("livre")) {
                                btnViagIndisponivel.setVisibility(View.VISIBLE);
                                btnViagDisponivel.setVisibility(View.GONE);
                                btnViagAndamento.setVisibility(View.GONE);
                            }
                            else{
                                btnViagIndisponivel.setVisibility(View.GONE);
                                btnViagDisponivel.setVisibility(View.GONE);
                                btnViagAndamento.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            }

    public void InfoViagem(View view) {
        Intent intent = new Intent(InicialPosLoginActivity.this, InfoViagemActivity.class);
        startActivity(intent);
    }

    public void Mapa_e_Relatorio(View view) {
        Intent intent = new Intent(InicialPosLoginActivity.this, ViagemActivity.class);
        startActivity(intent);
    }
}

