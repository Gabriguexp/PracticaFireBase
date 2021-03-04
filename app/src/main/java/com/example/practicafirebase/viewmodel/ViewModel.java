package com.example.practicafirebase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicafirebase.model.Repository;
import com.example.practicafirebase.model.pojo.Consola;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
    public static FirebaseUser currentUser;
    public static Consola currentConsola;
    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void registro(String email, String pass) {
        repository.registro(email, pass);
    }

    public void login(String email, String pass, OnSuccessListener successListener, OnFailureListener failureListener) {
        repository.login(email, pass, successListener, failureListener  );
    }

    public void añadirConsola(Consola consola) {
        repository.añadirConsola(consola);
    }

    public void borrarConsola(Consola consola) {
        repository.borrarConsola(consola);
    }

    public void getConsolas() {
        repository.getConsolas();
    }

    public FirebaseUser getCurrentuser() {
        currentUser = repository.getCurrentuser();
        return repository.getCurrentuser();
    }

    public MutableLiveData<List<Consola>> getConsolaLiveData() {
        return repository.getConsolaLiveData();
    }

    public void editarConsola(Consola consola) {
        repository.editarConsola(consola);
    }

    public void logOut() {
        repository.logOut();
    }

    public void setCurrentuser(FirebaseUser currentuser) {
        repository.setCurrentuser(currentuser);
    }
}
