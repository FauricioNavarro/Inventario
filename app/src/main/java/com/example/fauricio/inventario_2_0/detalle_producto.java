package com.example.fauricio.inventario_2_0;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

public class detalle_producto extends AppCompatActivity {
    private TextView nom,pre,des;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        nom = findViewById(R.id.nombre);
        pre = findViewById(R.id.precio);
        des = findViewById(R.id.descripcion);
        img = findViewById(R.id.imagen);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("productos");
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            final String j =(String) extras.get("producto_id");
            nom.setText(j);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.w("PRODUCTO",dataSnapshot.child(j).child("nombre").getValue().toString());
                    nom.setText("Nombre: "+dataSnapshot.child(j).child("nombre").getValue().toString());
                    pre.setText("Precio: "+dataSnapshot.child(j).child("precio").getValue().toString());
                    des.setText("Descripción: "+dataSnapshot.child(j).child("descripcion").getValue().toString());
                    ImageDownloadTask downloadTask = new ImageDownloadTask();
                    try {
                        Bitmap result = downloadTask.execute("https://firebasestorage.googleapis.com/v0/b/inventario-4d73a.appspot.com/o/"+
                                dataSnapshot.child(j).child("nombre").getValue().toString()+
                                "?alt=media&token=49d0fda1-dbb4-4189-9ac3-84f40c21d047").get();
                        img.setImageBitmap(result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}
