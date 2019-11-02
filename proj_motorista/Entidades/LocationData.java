package com.example.patrick.proj_motorista.Entidades;

import java.io.Serializable;

public class LocationData implements Serializable {

    public double latitude;
    public double longitude;
    public String idViagem;

    public LocationData(double latitude, double longitude, String idViagem) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.idViagem = idViagem;
    }
}
