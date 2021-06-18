package com.example.myteams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailBox,passBox,nameBox;
    Button loginbtn,signUpbtn;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        emailBox=findViewById(R.id.emailBox);
        passBox=findViewById(R.id.passwordBox);
        nameBox=findViewById(R.id.namebox);

        loginbtn=findViewById(R.id.loginBtn);
        signUpbtn=findViewById(R.id.createBtn);

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,name,pass;
                email=emailBox.getText().toString();
                name=nameBox.getText().toString();
                pass=passBox.getText().toString();

                final User user= new User();
                user.setEmail(email);
                user.setName(name);
                user.setPass(pass);

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(SignupActivity.this,loginActivity.class));

                                }
                            });
                            Toast.makeText(SignupActivity.this,"Account is Created",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}