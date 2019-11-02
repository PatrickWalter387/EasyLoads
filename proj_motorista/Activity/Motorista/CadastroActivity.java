
package com.example.patrick.proj_motorista.Activity.Motorista;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.patrick.proj_motorista.Activity.LoginActivity;
import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Empresa;
import com.example.patrick.proj_motorista.Entidades.Usuario;
import com.example.patrick.proj_motorista.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadSenha;
    private EditText edtCadRepitirSenha;
    private EditText edtCadNome;
    private Spinner spnCadCodEmpresa;
    private EditText edtCadTelefone;
    private EditText edtCadData_nasc;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private RadioButton rbVeiculoEmpresa;
    private RadioButton rbVeiculoParticular;
    private Button btnCadastrar;
    private Usuario usuario;
    private FirebaseAuth autentificacao;
    private DatabaseReference referencia;
    private List<Empresa> listEmpresas = new ArrayList<Empresa>();
    private ArrayAdapter<Empresa> arrayAdapterEmpresas;
    private Empresa empresaSelect;
    private EditText edtCadModelo;
    private EditText edtCadMarca;
    private EditText edtCadCor;
    private EditText edtCadPlaca;
    private EditText edtCadAno;
    private Uri uriSelect;
    private ImageView imgPerfil;
    private Button btnFoto;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        eventDatabase();

        edtCadEmail = (EditText)findViewById(R.id.edtCadEmail);
        edtCadSenha = (EditText)findViewById(R.id.edtCadSenha);
        edtCadRepitirSenha = (EditText)findViewById(R.id.edtCadRepetirSenha);
        edtCadNome = (EditText)findViewById(R.id.edtCadNome);
        spnCadCodEmpresa = (Spinner)findViewById(R.id.spnCadCodEmpresa);
        edtCadTelefone = (EditText)findViewById(R.id.edtCadTelefone);
        edtCadData_nasc = (EditText)findViewById(R.id.edtCadData_nasc);
        rbFeminino = (RadioButton)findViewById(R.id.rbFeminino);
        rbMasculino = (RadioButton)findViewById(R.id.rbMasculino);

        rbVeiculoEmpresa = (RadioButton)findViewById(R.id.rbVeiculoEmpresa);
        rbVeiculoParticular = (RadioButton)findViewById(R.id.rbVeiculoParticular);
        edtCadModelo = (EditText)findViewById(R.id.edtCadModelo);
        edtCadMarca = (EditText)findViewById(R.id.edtCadMarca);
        edtCadCor = (EditText)findViewById(R.id.edtCadCor);
        edtCadPlaca = (EditText)findViewById(R.id.edtCadPlaca);
        edtCadAno = (EditText)findViewById(R.id.edtCadAno);

        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        btnFoto = findViewById(R.id.btnFotoCadastroMotorista);

        imgPerfil = findViewById(R.id.imgCadastroMotorista);



        spnCadCodEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                empresaSelect = (Empresa) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog = new ProgressDialog(CadastroActivity.this);
                dialog.setTitle("Carregando");
                dialog.setMessage("Aguarde o fim da requisição...");
                dialog.show();


                if(( edtCadSenha.getText().toString().equals(edtCadRepitirSenha.getText().toString()) ) && ( !edtCadSenha.getText().toString().trim().isEmpty())){
                    usuario = new Usuario();
                    usuario.setEmail(edtCadEmail.getText().toString());
                    usuario.setSenha(edtCadSenha.getText().toString());
                    usuario.setNome(edtCadNome.getText().toString());
                    usuario.setCodEmpresa(empresaSelect.getId());
                    usuario.setTelefone(edtCadTelefone.getText().toString());
                    usuario.setData_nasc(edtCadData_nasc.getText().toString());
                    usuario.setViagem("livre");
                    System.out.println("GT: " + empresaSelect);
                    if (rbFeminino.isChecked()) {
                        usuario.setSexo("Feminino");
                    } else {
                        usuario.setSexo("Masculino");
                    }

                    if (rbVeiculoEmpresa.isChecked()) {
                        usuario.setTipo("Empresa");
                    } else {
                        usuario.setTipo("Particular");
                    }
                    usuario.setMarca(edtCadMarca.getText().toString());
                    usuario.setModelo(edtCadModelo.getText().toString());
                    usuario.setCor(edtCadCor.getText().toString());
                    usuario.setAno(edtCadAno.getText().toString());
                    usuario.setPlaca(edtCadPlaca.getText().toString());

                    CadastroUser();
                }
                else{
                    dialog.dismiss();
                    Toast.makeText(CadastroActivity.this, "As senhas não são iguais ou estão em branco", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void eventDatabase() {
        referencia = ConfigFirebase.getFirebase();

        referencia.child("Empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listEmpresas.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Empresa empresa = objSnapshot.getValue(Empresa.class);
                    listEmpresas.add(empresa);
                }
                arrayAdapterEmpresas = new ArrayAdapter<Empresa>(CadastroActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, listEmpresas);

                arrayAdapterEmpresas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnCadCodEmpresa.setAdapter(arrayAdapterEmpresas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void CadastroUser(){


        autentificacao = ConfigFirebase.getFirebaseAutentificacao();
        autentificacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String filename =  UUID.randomUUID().toString();
                    final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);

                    try {
                        ref.putFile(uriSelect);
                    }
                    catch(Exception e){
                        Toast.makeText(CadastroActivity.this, "Escolha uma Foto", Toast.LENGTH_SHORT).show();
                    }


                    Toast.makeText(CadastroActivity.this, "Cadastro Bem-Sucedido", Toast.LENGTH_SHORT).show();



                    FirebaseUser UsuarioFirebase = task.getResult().getUser();
                    usuario.setId(UsuarioFirebase.getUid());
                    usuario.Salvar();


                    AbrirLogin();
                }
                else{
                    String Erro = "";
                    dialog.dismiss();

                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e){
                        Erro = "Digite uma senha de pelomenos 8 caracteres com numeros e letras;";
                    }
                    catch(FirebaseAuthInvalidCredentialsException e){
                        Erro = "Email digitado invalido!";
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        Erro = "Email já cadastrado";
                    }
                    catch (Exception e){
                        Erro = "Erro ao realizar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, "Erro: " + Erro, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void AbrirLogin(){
        dialog.dismiss();
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void selecionarFoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            uriSelect = data.getData();

            Bitmap map = null;
            try {
                map = MediaStore.Images.Media.getBitmap(getContentResolver(), uriSelect);
                Drawable drawable = RoundedBitmapDrawableFactory.create(getResources(), map);
                imgPerfil.setImageDrawable(drawable);
                btnFoto.setAlpha(0);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}