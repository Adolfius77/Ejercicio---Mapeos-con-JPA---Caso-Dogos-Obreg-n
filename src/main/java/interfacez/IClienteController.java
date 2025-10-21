package interfacez;

import Entidades.Cliente;
import java.util.List;

/**
 * Interfaz para el Controlador de Clientes.
 * Define los métodos que la vista (main) puede llamar.
 */
public interface IClienteController {
   
    
    void crearCliente(Cliente cliente);
    Cliente getCliente(Long id);
    List<Cliente> getClientes(int limit);
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(Long id);
}