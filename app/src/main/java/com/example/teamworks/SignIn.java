package com.example.teamworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private Button btnLogin;
    private TextView signUp;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth=FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        mDialog=new ProgressDialog(this);

        email = findViewById(R.id.lg_email);
        pass = findViewById(R.id.lg_pass);
        btnLogin = findViewById(R.id.lg_btn);
        signUp = findViewById(R.id.reg_txt);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail=email.getText().toString().trim();
                String mPass=pass.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)){
                    email.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(mPass)){
                    pass.setError("Required Field..");
                    return;
                }

                mDialog.setMessage("Processing..");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            checkEmailVerification();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Failed or Verify Your Email",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SignIn.this, Signup.class);
                startActivity(intent2);

            }
        });
    }

    private void checkEmailVerification(){

        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if(emailFlag){
            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
            finish();
            startActivity(new Intent(SignIn.this,MainActivity.class));
        }else{

            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT ).show();
            mDialog.dismiss();
            mAuth.signOut();
        }
    }
}