package com.nextimpulse.meprotege;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormularioClientes extends AppCompatActivity {
    private CircleImageView tvFotoP;
    private ImageView btnFoto,btnComp,btnine,btnext;
    private ImageButton imageButton;
    private EditText email,pass;
    private EditText eNombre,eApellido,eDir,eNoC,eNoT;
    private Button btn1;
    //variables a guardar
    private String correo="", contra="";
    private String nombre="", apellido="",direccion="",noCel="",noTel="";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri mImageUri;
    private Uri mImageINE;
    private Uri mImageCOM;
    private Uri mImageEXT;
    private Uri ruta;
    private int p=0,i=0,c=0,e=0;

    String rutafinal;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_clientes);
        //iniciar firebase//
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //iniciar firebase//
        email=(EditText)findViewById(R.id.edtCorreoElectronico);
        pass=(EditText)findViewById(R.id.edtContraseña);
        eNombre=(EditText)findViewById(R.id.edtNombre);
        eApellido=(EditText)findViewById(R.id.edtApellidos);
        eDir=(EditText)findViewById(R.id.edtNoLicenciadeConducir);
        eNoC=(EditText)findViewById(R.id.edtCelular);
        eNoT=(EditText)findViewById(R.id.edtTelefonoFijo);
        btn1=(Button)findViewById(R.id.btnRegistro);
        btnFoto=(ImageView) findViewById(R.id.btnCamarita);
        btnComp=(ImageView)findViewById(R.id.btnComprobanteDomicilio);
        btnine=(ImageView)findViewById(R.id.btnINEoLicencia);
        btnext=(ImageView)findViewById(R.id.btnFotodeFachada);
        tvFotoP=(CircleImageView)findViewById(R.id.profile_image);

        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarExt();
            }
        });
        btnine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarINE();
            }
        });
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto();
            }
        });
        btnComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarComp();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo=email.getText().toString();
                contra=pass.getText().toString();
                nombre=eNombre.getText().toString();
                apellido=eApellido.getText().toString();
                direccion=eDir.getText().toString();
                noCel=eNoC.getText().toString();
                noTel=eNoT.getText().toString();

                if (!correo.isEmpty() &&!contra.isEmpty() &&!nombre.isEmpty()&&!apellido.isEmpty()&&
                        !direccion.isEmpty()&&
                        !noCel.isEmpty()&&
                        !noTel.isEmpty())
                {

                    if (contra.length() > 6){
                        if (p==0 || c==0 || e==0 || i==0){
                            Toast.makeText(FormularioClientes.this, "Sube los archivos correspondientes",Toast.LENGTH_SHORT).show();
                        }else{
                            registroUsuario();
                                mAuth.signInWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            subirfoto();
                                            subirfotoINE();
                                            subirfotoCOM();
                                            subirfotoEXT();
                                            startActivity(new Intent(FormularioClientes.this,MainActivity.class));
                                            finish();
                                        }else{
                                            Toast.makeText(FormularioClientes.this, "Archivos no cargados",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            //mAuth.signOut();
                        }
                    }else{
                        Toast.makeText(FormularioClientes.this, "La contraseña debe de tener minimo 6 digitos",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(FormularioClientes.this, "Debe completar los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void  registroUsuario(){
            mAuth.createUserWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task1) {
                    if(task1.isSuccessful()){
                        int alt=1;
                        alt=funcional(1);
                        Map<String,Object> map=new HashMap<>();
                        map.put("correo",correo);
                        map.put("pass",contra);
                        map.put("folio",alt);
                        map.put("nombre",nombre);
                        map.put("apellido",apellido);
                        map.put("direccion",direccion);
                        map.put("no Celular",noCel);
                        map.put("no Telefono",noTel);
                        String id=mAuth.getCurrentUser().getUid();
                        mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if (task2.isSuccessful()){
                                    Toast.makeText(FormularioClientes.this, "Usuario creado exitosamente",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(FormularioClientes.this, "Usuario no creado",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(FormularioClientes.this, "Usuario Existente",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    //Folio aleatorio//
    public static int funcional(int alt){
        int n2;
        Random num =new Random();
        alt=1+num.nextInt(5000);
        n2=1+num.nextInt(5000);
        System.out.println(alt);

        return alt;
    }
    private void subirfoto(){
            final StorageReference filePath=mStorageRef.child("Fotos").child(correo).child("Perfil").child(mImageUri.getLastPathSegment());
            filePath.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task1) throws Exception {
                    if (!task1.isSuccessful()){
                        throw new Exception();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri link=task.getResult();
                    Map<String,Object> map=new HashMap<>();
                    map.put("perfil",link.toString());
                    String id=mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).child("ImgPerfil").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (!task2.isSuccessful()){
                                Toast.makeText(FormularioClientes.this, "Error al cargar foto",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
    }
    private void subirfotoINE(){
        final StorageReference filePath=mStorageRef.child("Fotos").child(correo).child("INE").child(mImageINE.getLastPathSegment());
        filePath.putFile(mImageINE).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task1) throws Exception {
                if (!task1.isSuccessful()){
                    throw new Exception();
                }

                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri link=task.getResult();
                Map<String,Object> map=new HashMap<>();
                map.put("INE",link.toString());
                String id=mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(id).child("ImgINE").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if (!task2.isSuccessful()){
                            Toast.makeText(FormularioClientes.this, "Error al cargar foto",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
    private void subirfotoCOM(){
        final StorageReference filePath=mStorageRef.child("Fotos").child(correo).child("Comprobante").child(mImageCOM.getLastPathSegment());
        filePath.putFile(mImageCOM).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task1) throws Exception {
                if (!task1.isSuccessful()){
                    throw new Exception();
                }

                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri link=task.getResult();
                Map<String,Object> map=new HashMap<>();
                map.put("Comp",link.toString());
                String id=mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(id).child("ImgCom").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if (!task2.isSuccessful()){
                            Toast.makeText(FormularioClientes.this, "Error al cargar foto",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
    private void subirfotoEXT(){
        final StorageReference filePath=mStorageRef.child("Fotos").child(correo).child("Exterior").child(mImageEXT.getLastPathSegment());
        filePath.putFile(mImageEXT).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task1) throws Exception {
                if (!task1.isSuccessful()){
                    throw new Exception();
                }

                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri link=task.getResult();
                Map<String,Object> map=new HashMap<>();
                map.put("EXT",link.toString());
                String id=mAuth.getCurrentUser().getUid();
                mDatabase.child("Users").child(id).child("ImgExt").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if (!task2.isSuccessful()){
                            Toast.makeText(FormularioClientes.this, "Error al cargar foto",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
    public void tomarFoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(FormularioClientes.this, "Error mientras se crear el archivo", Toast.LENGTH_SHORT).show();
            }
            if (photoFile !=null){
                p=1;
                mImageUri = FileProvider.getUriForFile(this,"com.nextimpulse.android.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }

    }
    public void tomarINE(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFileINE = null;
            try {
                photoFileINE = createImageFile();
            } catch (IOException e) {
                Toast.makeText(FormularioClientes.this, "Error mientras se crear el archivo", Toast.LENGTH_SHORT).show();
            }
            if (photoFileINE !=null){
                i=1;
                mImageINE =FileProvider.getUriForFile(this,"com.nextimpulse.android.fileprovider",photoFileINE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageINE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }
    public void tomarComp(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFileINE = null;
            try {
                photoFileINE = createImageFile();
            } catch (IOException e) {
                Toast.makeText(FormularioClientes.this, "Error mientras se crear el archivo", Toast.LENGTH_SHORT).show();
            }
            if (photoFileINE !=null){
                c=1;
                mImageCOM =FileProvider.getUriForFile(this,"com.nextimpulse.android.fileprovider",photoFileINE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageCOM);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }
    public void tomarExt(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFileINE = null;
            try {
                photoFileINE = createImageFile();
            } catch (IOException e) {
                Toast.makeText(FormularioClientes.this, "Error mientras se crear el archivo", Toast.LENGTH_SHORT).show();
            }
            if (photoFileINE !=null){
                e=1;
                mImageEXT =FileProvider.getUriForFile(this,"com.nextimpulse.android.fileprovider",photoFileINE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageEXT);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }
    String currentPhotoPath;
    private File createImageFile() throws IOException {
        String nombreimagen= "IMG_" + eNombre.getText().toString()+"_";
        File storageDir =getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image =File.createTempFile(nombreimagen, "jpg", storageDir);
        currentPhotoPath=image.getAbsolutePath();
        rutafinal=currentPhotoPath;
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ){
            tvFotoP.setImageURI(mImageUri);
        }
    }
}