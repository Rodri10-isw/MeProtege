package com.nextimpulse.meprotege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Inicio extends AppCompatActivity {
    private TextView txtName,txtId,btncerr;
    private ImageView tvPerfil;
    private Button agregar;
    private FirebaseAuth mAut;
    private DatabaseReference mDatabase;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        mAut=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        txtName=(TextView)findViewById(R.id.txtUsuario);
        txtId=(TextView)findViewById(R.id.idUser);
        tvPerfil=(ImageView)findViewById(R.id.tvPerfil);
        agregar=(Button)findViewById(R.id.btnCarrito);
        btncerr=(TextView)findViewById(R.id.btnCerrar);

        obtenerDatosUsr();
        btncerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAut.signOut();
                startActivity(new Intent(Inicio.this,MainActivity.class));
                finish();
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inicio.this,CatalogoProductos.class));
            }
        });
    }

    private void obtenerDatosUsr(){
        String id = mAut.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String nombre=dataSnapshot.child("nombre").getValue().toString();
                    String apellido=dataSnapshot.child("apellido").getValue().toString();
                    String folio=dataSnapshot.child("folio").getValue().toString();
                    url=dataSnapshot.child("ImgPerfil").child("perfil").getValue().toString();
                    String nombreCom=nombre+" "+apellido;
                    txtId.setText(folio);
                    txtName.setText(nombreCom);
                    Glide
                            .with(Inicio.this)
                            .load(url)
                            .into(tvPerfil);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}