/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author USER
 */
@Entity
public class PedidoHotDog implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private PedidoHotDogId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pedidoId")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hotDogId")
    private HotDog hotDog;

    @Column(nullable = false)
    private Integer cantidad;

    @Transient
    private BigDecimal subtotal;

    @Column(name = "precio_venta", precision = 10, scale = 2)
    private BigDecimal precioVenta;

    public PedidoHotDog() {
    }

    public PedidoHotDog(PedidoHotDogId id, Pedido pedido, HotDog hotDog, Integer cantidad, BigDecimal subtotal, BigDecimal precioVenta) {
        this.id = id;
        this.pedido = pedido;
        this.hotDog = hotDog;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.precioVenta = precioVenta;
    }

    public PedidoHotDogId getId() {
        return id;
    }

    public void setId(PedidoHotDogId id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public HotDog getHotDog() {
        return hotDog;
    }

    public void setHotDog(HotDog hotDog) {
        this.hotDog = hotDog;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        if (this.precioVenta != null && this.cantidad != null) {
            return this.precioVenta.multiply(new BigDecimal(this.cantidad));
        }
        return BigDecimal.ZERO;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

}
