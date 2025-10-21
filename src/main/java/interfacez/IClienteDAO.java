package interfacez;

import Entidades.Cliente;
import java.util.List;

public interface IClienteDAO {
    void crear(Cliente cliente);
    Cliente buscarPorId(Long id);
    List<Cliente> listar(int limit); 
    void actualizar(Cliente cliente);
    void eliminar(Long id);
    
    List<Cliente> buscarPorPreferencia(String preferencia, int limit);
    List<Object[]> clientesConMayorGasto(int limit);
    List<Cliente> clientesSinPedidos(int limit);
    List<Cliente> clientesRecomendadosPor(Long clienteId, int limit);
}