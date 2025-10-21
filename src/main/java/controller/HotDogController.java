package controller;

import Entidades.HotDog;
import interfacez.IHotDogController;
import interfacez.IHotDogService;
import java.util.List;


/**
 * Implementación del Controlador de HotDog.
 */
public class HotDogController implements IHotDogController {

    private IHotDogService hotdogService;

    public HotDogController() {
        this.hotdogService = new HotDogService();
    }
    
    public HotDogController(IHotDogService hotdogService) {
        this.hotdogService = hotdogService;
    }

    @Override
    public void crearHotDog(HotDog hotDog) {
        try {
            hotdogService.crearHotDog(hotDog);
            System.out.println("HotDog creado exitosamente: " + hotDog.getNombre());
        } catch (Exception e) {
            System.err.println("Error al crear HotDog: " + e.getMessage());
        }
    }

    @Override
    public HotDog getHotDog(Long id) {
        try {
            HotDog h = hotdogService.buscarHotDogPorId(id);
            if (h == null) {
                System.out.println("HotDog con ID " + id + " no encontrado.");
            }
            return h;
        } catch (Exception e) {
            System.err.println("Error al buscar HotDog: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<HotDog> getHotDogs(int limit) {
        try {
            return hotdogService.listarHotDogs(limit);
        } catch (Exception e) {
            System.err.println("Error al listar HotDogs: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public void actualizarHotDog(HotDog hotDog) {
        try {
            hotdogService.actualizarHotDog(hotDog);
            System.out.println("HotDog actualizado exitosamente: " + hotDog.getId());
        } catch (Exception e) {
            System.err.println("Error al actualizar HotDog: " + e.getMessage());
        }
    }

    @Override
    public void eliminarHotDog(Long id) {
        try {
            hotdogService.eliminarHotDog(id);
            System.out.println("HotDog con ID " + id + " eliminado.");
        } catch (Exception e) {
            System.err.println("Error al eliminar HotDog: " + e.getMessage());
        }
    }
}