package controller;

import DAO.HotDogDAO;
import DAO.IHotDogDAO;
import DAO.IPedidoDAO;
import DAO.PedidoDAO;
import Entidades.HotDog;
import Entidades.Pedido;
import Entidades.PedidoHotDog;
import interfacez.IPedidoService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementación de las reglas de negocio para Pedidos.
 */
public class PedidoService implements IPedidoService {

    private IPedidoDAO pedidoDAO;
    private IHotDogDAO hotdogDAO; 

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.hotdogDAO = new HotDogDAO();
    }
    
    public PedidoService(IPedidoDAO pedidoDAO, IHotDogDAO hotdogDAO) {
        this.pedidoDAO = pedidoDAO;
        this.hotdogDAO = hotdogDAO;
    }

    @Override
    public void crearPedido(Pedido pedido) throws Exception {
        if (pedido == null) {
            throw new Exception("El Pedido no puede ser nulo.");
        }
        if (pedido.getCliente() == null || pedido.getCliente().getNum_cliente() == null) {
            throw new Exception("El Pedido debe estar asociado a un cliente existente.");
        }
        if (pedido.getFecha() == null || pedido.getFecha().isAfter(LocalDate.now())) {
            throw new Exception("La fecha del pedido no puede ser futura.");
        }
        if (pedido.getDetalle() == null || pedido.getDetalle().isEmpty()) {
            throw new Exception("El pedido debe tener al menos un HotDog.");
        }
        
        for (PedidoHotDog phd : pedido.getDetalle()) {
            
            if (phd.getCantidad() == null || phd.getCantidad() <= 0) {
                throw new Exception("La cantidad de cada HotDog debe ser mayor a 0.");
            }
            
            HotDog hotDogDB = hotdogDAO.buscarPorId(phd.getHotDog().getId());
            if (hotDogDB == null) {
                throw new Exception("El HotDog con ID " + phd.getHotDog().getId() + " no existe.");
            }
            
           
            phd.setPrecioVenta(hotDogDB.getPrecio());
            
            
            if (phd.getSubtotal().compareTo(BigDecimal.ZERO) <= 0) {
                 throw new Exception("El subtotal calculado es inválido.");
            }
        }
        
        
        pedidoDAO.crear(pedido);
    }

    @Override
    public Pedido buscarPedidoPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return pedidoDAO.buscarPorId(id);
    }

    @Override
    public List<Pedido> listarPedidos(int limit) {
        int validLimit = (limit > 100 || limit <= 0) ? 100 : limit;
        return pedidoDAO.listar(validLimit);
    }

    @Override
    public void actualizarPedido(Pedido pedido) throws Exception {
        if (pedido == null || pedido.getId() == null) {
            throw new Exception("No se puede actualizar un Pedido sin ID.");
        }
        
        pedidoDAO.actualizar(pedido);
    }

    @Override
    public void eliminarPedido(Long id) throws Exception {
        if (id == null || id <= 0) {
             throw new Exception("ID de Pedido no válido para eliminar.");
        }
        pedidoDAO.eliminar(id);
    }

    @Override
    public Double getTicketPromedio() {
        return pedidoDAO.ticketPromedio();
    }
}