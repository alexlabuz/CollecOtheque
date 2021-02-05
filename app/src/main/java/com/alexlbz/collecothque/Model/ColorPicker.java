package com.alexlbz.collecothque.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alexlbz.collecothque.R;

import java.util.ArrayList;

public abstract class ColorPicker implements View.OnClickListener {
    private Context context;
    private ViewGroup emplacement;
    private ArrayList<Integer> colorList;
    private ArrayList<TuileColor> tuileColorList = new ArrayList<>();

    public ColorPicker(Context context, ViewGroup emplacement, ArrayList<Integer> colorList) {
        this.context = context;
        this.emplacement = emplacement;
        this.colorList = colorList;
    }
    
    /**
     * Créer la palette de couleurs avec les couleurs demandé
     */
    public void createPicker(){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0,0,0,8);
        linearLayout.setLayoutParams(params);

        for(Integer c: this.colorList){
            int width = (int) context.getResources().getDimension(R.dimen.sizeCase);
            LinearLayout.LayoutParams paramsTuile = new LinearLayout.LayoutParams(width, width);

            TuileColor tuileColor = new TuileColor(context, c);
            tuileColor.setLayoutParams(paramsTuile);
            tuileColor.setTag(c);
            tuileColor.setOnClickListener(this);

            linearLayout.addView(tuileColor);
            this.tuileColorList.add(tuileColor);
        }

        this.emplacement.addView(linearLayout);
    }

    /**
     * Lors du clic sur la case
     * @param view la case cliqué
     */
    @Override
    public void onClick(View view) {
        TuileColor tuileColor = (TuileColor) view;
        setSelectCase((Integer) tuileColor.getTag());
    }

    /**
     * Fonction qui entoure en la tuile séléctionné et déclanche ma méthode selectedCase
     * @param color la couleur de la case
     */
    public void setSelectCase(Integer color){
        for(TuileColor tc : this.tuileColorList){
            tc.setColorSelected(tc.getTag().equals(color));
        }
        selectedColor(color);
    }

    public abstract void selectedColor(Integer color);

    private class TuileColor extends View {
        private Paint paint = new Paint();
        private Paint stroke = new Paint();
        private Integer color;
        private Boolean selected;
        private Integer marge;

        public TuileColor(Context context, Integer color) {
            super(context);
            this.color = color;
            this.selected = false;
            this.marge = (int) context.getResources().getDimension(R.dimen.marginCase);
            init();
        }

        private void init(){
            this.paint.setAntiAlias(true);
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(this.color);

            this.stroke.setAntiAlias(true);
            this.stroke.setStyle(Paint.Style.STROKE);
            this.stroke.setStrokeWidth(7);
            this.stroke.setColor(Color.BLACK);
        }

        public void setColorSelected(Boolean selected){
            this.selected = selected;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawOval(marge, marge, getWidth() - marge, getHeight() - marge, this.paint);
                if(selected) {
                    canvas.drawOval(marge, marge, getWidth() - marge, getHeight() - marge, this.stroke);
                }
            }
        }
    }

    /**
     * Fonction qui permet de récupèrer les couleurs de la palette
     */
    public static ArrayList<Integer> getColorList(){
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(0,0,0));
        colors.add(Color.rgb(208,44,44));
        colors.add(Color.rgb(208,126,44));
        colors.add(Color.rgb(158,208,44));
        colors.add(Color.rgb(82,208,44));
        colors.add(Color.rgb(44,157,208));
        colors.add(Color.rgb(44,57,208));
        colors.add(Color.rgb(207,44,208));
        colors.add(Color.rgb(208,44,150));
        return colors;
    }

}
