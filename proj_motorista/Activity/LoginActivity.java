package com.example.patrick.proj_motorista.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.proj_motorista.Activity.Empresa.CadastroEmpresaActivity;
import com.example.patrick.proj_motorista.Activity.Empresa.InicialPosLoginEmpresaActivity;
import com.example.patrick.proj_motorista.Activity.Motorista.CadastroActivity;
import com.example.patrick.proj_motorista.Activity.Motorista.InicialPosLoginActivity;
import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Usuario;
import com.example.patrick.proj_motorista.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private TextView tvAbreCadastro;
    private Button btnLogar;
    private FirebaseAuth autentificacao;
    private Usuario usuario;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        tvAbreCadastro = (TextView) findViewById(R.id.tvCadastro);
        btnLogar = (Button) findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){
                    usuario = new Usuario();
                    usuario.setEmail(edtEmail.getText().toString());
                    usuario.setSenha(edtSenha.getText().toString());
                    new Task2(v.getContext()).execute();

                }
                else{
                    Toast.makeText(LoginActivity.this, "Campos de e-mail e/ou senha vazios!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConfigFirebase.getTipoUserLogado().equals("motorista")) {
                    abrirCadastroMotorista();
                }
                else{
                    abrirCadastroEmpresa();
                }
            }
        });
    }

    private class Task2 extends AsyncTask<Void, Void, String> {

        private ProgressDialog dialog;
        private Context context;

        public Task2(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setTitle("Carregando");
            dialog.setMessage("Aguarde o fim da requisição...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {


            autentificacao = ConfigFirebase.getFirebaseAutentificacao();

            if(ConfigFirebase.getTipoUserLogado().equals("motorista")) {

                ConfigFirebase.getFirebase().child("Usuario").orderByChild("email").equalTo(usuario.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        user = dataSnapshot.getValue(Usuario.class);

                        if(user == null){
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "E-mail incorreto", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ConfigFirebase.Deslogar();
                            autentificacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        abrirTelaPrincipalMotorista();
                                        Toast.makeText(LoginActivity.this, "Login realizado!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "E-mail e/ou senha incorretos!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            else{
                ConfigFirebase.getFirebase().child("Empresa").orderByChild("email").equalTo(usuario.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        user = dataSnapshot.getValue(Usuario.class);

                        if(user == null){
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "E-mail incorreto", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ConfigFirebase.Deslogar();
                            autentificacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        abrirTelaPrincipalEmpresa();
                                        Toast.makeText(LoginActivity.this, "Login realizado!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "E-mail e/ou senha incorretos!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }



            return "";
        }

        @Override
        protected void onPostExecute(String retorno) {


        }
    }

    public void abrirTelaPrincipalMotorista(){
        Intent IntentAbrirTela = new Intent(LoginActivity.this, InicialPosLoginActivity.class);
        startActivity(IntentAbrirTela);
    }

    public void abrirTelaPrincipalEmpresa(){
        Intent IntentAbrirTela = new Intent(LoginActivity.this, InicialPosLoginEmpresaActivity.class);
        startActivity(IntentAbrirTela);
    }

    public void abrirCadastroMotorista(){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    public void abrirCadastroEmpresa(){
        Intent intent = new Intent(LoginActivity.this, CadastroEmpresaActivity.class);
        startActivity(intent);
    }
}
