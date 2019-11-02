package com.example.patrick.proj_motorista.Entidades;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Viagens implements Serializable {
    String tipoCarga;
    String id;
    String id_Motorista;
    String id_Empresa;
    String status; // em andamento or concluida or em espera //
    String localCarregamento;
    String localDescarga;
    String nomeLocalCarregamento;
    String nomeLocalDescarga;
    String horario_maximo_do_saida;
    String horario_maximo_chegada;
    String horario_chegada;
    String horario_saida;
    String situacaoEmAndamento;
    String infoCarga;
    String id_Motorista_status;

    public void Salvar(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("Viagens").child(String.valueOf(getId())).setValue(this);



    }

    public String getSituacaoEmAndamento() {
        return situacaoEmAndamento;
    }

    public void setSituacaoEmAndamento(String situacaoEmAndamento) {
        this.situacaoEmAndamento = situacaoEmAndamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeLocalCarregamento() {
        return nomeLocalCarregamento;
    }

    public void setNomeLocalCarregamento(String nomeLocalCarregamento) {
        this.nomeLocalCarregamento = nomeLocalCarregamento;
    }

    public String getNomeLocalDescarga() {
        return nomeLocalDescarga;
    }

    public void setNomeLocalDescarga(String nomeLocalDescarga) {
        this.nomeLocalDescarga = nomeLocalDescarga;
    }

    public String getId_Motorista() {
        return id_Motorista;
    }

    public void setId_Motorista(String id_Motorista) {
        this.id_Motorista = id_Motorista;
    }

    public String getId_Empresa() {
        return id_Empresa;
    }

    public void setId_Empresa(String id_Empresa) {
        this.id_Empresa = id_Empresa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocalCarregamento() {
        return localCarregamento;
    }

    public void setLocalCarregamento(String localCarregamento) {
        this.localCarregamento = localCarregamento;
    }

    public String getLocalDescarga() {
        return localDescarga;
    }

    public void setLocalDescarga(String localDescarga) {
        this.localDescarga = localDescarga;
    }

    public String getHorario_maximo_do_saida() {
        return horario_maximo_do_saida;
    }

    public void setHorario_maximo_do_saida(String horario_maximo_do_saida) {
        this.horario_maximo_do_saida = horario_maximo_do_saida;
    }

    public String getHorario_maximo_chegada() {
        return horario_maximo_chegada;
    }

    public void setHorario_maximo_chegada(String horario_maximo_chegada) {
        this.horario_maximo_chegada = horario_maximo_chegada;
    }

    public String getInfoCarga() {
        return infoCarga;
    }

    public void setInfoCarga(String infoCarga) {
        this.infoCarga = infoCarga;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public String getId_Motorista_status() {
        return id_Motorista_status;
    }

    public void setId_Motorista_status(String id_Motorista_status) {
        this.id_Motorista_status = id_Motorista_status;
    }

    public String getHorario_chegada() {
        return horario_chegada;
    }

    public void setHorario_chegada(String horario_chegada) {
        this.horario_chegada = horario_chegada;
    }

    public String getHorario_saida() {
        return horario_saida;
    }

    public void setHorario_saida(String horario_saida) {
        this.horario_saida = horario_saida;
    }
}
