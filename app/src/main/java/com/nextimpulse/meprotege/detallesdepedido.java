package com.nextimpulse.meprotege;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class detallesdepedido extends AppCompatActivity {
    private Button btnCat;
    private RecyclerView rvListCarr;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallesdepedido);
        mAuth=FirebaseAuth.getInstance();
        btnCat=(Button)findViewById(R.id.btnVolverAlCatalogo);
        id=mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("carrito");
        mDatabase.keepSynced(true);

        rvListCarr=(RecyclerView)findViewById(R.id.myresycle);
        rvListCarr.setHasFixedSize(true);
        rvListCarr.setLayoutManager(new LinearLayoutManager(this));


        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(detallesdepedido.this,CatalogoProductos.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<detalleCarr,CarritoViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<detalleCarr, CarritoViewHolder>
                (detalleCarr.class,R.layout.carrito,CarritoViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(CarritoViewHolder ViewHolder, detalleCarr model, int position) {
                ViewHolder.setTitle(model.getProducto());
                ViewHolder.setPiez(model.getPiezas());
                ViewHolder.setCan(model.getCantidad());
                ViewHolder.setTotal(model.getTotal());
                ViewHolder.setImg(getApplicationContext(),model.getImg());
            }
        };
        rvListCarr.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public CarritoViewHolder(View itemView){
            super(itemView);
            mView=itemView;
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
        public void setImg(Context ctx,String img){
            ImageView post_img=(ImageView)mView.findViewById(R.id.tvFotoCarr);
            Glide.with(ctx).load(img).into(post_img);
        }
    }
}