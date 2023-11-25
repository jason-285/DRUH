package com.jason.druh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {
    String email;

    EditText emailAInput, passwordAInput;
    Button signinABtn;
    TextView signupABtn;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        emailAInput = findViewById(R.id.emailAInput);
        passwordAInput = findViewById(R.id.passwordAInput);
        signupABtn = findViewById(R.id.signupABtn);
        signinABtn = findViewById(R.id.signinABtn);

        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);

        email = prefs.getString("email", null);

        sessionVerify();

        signinABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        signupABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singupIntent = new Intent(AuthActivity.this, RegisterActivity.class);
                startActivity(singupIntent);
            }
        });

    }


    private void cleaner(){
        emailAInput.setText("");
        passwordAInput.setText("");
    }

    private boolean verifyData(){
        if (emailAInput.getText().toString().isEmpty() || passwordAInput.getText().toString().isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    private void intentGenerator(String email){
        Intent homeIntent = new Intent(AuthActivity.this, HomeActivity.class);
        homeIntent.putExtra("email", email);
        startActivity(homeIntent);
    }

    private void alertBuilder(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cleaner();
            }
        });

        builder.show();
    }

    private void sessionVerify(){
        if (email != null) {
            intentGenerator(email);
        }
    }

    private void signIn(){
        if (verifyData()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailAInput.getText().toString(),
                    passwordAInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        intentGenerator(emailAInput.getText().toString());
                    } else {
                        alertBuilder("Sign In Error", "Error to sign in");
                    }
                }
            });

        } else {
            Toast.makeText(this, "Please enter al the data", Toast.LENGTH_SHORT).show();
        }
    }




}