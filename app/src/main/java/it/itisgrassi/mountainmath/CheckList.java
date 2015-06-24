package it.itisgrassi.mountainmath;

import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Claudiu on 13/06/2015.
 */
public class CheckList {
    private static int []array = new int[50];

    public static void setCheckList(int val,int i){
        array[i]=val;
    }

    public static int getCheckList(int i){
        return array[i];
    }

    public void setInvisible(LinearLayout l){
       l.setVisibility(LinearLayout.GONE);
    }
}
