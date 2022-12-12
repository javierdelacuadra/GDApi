package ui.pantallas.pantallaniveles;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import domain.modelo.Nivel;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class ResultadoNivelesController extends BasePantallaController implements Initializable {

    @FXML
    private ImageView backButton;

    @FXML
    private TableView<Nivel> table;

    @FXML
    private TableColumn<Nivel, String> name;

    @FXML
    private TableColumn<Nivel, String> id;

    @FXML
    private TableColumn<Nivel, String> author;

    @FXML
    private TableColumn<Nivel, String> difficulty;

    @FXML
    private TableColumn<Nivel, String> length;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.BACK_BUTTON)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            backButton.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        name.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NAME));
        id.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.ID));
        author.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.AUTHOR));
        difficulty.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.DIFFICULTY));
        length.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.LENGTH));
    }

    @Override
    public void principalCargado() {
        table.getItems().clear();
        List<Nivel> levels = getMainController().getResponseLevels();
        table.getItems().addAll(levels);
    }

    public void volverAtras() {
        this.getMainController().cargarPantallaBusqueda();
    }
}