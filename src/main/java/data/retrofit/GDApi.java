package data.retrofit;

import data.common.Constantes;
import io.reactivex.rxjava3.core.Single;
import domain.modelo.ResponseLevelsItem;
import domain.modelo.ResponseUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface GDApi {

    @GET(Constantes.LEVELS_URL)
    Call<List<ResponseLevelsItem>> getNiveles(@Path(Constantes.TEXT) String text,
                                              @Query(Constantes.DIFF) String difficulty);

    @GET(Constantes.LEVELS_URL)
    Single<List<ResponseLevelsItem>> getNiveles(@Path(Constantes.TEXT) String text);

    @GET(Constantes.PROFILE_URL)
    Call<ResponseUser> getUser(@Path(Constantes.USERNAME) String username);
}