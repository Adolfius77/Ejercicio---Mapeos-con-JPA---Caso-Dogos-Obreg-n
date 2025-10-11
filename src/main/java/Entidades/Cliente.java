/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

/**
 *
 * @author USER
 */
@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "num_cliente")
    private Long num_cliente;

    @Embedded
    private NombreCompleto nombreCompleto;

    @Column(name = "fch_nac")
    private LocalDate fch_nac;

    @Transient
    private Integer edad;

    @ElementCollection
    @CollectionTable(name = "cliente_telefonos", joinColumns = @JoinColumn(name = "num_cliente")) // <-- Tabla para teléfonos
    @Column(name = "telefono")
    private List<String> telefonos;

    @ElementCollection
    @CollectionTable(name = "cliente_preferencias", joinColumns = @JoinColumn(name = "num_cliente")) // <-- CORRECCIÓN: Tabla diferente para preferencias
    @Column(name = "preferencia")
    private List<String> preferencias;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pedido> pedidos;

    public Cliente() {
    }

    public Cliente(Long num_cliente, NombreCompleto nombreCompleto, LocalDate fch_nac, Integer edad, List<String> telefonos, List<String> preferencias, Set<Pedido> pedidos) {
        this.num_cliente = num_cliente;
        this.nombreCompleto = nombreCompleto;
        this.fch_nac = fch_nac;
        this.edad = edad;
        this.telefonos = telefonos;
        this.preferencias = preferencias;
        this.pedidos = pedidos;
    }

    public Long getNum_cliente() {
        return num_cliente;
    }

    public void setNum_cliente(Long num_cliente) {
        this.num_cliente = num_cliente;
    }

    public NombreCompleto getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(NombreCompleto nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFch_nac() {
        return fch_nac;
    }

    public void setFch_nac(LocalDate fch_nac) {
        this.fch_nac = fch_nac;
    }

    public Integer getEdad() {
        if (this.fch_nac != null) {
            return Period.between(this.fch_nac, LocalDate.now()).getYears();
        }
        return 0;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }

    public List<String> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(List<String> preferencias) {
        this.preferencias = preferencias;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

}
