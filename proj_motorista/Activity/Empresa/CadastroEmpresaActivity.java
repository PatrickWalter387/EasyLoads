package com.example.patrick.proj_motorista.Activity.Empresa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.patrick.proj_motorista.Activity.LoginActivity;
import com.example.patrick.proj_motorista.Activity.Motorista.CadastroActivity;
import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Empresa;
import com.example.patrick.proj_motorista.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

public class CadastroEmpresaActivity extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadSenha;
    private EditText edtCadRepitirSenha;
    private EditText edtCadNome;
    private EditText edtCadRamoAtuacao;
    private EditText edtCadTelefone;
    private EditText edtCadCnpj;
    private Button btnCadastrar;
    private Empresa empresa;
    private FirebaseAuth autentificacao;
    private Uri uriSelect;
    private ImageView imgPerfil;
    private Button btnFoto;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_empresa);

        edtCadEmail = (EditText)findViewById(R.id.edtCadEmail);
        edtCadSenha = (EditText)findViewById(R.id.edtCadSenha);
        edtCadRepitirSenha = (EditText)findViewById(R.id.edtCadRepetirSenha);
        edtCadNome = (EditText)findViewById(R.id.edtCadNome);
        edtCadRamoAtuacao = (EditText)findViewById(R.id.edtCadRamoAtuacao);
        edtCadTelefone = (EditText)findViewById(R.id.edtCadTelefone);
        edtCadCnpj = (EditText)findViewById(R.id.edtCadCnpj);
        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);

        btnFoto = findViewById(R.id.btnFotoCadastroEmpresa);

        imgPerfil = findViewById(R.id.imgCadastroEmpresa);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new ProgressDialog(CadastroEmpresaActivity.this);
                dialog.setTitle("Carregando");
                dialog.setMessage("Aguarde o fim da requisição...");
                dialog.show();

                if(edtCadSenha.getText().toString().equals(edtCadRepitirSenha.getText().toString())){
                    empresa = new Empresa();
                    empresa.setEmail(edtCadEmail.getText().toString());
                    empresa.setSenha(edtCadSenha.getText().toString());
                    empresa.setNome(edtCadNome.getText().toString());
                    empresa.setRamoAtuacao(edtCadRamoAtuacao.getText().toString());
                    empresa.setTelefone(edtCadTelefone.getText().toString());
                    empresa.setCnpj(edtCadCnpj.getText().toString());

                    CadastroUser();

                }
                else{
                    dialog.dismiss();
                    Toast.makeText(CadastroEmpresaActivity.this, "As senhas não são iguais", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void CadastroUser(){

        autentificacao = ConfigFirebase.getFirebaseAutentificacao();
        autentificacao.createUserWithEmailAndPassword(empresa.getEmail(), empresa.getSenha()).addOnCompleteListener(CadastroEmpresaActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String filename =  UUID.randomUUID().toString();
                    final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);

                    try {
                        ref.putFile(uriSelect);
                    }
                    catch(Exception e){
                        Toast.makeText(CadastroEmpresaActivity.this, "Escolha uma Foto", Toast.LENGTH_SHORT).show();
                    }


                    Toast.makeText(CadastroEmpresaActivity.this, "Cadastro Bem-Sucedido", Toast.LENGTH_SHORT).show();

                    FirebaseUser UsuarioFirebase = task.getResult().getUser();
                    empresa.setId(UsuarioFirebase.getUid());
                    empresa.Salvar();

                    AbrirLogin();
                }
                else{
                    String Erro = "";

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
                    dialog.dismiss();
                    Toast.makeText(CadastroEmpresaActivity.this, "Erro: " + Erro, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void AbrirLogin(){
        dialog.dismiss();
        Intent intent = new Intent(CadastroEmpresaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void selecionarFoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
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