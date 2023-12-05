//--------------------------------------------------------------------------------------------------

/*IMPORTS*/

package com.jason.druh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

//--------------------------------------------------------------------------------------------------

/*CLASS*/

public class RegisterActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------

    /*VARS DEFINITION*/

    EditText emailRInput, passwordRInput;
    Button signupRBtn;
    TextView signinRBtn;

    //----------------------------------------------------------------------------------------------

    /*ONCREATE METHOD*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //------------------------------------------------------------------------------------------

        /*VARS INIT*/

        emailRInput = findViewById(R.id.emailRInput);
        passwordRInput = findViewById(R.id.passwordRInput);
        signinRBtn = findViewById(R.id.signinRBtn);
        signupRBtn = findViewById(R.id.signupRBtn);

        //------------------------------------------------------------------------------------------

        /*LISTENER METHODS*/

        signupRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        }); // END LISTENER

        signinRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singinIntent = new Intent(RegisterActivity.this,
                        AuthActivity.class);
                startActivity(singinIntent);
            }
        }); // END LISTENER

        //------------------------------------------------------------------------------------------

    } // END ONCREATE

    //----------------------------------------------------------------------------------------------

    /*HELPERS METHODS*/

    private void cleaner(){
        emailRInput.setText("");
        passwordRInput.setText("");
    } // END CLEANER

    private boolean verifyData(){
        if (emailRInput.getText().toString().isEmpty() || passwordRInput.getText().toString()
                .isEmpty()){
            return false;
        } else {
            return true;
        }
    } // END VERIFYDATA

    private void intentGenerator(String email){
        Intent homeIntent = new Intent(RegisterActivity.this, HomeActivity.class);
        homeIntent.putExtra("email", email);
        startActivity(homeIntent);
    } // END INTENTGENERATOR

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
    } // END ALERTBUILDER

    //----------------------------------------------------------------------------------------------

    /*SINGIN METHODS*/

    private void signUp(){
        if (verifyData()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailRInput.getText()
                            .toString(),passwordRInput.getText().toString()).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        intentGenerator(emailRInput.getText().toString());
                    } else {
                        alertBuilder("Create User Error", "Error creating user");
                    }
                }
            });

        } else {
            Toast.makeText(this, "Please enter all the data", Toast.LENGTH_SHORT).show();
        }
    } // END SIGNIN

    //----------------------------------------------------------------------------------------------

} // END CLASS

//--------------------------------------------------------------------------------------------------