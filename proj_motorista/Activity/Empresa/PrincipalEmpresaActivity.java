package com.example.patrick.proj_motorista.Activity.Empresa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

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

public class PrincipalEmpresaActivity extends AppCompatActivity {
    ListView ListV_Dados;
    DatabaseReference referencia;
    private List<Motorista> listUser = new ArrayList<Motorista>();
    private ArrayAdapter<Motorista> arrayAdapterUser;
    Motorista motoristaSelect;
    private EditText edtCadEmail2;
    private EditText edtCadSenha2;
    private EditText edtCadRepitirSenha2;
    private EditText edtCadNome2;
    private EditText edtCadData_nasc2;
    private EditText edtCadTelefone2;
    private RadioButton rbMasculino2;
    private RadioButton rbFeminino2;
    private Button btnAtt;
    private Button btnDel;
    DatabaseReference autentificacao;

    private EditText edtCadModelo2;
    private EditText edtCadMarca2;
    private EditText edtCadCor2;
    private EditText edtCadPlaca2;
    private EditText edtCadAno2;
    private RadioButton rbVeiculoEmpresa2;
    private RadioButton rbVeiculoParticular2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_empresa);
        edtCadEmail2 = (EditText)findViewById(R.id.edtCadEmail2);
        edtCadSenha2 = (EditText)findViewById(R.id.edtCadSenha2);
        edtCadNome2 = (EditText)findViewById(R.id.edtCadNome2);
        edtCadTelefone2 = (EditText)findViewById(R.id.edtCadTelefone2);
        ListV_Dados = (ListView) findViewById(R.id.listUser);
        edtCadData_nasc2 = (EditText)findViewById(R.id.edtCadData_nasc2);
        rbFeminino2 = (RadioButton)findViewById(R.id.rbFeminino2);
        rbMasculino2 = (RadioButton)findViewById(R.id.rbMasculino2);
        btnAtt = (Button) findViewById(R.id.btnAtualizar);
        btnDel = (Button) findViewById(R.id.btExcluir);

        rbVeiculoEmpresa2 = (RadioButton)findViewById(R.id.rbVeiculoEmpresa2);
        rbVeiculoParticular2 = (RadioButton)findViewById(R.id.rbVeiculoParticular2);
        edtCadModelo2 = (EditText)findViewById(R.id.edtCadModelo2);
        edtCadMarca2 = (EditText)findViewById(R.id.edtCadMarca2);
        edtCadCor2 = (EditText)findViewById(R.id.edtCadCor2);
        edtCadPlaca2 = (EditText)findViewById(R.id.edtCadPlaca2);
        edtCadAno2 = (EditText)findViewById(R.id.edtCadAno2);

        edtCadEmail2.setEnabled(false);
        edtCadSenha2.setEnabled(false);

        eventDatabase();

        btnAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    final Motorista motorista = new Motorista();
                    motorista.setId(motoristaSelect.getId());
                    motorista.setNome(edtCadNome2.getText().toString());
                    motorista.setTelefone(edtCadTelefone2.getText().toString());
                    motorista.setData_nasc(edtCadData_nasc2.getText().toString());

                    if (rbFeminino2.isChecked()) {
                            motorista.setSexo("Feminino");
                    }
                    else{
                            motorista.setSexo("Masculino");
                    }

                    if (rbVeiculoEmpresa2.isChecked()) {
                        motorista.setSexo("Empresa");
                    } else {
                        motorista.setSexo("Particular");
                    }
                    motorista.setMarca(edtCadMarca2.getText().toString());
                    motorista.setModelo(edtCadModelo2.getText().toString());
                    motorista.setCor(edtCadCor2.getText().toString());
                    motorista.setAno(edtCadAno2.getText().toString());
                    motorista.setPlaca(edtCadPlaca2.getText().toString());

                        autentificacao = ConfigFirebase.getFirebase();
                        autentificacao.child("Empresa").child(motorista.getId()).setValue(motorista);


                        Toast.makeText(PrincipalEmpresaActivity.this, "Dados atualizados com sucesso",
                                Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e){
                        Toast.makeText(PrincipalEmpresaActivity.this, "Erro ao atualizar dados",
                                Toast.LENGTH_SHORT).show();
                    }

                }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Motorista motorista = new Motorista();
                    motorista.setId(motoristaSelect.getId());
                    autentificacao = ConfigFirebase.getFirebase();
                    autentificacao.child("Empresa").child(motorista.getId()).removeValue();
                }
                catch (Exception e){
                    Toast.makeText(PrincipalEmpresaActivity.this, "Erro ao excluir conta",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        ListV_Dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                motoristaSelect = (Motorista) parent.getItemAtPosition(position);
                edtCadNome2.setText(motoristaSelect.getNome());
                edtCadEmail2.setText(motoristaSelect.getEmail());
                edtCadSenha2.setText(motoristaSelect.getSenha());
                edtCadTelefone2.setText(motoristaSelect.getTelefone());
                edtCadData_nasc2.setText(motoristaSelect.getTelefone());

                String TesteSexo;
                TesteSexo = motoristaSelect.getSexo();
                if(TesteSexo.equals("Feminino")){
                    rbFeminino2.setSelected(true);
                    rbMasculino2.setSelected(false);
                }
                else{
                    rbFeminino2.setSelected(false);
                    rbMasculino2.setSelected(true);
                }
            }
        });

    }

    private void eventDatabase() {
        referencia = ConfigFirebase.getFirebase();
        FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
        referencia.child("Usuario").orderByChild("codEmpresa").equalTo(userAtual.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Motorista user = objSnapshot.getValue(Motorista.class);
                    listUser.add(user);
                }
                arrayAdapterUser = new ArrayAdapter<Motorista>(PrincipalEmpresaActivity.this,
                        android.R.layout.simple_list_item_1, listUser);
                ListV_Dados.setAdapter(arrayAdapterUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
