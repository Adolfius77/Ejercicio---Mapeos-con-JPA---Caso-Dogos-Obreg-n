package DAO;

import Entidades.Cliente;
import Entidades.Pedido;
import java.math.BigDecimal;
import java.util.List;

public interface IPedidoDAO {

    void crear(Pedido pedido);

    Pedido buscarPorId(Long id);

    List<Pedido> listar(int limit);

    void actualizar(Pedido pedido);

    void eliminar(Long id);

    Double ticketPromedio();

    List<Object[]> pedidosPorMetodoPago();

    List<Pedido> pedidosConFiltros(int minArticulos, BigDecimal totalMinimo, int limit);

    Pedido ultimoPedidoPorCliente(Cliente cliente);
}
