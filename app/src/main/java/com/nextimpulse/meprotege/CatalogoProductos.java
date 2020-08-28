package com.nextimpulse.meprotege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CatalogoProductos extends AppCompatActivity {
    private Spinner spinner1,spinner2;
    private Button btncarr;
    private TextView Total,total2;
    private EditText cantidad,cantidadTP;
    private double kn=7.5;
    private String can="",NoPiezas="",canEm="",total="",kn95="KN-95",codigoP="proKN95";
    private String tc="Tricapa Plisado Adulto",codigoT="proTCPAD";
    private String imgkn95="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Fnk.png?alt=media&token=a2d3d5cc-8a00-443f-892c-aa0ca3e9b8a2";
    private String imgTcpA="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Ftricapa.png?alt=media&token=cd89a1ab-5585-4a54-ad7e-0c81b79ecc1d";
    private double canN=0;
    private int v1=0,v2=0;
    private ImageView btnAddCarrKN,btnAddCarrTC;
    private String resultado,cantidadEm,id,resultadodos,cantidadDos;
    long maxid=0;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase,carr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_productos);

        //iniciar firebase//
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        id=mAuth.getCurrentUser().getUid();
        //firebase
        btncarr=(Button)findViewById(R.id.btnCarrito);
        spinner1 =(Spinner)findViewById(R.id.spKn95);
        spinner2 =(Spinner)findViewById(R.id.spKn2);
        Total=(TextView)findViewById(R.id.tvTotal);
        total2=(TextView)findViewById(R.id.tvTotal2);
        cantidad=(EditText)findViewById(R.id.edtCanKN);
        cantidadTP=(EditText)findViewById(R.id.edtCanTP);
        btnAddCarrKN=(ImageView) findViewById(R.id.btnAgregarCarritoKN);
        btnAddCarrTC=(ImageView) findViewById(R.id.btnAgregarCarritoTC);
        can=cantidad.getText().toString();
        carr= FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("carrito");
        carr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    maxid=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        String [] opciones = {"1","3","5","10"};
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinner2.setAdapter(ada);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView parentView, View selectedItemView, int position, long id) {
                String seleccion=spinner2.getSelectedItem().toString();
                if(seleccion.equals("1")){
                    v2=1;
                    cantidadDos="1";
                    obtenerTotalDos(v2);

                }else{
                    if (seleccion.equals("3")){
                        v2=3;
                        cantidadDos="3";
                        obtenerTotalDos(v2);
                    }else{
                        if (seleccion.equals("5")){
                            v2=5;
                            cantidadDos="5";
                            obtenerTotalDos(v2);
                        }else{
                            if (seleccion.equals("10")){
                                v2=10;
                                cantidadDos="10";
                                obtenerTotalDos(v2);
                            }
                        }
                    }
                }
            }

            @Override public void onNothingSelected(AdapterView parentView) {
                // your code here
            }
        });
        spinner1.setAdapter(ada);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView parentView, View selectedItemView, int position, long id) {
                String seleccion=spinner1.getSelectedItem().toString();
                if(seleccion.equals("1")){
                    v1=1;
                    cantidadEm="1";
                    obtenerTotal(v1);

                }else{
                    if (seleccion.equals("3")){
                        v1=3;
                        cantidadEm="3";
                        obtenerTotal(v1);
                    }else{
                        if (seleccion.equals("5")){
                            v1=5;
                            cantidadEm="5";
                            obtenerTotal(v1);
                        }else{
                            if (seleccion.equals("10")){
                                v1=10;
                                cantidadEm="10";
                                obtenerTotal(v1);
                            }
                        }
                    }
                }
            }
            @Override public void onNothingSelected(AdapterView parentView) {
                // your code here
            }
        });

        spinner1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    can=cantidad.getText().toString();
                    double valor1_int=Integer.parseInt(can);
                    double total =canN*valor1_int;
                    String resultado=String.valueOf(total);
                    Total.setText("$ "+resultado);
                    return true;
                }
                return false;
            }
        });
///botones para agregar a carrito
        btnAddCarrKN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCarrito();
            }
        });
        btnAddCarrTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCarritokn();
            }
        });
        btncarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CatalogoProductos.this,detallesdepedido.class));
            }
        });
    }

    ///botones para agregar a carrito
    private void guardarCarrito() {
        canEm=cantidad.getText().toString();
        total=resultado;
        Map<String,Object> map=new HashMap<>();
        map.put("producto",kn95);
        map.put("piezas",cantidadDos);
        map.put("cantidad",canEm);
        map.put("total",resultado);
        map.put("codigoP",codigoP);
        map.put("img",imgkn95);

        carr.child(String.valueOf(maxid+1)).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(CatalogoProductos.this,detallesdepedido.class));
            }
        });

    }
    private void guardarCarritokn() {
        canEm=cantidadTP.getText().toString();
        total=resultado;
        Map<String,Object> map=new HashMap<>();
        map.put("producto",tc);
        map.put("piezas",cantidadEm);
        map.put("cantidad",canEm);
        map.put("total",resultadodos);
        map.put("codigoP",codigoT);
        map.put("img",imgTcpA);

        carr.child(String.valueOf(maxid+1)).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(CatalogoProductos.this,detallesdepedido.class));
            }
        });

    }

    private void obtenerTotal(int v1) {
        canN= (double) (this.v1 *kn);
        cantidad.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    can=cantidad.getText().toString();
                    double valor1_int=Integer.parseInt(can);
                    double total =canN*valor1_int;
                     resultado=String.valueOf(total);
                     Total.setText("$ " + resultado);
                    return true;
                }
                return false;
            }
        });
        cantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    can=cantidad.getText().toString();
                    double valor1_int=Integer.parseInt(can);
                    double total =canN*valor1_int;
                     resultado=String.valueOf(total);
                    Total.setText("$ "+resultado);
                }
            }
        });

    }

    private void obtenerTotalDos(int v1) {
        canN= (double) (this.v2 *kn);
        cantidadTP.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    can=cantidadTP.getText().toString();
                    double valor1_int=Integer.parseInt(can);
                    double total =canN*valor1_int;
                    resultadodos=String.valueOf(total);
                     total2.setText("$ " + resultadodos);
                    return true;
                }
                return false;
            }
        });
        cantidadTP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    can=cantidadTP.getText().toString();
                    double valor1_int=Integer.parseInt(can);
                    double total =canN*valor1_int;
                    resultadodos=String.valueOf(total);
                    total2.setText("$ "+resultadodos);
                }
            }
        });

    }


}