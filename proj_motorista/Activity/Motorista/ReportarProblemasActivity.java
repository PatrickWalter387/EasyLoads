package com.example.patrick.proj_motorista.Activity.Motorista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrick.proj_motorista.DAO.ConfigFirebase;
import com.example.patrick.proj_motorista.Entidades.Gastos;
import com.example.patrick.proj_motorista.Entidades.RelatorioProblemas;
import com.example.patrick.proj_motorista.Entidades.Viagens;
import com.example.patrick.proj_motorista.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportarProblemasActivity extends AppCompatActivity {
    DatabaseReference referencia = ConfigFirebase.getFirebase();
    RelatorioProblemas relatorio = new RelatorioProblemas();
    FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
    Viagens viagem = new Viagens();
    Gastos gastos = new Gastos();
    TextView edtOutrosGasto;
    TextView edtGastosDisel;
    TextView edtLitros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_problemas);
        Intent intent = getIntent();
        viagem = (Viagens) intent.getSerializableExtra("viagem");
        Button btnEnviarJustificativa = (Button)findViewById(R.id.btnSubmeterJustificativa);

         edtOutrosGasto = (TextView) findViewById(R.id.edtOutrosGastos);
         edtGastosDisel = (TextView) findViewById(R.id.edtGastoDisel);
         edtLitros = (TextView) findViewById(R.id.edtLitros);



        referencia.child("RelatorioProblemas").orderByChild("id_Viagem").equalTo(viagem.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RelatorioProblemas relat;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    relat = objSnapshot.getValue(RelatorioProblemas.class);
                    relatorio = relat;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    public void SubmeterRelatorioProb(View view) {
        TextView edtJustificativa = (TextView)findViewById(R.id.edtCampoJustificativa);

        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date newData = new Date();
        String dataAtual = data.format(newData);

        String msg = dataAtual + " = " + edtJustificativa.getText().toString();
        String msgAntiga;
        System.out.println("Teste1" + viagem.getId());



        System.out.println("Teste2" + relatorio.getId());
        if(relatorio.getId() == null) {
            String id = referencia.push().toString();
            id = id.substring(45, id.length()-0);
            relatorio.setId(id);
            relatorio.setId_Viagem(viagem.getId());
            relatorio.setConteudo_msg(msg);
        }
        else{
            msgAntiga = relatorio.getConteudo_msg();
            relatorio.setConteudo_msg(msgAntiga + "\n" + msg);
        }

        relatorio.SubmeterRelatorioProb();

        Toast.makeText(ReportarProblemasActivity.this, "Relato enviado com sucesso", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ReportarProblemasActivity.this, ViagemActivity.class);
        startActivity(intent);


    }

    public void btnEnviarOutrosGastos(View view) {

        gastos.setGasto(Integer.valueOf(edtOutrosGasto.getText().toString()));
        gastos.setTipo("outrosGastos");
        gastos.setLitros(0);

        String id = referencia.push().toString();
        id = id.substring(45, id.length()-0);
        gastos.setId(id);
        gastos.setIdViagem(viagem.getId());
        gastos.setCodEmpresa(viagem.getId_Empresa());


        SimpleDateFormat dtMes = new SimpleDateFormat("MM");
        Date newDate = new Date();

        SimpleDateFormat dtAno = new SimpleDateFormat("yyyy");
        Date newAno = new Date();


        gastos.setDataMes(Integer.valueOf(dtMes.format(newDate)));
        gastos.setDataAno(Integer.valueOf(dtAno.format(newAno)));

        gastos.submeterGastos();



        Toast.makeText(ReportarProblemasActivity.this, "Outros gastos registrados com sucesso", Toast.LENGTH_SHORT).show();
    }

    public void btnEnviarGastosDisel(View view) {

        gastos.setGasto(Integer.valueOf(edtGastosDisel.getText().toString()));
        gastos.setLitros(Integer.valueOf(edtLitros.getText().toString()));
        gastos.setTipo("gastosDisel");

        String id = referencia.push().toString();
        id = id.substring(45, id.length()-0);
        gastos.setId(id);
        gastos.setIdViagem(viagem.getId());
        gastos.setCodEmpresa(viagem.getId_Empresa());

        SimpleDateFormat dtMes = new SimpleDateFormat("MM");
        Date newDate = new Date();

        SimpleDateFormat dtAno = new SimpleDateFormat("yyyy");
        Date newAno = new Date();


        gastos.setDataMes(Integer.valueOf(dtMes.format(newDate)));
        gastos.setDataAno(Integer.valueOf(dtAno.format(newAno)));

        gastos.submeterGastos();

        Toast.makeText(ReportarProblemasActivity.this, "Gastos com abastecimento registrados com sucesso", Toast.LENGTH_SHORT).show();
    }
}
