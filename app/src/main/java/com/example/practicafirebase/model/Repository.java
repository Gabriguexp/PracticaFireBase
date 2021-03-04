package com.example.practicafirebase.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.practicafirebase.viewmodel.ViewModel;


import com.example.practicafirebase.model.pojo.Consola;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    private MutableLiveData<List<Consola>> consolaLiveData = new MutableLiveData<>();

    public Repository() {
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registro(String email, String pass){
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //currentuser = firebaseAuth.getCurrentUser();
                //ViewModel.currentUser = currentuser;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void login(String email, String pass, OnSuccessListener successListener, OnFailureListener failureListener){
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(successListener).addOnFailureListener(failureListener);
        //currentuser = firebaseAuth.getCurrentUser();
        //ViewModel.currentUser = currentuser;
    }



    public void añadirConsola(Consola consola) {
        CollectionReference collection = db.collection("/users/"+currentuser.getUid()+"/consolas");
        collection.document(consola.getId()).set(consola);
        getConsolas();

    }
    public void borrarConsola(Consola consola){
        CollectionReference collection = db.collection("/users/"+currentuser.getUid()+"/consolas");
        collection.document(consola.getId()).delete();
        getConsolas();
    }

    public void editarConsola(Consola consola){
        CollectionReference collection = db.collection("/users/"+currentuser.getUid()+"/consolas");
        collection.document(consola.getId()).update("nombre", consola.getNombre(), "precio", consola.getPrecio(), "estado" , consola.getEstado());
        getConsolas();
    }

    public void getConsolas() {
        CollectionReference collection = db.collection("/users/"+currentuser.getUid()+"/consolas");
        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    consolaLiveData.setValue(task.getResult().toObjects(Consola.class));
                } else {
                    Log.v("xyz", "algo ha fallado");
                }
            }
        });

    }

    public FirebaseUser getCurrentuser() {
        return currentuser;
    }

    public MutableLiveData<List<Consola>> getConsolaLiveData() {
        return consolaLiveData;
    }

    public void logOut(){
        currentuser = null;
        ViewModel.currentUser = null;
    }

    public void setCurrentuser(FirebaseUser currentuser) {
        this.currentuser = currentuser;
    }
}
