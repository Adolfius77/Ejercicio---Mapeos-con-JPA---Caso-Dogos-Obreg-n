package interfacez;

import Entidades.Pedido;
import java.util.List;

/**
 * Interfaz para la lógica de negocio de Pedidos.
 */
public interface IPedidoService {
    
    void crearPedido(Pedido pedido) throws Exception;
    Pedido buscarPedidoPorId(Long id);
    List<Pedido> listarPedidos(int limit);
    void actualizarPedido(Pedido pedido) throws Exception;
    void eliminarPedido(Long id) throws Exception;
    
    
    Double getTicketPromedio();
}