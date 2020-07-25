package com.firedup.adobefirebasetaskreminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskAct extends AppCompatActivity {
    TextView titlepage, addtitle, adddesc, adddate;
    EditText titledoes;
    EditText descdoes;
    DatePicker datedoes;
    TimePicker timedoes;
    Button btnSaveTask, btnCancel;
    DatabaseReference reference;
    static Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);
        timedoes = findViewById(R.id.timedoes);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
                        child("Does" + (doesNum++));
                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                        String month;
                        if(datedoes.getMonth()<9)
                            month = "0"+(datedoes.getMonth()+1);
                        else
                            month = ""+(datedoes.getMonth()+1);
                        dataSnapshot.getRef().child("datedoes").setValue(datedoes.getYear()+"-"+month+"-"+datedoes.getDayOfMonth());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String hour = (timedoes.getHour()<10)?("0"+timedoes.getHour()):(""+timedoes.getHour());
                            String minute = (timedoes.getMinute()<10)?("0"+timedoes.getMinute()):(""+timedoes.getMinute());
                            dataSnapshot.getRef().child("timedoes").setValue(hour+":"+minute);
                        }
                        dataSnapshot.getRef().child("keydoes").setValue(keydoes);

                        Intent a = new Intent(NewTaskAct.this,MainActivity.class);
                        startActivity(a);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        // customize font
        titlepage.setTypeface(MMedium);

        addtitle.setTypeface(MLight);
        titledoes.setTypeface(MMedium);

        adddesc.setTypeface(MLight);
        descdoes.setTypeface(MMedium);

        adddate.setTypeface(MLight);
//        datedoes.setTypeface(MMedium);

        btnSaveTask.setTypeface(MMedium);
        btnCancel.setTypeface(MLight);
    }
}