package com.nextimpulse.meprotege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
    private TextView txtId,txtName;
    private  ImageView imgVinfantil,imgVakn95, imgVadulto; // ImageView de los productos solo son tres
    private EditText cantidad,cantidadTP,cantidadTpInf;
    private double kn=7.5;
    private String can="",NoPiezas="",canEm="",total="",kn95="KN-95",codigoP="proKN95";
    private String tc="Tricapa Plisado Adulto",tcIn="Tricapa Plisado Infantil",codigoT="proTCPAD",codigoTI="proTCPIN";
    private String colorSelAdulto,colorSelInfantil;
    //direcciones de imagenes
    private String imgkn95="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Fakn95.png?alt=media&token=2759c6dd-7a16-4859-88aa-d84b1848538f";
    private String imgTcAazul="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Faazul.png?alt=media&token=a8fb50fa-65a9-46d4-8d80-73cb0a0c15cb";
    private String imgTcArosa="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Farosa.png?alt=media&token=cde228d1-800f-4b01-aff2-e3868e2e619b";
    private String imgTcAnegro="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Fanegro.png?alt=media&token=928edbe8-61ef-4d5e-9eae-1838c53ef65e";
    //direcciones de imagenes infantil
    private String imgTcIazul="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Fizaul.png?alt=media&token=9361db83-5d87-43d4-a7b1-6adb7779879f";
    private String imgTcIrosa="https://firebasestorage.googleapis.com/v0/b/meprotege-610fa.appspot.com/o/Fotos%2Fproductos%2Firosa.png?alt=media&token=897f1a10-b4b7-4cda-a9c7-efdf9cc94578";
    private double canN=0;
    private int v1=0,v2=0,v3=0;
    private ImageView btnAddCarrKN,btnAddCarrTC,btnAddCarrTCIN,tvPerfil;
    private String resultado,cantidadEm,id,resultadodos,cantidadDos,cantidadTres,resultadoTres,url,colorTa,colorTi,colorkn="Blanco";
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
        id=getIntent().getExtras().getString("cliente");
        //firebase
        txtName=(TextView)findViewById(R.id.txtUsuario);
        txtId=(TextView)findViewById(R.id.idUser);
        tvPerfil=(ImageView)findViewById(R.id.tvPerfil);
        btncarr=(Button)findViewById(R.id.btnCarrito);
        spinner1 =(Spinner)findViewById(R.id.spKn95);
        spinner2 =(Spinner)findViewById(R.id.spKn2);
        spinner3=(Spinner)findViewById(R.id.spKn3);
        cantidad=(EditText)findViewById(R.id.edtCanKN);
        cantidadTP=(EditText)findViewById(R.id.edtCanTP);
        cantidadTpInf=(EditText)findViewById(R.id.edtCanKnIn);
        btnAddCarrKN=(ImageView) findViewById(R.id.btnAgregarCarritoKN);
        btnAddCarrTC=(ImageView) findViewById(R.id.btnAgregarCarritoTC);
        btnAddCarrTCIN=(ImageView)findViewById(R.id.btnAgregarCarritoTCIn);
        azTa=(RadioButton)findViewById(R.id.rbAzTa);  //opcion cubrebocas tricapa color azul adulto
        rosTa=(RadioButton)findViewById(R.id.rbRoTa); //opcion cubrebocas tricapa color rosa adulto
        negTa=(RadioButton)findViewById(R.id.rbNeTa); //opcion cubrebocas tricapa color negro adulto
        azTi=(RadioButton)findViewById(R.id.rbAzTi);  //opcion cubrebocas tricapa color azul infantil
        rosTi=(RadioButton)findViewById(R.id.rbRoTi); //opcion cubrebocas tricapa color azul infantil
        imgVakn95=(ImageView)findViewById(R.id.imageView6);
        imgVadulto=(ImageView)findViewById(R.id.imageView3); //foto adulto
        imgVinfantil=(ImageView)findViewById(R.id.tvInf);
        //can=cantidad.getText().toString();
        obtenerDatosUsr();
        carr= FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("carrito");
        ////cargar imagenes////
        //iniciando//
        imgVakn95.setImageResource(R.drawable.akn95);
        imgVadulto.setImageResource(R.drawable.aazul);
        imgVinfantil.setImageResource(R.drawable.izaul);
        // cuando se seleccione la opc curbebocas adulto cambiara la imagen
        azTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVadulto.setImageResource(R.drawable.aazul);
            }
        });
        rosTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVadulto.setImageResource(R.drawable.arosa);
            }
        });
        negTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVadulto.setImageResource(R.drawable.anegro);
            }
        });

        // cuando se seleccione la opc curbebocas infantil cambiara la imagen
        azTi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVinfantil.setImageResource(R.drawable.izaul);
            }
        });
        rosTi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVinfantil.setImageResource(R.drawable.irosa);
            }
        });
        String [] opciones = {"1","3","5","10"};
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinner3.setAdapter(ada);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView parentView, View selectedItemView, int position, long id) {
                String seleccion=spinner3.getSelectedItem().toString();
                if(seleccion.equals("1")){
                    v3=1;
                    cantidadTres="1";
                }else{
                    if (seleccion.equals("3")){
                        v3=3;
                        cantidadTres="3";
                    }else{
                        if (seleccion.equals("5")){
                            v3=5;
                            cantidadTres="5";

                        }else{
                            if (seleccion.equals("10")){
                                v3=10;
                                cantidadTres="10";
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
                }else{
                    if (seleccion.equals("3")){
                        v2=3;
                        cantidadDos="3";
                    }else{
                        if (seleccion.equals("5")){
                            v2=5;
                            cantidadDos="5";
                        }else{
                            if (seleccion.equals("10")){
                                v2=10;
                                cantidadDos="10";
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
                }else{
                    if (seleccion.equals("3")){
                        v1=3;
                        cantidadEm="3";
                    }else{
                        if (seleccion.equals("5")){
                            v1=5;
                            cantidadEm="5";
                        }else{
                            if (seleccion.equals("10")){
                                v1=10;
                                cantidadEm="10";
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
                    new AlertDialog.Builder(CatalogoProductos.this)
                            .setTitle("¿Agregar al carrito?")
                            .setMessage(Html.fromHtml("Numero de piezas por empaque: <strong>"+cantidadEm+"</strong><br><br>"+
                                    "Cantidad de empaques: <strong>"+can+"</strong><br><br>"+
                                    "<big>Subtotal: <strong>$"+resultado+"</big>"))
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CatalogoProductos.this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
                                    guardarCarrito();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CatalogoProductos.this,"Accion cancelada",Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }

            }
        });

        //boton guardar carrito cubrebocas tricapa termosellado adulto
        btnAddCarrTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canEm=cantidadTP.getText().toString();
                if (canEm.equals("")){
                    Toast.makeText(CatalogoProductos.this,"Introdusca informacion",Toast.LENGTH_LONG).show();
                }else{
                    double pXE=kn*v2;  //precio por cantidad de empaque
                    double valor= Integer.parseInt(canEm);
                    double total = pXE * valor;
                    resultadodos = String.valueOf(total);
                    new AlertDialog.Builder(CatalogoProductos.this)
                            .setTitle("¿Agregar al carrito?")
                            .setMessage(Html.fromHtml("Numero de piezas por empaque: <strong>"+cantidadDos+"</strong><br><br>"+
                                    "Cantidad de empaques: <strong>"+canEm+"</strong><br><br>"+
                                    "<big>Subtotal: <strong>$"+resultadodos+"</big>"))
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CatalogoProductos.this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
                                    guardarCarritokn();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CatalogoProductos.this,"Accion cancelada",Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }
            }
        });
        btnAddCarrTCIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canEm=cantidadTpInf.getText().toString();
                if (canEm.equals("")){
                    Toast.makeText(CatalogoProductos.this,"Introdusca informacion",Toast.LENGTH_LONG).show();
                }else {
                    double pXE = kn * v3;  //precio por cantidad de empaque
                    double valor = Integer.parseInt(canEm);
                    double total = pXE * valor;
                    resultadoTres = String.valueOf(total);
                    new AlertDialog.Builder(CatalogoProductos.this)
                            .setTitle("¿Agregar al carrito?")
                            .setMessage(Html.fromHtml("Numero de piezas por empaque: <strong>" + cantidadTres + "</strong><br><br>" +
                                    "Cantidad de empaques: <strong>" + canEm + "</strong><br><br>" +
                                    "<big>Subtotal: <strong>$" + resultadoTres + "</big>"))
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CatalogoProductos.this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
                                    guardarCarritoknIN();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CatalogoProductos.this, "Accion cancelada", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }
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
        String num= UUID.randomUUID().toString();
        Map<String,Object> map=new HashMap<>();
        map.put("producto",kn95);
        map.put("piezas",cantidadEm);
        map.put("cantidad",canEm);
        map.put("subtotal",resultado);
        map.put("codigoP",codigoP);
        map.put("img",imgkn95);
        map.put("num",num);
        map.put("color",colorkn);
        carr.child(num).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CatalogoProductos.this, "Añadido al carrito",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void guardarCarritokn() {
        canEm=cantidadTP.getText().toString();
        total=resultado;
        String num= UUID.randomUUID().toString();
        obtenerColor();
        Map<String,Object> map=new HashMap<>();
        map.put("producto",tc);
        map.put("piezas",cantidadDos);
        map.put("cantidad",canEm);
        map.put("subtotal",resultadodos);
        map.put("codigoP",codigoT);
        map.put("img",colorSelAdulto);
        map.put("color",colorTa);
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
        String num= UUID.randomUUID().toString();
        obtenerColorI();
        Map<String,Object> map=new HashMap<>();
        map.put("producto",tcIn);
        map.put("piezas",cantidadTres);
        map.put("cantidad",canEm);
        map.put("subtotal",resultadoTres);
        map.put("codigoP",codigoTI);
        map.put("img",colorSelInfantil);
        map.put("color",colorTi);
        map.put("num",num);
        carr.child(num).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //startActivity(new Intent(CatalogoProductos.this,detallesdepedido.class));
                Toast.makeText(CatalogoProductos.this, "Añadido al carrito",Toast.LENGTH_SHORT).show();
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
            colorSelAdulto=imgTcAazul;
        }else {
            if (rosTa.isChecked()==true){
                colorTa="Rosa";
                colorSelAdulto=imgTcArosa;
            }else{
                if (negTa.isChecked()==true){
                    colorTa="Negro";
                    colorSelAdulto=imgTcAnegro;
                }else{
                    Toast.makeText(this,"Selecciona un color",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private void obtenerColorI() {
        if (azTi.isChecked()==true){
            colorTi="Azul";
            colorSelInfantil=imgTcIazul;
        }else {
            if (rosTi.isChecked()==true){
                colorTi="Rosa";
                colorSelInfantil=imgTcIrosa;
            }else{
                Toast.makeText(this,"Selecciona un color",Toast.LENGTH_LONG).show();
            }
        }
    }
}