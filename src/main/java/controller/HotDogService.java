package controller;

import DAO.HotDogDAO;
import DAO.IHotDogDAO;
import Entidades.HotDog;
import interfacez.IHotDogService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementación de las reglas de negocio para HotDogs.
 */
public class HotDogService implements IHotDogService {

    private IHotDogDAO hotdogDAO;

    public HotDogService() {
        this.hotdogDAO = new HotDogDAO();
    }

    public HotDogService(IHotDogDAO hotdogDAO) {
        this.hotdogDAO = hotdogDAO;
    }

    @Override
    public void crearHotDog(HotDog hotDog) throws Exception {
        if (hotDog == null) {
            throw new Exception("El HotDog no puede ser nulo.");
        }
        if (hotDog.getNombre() == null || hotDog.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del HotDog es obligatorio.");
        }
        if (hotDog.getPrecio() == null || hotDog.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El precio debe ser mayor que cero.");
        }

        hotdogDAO.crear(hotDog);
    }

    @Override
    public HotDog buscarHotDogPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return hotdogDAO.buscarPorId(id);
    }

    @Override
    public List<HotDog> listarHotDogs(int limit) {
        int validLimit = (limit > 100 || limit <= 0) ? 100 : limit;
        return hotdogDAO.listar(validLimit);
    }

    @Override
    public void actualizarHotDog(HotDog hotDog) throws Exception {
        if (hotDog == null || hotDog.getId() == null) {
            throw new Exception("No se puede actualizar un HotDog sin ID.");
        }
        if (hotDog.getNombre() == null || hotDog.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del HotDog es obligatorio.");
        }
        if (hotDog.getPrecio() == null || hotDog.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El precio debe ser mayor que cero.");
        }

        hotdogDAO.actualizar(hotDog);
    }

    @Override
    public void eliminarHotDog(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("ID de HotDog no válido para eliminar.");
        }

        hotdogDAO.eliminar(id);
    }

    @Override
    public List<Object[]> hotDogsMasVendidos(int limit) {
        int validLimit = (limit > 100 || limit <= 0) ? 100 : limit;
        return hotdogDAO.hotDogsMasVendidos(validLimit);
    }

    @Override
    public List<HotDog> buscarPorRangoPrecio(BigDecimal min, BigDecimal max, int limit) {
        if (min == null || max == null || min.compareTo(max) > 0) {
            return List.of();
        }
        int validLimit = (limit > 100 || limit <= 0) ? 100 : limit;
        return hotdogDAO.buscarPorRangoPrecio(min, max, validLimit);
    }
}
