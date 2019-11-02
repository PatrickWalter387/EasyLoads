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
import com.example.patrick.proj_motorista.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaMotoristasViagemEmpresaActivity extends AppCompatActivity {
    ListView ListV_Dados;
    DatabaseReference referencia;
    private List<Motorista> listUser = new ArrayList<Motorista>();
    private ArrayAdapter<Motorista> arrayAdapterUser;
    Motorista motoristaSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_motoristas_viagem_empresa);
        ListV_Dados = (ListView) findViewById(R.id.listUser);

        eventDatabase();

        ListV_Dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                motoristaSelect = (Motorista) parent.getItemAtPosition(position);
                Intent intent = new Intent(ListaMotoristasViagemEmpresaActivity.this, StatsViagemEmpresaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("motorista", motoristaSelect);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private void eventDatabase() {
        referencia = ConfigFirebase.getFirebase();
        FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("GT: " + userAtual.getUid());
        referencia.child("Usuario").orderByChild("codEmpresa").equalTo(userAtual.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Motorista user = objSnapshot.getValue(Motorista.class);
                    System.out.println("GT2: " + user);
                    listUser.add(user);
                }
                arrayAdapterUser = new ArrayAdapter<Motorista>(ListaMotoristasViagemEmpresaActivity.this,
                        android.R.layout.simple_list_item_1, listUser);
                ListV_Dados.setAdapter(arrayAdapterUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public Motorista Enviar_objeto(){
        return motoristaSelect;
    }


}
