package com.example.logo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logo.Entities.ProgrammazioneTerapia;
import com.example.logo.Entities.Svolgimento;
import com.example.logo.R;

import java.util.List;

public class ProgrammazioneTerapiaAdapter extends ExpandableRecyclerAdapter<ProgrammazioneTerapia, Svolgimento, ProgrammazioneTerapiaViewHolder, SvolgimentoViewHolder> {

    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_VEGETARIAN = 2;
    private static final int CHILD_NORMAL = 3;

    private LayoutInflater mInflater;
    private List<ProgrammazioneTerapia> mProgrammazioneTerapiaList;

    public ProgrammazioneTerapiaAdapter(Context context, @NonNull List<ProgrammazioneTerapia> programmazioneTerapiaList) {
        super(programmazioneTerapiaList);
        mProgrammazioneTerapiaList = programmazioneTerapiaList;
        mInflater = LayoutInflater.from(context);
    }

    @UiThread
    @NonNull
    @Override
    public ProgrammazioneTerapiaViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View programmazioneTerapiaView;
        programmazioneTerapiaView = mInflater.inflate(R.layout.prog_terapia_view, parentViewGroup, false);

        return new ProgrammazioneTerapiaViewHolder(programmazioneTerapiaView);
    }

    @UiThread
    @NonNull
    @Override
    public SvolgimentoViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View svolgimentoView;
                svolgimentoView = mInflater.inflate(R.layout.svolgimento_view, childViewGroup, false);


        return new SvolgimentoViewHolder(svolgimentoView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull ProgrammazioneTerapiaViewHolder recipeViewHolder, int parentPosition, @NonNull ProgrammazioneTerapia programmazione) {
        recipeViewHolder.bind(programmazione);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull SvolgimentoViewHolder ingredientViewHolder, int parentPosition, int childPosition, @NonNull Svolgimento ingredient) {
        ingredientViewHolder.bind(ingredient);
    }







}
