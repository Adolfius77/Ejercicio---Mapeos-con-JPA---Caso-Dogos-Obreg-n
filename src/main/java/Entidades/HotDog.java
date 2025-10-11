/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author USER
 */
@Entity
public class HotDog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Transient
    private BigDecimal iva;

    @OneToMany(mappedBy = "hotDog")
    private Set<PedidoHotDog> pedidosDondeAparece;

    public HotDog() {
    }

    public HotDog(Long id, String nombre, BigDecimal precio, BigDecimal iva, Set<PedidoHotDog> pedidosDondeAparece) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.iva = iva;
        this.pedidosDondeAparece = pedidosDondeAparece;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getIva() {
        if (this.precio != null) {
            return this.precio.multiply(new BigDecimal("0.16"));
        }
        return BigDecimal.ZERO;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Set<PedidoHotDog> getPedidosDondeAparece() {
        return pedidosDondeAparece;
    }

    public void setPedidosDondeAparece(Set<PedidoHotDog> pedidosDondeAparece) {
        this.pedidosDondeAparece = pedidosDondeAparece;
    }

}
