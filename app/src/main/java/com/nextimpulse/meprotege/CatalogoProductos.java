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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.UUID;

public class CatalogoProductos extends AppCompatActivity {
    private RadioButton azTa,rosTa,negTa,azTi,rosTi;
    private Spinner spinner1,spinner2,spinner3;
    private Button btncarr;
    private TextView Total,total2,tvTota3,txtId,txtName;
    private EditText cantidad,cantidadTP,cantidadTpInf;
    private double kn=7.5;
    private String can="",NoPiezas="",canEm="",total="",kn95="KN-95",codigoP="proKN95";
    private String tc="Tricapa Plisado Adulto",tcIn="Tricapa Plisado Infantil",codigoT="proTCPAD",codigoTI="proTCPIN";
    private String imgkn95="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Fnk.png?alt=media&token=a2d3d5cc-8a00-443f-892c-aa0ca3e9b8a2";
    private String imgTcpA="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Ftricapa.png?alt=media&token=cd89a1ab-5585-4a54-ad7e-0c81b79ecc1d";
    private String imgTcIn="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Finfantil.png?alt=media&token=9b48f8a1-0286-4372-8a15-fe9aceb7ccd5";
    private double canN=0;
    private int v1=0,v2=0,v3=0;
    private ImageView btnAddCarrKN,btnAddCarrTC,btnAddCarrTCIN,tvPerfil;
    private String resultado,cantidadEm,id,resultadodos,cantidadDos,cantidadTres,resultadoTres,url,colorTa,colorTi;
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
        //id=mAuth.getCurrentUser().getUid();
        id=getIntent().getExtras().getString("cliente");
        //firebase
        txtName=(TextView)findViewById(R.id.txtUsuario);
        txtId=(TextView)findViewById(R.id.idUser);
        tvPerfil=(ImageView)findViewById(R.id.tvPerfil);
        btncarr=(Button)findViewById(R.id.btnCarrito);
        spinner1 =(Spinner)findViewById(R.id.spKn95);
        spinner2 =(Spinner)findViewById(R.id.spKn2);
        spinner3=(Spinner)findViewById(R.id.spKn3);
        Total=(TextView)findViewById(R.id.tvTotal);
        tvTota3=(TextView)findViewById(R.id.tvTotal4);
        total2=(TextView)findViewById(R.id.tvTotal2);
        cantidad=(EditText)findViewById(R.id.edtCanKN);
        cantidadTP=(EditText)findViewById(R.id.edtCanTP);
        cantidadTpInf=(EditText)findViewById(R.id.edtCanKnIn);
        btnAddCarrKN=(ImageView) findViewById(R.id.btnAgregarCarritoKN);
        btnAddCarrTC=(ImageView) findViewById(R.id.btnAgregarCarritoTC);
        btnAddCarrTCIN=(ImageView)findViewById(R.id.btnAgregarCarritoTCIn);
        azTa=(RadioButton)findViewById(R.id.rbAzTa);
        rosTa=(RadioButton)findViewById(R.id.rbRoTa);
        negTa=(RadioButton)findViewById(R.id.rbNeTa);
        azTi=(RadioButton)findViewById(R.id.rbAzTi);
        rosTi=(RadioButton)findViewById(R.id.rbRoTi);
        //can=cantidad.getText().toString();
        obtenerDatosUsr();
        carr= FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("carrito");

        String [] opciones = {"1","3","5","10"};
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinner3.setAdapter(ada);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView parentView, View selectedItemView, int position, long id) {
                String seleccion=spinner3.getSelectedItem().toString();
                if(seleccion.equals("1")){
                    v3=1;
                    cantidadTres="1";
                    obtenerTotalTres(v3);

                }else{
                    if (seleccion.equals("3")){
                        v3=3;
                        cantidadTres="3";
                        obtenerTotalTres(v3);
                    }else{
                        if (seleccion.equals("5")){
                            v3=5;
                            cantidadTres="5";
                            obtenerTotalTres(v3);
                        }else{
                            if (seleccion.equals("10")){
                                v3=10;
                                cantidadTres="10";
                                obtenerTotalTres(v3);
                            }
                        }
                    }
                }
            }

            @Override public void onNothingSelected(AdapterView parentView) {
                // your code here
            }
        });
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

///botones para agregar a carrito
        btnAddCarrKN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                can=cantidad.getText().toString();

                if (can.equals("")){
                    Toast.makeText(CatalogoProductos.this,"Introdusca informacion",Toast.LENGTH_LONG).show();
                }else{
                    double pXE=kn*v1;  //precio por cantidad de empaque
                    double valor= Integer.parseInt(can);
                    double total = pXE * valor;
                    resultado = String.valueOf(total);
                    Total.setText("$ " + resultado);
                    guardarCarrito();
                }
            }
        });
        btnAddCarrTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCarritokn();
            }
        });
        btnAddCarrTCIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCarritoknIN();
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
        map.put("piezas",cantidadEm);
        map.put("cantidad",canEm);
        map.put("total",resultado);
        map.put("codigoP",codigoP);
        map.put("img",imgkn95);
        String num= UUID.randomUUID().toString();
        map.put("num",num);
        carr.child(num).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CatalogoProductos.this, "Añadido al carrito",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(CatalogoProductos.this,detallesdepedido.class));
            }
        });

    }
    private void guardarCarritokn() {
        canEm=cantidadTP.getText().toString();
        total=resultado;
        obtenerColor();
        Map<String,Object> map=new HashMap<>();
        map.put("producto",tc);
        map.put("piezas",cantidadDos);
        map.put("cantidad",canEm);
        map.put("total",resultadodos);
        map.put("codigoP",codigoT);
        map.put("img",imgTcpA);
        map.put("color",colorTa);
        String num= UUID.randomUUID().toString();
        map.put("num",num);
        carr.child(num).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CatalogoProductos.this, "Añadido al carrito",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(CatalogoProductos.this,detallesdepedido.class));
            }
        });

    }
    private void guardarCarritoknIN() {
            canEm=cantidadTpInf.getText().toString();
            total=resultado;
            obtenerColorI();
            Map<String,Object> map=new HashMap<>();
            map.put("producto",tcIn);
            map.put("piezas",cantidadTres);
            map.put("cantidad",canEm);
            map.put("total",resultadoTres);
            map.put("codigoP",codigoTI);
            map.put("img",imgTcIn);
            map.put("color",colorTi);
            String num= UUID.randomUUID().toString();
            map.put("num",num);
            carr.child(num).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //startActivity(new Intent(CatalogoProductos.this,detallesdepedido.class));
                    Toast.makeText(CatalogoProductos.this, "Añadido al carrito",Toast.LENGTH_SHORT).show();
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
                    if(can.equals("")){
                        Toast.makeText(CatalogoProductos.this, "Ingresa una cantidad",Toast.LENGTH_SHORT).show();
                    }else{
                        double valor1_int=Integer.parseInt(can);
                        double total =canN*valor1_int;
                        resultado=String.valueOf(total);
                        Total.setText("$ " + resultado);
                        return true;
                    }

                }
                return false;
            }
        });
        cantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    can=cantidad.getText().toString();
                    if(can.equals("")){
                        Toast.makeText(CatalogoProductos.this, "Ingresa una cantidad",Toast.LENGTH_SHORT).show();
                    }else {
                        double valor1_int = Integer.parseInt(can);
                        double total = canN * valor1_int;
                        resultado = String.valueOf(total);
                        Total.setText("$ " + resultado);
                    }
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
                    if(can.equals("")){
                        Toast.makeText(CatalogoProductos.this, "Ingresa una cantidad",Toast.LENGTH_SHORT).show();
                    }else {
                        double valor1_int = Integer.parseInt(can);
                        double total = canN * valor1_int;
                        resultadodos = String.valueOf(total);
                        total2.setText("$ " + resultadodos);
                        return true;
                    }
                }
                return false;
            }
        });
        cantidadTP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    can=cantidadTP.getText().toString();
                    if(can.equals("")){
                        Toast.makeText(CatalogoProductos.this, "Ingresa una cantidad",Toast.LENGTH_SHORT).show();
                    }else {
                        double valor1_int = Integer.parseInt(can);
                        double total = canN * valor1_int;
                        resultadodos = String.valueOf(total);
                        total2.setText("$ " + resultadodos);
                    }
                }
            }
        });

    }
    private void obtenerTotalTres(int v3) {
        canN= (double) (this.v3 *kn);
        cantidadTpInf.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    can=cantidadTpInf.getText().toString();
                    if(can.equals("")){
                        Toast.makeText(CatalogoProductos.this, "Ingresa una cantidad",Toast.LENGTH_SHORT).show();
                    }else {
                        double valor1_int = Integer.parseInt(can);
                        double total = canN * valor1_int;
                        resultadoTres = String.valueOf(total);
                        tvTota3.setText("$ " + resultadoTres);
                    }
                    return true;
                }
                return false;
            }
        });
        cantidadTpInf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    can=cantidadTpInf.getText().toString();
                    if(can.equals("")){
                        Toast.makeText(CatalogoProductos.this, "Ingresa una cantidad",Toast.LENGTH_SHORT).show();
                    }else {
                        double valor1_int = Integer.parseInt(can);
                        double total = canN * valor1_int;
                        resultadoTres = String.valueOf(total);
                        tvTota3.setText("$ " + resultadoTres);
                    }
                }
            }
        });

    }
    private void obtenerDatosUsr(){
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
                            .with(CatalogoProductos.this)
                            .load(url)
                            .into(tvPerfil);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void obtenerColor() {
        if (azTa.isChecked()==true){
            colorTa="Azul";
        }else {
            if (rosTa.isChecked()==true){
                colorTa="Rosa";
            }else{
                if (negTa.isChecked()==true){
                    colorTa="Negro";
                }else{
                    Toast.makeText(this,"Selecciona un color",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private void obtenerColorI() {
        if (azTi.isChecked()==true){
            colorTi="Azul";
        }else {
            if (rosTi.isChecked()==true){
                colorTi="Rosa";
                }else{
                    Toast.makeText(this,"Selecciona un color",Toast.LENGTH_LONG).show();
                }
        }
    }
}