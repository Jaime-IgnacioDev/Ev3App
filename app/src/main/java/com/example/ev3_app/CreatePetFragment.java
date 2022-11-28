package com.example.ev3_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreatePetFragment extends DialogFragment {

    Button btn_agregar;
    EditText name, age, color;
    private FirebaseFirestore mfirestore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_pet, container, false);
        mfirestore = FirebaseFirestore.getInstance();

        name = v.findViewById(R.id.nameM);
        age = v.findViewById(R.id.edadM);
        color = v.findViewById(R.id.colorM);
        btn_agregar = v.findViewById(R.id.btn_agregar);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namepet = name.getText().toString().trim();
                String agepet = age.getText().toString().trim();
                String colorpet = color.getText().toString().trim();

                if (namepet.isEmpty() && agepet.isEmpty() && colorpet.isEmpty()) {
                    Toast.makeText(getContext(), "Rellenar Campos", Toast.LENGTH_SHORT).show();
                }else {
                    postPet(namepet, agepet, colorpet);
                }
            }
        });

        return v;
    }
    private void postPet(String namepet, String agepet, String colorpet) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", namepet);
        map.put("age", agepet);
        map.put("color", colorpet);
        mfirestore.collection("mascota").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(),"Creado Exitosamente",Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error al ingresar",Toast.LENGTH_SHORT).show();
            }
        });
    }
}