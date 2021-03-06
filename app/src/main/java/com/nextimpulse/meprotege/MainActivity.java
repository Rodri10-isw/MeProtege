package com.nextimpulse.meprotege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText edtemail,edtpass;
    private Button btnEn,btnReg;

    private String correo="", passw="";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        edtemail=(EditText)findViewById(R.id.edtCorreo);
        edtpass=(EditText)findViewById(R.id.edtContrasena);
        btnReg=(Button)findViewById(R.id.btnRegistrarse);
        btnEn=(Button)findViewById(R.id.btnEntrar);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FormularioClientes.class));
            }
        });
        btnEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo=edtemail.getText().toString();
                passw=edtpass.getText().toString();
                if (!correo.isEmpty()&&!passw.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(MainActivity.this, "Complete los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
        private void  loginUser(){
            mAuth.signInWithEmailAndPassword(correo,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Bienvenido",Toast.LENGTH_SHORT).show();
                        Intent interfaz=new Intent(MainActivity.this,Inicio.class);
                        startActivity(interfaz);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,Inicio.class));
            finish();
        }
    }
}

