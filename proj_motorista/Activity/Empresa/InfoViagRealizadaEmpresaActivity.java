package com.example.patrick.proj_motorista.Activity.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.patrick.proj_motorista.Entidades.Viagens;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.database.DatabaseReference;

public class InfoViagRealizadaEmpresaActivity extends AppCompatActivity {
    Viagens viagem;
    private EditText edtCadTipoCarga;
    private EditText edtCadLocCarregamento;
    private EditText edtCadLocDescarregamento;
    private EditText edtCadHorarioSaidaMaximo;
    private EditText edtCadHorarioChegadaMaximo;
    private EditText edtCadHorarioSaida;
    private EditText edtCadHorarioChegada;
    private EditText edtCadInfoCarga;
    DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viag_realizada_empresa);
        Intent intent = getIntent();
        Viagens viagens = (Viagens) intent.getSerializableExtra("viagem");
        viagem = viagens;

        edtCadTipoCarga = (EditText)findViewById(R.id.edtTipodeCarga3);
        edtCadLocCarregamento = (EditText)findViewById(R.id.edtLocCarregamento3);
        edtCadLocDescarregamento = (EditText)findViewById(R.id.edtLocDescarregamento3);
        edtCadHorarioSaidaMaximo = (EditText)findViewById(R.id.edtHorarioSaidaMaximo3);
        edtCadHorarioChegadaMaximo = (EditText)findViewById(R.id.edtHorarioChegadaMaximo3);
        edtCadHorarioSaida = (EditText)findViewById(R.id.edtHorarioSaida3);
        edtCadHorarioChegada = (EditText)findViewById(R.id.edtHorarioChegada3);
        edtCadInfoCarga = (EditText)findViewById(R.id.edtInfoCarga3);

        eventDatabase();
        edtCadTipoCarga.setEnabled(false);
        edtCadLocCarregamento.setEnabled(false);
        edtCadLocDescarregamento.setEnabled(false);
        edtCadHorarioSaidaMaximo.setEnabled(false);
        edtCadHorarioChegadaMaximo.setEnabled(false);
        edtCadHorarioSaida.setEnabled(false);
        edtCadHorarioChegada.setEnabled(false);
        edtCadInfoCarga.setEnabled(false);
    }

    public void eventDatabase() {
        edtCadTipoCarga.setText(viagem.getTipoCarga());
        edtCadLocCarregamento.setText(viagem.getLocalCarregamento());
        edtCadLocDescarregamento.setText(viagem.getLocalDescarga());
        edtCadHorarioSaidaMaximo.setText(viagem.getHorario_maximo_do_saida());
        edtCadHorarioChegadaMaximo.setText(viagem.getHorario_maximo_chegada());
        edtCadHorarioSaida.setText(viagem.getHorario_saida());
        edtCadHorarioChegada.setText(viagem.getHorario_chegada());
        edtCadInfoCarga.setText(viagem.getInfoCarga());

    }

    public void VerProbViagConc(View view) {
        Intent intent = new Intent(InfoViagRealizadaEmpresaActivity.this, RelatoProbEmpresaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("viagem", viagem);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void MapsViagRealizada(View view) {
        Intent intent = new Intent(InfoViagRealizadaEmpresaActivity.this, MapsRastreamentoEmpresaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("viagem", viagem);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
