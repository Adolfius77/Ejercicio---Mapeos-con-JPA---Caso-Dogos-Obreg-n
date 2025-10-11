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
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author USER
 */
@Entity
public class PedidoHotDogId implements Serializable {

    @Column (name = "pedido_id")
    private Long pedidoID;
    
    @Column(name = "hotDog_id")
    private Long hotDogID;

    public PedidoHotDogId() {
    }

    public PedidoHotDogId(Long pedidoID, Long hotDogID) {
        this.pedidoID = pedidoID;
        this.hotDogID = hotDogID;
    }

    public Long getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(Long pedidoID) {
        this.pedidoID = pedidoID;
    }

    public Long getHotDogID() {
        return hotDogID;
    }

    public void setHotDogID(Long hotDogID) {
        this.hotDogID = hotDogID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.pedidoID);
        hash = 67 * hash + Objects.hashCode(this.hotDogID);
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
        if (!Objects.equals(this.pedidoID, other.pedidoID)) {
            return false;
        }
        return Objects.equals(this.hotDogID, other.hotDogID);
    }
    
    
   
    
}
