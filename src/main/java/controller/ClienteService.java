package controller;

import DAO.ClienteDAO;
import Entidades.Cliente;
import interfacez.IClienteDAO;
import interfacez.IClienteService;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación de las reglas de negocio para Clientes.
 */
public class ClienteService implements IClienteService {

    private IClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public ClienteService(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void crearCliente(Cliente cliente) throws Exception {

        if (cliente == null) {
            throw new Exception("El cliente no puede ser nulo.");
        }
        if (cliente.getNombreCompleto() == null
                || cliente.getNombreCompleto().getNombre() == null
                || cliente.getNombreCompleto().getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del cliente es obligatorio.");
        }

        if (cliente.getFch_nac() != null && cliente.getFch_nac().isAfter(LocalDate.now())) {
            throw new Exception("La fecha de nacimiento no puede ser futura.");
        }

        if (cliente.getTelefonos() == null || cliente.getTelefonos().isEmpty()) {
            throw new Exception("Debe proporcionar al menos un teléfono.");
        }

        clienteDAO.crear(cliente);
    }

    @Override
    public Cliente buscarClientePorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return clienteDAO.buscarPorId(id);
    }

    @Override
    public List<Cliente> listarClientes(int limit) {
        int validLimit = (limit > 100 || limit <= 0) ? 100 : limit;
        return clienteDAO.listar(validLimit);
    }

    @Override
    public void actualizarCliente(Cliente cliente) throws Exception {
        if (cliente == null || cliente.getNum_cliente() == null) {
            throw new Exception("No se puede actualizar un cliente sin ID.");
        }
        if (cliente.getNombreCompleto() == null
                || cliente.getNombreCompleto().getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del cliente es obligatorio.");
        }

        clienteDAO.actualizar(cliente);
    }

    @Override
    public void eliminarCliente(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("ID de cliente no válido para eliminar.");
        }
        clienteDAO.eliminar(id);
    }

    @Override
    public List<Cliente> buscarPorPreferencia(String preferencia, int limit) {
        if (preferencia == null || preferencia.trim().isEmpty()) {
            return List.of();
        }
        int validLimit = (limit > 100 || limit <= 0) ? 100 : limit;
        return clienteDAO.buscarPorPreferencia(preferencia, validLimit);
    }
}
