package com.firedup.adobefirebasetaskreminder;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    //FireBase imports
    FirebaseAuth mAuth;
    List<AuthUI.IdpConfig> providers;
    final int RC_SIGN_IN=12;
    FirebaseUser currentUser;



    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();
        //Current FireBase user.
        currentUser =mAuth.getCurrentUser();
        //List of providers for authentication
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        checkUser();

//Listener for authentication changes
        FirebaseAuth.AuthStateListener firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                    Animatoo.animateFade(LoginActivity.this);
                    finish();
                }
            }
        };
        mAuth.addAuthStateListener(firebaseAuthListener);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {

                currentUser = FirebaseAuth.getInstance().getCurrentUser();
            }
            else {
                Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //Method to check for previous login
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void checkUser()
    {
        if(currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            Animatoo.animateSlideUp(LoginActivity.this);
        }


        else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .setTheme(R.style.FullscreenTheme)
                            .build(),
                    RC_SIGN_IN);


        }


    }



}
