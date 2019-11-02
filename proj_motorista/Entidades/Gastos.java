package com.example.patrick.proj_motorista.Entidades;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class Gastos {
    int litros;
    int gasto;
    String tipo;
    String id;
    int dataMes;
    int dataAno;
    String codEmpresa;
    String idViagem;

    public int getLitros() {
        return litros;
    }

    public void setLitros(int litros) {
        this.litros = litros;
    }

    public int getGasto() {
        return gasto;
    }

    public void setGasto(int gasto) {
        this.gasto = gasto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdViagem() {
        return idViagem;
    }

    public void setIdViagem(String idViagem) {
        this.idViagem = idViagem;
    }

    public int getDataMes() {
        return dataMes;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public void setDataMes(int dataMes) {
        this.dataMes = dataMes;
    }

    public int getDataAno() {
        return dataAno;
    }

    public void setDataAno(int dataAno) {
        this.dataAno = dataAno;
    }

    public void submeterGastos(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("Gastos").child(getId()).setValue(this);
    }
}
