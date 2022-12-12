package data;

import data.common.Constantes;
import data.retrofit.GDApi;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import domain.modelo.Nivel;
import domain.modelo.ResponseLevelsItem;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public class DaoNiveles {

    private final GDApi gdApi;

    @Inject
    public DaoNiveles(GDApi gdApi) {
        this.gdApi = gdApi;
    }

    public Single<Either<String, List<Nivel>>> getNivelSinFiltro(String text) {

        return gdApi.getNiveles(text)
                .map(responseLevelsItems -> {
                    List<Nivel> niveles = new ArrayList<>();
                    Nivel nivel;
                    for (ResponseLevelsItem responseLevelsItem : responseLevelsItems) {
                        nivel = new Nivel(responseLevelsItem.getName(), responseLevelsItem.getId(), responseLevelsItem.getAuthor(), responseLevelsItem.getDifficulty(), responseLevelsItem.getLength());
                        niveles.add(nivel);
                    }
                    return Either.right(niveles).mapLeft(Object::toString);
                })
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, List<Nivel>> error = Either.left(throwable.getMessage());
                    if (throwable instanceof HttpException httpException) {
                        try (ResponseBody errorBody = Objects.requireNonNull(httpException.response()).errorBody()) {
                            assert errorBody != null;
                            if (Objects.equals(errorBody.contentType(), MediaType.get(Constantes.APPLICATION_JSON))) {
                                error = Either.left(httpException.getMessage());
                            } else {
                                error = Either.left(Objects.requireNonNull(httpException.response()).message());
                            }
                        }
                    }
                    return error;
                });
    }

    public Either<String, List<Nivel>> getNivelConFiltro(String text, String difficulty) {
        try {
            Response<List<ResponseLevelsItem>> response = gdApi.getNiveles(text, difficulty).execute();
            if (response.isSuccessful()) {
                List<Nivel> niveles = new ArrayList<>();
                Nivel nivel;
                assert response.body() != null;
                for (ResponseLevelsItem responseLevelsItem : response.body()) {
                    nivel = new Nivel(responseLevelsItem.getName(), responseLevelsItem.getId(), responseLevelsItem.getAuthor(), responseLevelsItem.getDifficulty(), responseLevelsItem.getLength());
                    niveles.add(nivel);
                }
                return Either.right(niveles).mapLeft(Object::toString);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constantes.ERROR_NO_SE_PUDO_CONECTAR_CON_EL_SERVIDOR);
        }
        return Either.left(Constantes.ERROR_NO_SE_PUDO_CONECTAR_CON_EL_SERVIDOR);
    }
}