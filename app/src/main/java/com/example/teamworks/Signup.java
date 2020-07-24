package com.example.teamworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    public EditText rg_email;
    public EditText rg_pass;
    public EditText rg_usname;
    public EditText rg_phone;
    private EditText crf_pass;
    int o1;
    final int o = 1 ;
    private Button btn_lgin;
    private Button btnReg;

    private FirebaseAuth mAuth;

    String email1,password1,username1,mobile1,crf_password;

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        rg_email = findViewById(R.id.rg_email);
        rg_pass = findViewById(R.id.rg_pass);
        rg_usname = findViewById(R.id.rg_usname);
        btn_lgin = findViewById(R.id.reg_login);
        btnReg = findViewById(R.id.reg_btn);
        crf_pass = findViewById(R.id.crf_pass);

        mAuth = FirebaseAuth.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();
            }


        });

        btn_lgin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i1 = new Intent( Signup.this, MainActivity.class );
                Intent i2 = new Intent( Signup.this, MainActivity.class );
                Intent i3 = new Intent( Signup.this, MainActivity.class );



                email1 = rg_email.getText().toString().trim();
                password1 = rg_pass.getText().toString().trim();
                username1= rg_usname.getText().toString().trim();


                i1.putExtra( "Value1" ,email1 );
                i2.putExtra( "Value2" ,password1 );
                i3.putExtra( "Value3" ,username1 );




                startActivity(new Intent(getApplicationContext(), SignIn.class));
                finish();
            }

        });
    }







    private void validate() {
        email1 = rg_email.getText().toString().trim();
        password1 = rg_pass.getText().toString().trim();
        username1 = rg_usname.getText().toString().trim();
        crf_password = crf_pass.getText().toString().trim();

        if ((email1.isEmpty()) || !Patterns.EMAIL_ADDRESS.matcher(email1).matches() || (password1.isEmpty()) || (username1.isEmpty()) || (crf_password.isEmpty())){
            Toast.makeText( getApplicationContext(), "Some details seems to be empty or invalid ..", Toast.LENGTH_SHORT ).show();
        }else{
            if (!password1.equals( crf_password )){
                Toast.makeText( getApplicationContext(), "Password Doesn't match ...", Toast.LENGTH_SHORT ).show();
            }else {
                createUser();
            }
        }



    }

    void createUser(){

        {
            o1 = o;

            mAuth.createUserWithEmailAndPassword(rg_email.getText().toString(), rg_pass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        String email = rg_email.getText().toString().trim();
                        String password = rg_pass.getText().toString().trim();
                        String username = rg_usname.getText().toString().trim();

                        Data data = new Data(
                                email,
                                password,
                                username
                        );

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                assert user != null;
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(getApplicationContext(), "SignUp Successful",
                                                                    Toast.LENGTH_SHORT).show();
                                                            mAuth.signOut();
                                                        }
                                                    });

                                                    Toast.makeText(getApplicationContext(), "Verification Mail Sent",
                                                            Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(), "SignUp failed.Try again after sometime",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}