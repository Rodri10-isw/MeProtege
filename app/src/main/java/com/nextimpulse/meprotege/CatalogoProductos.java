package com.nextimpulse.meprotege;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class CatalogoProductos extends AppCompatActivity {
    private Spinner spinner1;
    private TextView Total;
    private EditText cantidad;
    private int kn=100,valor1_int;
    private String can="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_productos);

        spinner1 =(Spinner)findViewById(R.id.spKn95);
        //Total=(TextView)findViewById(R.id.tvTotal);
        cantidad=(EditText)findViewById(R.id.edtCanKN);
        can=cantidad.getText().toString();
        String [] opciones = {"1","3","5","10"};
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opciones);

        spinner1.setAdapter(ada);
        String seleccion=spinner1.getSelectedItem().toString();
        if(seleccion.equals("1")){

            int total =kn*valor1_int;
            String resultado=String.valueOf(total);
            //Total.setText(resultado);
        }
    }
}