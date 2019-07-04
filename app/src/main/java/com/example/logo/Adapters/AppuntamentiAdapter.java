package com.example.logo.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.logo.Entities.Appuntamento;
import com.example.logo.R;

import java.util.List;

public class AppuntamentiAdapter extends RecyclerView.Adapter<AppuntamentiAdapter.ViewHolder>{
    private List<Appuntamento> rankList;
    private Context context;

    public AppuntamentiAdapter(List<Appuntamento> list, Context context){
        this.context = context;
        rankList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_appuntamento,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Appuntamento appuntamento = rankList.get(position);
        holder.data.setText(String.valueOf(appuntamento.getData()));
        holder.medico.setText(String.valueOf(appuntamento.getMedico()));
        holder.luogo.setText(String.valueOf(appuntamento.getLuogo()));
        holder.orario.setText(String.valueOf(appuntamento.getOrario()));


    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView data;
        TextView medico;
        TextView luogo;
        TextView orario;

        public ViewHolder(View itemView) {
            super(itemView);

            data = (TextView) itemView.findViewById(R.id.data_appuntamento);
            medico = (TextView) itemView.findViewById(R.id.nome_medico);
            luogo = (TextView) itemView.findViewById(R.id.luogo_appuntamento);
            orario = (TextView) itemView.findViewById(R.id.orario_appuntamento);

        }
    }
}
