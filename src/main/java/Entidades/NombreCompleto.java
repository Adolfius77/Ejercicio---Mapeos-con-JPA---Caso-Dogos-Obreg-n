/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;


/**
 *
 * @author USER
 */
@Embeddable
public class NombreCompleto{

    @Column(name = "nombre",length = 50)
    private String nombre;
    
    @Column(name = "apellido_paterno", length = 50)
    private String apellido_paterno;
    
    @Column(name = "apellido_materno", length = 50)
    private String apellido_materno;

    public NombreCompleto() {
    }

    public NombreCompleto(String nombre, String apellido_paterno, String apellido_materno) {
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }
    
    
    
    
}
