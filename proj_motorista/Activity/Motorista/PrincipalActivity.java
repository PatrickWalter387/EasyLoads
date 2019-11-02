package com.example.patrick.proj_motorista.Activity.Motorista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.patrick.proj_motorista.Activity.LoginActivity;
import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Usuario;
import com.example.patrick.proj_motorista.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    ListView ListV_Dados;
    DatabaseReference referencia;
    private List<Usuario> listUser = new ArrayList<Usuario>();
    private ArrayAdapter<Usuario> arrayAdapterUser;
    Usuario usuarioSelect;
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
        setContentView(R.layout.activity_principal);
        edtCadEmail2 = (EditText)findViewById(R.id.edtAttEmail);
        edtCadSenha2 = (EditText)findViewById(R.id.edtAttSenha);
        edtCadRepitirSenha2 = (EditText)findViewById(R.id.edtAttRepetirSenha);
        edtCadNome2 = (EditText)findViewById(R.id.edtAttNome);
        edtCadTelefone2 = (EditText)findViewById(R.id.edtAttTelefone);
        edtCadData_nasc2 = (EditText)findViewById(R.id.edtAttData_nasc);
        rbFeminino2 = (RadioButton)findViewById(R.id.rbAttFeminino);
        rbMasculino2 = (RadioButton)findViewById(R.id.rbAttFeminino);
        rbVeiculoEmpresa2 = (RadioButton)findViewById(R.id.rbAttVeiculoEmpresa);
        rbVeiculoParticular2 = (RadioButton)findViewById(R.id.rbAttVeiculoParticular);
        edtCadModelo2 = (EditText)findViewById(R.id.edtAttModelo);
        edtCadMarca2 = (EditText)findViewById(R.id.edtAttMarca);
        edtCadCor2 = (EditText)findViewById(R.id.edtAttCor);
        edtCadPlaca2 = (EditText)findViewById(R.id.edtAttPlaca);
        edtCadAno2 = (EditText)findViewById(R.id.edtAttAno);

        btnAtt = (Button) findViewById(R.id.btnAtualizar);
        btnDel = (Button) findViewById(R.id.btExcluir);
        eventDatabase();

        btnAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCadSenha2.getText().toString().equals(edtCadRepitirSenha2.getText().toString()) && ( !edtCadSenha2.getText().toString().trim().isEmpty())){
                    final Usuario usuario = new Usuario();
                    usuario.setId(usuarioSelect.getId());
                    usuario.setViagem(usuarioSelect.getViagem());
                    usuario.setCodEmpresa(usuarioSelect.getCodEmpresa());
                    usuario.setEmail(edtCadEmail2.getText().toString());
                    usuario.setSenha(edtCadSenha2.getText().toString());
                    usuario.setNome(edtCadNome2.getText().toString());
                    usuario.setTelefone(edtCadTelefone2.getText().toString());
                    usuario.setData_nasc(edtCadData_nasc2.getText().toString());

                    if(rbFeminino2.isChecked()){
                        usuario.setSexo("Feminino");
                    }
                    else{
                        usuario.setSexo("Masculino");
                    }

                    if (rbVeiculoEmpresa2.isChecked()) {
                        usuario.setSexo("Empresa");
                    } else {
                        usuario.setSexo("Particular");
                    }
                    usuario.setMarca(edtCadMarca2.getText().toString());
                    usuario.setModelo(edtCadModelo2.getText().toString());
                    usuario.setCor(edtCadCor2.getText().toString());
                    usuario.setAno(edtCadAno2.getText().toString());
                    usuario.setPlaca(edtCadPlaca2.getText().toString());


                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updateEmail(usuario.getEmail())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(usuario.getSenha())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            autentificacao = ConfigFirebase.getFirebase();
                                                            autentificacao.child("Usuario").child(usuario.getId()).setValue(usuario);

                                                            Toast.makeText(PrincipalActivity.this, "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            Toast.makeText(PrincipalActivity.this, "Erro ao atualizar dados 2", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });



                                    }
                                    else{
                                        Toast.makeText(PrincipalActivity.this, "Erro ao atualizar dados 1", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });




                }
                else{
                    Toast.makeText(PrincipalActivity.this, "As senhas não são iguais", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Usuario usuario = new Usuario();
                                    usuario.setId(usuarioSelect.getId());
                                    autentificacao = ConfigFirebase.getFirebase();
                                    autentificacao.child("Usuario").child(usuario.getId()).removeValue();
                                    ConfigFirebase.Deslogar();
                                    Intent AbrirTelaLogin = new Intent(PrincipalActivity.this, LoginActivity.class);
                                    startActivity(AbrirTelaLogin);
                                    Toast.makeText(PrincipalActivity.this, "Conta deletatada com sucesso", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });

    }

    public void eventDatabase(){
        referencia = ConfigFirebase.getFirebase();
        FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
        referencia.child("Usuario").orderByChild("id").equalTo(userAtual.getUid()).addValueEventListener(new ValueEventListener() {
            Usuario user;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    user = objSnapshot.getValue(Usuario.class);
                    usuarioSelect = user;
                    edtCadNome2.setText(usuarioSelect.getNome());
                    edtCadEmail2.setText(usuarioSelect.getEmail());
                    edtCadSenha2.setText(usuarioSelect.getSenha());
                    edtCadTelefone2.setText(usuarioSelect.getTelefone());
                    edtCadData_nasc2.setText(usuarioSelect.getData_nasc());


                    String TesteSexo;
                    TesteSexo = usuarioSelect.getSexo();
                    if(TesteSexo.equals("Masculino")){
                        rbFeminino2.setChecked(false);
                        rbMasculino2.setChecked(true);
                    }
                    else{
                        rbFeminino2.setChecked(true);
                        rbMasculino2.setChecked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
