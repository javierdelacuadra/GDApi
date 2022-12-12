package ui.pantallas.pantallamain;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;
import domain.modelo.Nivel;
import domain.modelo.Usuario;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;
import ui.pantallas.common.Pantallas;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j2
public class MainController extends BasePantallaController implements Initializable {

    Instance<Object> instance;

    @Inject
    public MainController(Instance<Object> instance) {
        this.instance = instance;
    }

    @FXML
    private Stage primaryStage;

    @FXML
    private HBox header;

    double xOffset = 0;
    double yOffset = 0;

    @FXML
    public BorderPane root;

    @FXML
    private ImageView botonClose;

    public List<Nivel> levels;

    public List<Nivel> getResponseLevels() {
        return levels;
    }

    public void setResponseLevels(List<Nivel> responseLevels) {
        this.levels = responseLevels;
    }

    public Usuario usuario;

    public Usuario getResponseUser() {
        return usuario;
    }

    public void setResponseUser(Usuario responseUser) {
        this.usuario = responseUser;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.CLOSE_BUTTON)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            botonClose.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        botonClose.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(ConstantesPantallas.SALIR);
            alert.setHeaderText(ConstantesPantallas.ESTAS_SEGURO_DE_QUE_QUIERES_SALIR);
            alert.setContentText(ConstantesPantallas.SI_SALES_PERDERAS_TODO_TU_PROGRESO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
        });
        header.setOnMousePressed(mouseEvent -> {
            xOffset = primaryStage.getX() - mouseEvent.getScreenX();
            yOffset = primaryStage.getY() - mouseEvent.getScreenY();
        });
        header.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() + xOffset);
            primaryStage.setY(mouseEvent.getScreenY() + yOffset);
        });
    }

    private void closeWindowEvent(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle(ConstantesPantallas.SALIR_DE_LA_APLICACION);
        alert.setContentText(ConstantesPantallas.ESTA_SEGURO_QUE_DESEA_SALIR_DE_LA_APLICACION_PODRIAN_PERDERSE_LOS_DATOS_DE_ESTA_SESION);
        alert.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.CANCEL) {
                event.consume();
            }
        });
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        cargarPantalla(Pantallas.PANTALLAINICIO);
    }

    private void cargarPantalla(Pantallas pantalla) {
        Pane panePantalla;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(pantalla.getRuta()));
            BasePantallaController pantallaController = fxmlLoader.getController();
            pantallaController.setMainController(this);
            pantallaController.principalCargado();
            root.setCenter(panePantalla);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void cargarPantallaBusqueda() {
        cargarPantalla(Pantallas.PANTALLABUSQUEDA);
    }

    public void cargarPantallaUsers() {
        cargarPantalla(Pantallas.PANTALLAUSERS);
    }

    public void cargarPantallaNiveles() {
        cargarPantalla(Pantallas.PANTALLANIVELES);
    }

    public void cargarPantallaInicio() {
        cargarPantalla(Pantallas.PANTALLAINICIO);
    }

    public void crearAlertError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ConstantesPantallas.ERROR);
        alert.setHeaderText(ConstantesPantallas.ERROR);
        alert.setContentText(error);
        alert.showAndWait();
    }
}
