package com.example.patrick.proj_motorista.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {
    private static FirebaseDatabase database;
    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;
    private static FirebaseUser usuario;
    private static String tipoUserLogado;

    public static DatabaseReference getFirebase(){
        if (referenciaFirebase == null){
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
            referenciaFirebase = database.getReference();
        }
        return referenciaFirebase;
    }

    public static String getTipoUserLogado() {
        return tipoUserLogado;
    }

    public static void setTipoUserLogado(String tipoUserLogado) {
        ConfigFirebase.tipoUserLogado = tipoUserLogado;
    }

    public static FirebaseAuth getFirebaseAutentificacao(){
        if (autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    public static FirebaseUser getFirabaseUser(){
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        return usuario;
    }

    public static void Deslogar(){
        getFirebaseAutentificacao();
        autenticacao.signOut();
    }
}
