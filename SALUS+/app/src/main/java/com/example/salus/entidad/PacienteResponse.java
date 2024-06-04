package com.example.salus.entidad;

public class PacienteResponse {
    private int id;
    private String dni_paciente;
    private String nombre;
    private String apellido;
    private String email;
    private String clave;
    private String telefono;
    private int pacienteUser;

    public PacienteResponse(int id, String dni_paciente, String nombre, String apellido, String email, String clave, String telefono, int pacienteUser) {
        this.id = id;
        this.dni_paciente = dni_paciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
        this.pacienteUser = pacienteUser;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni_paciente() {
        return dni_paciente;
    }

    public void setDni_paciente(String dni_paciente) {
        this.dni_paciente = dni_paciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getPacienteUser() {
        return pacienteUser;
    }

    public void setPacienteUser(int pacienteUser) {
        this.pacienteUser = pacienteUser;
    }
}
