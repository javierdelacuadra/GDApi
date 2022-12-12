package ui.pantallas.pantallabusqueda;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.modelo.Nivel;
import domain.modelo.Usuario;
import domain.servicios.ServiciosNiveles;
import domain.servicios.ServiciosUsers;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BusquedaViewModel {

    private final ServiciosNiveles serviciosNiveles;
    private final ServiciosUsers serviciosUsers;

    @Inject
    public BusquedaViewModel(ServiciosNiveles serviciosNiveles, ServiciosUsers serviciosUsers) {
        this.serviciosNiveles = serviciosNiveles;
        this.serviciosUsers = serviciosUsers;
    }


    public Either<String, List<Nivel>> getNivelesSinFiltro(String text) {
        AtomicReference<Either<String, List<Nivel>>> respuesta = new AtomicReference<>();
        serviciosNiveles.getNivelesSinFiltro(text)
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft()) {
                        respuesta.set(Either.left(either.getLeft()));
                    } else {
                        respuesta.set(Either.right(either.get()));
                    }
                });
        return respuesta.get();
    }

    public Either<String, List<Nivel>> getNivelesConFiltro(String text, String diff) {
        return serviciosNiveles.getNivelesConFiltro(text, diff);
    }

    public Either<String, Usuario> getUser(String text) {
        return serviciosUsers.getUser(text);
    }
}
