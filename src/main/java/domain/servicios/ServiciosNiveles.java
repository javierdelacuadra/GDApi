package domain.servicios;

import data.DaoNiveles;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import domain.modelo.Nivel;

import java.util.List;

public class ServiciosNiveles {

    private final DaoNiveles dao;

    @Inject
    public ServiciosNiveles(DaoNiveles dao) {
        this.dao = dao;
    }

    public Either<String, List<Nivel>> getNivelesConFiltro(String text, String difficulty) {
        return dao.getNivelConFiltro(text, difficulty);
    }

    public Single<Either<String, List<Nivel>>> getNivelesSinFiltro(String text) {
        return dao.getNivelSinFiltro(text);
    }
}