package config;

import data.common.Constantes;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Getter
@Log4j2
@Singleton
public class Configuracion {

    private final String apiUrl;

    public Configuracion() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(Constantes.CONFIG_YAML);
        Map<String, Object> obj = yaml.load(inputStream);
        this.apiUrl = (String) obj.get(Constantes.API_URL);
    }
}