package com.example.patrick.proj_motorista.Entidades;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private String Id;
    private String Email;
    private String Senha;
    private String Viagem;
    private String Nome;
    private String CodEmpresa;
    private String Telefone;
    private String Data_nasc;
    private String Sexo;

    //Informacoes utilizado pelo motorista
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String ano;
    private String tipo;

    public Usuario() {
    }



    public void Salvar(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("Usuario").child(String.valueOf(getId())).setValue(this);

    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getCodEmpresa() {
        return CodEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        CodEmpresa = codEmpresa;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getData_nasc() {
        return Data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        Data_nasc = data_nasc;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }


    public String getViagem() {
        return Viagem;
    }

    public void setViagem(String viagem) {
        Viagem = viagem;
    }

    public String toString(){
        return Nome;
    }
}
