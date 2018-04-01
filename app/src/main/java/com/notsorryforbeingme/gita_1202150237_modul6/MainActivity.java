package com.notsorryforbeingme.gita_1202150237_modul6;

import android.content.Intent;
import android.graphics.Region;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //deklarasi variable
    EditText loginEmail, loginPassword;
    Button btnLogin, btnRegister;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bikin auth dari firebase pake feature email/password
        auth = FirebaseAuth.getInstance();

        //setelah user login lalu ke home
        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }


        loginEmail = findViewById(R.id.editTextEmail);
        loginPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.button2);
        btnLogin = findViewById(R.id.button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logFirebase();
            }
        });
    }

    private void logFirebase() {
        String email = loginEmail.getText().toString();
        String pass = loginPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Cek lagi e-mail Anda", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Cek lagi password Anda", Toast.LENGTH_SHORT).show();
        }

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //jika ngga berhasil login
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login tidak berhasil", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }


}
