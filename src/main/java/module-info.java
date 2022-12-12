module GDApi {
    requires lombok;
    requires jakarta.jakartaee.web.api;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires MaterialFX;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires io.vavr;
    requires org.yaml.snakeyaml;
    requires io.reactivex.rxjava3;
    requires org.pdfsam.rxjavafx;
    requires retrofit2.adapter.rxjava3;

    exports ui to javafx.graphics;
    exports data;
    exports ui.pantallas.pantallabusqueda;
    exports domain.modelo;
    exports domain.servicios;
    exports di;
    exports config;
    exports data.retrofit;
    exports ui.pantallas.pantallaniveles;
    exports ui.pantallas.pantallausers;
    exports ui.pantallas.pantallamain;
    exports ui.pantallas.common;
    exports ui.pantallas.pantallainicio;

    opens ui;
    opens config;
    opens ui.pantallas.common;
    opens ui.pantallas.pantallamain;
    opens ui.pantallas.pantallainicio;
    opens ui.pantallas.pantallabusqueda;
    opens ui.pantallas.pantallaniveles;
    opens ui.pantallas.pantallausers;
    opens data.retrofit;
    opens data.common;
    opens domain.modelo;
    opens domain.servicios;
}