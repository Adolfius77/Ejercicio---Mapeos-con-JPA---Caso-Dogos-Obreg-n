package interfacez;

import Entidades.HotDog;
import java.util.List;

public interface IHotDogController {
    
    void crearHotDog(HotDog hotDog);
    HotDog getHotDog(Long id);
    List<HotDog> getHotDogs(int limit);
    void actualizarHotDog(HotDog hotDog);
    void eliminarHotDog(Long id);
}