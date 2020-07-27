package com.firedup.adobefirebasetaskreminder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;




public class MainActivity extends AppCompatActivity {
    TextView title_page, subtitle_page, end_page;
    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<MyDoes> list;
    DoesAdapter doesAdapter;
    Button btnAddNew,btnSignOut;
    Intent intent;
    PendingIntent pendingIntent;
    AlarmManager  alarmManager;
    final String CHANNEL_ID="com.firedup.adobefirebasetaskreminder.ANDROID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title_page = findViewById(R.id.titlepage);
        subtitle_page = findViewById(R.id.subtitlepage);
        end_page = findViewById(R.id.endpage);
        btnAddNew = findViewById(R.id.btnAddNew);
        btnSignOut = findViewById(R.id.btnSignOut);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");


      title_page.setTypeface(MMedium);
       subtitle_page.setTypeface(MLight);
       end_page.setTypeface(MLight);
        btnAddNew.setTypeface(MLight);
    final String CHANNEL_ID = "com.firedup.adobefirebasetaskreminder";
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,NewTaskAct.class);
                a.putExtra("email_id",email);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // Setting the notification methods for different behaviours
//notification
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0 /* Request code */, a,
                        PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                                .setContentTitle("Reminder")
                                .setContentText("You are creating a new activity")
                                .setAutoCancel(true)
                                .setPriority(NotificationManager.IMPORTANCE_MAX)
                                .setFullScreenIntent(pendingIntent, true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                startActivity(a);
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                               MainActivity.this.finish();
                               startActivity(intent);
                            }
                        });

            }
        });

        ourdoes = findViewById(R.id.ourdoes);

        list = new ArrayList<MyDoes>();

        reference = FirebaseDatabase.getInstance().getReference().child("DoesApp");
//        FirebaseRecyclerOptions<MyDoes> options =
//                new FirebaseRecyclerOptions.Builder<MyDoes>()
//                        .setQuery(reference, MyDoes.class)
//                        .build();
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyDoes p = dataSnapshot1.getValue(MyDoes.class);
                    if ((p != null) && (p.email.equals(email))) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String currentDate = sdf.format(new Date());
                        String inDate = (p.getDatedoes() + " " + p.getTimedoes());

                        if (inDate.equals(currentDate)) {
                            Intent a = new Intent(MainActivity.this, EditTaskDesk.class);
                            a.putExtra("titleDoes", p.titledoes);
                            a.putExtra("descDoes", p.descdoes);
                            a.putExtra("dateDoes", p.datedoes);
                            a.putExtra("keyDoes", p.keydoes);
                            a.putExtra("timeDoes", p.timedoes);
                            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            // Setting the notification methods for different behaviours
                            //notification
                            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0 /* Request code */, a,
                                    PendingIntent.FLAG_ONE_SHOT);
                            NotificationCompat.Builder notificationBuilder =
                                    new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                                            .setContentTitle("Task")
                                            .setContentText(p.getTitledoes() + " Task to be completed on: " + p.getDatedoes() + " by: " + p.getTimedoes())
                                            .setAutoCancel(true)
                                            .setPriority(NotificationManager.IMPORTANCE_MAX)
                                            .setFullScreenIntent(pendingIntent, true)
                                            .setSound(defaultSoundUri)
                                            .setContentIntent(pendingIntent);

                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            // Since android Oreo notification channel is needed.
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                                        "Channel human readable title",
                                        NotificationManager.IMPORTANCE_DEFAULT);
                                notificationManager.createNotificationChannel(channel);
                            }

                            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
                            startActivity(a);
                        }
                        list.add(p);
                    }
                }

                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                ourdoes.setLayoutManager(llm);

                doesAdapter = new DoesAdapter(MainActivity.this, list);
                ourdoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
//        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<MyDoes, DoesAdapter.MyViewHolder>(options) {
//            @NonNull
//            @Override
//            public DoesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.item_does, parent, false);
//
//                return new DoesAdapter.MyViewHolder(view);
//            }
//            // ...
//
//            @Override
//            public void onDataChanged() {
//                // Called each time there is a new data snapshot. You may want to use this method
//                // to hide a loading spinner or check for the "no documents" state and update your UI.
//                // ...
//            }
//
//            @Override
//            public void onError(DatabaseError e) {
//                // Called when there is an error getting data. You may want to update
//                // your UI to display an error message to the user.
//                // ...
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull DoesAdapter.MyViewHolder holder, int position, @NonNull MyDoes model) {
//
//            }
//        };

    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void alert(String hour,String minute) {
//       intent = new Intent(this, MyBroadcastReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(
//                this.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
//
//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_HOUR, pendingIntent);
//
//        Toast.makeText(this, "Alarm will vibrate at time specified",
//                Toast.LENGTH_SHORT).show();
//    }
//    private void addNotification(Intent intent) {
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        // Setting the notification methods for different behaviours
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
//                        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
//                        .setContentTitle("Reminder")
//                        .setContentText("You are creating a new activity")
//                        .setAutoCancel(true)
//                        .setPriority(NotificationManager.IMPORTANCE_MAX)
//                        .setFullScreenIntent(pendingIntent, true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(DEFAULT_CHANNEL_ID,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
protected void onStop() {
    super.onStop();
    if (alarmManager != null) {
        alarmManager.cancel(pendingIntent);
    }
}

}