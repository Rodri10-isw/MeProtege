package com.nextimpulse.meprotege;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Pedidos extends AppCompatActivity {
    private Spinner spinner1;
    private TextView Total;
    private EditText cantidad;
    private int kn=100,valor1_int;
    private String can="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
    }
}