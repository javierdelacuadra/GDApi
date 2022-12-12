package data;

import data.common.Constantes;
import data.retrofit.GDApi;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import domain.modelo.ResponseUser;
import domain.modelo.Usuario;
import retrofit2.Response;

import java.io.IOException;

@Log4j2
public class DaoUsers {

    private final GDApi gdApi;

    @Inject
    public DaoUsers(GDApi gdApi) {
        this.gdApi = gdApi;
    }

    public Either<String, Usuario> getUser(String username) {
        try {
            Response<ResponseUser> response = gdApi.getUser(username).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                Usuario usuario = new Usuario(response.body().getUsername(), response.body().getStars(), response.body().getDiamonds(), response.body().getCoins(), response.body().getUserCoins(), response.body().getDemons(), response.body().getCp());
                return Either.right(usuario).mapLeft(Object::toString);
            } else {
                assert response.errorBody() != null;
                if (response.errorBody().string().equals(Constantes.SERVER_BAD_RESPONSE)) {
                    return Either.left(Constantes.NO_SE_HA_ENCONTRADO_EL_USUARIO);
                } else {
                    return Either.left(Constantes.ERROR_DESCONOCIDO);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(e.getMessage());
        }
    }
}