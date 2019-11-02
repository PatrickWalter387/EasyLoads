package com.example.patrick.proj_motorista.Entidades;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class Conversa {

    private String User;
    private String Mensagem_Id;
    private String Mensagem_User;
    private String Mensagem_Text;


    public void Chat(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();

        referenciaFirebase.child("Conversa").child(getUser()).child(getMensagem_Id())
                .setValue(getMensagem_Text());
    }


    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getMensagem_Id() {
        return Mensagem_Id;
    }

    public void setMensagem_Id(String mensagem_Id) {
        Mensagem_Id = mensagem_Id;
    }

    public String getMensagem_User() {
        return Mensagem_User;
    }

    public void setMensagem_User(String mensagem_User) {
        Mensagem_User = mensagem_User;
    }

    public String getMensagem_Text() {
        return Mensagem_Text;
    }

    public void setMensagem_Text(String mensagem_Text) {
        Mensagem_Text = mensagem_Text;
    }
}
