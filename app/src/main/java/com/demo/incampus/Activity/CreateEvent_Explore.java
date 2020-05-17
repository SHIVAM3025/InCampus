package com.demo.incampus.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.incampus.Interface.Api;
import com.demo.incampus.Network.GraphqlClient;
import com.demo.incampus.R;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateEvent_Explore extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Calendar myCalendar;
    EditText nameEditText, dateEditText, timeEditText, venueEditText, feeEditText, descriptionEditText, specialNoteEditText;
    String format;
    int CalendarHour, CalendarMinute;
    TimePickerDialog timepickerdialog;
    CheckBox feeCheckBox, ageLimitCheckBox;
    boolean flag = false;
    Button uploadPhotoButton;
    int width, height;
    boolean is_age_limted;
    public Api api;

    String eventName, eventDate, eventTime, eventVenue, eventDescription, picUrl, eventCategory, eventFee, eventAdditionalNote;
    String createdBy, communityId;
    int ageLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event__explore);

        eventFee = "0";
        nameEditText = findViewById(R.id.nameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        venueEditText = findViewById(R.id.venueEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        specialNoteEditText = findViewById(R.id.specialNoteEditText);

        feeCheckBox = findViewById(R.id.feeCheckBox);
        ageLimitCheckBox = findViewById(R.id.ageLimitcheckBox);


        //upload cover photo
        uploadPhotoButton = findViewById(R.id.uploadPhoto);
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

        //date picker
        myCalendar = Calendar.getInstance();
        dateEditText = findViewById(R.id.dateEditText);
        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        final DatePickerDialog dpd = new DatePickerDialog(CreateEvent_Explore.this, date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        );
        dateEditText.setOnClickListener(v -> dpd.show());

        //time picker
        timeEditText = findViewById(R.id.timeEditText);
        timeEditText.setOnClickListener(v -> {
            myCalendar = Calendar.getInstance();
            CalendarHour = myCalendar.get(Calendar.HOUR_OF_DAY);
            CalendarMinute = myCalendar.get(Calendar.MINUTE);
            timepickerdialog = new TimePickerDialog(CreateEvent_Explore.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (hourOfDay == 0) {
                        hourOfDay += 12;
                        format = "AM";
                    } else if (hourOfDay == 12) {
                        format = "PM";
                    } else if (hourOfDay > 12) {
                        hourOfDay -= 12;
                        format = "PM";
                    } else {
                        format = "AM";
                    }
                    if (minute < 10) {
                        timeEditText.setText(hourOfDay + ":0" + minute + " " + format);
                    } else {
                        timeEditText.setText(hourOfDay + ":" + minute + " " + format);
                    }

                }
            }, CalendarHour, CalendarMinute, false);
            timepickerdialog.show();
        });

        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;
        width = size.x;
        ViewGroup.LayoutParams paramS = RL.getLayoutParams();
        paramS.height = (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        //online or offline
        final Button onlineButton = findViewById(R.id.online);
        final Button offlineButton = findViewById(R.id.offline);
        onlineButton.setOnClickListener(v -> {
            onlineButton.getBackground().setTint(Color.parseColor("#D47FA6"));
            offlineButton.getBackground().setTint(Color.parseColor("#757575"));
        });

        offlineButton.setOnClickListener(v -> {
            offlineButton.getBackground().setTint(Color.parseColor("#D47FA6"));
            onlineButton.getBackground().setTint(Color.parseColor("#757575"));
        });

        //spinner code
        Spinner eventCategorySpinner = findViewById(R.id.eventCategorySpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.communities, R.layout.spinner_community2);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dialog_item);
        eventCategorySpinner.setAdapter(spinnerAdapter);
        eventCategorySpinner.setOnItemSelectedListener(this);

        //fees checkbox code
        feeCheckBox = findViewById(R.id.feeCheckBox);
        feeEditText = findViewById(R.id.feeEditText);
        feeEditText.setEnabled(false);
        feeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!feeCheckBox.isChecked()) {
                    feeEditText.setEnabled(false);
                    feeEditText.setTextColor(Color.parseColor("#C3C3C3"));
                } else {
                    feeEditText.setEnabled(true);
                    feeEditText.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });


        //cancel event
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //create event button listener
        Button createEventButton = findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(v -> {
            //TODO change the flag to not
            if (flag) {
                Toast.makeText(CreateEvent_Explore.this, "Please Upload a Cover Photo", Toast.LENGTH_SHORT).show();
            } else {
              //  Toast.makeText(CreateEvent_Explore.this, "Event Created", Toast.LENGTH_SHORT).show();
                eventName=nameEditText.getText().toString();
                eventDate=dateEditText.getText().toString();
                try{
                    eventDate=convert_date(eventDate);
                }
                catch (Exception e)
                {
                    Toast.makeText(CreateEvent_Explore.this, "Invalid date", Toast.LENGTH_SHORT).show();
                }
                eventTime=timeEditText.getText().toString();
                eventVenue=venueEditText.getText().toString();
                eventDescription=descriptionEditText.getText().toString();
                eventCategory=eventCategorySpinner.getSelectedItem().toString();
                eventFee=feeEditText.getText().toString();
                eventAdditionalNote=specialNoteEditText.getText().toString();
                if(ageLimitCheckBox.isChecked())
                {
                    ageLimit=18;
                    is_age_limted=true;
                }
                else
                {
                    ageLimit=0;
                    is_age_limted=false;
                }
                //(TODO)HARD CODED USER_ID,COMMUNITY_ID,PIC_URL HERE
                createdBy="181";
                communityId="200";
                picUrl="https://istart.com.au/wp-content/uploads/2018/03/cache/Top-four-CFO-imperatives-for-2018-200pxx200px.jpg";
                create_event_mutation_function();
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
            ImageView imageView = findViewById(R.id.coverImage);
            imageView.setImageURI(data.getData());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewGroup.LayoutParams[] params = {imageView.getLayoutParams()};
            params[0].height = (int) (width * 0.5);
            params[0].width = (int) (width * 0.5);
            uploadPhotoButton.setText("Change Cover Photo");
            flag = true;
        }
    }

    //update label
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }


    //spinnere click listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void create_event_mutation_function()
    {



        Api apiInterface = GraphqlClient.getApi();

        JsonObject jsonObject1 = new JsonObject();
        if(is_age_limted)
        {
            jsonObject1.addProperty("query","mutation MyMutation {\n" +
                    "  insert_Events(objects: {name: \""+eventName+"\", date: \""+eventDate+"\", time: \""+eventTime+"\", venue: \""+eventVenue+"\", description: \""+eventDescription+"\", cover_pic: \""+picUrl+"\", category: \""+eventCategory+"\", community_id: \""+communityId+"\", created_by: \""+createdBy+"\", registration_fees: \""+eventFee+"\", age_limit: 18, additional_notes: \""+eventAdditionalNote+"\"}) {\n" +
                    "    affected_rows\n" +
                    "    returning {\n" +
                    "      event_id\n" +
                    "    }\n" +
                    "  }\n" +
                    "}\n");

        }
        else
        {
            jsonObject1.addProperty("query","mutation MyMutation {\n" +
                    "  insert_Events(objects: {name: \""+eventName+"\", date: \""+eventDate+"\", time: \""+eventTime+"\", venue: \""+eventVenue+"\", description: \""+eventDescription+"\", cover_pic: \""+picUrl+"\", category: \""+eventCategory+"\", community_id: \""+communityId+"\", created_by: \""+createdBy+"\", registration_fees: \""+eventFee+"\", additional_notes: \""+eventAdditionalNote+"\"}) {\n" +
                    "    affected_rows\n" +
                    "    returning {\n" +
                    "      event_id\n" +
                    "    }\n" +
                    "  }\n" +
                    "}\n");
        }

        Log.i("mut",jsonObject1.toString());
        Call<JsonObject> create_event =apiInterface.createEvent(jsonObject1);
        //Toast.makeText(this, "date: "+eventDate, Toast.LENGTH_SHORT).show();

        create_event.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(CreateEvent_Explore.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateEvent_Explore.this, ExploreActivity.class), ActivityOptions.makeSceneTransitionAnimation(CreateEvent_Explore.this).toBundle());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(CreateEvent_Explore.this,"Failure:\n"+ t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.i("Failure",t.getMessage());
            }
        });
    }

    public String convert_date(String date) throws ParseException {
        String []d=new String[3];
        d[0]="";
        d[1]="";
        d[2]="";
        /*
        int i=0,j=0;
        while(i<=2&&j<=9)
        {
            if(date.charAt(j)=='/')
            {
                i++;
                j++;
            }
            d[i]+=(date[j]);
            j++;
        }
        return d[2]+'/'+d[1]+'/'+d[0];
        */
        Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String parsedDate = formatter.format(initDate);
        return parsedDate;
        //return d[2]+'/'+d[1]+'/'+d[0];
    }


}
