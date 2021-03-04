package com.example.practicafirebase.model.pojo;

public class Consola {

    private String nombre, estado, id;
    private double precio;


    public Consola(String nombre, String estado, double precio) {
        this.nombre = nombre;
        this.estado = estado;
        this.precio = precio;
        id = setId();
    }

    private String setId() {
        String lista = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
        String s="";

        for(int i = 0; i < 20; i++){
            s+= lista.charAt((int) (Math.random() *61)+1);

        }

        return s;
    }

    public String getId() {
        return id;
    }

    public Consola() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Consola{" +
                "nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", precio=" + precio +
                '}';
    }
}
