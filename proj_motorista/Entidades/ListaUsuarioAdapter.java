package com.example.patrick.proj_motorista.Entidades;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.patrick.proj_motorista.R;

import java.util.List;

public class ListaUsuarioAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> listUsuario;

    public ListaUsuarioAdapter(Context context, List<Usuario> listUsuario) {
        this.context = context;
        this.listUsuario = listUsuario;
    }

    @Override
    public int getCount() {
        return listUsuario.size();
    }

    @Override
    public Object getItem(int position) {
        return listUsuario.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_usuario, null);

        TextView tvEmail = (TextView)v.findViewById(R.id.tvEmail);
        TextView tvSenha = (TextView)v.findViewById(R.id.tvSenha);
        TextView tvNome = (TextView)v.findViewById(R.id.tvNome);
        TextView tvTelefone = (TextView)v.findViewById(R.id.tvTelefone);
        TextView tvData_nasc = (TextView)v.findViewById(R.id.tvData_nasc);

        tvEmail.setText(listUsuario.get(position).getEmail());
        tvSenha.setText("Senha: " + listUsuario.get(position).getSenha());
        tvNome.setText("Nome: " + listUsuario.get(position).getNome());
        tvTelefone.setText("Telefone: " + String.valueOf(listUsuario.get(position).getTelefone()));
        tvData_nasc.setText("Nascimento: " + String.valueOf(listUsuario.get(position).getData_nasc()));

        v.setTag(listUsuario.get(position).getId());

        return v;
    }
}
