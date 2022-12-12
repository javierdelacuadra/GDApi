package ui.pantallas.common;

public enum Pantallas {
    PANTALLAMAIN("/fxml/PantallaMain.fxml"),
    PANTALLABUSQUEDA("/fxml/Busqueda.fxml"),
    PANTALLAUSERS("/fxml/ResultadoUsers.fxml"),
    PANTALLAINICIO("/fxml/Inicio.fxml"),
    PANTALLANIVELES("/fxml/ResultadoNiveles.fxml");

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
