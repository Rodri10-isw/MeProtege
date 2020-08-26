package com.nextimpulse.meprotege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CatalogoProductos extends AppCompatActivity {
    private Spinner spinner1;
    private TextView Total;
    private EditText cantidad;
    private double kn=7.5;
    private String can="",NoPiezas="",canEm="",total="",kn95="proKN95";
    private double canN=0;
    private int v1=0;
    private ImageView btnAddCarrKN;
    private String resultado,cantidadEm;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_productos);

        //iniciar firebase//
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        //firebase
        spinner1 =(Spinner)findViewById(R.id.spKn95);
        Total=(TextView)findViewById(R.id.tvTotal);
        cantidad=(EditText)findViewById(R.id.edtCanKN);
        btnAddCarrKN=(ImageView) findViewById(R.id.btnAgregarCarritoKN);
        can=cantidad.getText().toString();
        String [] opciones = {"1","3","5","10"};
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opciones);
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
                canEm=cantidad.getText().toString();
                total=resultado;
                Map<String,Object> map=new HashMap<>();
                map.put("producto",kn95);
                map.put("Piesas empaque",cantidadEm);
                map.put("cantidad",canEm);
                map.put("total",resultado);
                String id=mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(id).child("Carrito").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CatalogoProductos.this, "Agregado al carrito con exito "+cantidadEm,Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(CatalogoProductos.this, "Algo salio mal "+cantidadEm,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Toast.makeText(CatalogoProductos.this, "cantidad "+cantidadEm,Toast.LENGTH_SHORT).show();
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


}