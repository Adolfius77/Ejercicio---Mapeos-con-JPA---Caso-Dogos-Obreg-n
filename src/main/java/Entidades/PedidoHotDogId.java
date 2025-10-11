/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author USER
 */
@Embeddable
public class PedidoHotDogId implements Serializable {

    @Column (name = "pedido_id")
    private Long pedidoId;
    
    @Column(name = "hotDog_id")
    private Long hotDogId;

    public PedidoHotDogId() {
    }

    public PedidoHotDogId(Long pedidoId, Long hotDogId) {
        this.pedidoId = pedidoId;
        this.hotDogId = hotDogId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getHotDogId() {
        return hotDogId;
    }

    public void setHotDogId(Long hotDogId) {
        this.hotDogId = hotDogId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.pedidoId);
        hash = 71 * hash + Objects.hashCode(this.hotDogId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PedidoHotDogId other = (PedidoHotDogId) obj;
        if (!Objects.equals(this.pedidoId, other.pedidoId)) {
            return false;
        }
        return Objects.equals(this.hotDogId, other.hotDogId);
    }

    
    
   
    
}
