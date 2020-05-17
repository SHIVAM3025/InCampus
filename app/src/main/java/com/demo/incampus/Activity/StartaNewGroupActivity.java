package com.demo.incampus.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demo.incampus.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartaNewGroupActivity extends AppCompatActivity {
    CircleImageView c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_a_new_group);

        //pick photo
        final Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        c = findViewById(R.id.profileImage);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickPhoto , 1);
            }
        });

        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS=RL.getLayoutParams();
        paramS.height= (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        //OnClickListener for Back Imageview
        ImageView backImageView = findViewById(R.id.back_arrow);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                c.setImageURI(selectedImage);
            }
        }
    }
    public void cont (View view) {
        startActivity(new Intent(this, SuccessGroupCreate.class));
    }


}
