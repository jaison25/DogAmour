package com.example.juanitobeltran.dog;

public class Usuario {

    private String nombreUser;
    private String telefono;
    private String tipoPerro;
    private String raza;
    private boolean sexo;
    private String nombrePero;
    private int edadPerro;
    private String ciudad;
    private String descripcion;
    private String uid;
    private String email;

    public Usuario(String nombreUser, String telefono, String tipoPerro, String raza, boolean sexo, String nombrePero, int edadPerro, String ciudad, String descripcion, String uid, String email) {
        this.nombreUser = nombreUser;
        this.telefono = telefono;
        this.tipoPerro = tipoPerro;
        this.raza = raza;
        this.sexo = sexo;
        this.nombrePero = nombrePero;
        this.edadPerro = edadPerro;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.uid = uid;
        this.email = email;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoPerro() {
        return tipoPerro;
    }

    public void setTipoPerro(String tipoPerro) {
        this.tipoPerro = tipoPerro;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public String getNombrePero() {
        return nombrePero;
    }

    public void setNombrePero(String nombrePero) {
        this.nombrePero = nombrePero;
    }

    public int getEdadPerro() {
        return edadPerro;
    }

    public void setEdadPerro(int edadPerro) {
        this.edadPerro = edadPerro;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
