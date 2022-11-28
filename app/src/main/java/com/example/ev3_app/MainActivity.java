package com.example.ev3_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ev3_app.adapter.PetAdapter;
import com.example.ev3_app.modelo.Pet;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    Button btn_add, btn_add_frg;
    RecyclerView mRecycler;
    PetAdapter mAdapter;
    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recycleViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("mascota");

        FirestoreRecyclerOptions<Pet> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Pet>().setQuery(query, Pet.class).build();

        mAdapter = new PetAdapter(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        btn_add = findViewById(R.id.btn_add);
        btn_add_frg = findViewById(R.id.btn_add_frg);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreatePetActivity.class));
            }
        });

        btn_add_frg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePetFragment fm = new CreatePetFragment();
                fm.show(getSupportFragmentManager(),"Navegar a Fragment");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}