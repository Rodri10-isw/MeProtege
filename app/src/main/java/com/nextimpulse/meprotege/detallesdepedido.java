package com.nextimpulse.meprotege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class detallesdepedido extends AppCompatActivity {
    TextView txtotal;
    private Button btnCat,btnPedido;
    private RecyclerView rvListCarr;
    private DatabaseReference mDatabase;
    private DatabaseReference pDatabase;
    private FirebaseAuth mAuth;
    private String id;
    float sum,tem;
    ListView listacubre;
    String resultado;
    private long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallesdepedido);
        mAuth=FirebaseAuth.getInstance();
        btnCat=(Button)findViewById(R.id.btnVolverAlCatalogo);
        id=mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("carrito");
        pDatabase= FirebaseDatabase.getInstance().getReference().child("Pedidos");
        mDatabase.keepSynced(true);
        btnPedido=(Button)findViewById(R.id.btnAceptaryEnviar);
        txtotal=(TextView)findViewById(R.id.textTotal);
        rvListCarr=(RecyclerView)findViewById(R.id.myresycle);
        rvListCarr.setHasFixedSize(true);
        rvListCarr.setLayoutManager(new LinearLayoutManager(this));
        pDatabase.addValueEventListener(new ValueEventListener() {
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
        obtenerTotal();

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtotal.getText().equals("$ 0.0 MXN")){
                    Toast.makeText(detallesdepedido.this, "Carrito vacio, Agrege productos",Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("carrito")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    guardarPedido();
                                    FirebaseDatabase.getInstance().getReference("Pedidos")
                                            .child(String.valueOf(maxid+1)).child("articulos")
                                            .setValue(dataSnapshot.getValue());
                                    mDatabase.removeValue();
                                    txtotal.setText("$ 0.0 MXN");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }

            }
        });
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent interfaz5=new Intent(detallesdepedido.this,CatalogoProductos.class);
                interfaz5.putExtra("cliente",id);
                startActivity(interfaz5);
                finish();
            }
        });
    }
    private void obtenerTotal() {
        final Query query=mDatabase.orderByChild("total");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int con=0;
                for(DataSnapshot datos:dataSnapshot.getChildren()){
                    detalleCarr c=datos.getValue(detalleCarr.class);
                    String num=c.getSubtotal();
                    float valor= Float.parseFloat(num);
                    sum=sum+valor;
                    con++;
                }
                String total =String.valueOf(sum);
                txtotal.setText("$ "+total +" MXN");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sum=0;
    }
    private void guardarPedido(){
        String total=txtotal.getText().toString();
        String uid=id;
        Map<String,Object> pe=new HashMap<>();
        pe.put("total",total);
        pe.put("uid",uid);
        pDatabase.child(String.valueOf(maxid+1)).setValue(pe).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(detallesdepedido.this, "Pedido generado",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<detalleCarr,CarritoViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<detalleCarr, CarritoViewHolder>
                (detalleCarr.class,R.layout.carrito,CarritoViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(final CarritoViewHolder ViewHolder, final detalleCarr model, int position) {
                ViewHolder.setTitle(model.getProducto());
                ViewHolder.setPiez(model.getPiezas());
                ViewHolder.setCan(model.getCantidad());
                ViewHolder.setColor(model.getColor());
                ViewHolder.setTotal(model.getSubtotal());
                ViewHolder.setImg(getApplicationContext(),model.getImg());
                ViewHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(detallesdepedido.this)
                                .setTitle("Eliminar")
                                .setMessage("Este dato se eliminara de forma definitiva ¿Desea continuar?")
                                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mDatabase.child(model.getNum()).removeValue();
                                        Toast.makeText(detallesdepedido.this, "Dato Eliminado", Toast.LENGTH_SHORT).show();
                                        obtenerTotal();
                                    }
                                })
                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(detallesdepedido.this,"Accion cancelada",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .show();

                    }
                });
            }
        };
        rvListCarr.setAdapter(firebaseRecyclerAdapter);

    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View mView;
        private View.OnClickListener listener;

        public CarritoViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            itemView.setOnClickListener(this);
        }

        public void setTitle(String title){
            TextView post_title=(TextView)mView.findViewById(R.id.titulo1);
            post_title.setText(title);
        }
        public  void setPiez(String piez){
            TextView post_piez=(TextView)mView.findViewById(R.id.titulo2);
            post_piez.setText(piez);
        }
        public void setCan(String can){
            TextView post_can=(TextView)mView.findViewById(R.id.titulo3);
            post_can.setText(can);
        }
        public void setTotal(String total){
            TextView post_total=(TextView)mView.findViewById(R.id.titulo4);
            post_total.setText(total);
        }
        public void setColor(String color){
            TextView post_color=(TextView)mView.findViewById(R.id.titulo5);
            post_color.setText(color);
        }
        public void setImg(Context ctx,String img){
            ImageView post_img=(ImageView)mView.findViewById(R.id.tvFotoCarr);
            Glide.with(ctx).load(img).into(post_img);
        }
        public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
        }
        @Override
        public void onClick(View view) {
            if(listener!=null){
                listener.onClick(view);
            }
        }
    }
}