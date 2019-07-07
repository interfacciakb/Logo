package com.example.logo.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logo.Entities.Svolgimento;
import com.example.logo.R;
import com.example.logo.SvolgimentoActivity;

import static android.support.v4.content.ContextCompat.startActivity;

public class SvolgimentoViewHolder extends ChildViewHolder{

    @NonNull
    private String idSvolgimento;
    private TextView nomeEsercizio;
    private TextView corretto;
    private ImageView stelle;
    private int numeroStelle;
    private String itemPosition;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public SvolgimentoViewHolder(@NonNull View itemView) {

        super(itemView);
        nomeEsercizio = (TextView) itemView.findViewById(R.id.nome_esercizio);
        corretto = (TextView) itemView.findViewById(R.id.corretto);
        stelle = (ImageView) itemView.findViewById(R.id.stelle);
    }


    public void bind(@NonNull Svolgimento svolgimento) {
        idSvolgimento = svolgimento.getId();
        nomeEsercizio.setText(svolgimento.getNomeEsercizio());
        corretto.setText(svolgimento.getCorretto());
        numeroStelle = svolgimento.getNumeroStelle();
        switch(numeroStelle){
            case 1:
                stelle.setImageResource(R.drawable.star);
                break;

            case 2:
                stelle.setImageResource(R.drawable.star);
                break;

                case 3:
                    stelle.setImageResource(R.drawable.star);
                break;
        }
        //todo
    }


    public String getitemPosition() {
        return itemPosition;
    }

    public void setitemPosition(String itemPosition) {
        this.itemPosition = itemPosition;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intentSvolgimento = new Intent(v.getContext(), SvolgimentoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("idsvolgimento", idSvolgimento);
        intentSvolgimento.putExtras(bundle);
        startActivity(v.getContext(),intentSvolgimento,null);

    }
}
