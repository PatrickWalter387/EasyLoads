package com.example.patrick.proj_motorista.Entidades;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Empresa {
    private String Id;
    private String Email;
    private String Senha;
    private String Nome;
    private String Cnpj;
    private String Telefone;
    private String RamoAtuacao;

    public Empresa() {
    }

    public void Salvar(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("Empresa").child(String.valueOf(getId())).setValue(this);

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String toString(){
        return Nome;
    }

    public String getCnpj() {
        return Cnpj;
    }

    public void setCnpj(String cnpj) {
        Cnpj = cnpj;
    }

    public String getRamoAtuacao() {
        return RamoAtuacao;
    }

    public void setRamoAtuacao(String ramoAtuacao) {
        RamoAtuacao = ramoAtuacao;
    }
}
