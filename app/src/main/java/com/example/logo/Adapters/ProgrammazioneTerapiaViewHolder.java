package com.example.logo.Adapters;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.logo.Entities.ProgrammazioneTerapia;
import com.example.logo.R;

public class ProgrammazioneTerapiaViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;


    @NonNull
    private final ImageView mArrowExpandImageView;
    private TextView data;
    private ProgressBar progress;


    public ProgrammazioneTerapiaViewHolder(@NonNull View itemView) {
        super(itemView);
        data = (TextView) itemView.findViewById(R.id.data_appuntamento);
        progress = (ProgressBar) itemView.findViewById(R.id.progressBar);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.arrow_expand_imageview);
    }


    public void bind(@NonNull ProgrammazioneTerapia programmazioneTerapia) {
        data.setText(programmazioneTerapia.getDataProgrammazione());
        progress.setProgress(programmazioneTerapia.getProgressPercentage());
    }




 @SuppressLint("NewApi")
 @Override
 public void setExpanded(boolean expanded) {
     super.setExpanded(expanded);
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
         if (expanded) {
             mArrowExpandImageView.setRotation(ROTATED_POSITION);
         } else {
             mArrowExpandImageView.setRotation(INITIAL_POSITION);
         }
     }
 }




    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            mArrowExpandImageView.startAnimation(rotateAnimation);
        }
    }

}

