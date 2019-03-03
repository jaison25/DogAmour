package com.example.juanitobeltran.dog;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText textoMail, textoContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        textoMail = (EditText)findViewById(R.id.tMail);
        textoContraseña = (EditText)findViewById(R.id.tPass);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void MetodoRegistro(View view){
        //createAccount("juancbeltranc@gmail.com","polo12345");
        //singIn("juancbeltranc@gmail.com","polo12345");

        String user = textoMail.getText().toString();
        String pass = textoContraseña.getText().toString();

        if(pass.length()<8){
            Toast.makeText(MainActivity.this, "La contraseña debe tener minimo 8 caracteres.",
                    Toast.LENGTH_SHORT).show();

        }else{
            createAccount(user,pass);
            singIn(user,pass);

            finish();
            Intent registroActivity = new Intent(getApplicationContext(), Registro_Activity.class);
            startActivity(registroActivity);
        }
    }

    public void MetodoEntrar(View view){

        //singIn("juancbeltranc@gmail.com","poli12345");

        String user = textoMail.getText().toString();
        String pass = textoContraseña.getText().toString();

        singIn(user,pass);

        finish();
        Intent verUserActivity = new Intent(getApplicationContext(), verUsers_Activity.class);
        startActivity(verUserActivity);
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Registro Exitoso!!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Error Intentando hacer Registro",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void singIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Usuario Autenticado",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Autenticacion Fallida",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
//hola pruba de vane pro en github jajaja
}

