package com.example.patrick.proj_motorista.Activity.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.List;

public class ListaViagensConcluidasEmpresaActivity extends AppCompatActivity {
    ListView ListV_Dados;
    DatabaseReference referencia;
    private List<Viagens> listUser = new ArrayList<Viagens>();
    private ArrayAdapter<Viagens> arrayAdapterUser;
    Motorista motorist;
    Viagens viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viagens_concluidas_empresa);
        Intent intent = getIntent();
        Motorista motorista = (Motorista) intent.getSerializableExtra("motorista");
        motorist = motorista;
        ListV_Dados = (ListView) findViewById(R.id.listUser);

        eventDatabase();

        ListV_Dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viagem = (Viagens) parent.getItemAtPosition(position);
                Intent intent = new Intent(ListaViagensConcluidasEmpresaActivity.this, InfoViagRealizadaEmpresaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("viagem", viagem);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private void eventDatabase() {
        referencia = ConfigFirebase.getFirebase();
        FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
        referencia.child("Viagens").orderByChild("id_Motorista").equalTo(motorist.getId())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Viagens viag = objSnapshot.getValue(Viagens.class);
                    listUser.add(viag);
                }
                arrayAdapterUser = new ArrayAdapter<Viagens>(ListaViagensConcluidasEmpresaActivity.this,
                        android.R.layout.simple_list_item_1, listUser);
                ListV_Dados.setAdapter(arrayAdapterUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
