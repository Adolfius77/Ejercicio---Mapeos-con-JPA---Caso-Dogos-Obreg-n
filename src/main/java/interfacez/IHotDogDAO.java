package DAO;

import Entidades.HotDog;
import java.math.BigDecimal;
import java.util.List;

public interface IHotDogDAO {

    void crear(HotDog hotDog);

    HotDog buscarPorId(Long id);

    List<HotDog> listar(int limit);

    void actualizar(HotDog hotDog);

    void eliminar(Long id);

    List<Object[]> hotDogsMasVendidos(int limit);

    List<HotDog> buscarPorRangoPrecio(BigDecimal min, BigDecimal max, int limit);

    List<HotDog> hotdogsSinVentas(int limit);
}
