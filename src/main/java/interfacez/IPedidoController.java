package interfacez;

import Entidades.Pedido;
import java.util.List;

/**
 * Interfaz para el Controlador de Pedidos.
 */
public interface IPedidoController {
    
    void crearPedido(Pedido pedido);
    Pedido getPedido(Long id);
    List<Pedido> getPedidos(int limit);
    void actualizarPedido(Pedido pedido);
    void eliminarPedido(Long id);
}