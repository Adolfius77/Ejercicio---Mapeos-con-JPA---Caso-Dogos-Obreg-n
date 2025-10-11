/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author USER
 */
@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "fecha",nullable = false)
    private LocalDate fecha;
    
    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;
    
    @Transient
    private BigDecimal totalPagar;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_num_cliente", nullable = false)
    private Cliente cliente;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PedidoHotDog> detalle;

    public Pedido() {
    }

    public Pedido(Long id, LocalDate fecha, String metodoPago, BigDecimal totalPagar, Cliente cliente, Set<PedidoHotDog> detalle) {
        this.id = id;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.totalPagar = totalPagar;
        this.cliente = cliente;
        this.detalle = detalle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<PedidoHotDog> getDetalle() {
        return detalle;
    }

    public void setDetalle(Set<PedidoHotDog> detalle) {
        this.detalle = detalle;
    }
    
    
    
    
    
}
