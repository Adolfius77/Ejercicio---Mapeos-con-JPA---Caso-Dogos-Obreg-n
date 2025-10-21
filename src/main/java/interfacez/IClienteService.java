package interfacez;

import Entidades.Cliente;
import java.util.List;


public interface IClienteService {
    
    
    
    void crearCliente(Cliente cliente) throws Exception;
    Cliente buscarClientePorId(Long id);
    List<Cliente> listarClientes(int limit);
    void actualizarCliente(Cliente cliente) throws Exception;
    void eliminarCliente(Long id) throws Exception;
    
    List<Cliente> buscarPorPreferencia(String preferencia, int limit);
}