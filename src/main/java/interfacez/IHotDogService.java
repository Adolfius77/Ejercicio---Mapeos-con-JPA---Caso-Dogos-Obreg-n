package interfacez;

import Entidades.HotDog;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz para la lógica de negocio de HotDogs.
 */
public interface IHotDogService {
    
    void crearHotDog(HotDog hotDog) throws Exception;
    HotDog buscarHotDogPorId(Long id);
    List<HotDog> listarHotDogs(int limit);
    void actualizarHotDog(HotDog hotDog) throws Exception;
    void eliminarHotDog(Long id) throws Exception;
    
    
    List<Object[]> hotDogsMasVendidos(int limit);
    List<HotDog> buscarPorRangoPrecio(BigDecimal min, BigDecimal max, int limit);
}