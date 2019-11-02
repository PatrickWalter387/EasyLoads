package com.example.patrick.proj_motorista.Activity.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Locais;
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

public class AtribuirEmpresaActivity extends AppCompatActivity {

    private EditText edtCadTipoCarga;
    private EditText edtCadLocCarregamento;
    private EditText edtCadLocDescarregamento;
    private EditText edtCadHorarioSaida;
    private EditText edtCadHorarioChegada;
    private EditText edtCadInfoCarga;
    private Button btnCadastrar;
    private Viagens viagem;
    private FirebaseAuth autentificacao;
    private Spinner SpinnerLocCarregamento;
    private Spinner SpinnerLocDescarregamento;

    ListView ListV_Dados;
    DatabaseReference referencia;
    private List<Locais> listUser = new ArrayList<Locais>();
    private ArrayAdapter<Locais> arrayAdapterLocais;
    Motorista motoristaSelect;
    Locais localSelectCarregamento;
    Locais localSelectDescarregamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atribuir_empresa);
        Intent intent = getIntent();
        final Motorista motorista = (Motorista) intent.getSerializableExtra("motorista");
        eventDatabase();


        edtCadTipoCarga = (EditText) findViewById(R.id.edtCadTipoCarga);
        edtCadHorarioSaida = (EditText) findViewById(R.id.edtCadHorarioSaida);
        edtCadHorarioChegada = (EditText) findViewById(R.id.edtCadHorarioChegada);
        edtCadInfoCarga = (EditText) findViewById(R.id.edtCadInfoCarga);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        SpinnerLocCarregamento = (Spinner)findViewById(R.id.spinnerLocCarregamento);
        SpinnerLocDescarregamento = (Spinner)findViewById(R.id.spinnerLocDescarregamento);


        SpinnerLocCarregamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                localSelectCarregamento = (Locais) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerLocDescarregamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                localSelectDescarregamento = (Locais) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
                    viagem = new Viagens();
                    DatabaseReference referencia = ConfigFirebase.getFirebase();
                    String id = referencia.push().toString();
                    id = id.substring(45, id.length() - 0);

                    viagem.setId(id);
                    viagem.setId_Motorista(motorista.getId());
                    viagem.setId_Empresa(userAtual.getUid());
                    viagem.setStatus("em espera");
                    viagem.setTipoCarga(edtCadTipoCarga.getText().toString());

                    viagem.setLocalCarregamento(localSelectCarregamento.getCoordenadas());

                    viagem.setLocalDescarga(localSelectDescarregamento.getCoordenadas());

                    viagem.setNomeLocalCarregamento(localSelectCarregamento.toString());
                    viagem.setNomeLocalDescarga(localSelectDescarregamento.toString());

                    viagem.setSituacaoEmAndamento("EsperandoCarregamento");
                    viagem.setHorario_maximo_do_saida(edtCadHorarioSaida.getText().toString());
                    viagem.setHorario_maximo_chegada(edtCadHorarioChegada.getText().toString());
                    viagem.setInfoCarga(edtCadInfoCarga.getText().toString());
                    viagem.setId_Motorista_status(viagem.getId_Motorista() + "_" + viagem.getStatus());

                    viagem.Salvar();

                    motorista.setViagem("em espera");
                    DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
                    referenciaFirebase.child("Usuario").child(motorista.getId()).child("viagem")
                            .setValue(motorista.getViagem());

                    Toast.makeText(AtribuirEmpresaActivity.this, "Viagem atribuida com sucesso",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AtribuirEmpresaActivity.this, StatsViagemEmpresaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("motorista", motorista);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(AtribuirEmpresaActivity.this, "Falha ao cadastrar viagem",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void eventDatabase() {
        referencia = ConfigFirebase.getFirebase();

        referencia.child("Locais").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Locais locais = objSnapshot.getValue(Locais.class);
                    listUser.add(locais);
                }
                arrayAdapterLocais = new ArrayAdapter<Locais>(AtribuirEmpresaActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, listUser);

                arrayAdapterLocais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                SpinnerLocCarregamento.setAdapter(arrayAdapterLocais);
                SpinnerLocDescarregamento.setAdapter(arrayAdapterLocais);            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
