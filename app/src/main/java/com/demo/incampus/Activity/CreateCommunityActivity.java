package com.demo.incampus.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.demo.incampus.R;

public class CreateCommunityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Switch switchButton;
    TextView open;
    TextView close;
    boolean flag = false;
    int width, height;
    Button uploadPhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_community);

        //upload cover photo
        uploadPhotoButton = findViewById(R.id.uploadCoverPhoto);
        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, 1001);
                    } else {
                        pickImagefromGallery();
                    }
                } else {
                    pickImagefromGallery();
                }
            }
        });

        //Relative layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        ViewGroup.LayoutParams[] params = {RL.getLayoutParams()};
        params[0].height = (int) (height * 0.15);

        //open or close community
        switchButton = findViewById(R.id.switchButton);
        open = findViewById(R.id.open);
        close = findViewById(R.id.close);
        //initially switch is not checked
        notChecked();
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked();
                } else {
                    notChecked();
                }
            }
        });

        //spinner code
        Spinner communityCategorySpinner = findViewById(R.id.communityCategorySpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.spinner_community2);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dialog_item);
        communityCategorySpinner.setAdapter(spinnerAdapter);
        communityCategorySpinner.setOnItemSelectedListener(this);

        //cancel event
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //create event button listener
        Button createEventButton = findViewById(R.id.createCommunityButton);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    Toast.makeText(CreateCommunityActivity.this, "Please Upload a Cover Photo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateCommunityActivity.this, "Community Created", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //pick image function
    private void pickImagefromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1000);
    }

    //asking and checking for permission
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImagefromGallery();
            } else {
                Toast.makeText(this, "Permission was denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //getting image from the gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            ImageView imageView = findViewById(R.id.coverImageView);
            imageView.setImageURI(data.getData());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewGroup.LayoutParams[] params = {imageView.getLayoutParams()};
            params[0].height = (int) (width * 0.5);
            params[0].width = (int) (width * 0.5);
            uploadPhotoButton.setText("Change Cover Photo");
            flag = true;
        }
    }

    //switch not checked
    public void notChecked() {
        close.setTextColor(Color.parseColor("#c3c3c3"));
        close.setTypeface(ResourcesCompat.getFont(CreateCommunityActivity.this, R.font.montserrat_medium));
        open.setTextColor(Color.parseColor("#ffffff"));
        open.setTypeface(ResourcesCompat.getFont(CreateCommunityActivity.this, R.font.montserrat_bold));
        switchButton.getTrackDrawable().setColorFilter(ContextCompat.getColor(CreateCommunityActivity.this, R.color.white), PorterDuff.Mode.SRC_IN);
    }

    //switch checked
    public void checked() {
        open.setTextColor(Color.parseColor("#c3c3c3"));
        open.setTypeface(ResourcesCompat.getFont(CreateCommunityActivity.this, R.font.montserrat_medium));
        close.setTextColor(Color.parseColor("#ffffff"));
        close.setTypeface(ResourcesCompat.getFont(CreateCommunityActivity.this, R.font.montserrat_bold));
        switchButton.getTrackDrawable().setColorFilter(ContextCompat.getColor(CreateCommunityActivity.this, R.color.white), PorterDuff.Mode.SRC_IN);
    }

    //spinner click listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
