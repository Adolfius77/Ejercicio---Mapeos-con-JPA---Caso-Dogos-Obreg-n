package controller;

import Entidades.Pedido;
import interfacez.IPedidoController;
import interfacez.IPedidoService;
import java.util.List;

/**
 * Implementación del Controlador de Pedido.
 */
public class PedidoController implements IPedidoController {

    private IPedidoService pedidoService;

    public PedidoController() {
        this.pedidoService = new PedidoService();
    }
    
    public PedidoController(IPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Override
    public void crearPedido(Pedido pedido) {
        try {
            pedidoService.crearPedido(pedido);
            System.out.println("Pedido creado exitosamente con ID: " + pedido.getId());
        } catch (Exception e) {
            System.err.println("Error al crear Pedido: " + e.getMessage());
            if (e.getCause() != null) {
                 System.err.println("Causa: " + e.getCause().getMessage());
            }
        }
    }

    @Override
    public Pedido getPedido(Long id) {
        try {
            Pedido p = pedidoService.buscarPedidoPorId(id);
            if (p == null) {
                System.out.println("Pedido con ID " + id + " no encontrado.");
            }
            return p;
        } catch (Exception e) {
            System.err.println("Error al buscar Pedido: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Pedido> getPedidos(int limit) {
        try {
            return pedidoService.listarPedidos(limit);
        } catch (Exception e) {
            System.err.println("Error al listar Pedidos: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public void actualizarPedido(Pedido pedido) {
        try {
            pedidoService.actualizarPedido(pedido);
            System.out.println("Pedido actualizado exitosamente: " + pedido.getId());
        } catch (Exception e) {
            System.err.println("Error al actualizar Pedido: " + e.getMessage());
        }
    }

    @Override
    public void eliminarPedido(Long id) {
        try {
            pedidoService.eliminarPedido(id);
            System.out.println("Pedido con ID " + id + " eliminado.");
        } catch (Exception e) {
            System.err.println("Error al eliminar Pedido: " + e.getMessage());
        }
    }
}