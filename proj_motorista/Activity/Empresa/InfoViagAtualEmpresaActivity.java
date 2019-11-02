package com.example.patrick.proj_motorista.Activity.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.patrick.proj_motorista.Entidades.Viagens;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.database.DatabaseReference;

public class InfoViagAtualEmpresaActivity extends AppCompatActivity {
    Viagens viagem;
    private EditText edtCadTipoCarga;
    private EditText edtCadLocCarregamento;
    private EditText edtCadLocDescarregamento;
    private EditText edtCadHorarioSaida;
    private EditText edtCadHorarioChegada;
    private EditText edtCadHorarioSaidaMaximo;
    private EditText edtCadHorarioChegadaMaximo;
    private EditText edtCadInfoCarga;
    DatabaseReference referencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viag_atual_empresa);
        Intent intent = getIntent();
        Viagens viag = (Viagens) intent.getSerializableExtra("viagem");
        viagem = viag;

        edtCadTipoCarga = (EditText)findViewById(R.id.edtTipodeCarga4);
        edtCadLocCarregamento = (EditText)findViewById(R.id.edtLocCarregamento4);
        edtCadLocDescarregamento = (EditText)findViewById(R.id.edtLocDescarregamento4);
        edtCadHorarioSaida = (EditText)findViewById(R.id.edtHorarioSaida4);
        edtCadHorarioChegada = (EditText)findViewById(R.id.edtHorarioChegada4);
        edtCadInfoCarga = (EditText)findViewById(R.id.edtInfoCarga4);
        edtCadHorarioSaidaMaximo = (EditText)findViewById(R.id.edtHorarioSaidaMaximo4);
        edtCadHorarioChegadaMaximo = (EditText)findViewById(R.id.edtHorarioChegadaMaximo4);

        eventDatabase();

        edtCadTipoCarga.setEnabled(false);
        edtCadLocCarregamento.setEnabled(false);
        edtCadLocDescarregamento.setEnabled(false);
        edtCadHorarioSaida.setEnabled(false);
        edtCadHorarioChegada.setEnabled(false);
        edtCadHorarioSaidaMaximo.setEnabled(false);
        edtCadHorarioChegadaMaximo.setEnabled(false);
        edtCadInfoCarga.setEnabled(false);
    }

    public void eventDatabase() {
        edtCadTipoCarga.setText(viagem.getTipoCarga());
        edtCadLocCarregamento.setText(viagem.getLocalCarregamento());
        edtCadLocDescarregamento.setText(viagem.getLocalDescarga());
        edtCadHorarioSaida.setText(viagem.getHorario_saida());
        edtCadHorarioChegada.setText(viagem.getHorario_chegada());
        edtCadInfoCarga.setText(viagem.getInfoCarga());
        edtCadHorarioSaidaMaximo.setText(viagem.getHorario_maximo_do_saida());
        edtCadHorarioChegadaMaximo.setText(viagem.getHorario_maximo_chegada());

    }

    public void VerProbViagemAtual(View view) {
        Intent intent = new Intent(InfoViagAtualEmpresaActivity.this, RelatoProbEmpresaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("viagem", viagem);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void VerMapsViagAtual(View view) {
        Intent intent = new Intent(InfoViagAtualEmpresaActivity.this, MapsRastreamentoEmpresaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("viagem", viagem);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
