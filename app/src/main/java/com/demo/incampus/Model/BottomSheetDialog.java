package com.demo.incampus.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.incampus.Activity.HomeActivity;
import com.demo.incampus.Activity.WalkthroughActivity;
import com.demo.incampus.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    Context mContext;

    public BottomSheetDialog(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_apptour_bottomsheet,container,false);


        view.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, WalkthroughActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.maybe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, WalkthroughActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, HomeActivity.class);
                startActivity(intent);
            }
        });
        savePrefsData();
        return view;
    }

    public boolean restorePrefData() {
        SharedPreferences pref = mContext.getSharedPreferences("myPrefs",MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened",false);
    }

    public void savePrefsData() {
        SharedPreferences pref =mContext.getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.apply();
    }


}
