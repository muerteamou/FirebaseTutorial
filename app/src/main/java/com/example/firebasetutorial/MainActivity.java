package com.example.firebasetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextMensaje;
    private Button mBtnCrearDatos;
    private Button mBtnEliminarDatos;
    private Button mBtnActualizarDatos;
    private TextView mTextView;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextMensaje = findViewById(R.id.edittextMensaje);
        mBtnCrearDatos = findViewById(R.id.btnCrearDatos);
        mBtnEliminarDatos = findViewById(R.id.btnEliminarDatos);
        mBtnActualizarDatos = findViewById(R.id.btnActualizarDatos);
        mTextView = findViewById(R.id.textViewData);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mBtnCrearDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String mensaje = mEditTextMensaje.getText().toString();
                Map<String, Object> personaMap = new HashMap<>();
                personaMap.put("nombre", "andrea");
                personaMap.put("apellido", "garcia");
                personaMap.put("edad", 39);
                mDatabase.child("Persona").setValue(personaMap);
            }
        });

        mBtnEliminarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("texto").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "El objeto se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "El objeto no se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        mBtnActualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> personaMap = new HashMap<>();
                personaMap.put("nombre", "Sofía");
                personaMap.put("apellido", "Martinez");
                personaMap.put("edad", "44");
                personaMap.put("documento", 12345);
                personaMap.put("ciudad", "Huesca");
                mDatabase.child("Persona").updateChildren(personaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Los datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mDatabase.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    String nombre = datasnapshot.child("nombre").getValue().toString();
                    String apellido = datasnapshot.child("apellido").getValue().toString();
                    int edad = Integer.parseInt(datasnapshot.child("edad").getValue().toString());
                    mTextView.setText("El nombre es : " + nombre + "; apellido : " + apellido + "; edad : " + edad);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}