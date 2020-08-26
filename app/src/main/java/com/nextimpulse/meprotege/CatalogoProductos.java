package com.nextimpulse.meprotege;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CatalogoProductos extends AppCompatActivity {
    private Spinner spinner1;
    private TextView Total;
    private EditText cantidad;
    private double kn=7.5;
    private String can="";
    private double canN=0;
    private int v1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_productos);

        spinner1 =(Spinner)findViewById(R.id.spKn95);
        Total=(TextView)findViewById(R.id.tvTotal);
        cantidad=(EditText)findViewById(R.id.edtCanKN);
        can=cantidad.getText().toString();
        String [] opciones = {"1","3","5","10"};
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinner1.setAdapter(ada);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView parentView, View selectedItemView, int position, long id) {
                String seleccion=spinner1.getSelectedItem().toString();
                if(seleccion.equals("1")){
                    v1=1;
                    obtenerTotal(v1);

                }else{
                    if (seleccion.equals("3")){
                        v1=3;
                        obtenerTotal(v1);
                    }else{
                        if (seleccion.equals("5")){
                            v1=5;
                            obtenerTotal(v1);
                        }else{
                            if (seleccion.equals("10")){
                                v1=10;
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
                    String resultado=String.valueOf(total);
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
                    String resultado=String.valueOf(total);
                    Total.setText("$ "+resultado);
                }
            }
        });

    }


}