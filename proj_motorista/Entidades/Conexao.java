package com.example.patrick.proj_motorista.Entidades;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Conexao {
    private static FirebaseAuth firebaseauth;
    private static FirebaseUser firebaseuser;
    private static FirebaseAuth.AuthStateListener authstatelistener;

    public static FirebaseUser getUsuario(){
        return firebaseuser;
    }

    public static void Deslogar(){
        firebaseauth.signOut();
    }

}
