package com.example.patrick.proj_motorista.Entidades;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class RelatorioProblemas {
    private String Id;
    private String Id_Viagem;
    private String Conteudo_msg;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId_Viagem() {
        return Id_Viagem;
    }

    public void setId_Viagem(String id_Viagem) {
        Id_Viagem = id_Viagem;
    }

    public String getConteudo_msg() {
        return Conteudo_msg;
    }

    public void setConteudo_msg(String msg) {
        Conteudo_msg = msg;
    }

    public void SubmeterRelatorioProb(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("RelatorioProblemas").child(getId()).setValue(this);
    }


}
