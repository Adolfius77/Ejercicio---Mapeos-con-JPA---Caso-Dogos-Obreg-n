package controller;

import Entidades.Cliente;
import interfacez.IClienteController;
import interfacez.IClienteService;
import java.util.List;

public class ClienteController implements IClienteController {

    private IClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService();
    }

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public void crearCliente(Cliente cliente) {
        try {
            clienteService.crearCliente(cliente);
            System.out.println("Cliente creado exitosamente: " + cliente.getNombreCompleto().getNombre());
        } catch (Exception e) {

            System.err.println("Error al crear cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente getCliente(Long id) {
        try {
            Cliente c = clienteService.buscarClientePorId(id);
            if (c == null) {
                System.out.println("Cliente con ID " + id + " no encontrado.");
            }
            return c;
        } catch (Exception e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Cliente> getClientes(int limit) {
        try {
            return clienteService.listarClientes(limit);
        } catch (Exception e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        try {
            clienteService.actualizarCliente(cliente);
            System.out.println("Cliente actualizado exitosamente: " + cliente.getNum_cliente());
        } catch (Exception e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarCliente(Long id) {
        try {
            clienteService.eliminarCliente(id);
            System.out.println("Cliente con ID " + id + " eliminado.");
        } catch (Exception e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
}
