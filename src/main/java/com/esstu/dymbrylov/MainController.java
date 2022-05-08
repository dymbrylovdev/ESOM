package com.esstu.dymbrylov;



import com.esstu.dymbrylov.model.Material;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    private static final String insertImgName = "Выберите фото";
    private static String pathImg1 = "";
    private static String pathImg2 = "";
    private static String pathImg3 = "";
    private static String pathImg4 = "";

    @FXML
    public AnchorPane form_root;
    @FXML
    private Button add_dobavca_button;

    @FXML
    private Button add_material_button;

    @FXML
    private Label bdInfo;

    @FXML
    private Button button_img_1;

    @FXML
    private Button button_img_2;

    @FXML
    private Button button_img_3;

    @FXML
    private Button button_img_4;

    @FXML
    private MenuButton dobavca_select;

    @FXML
    private ImageView img_1;

    @FXML
    private ImageView img_2;

    @FXML
    private ImageView img_3;

    @FXML
    private ImageView img_4;

    @FXML
    private ComboBox<String> material_select;

    @FXML
    private TextField number_obrazca;

    @FXML
    private TextField procent;

    @FXML
    private Button save_form;

    @FXML
    private TextField sloi;




    private PreparedStatement preparedStatement;
    private Connection connect;
    private Statement statement;
    private ResultSet resultSet;

    public Connection ConnectorDB() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:maindb.sqlite");
        } catch (Exception exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at ");
            return null;
        }
        return connect;
    }

    public ObservableList<Material> getAllMaterial() {
        connect = ConnectorDB();
        ObservableList<Material> materials = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        String query = "SELECT * FROM material";
        try {
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Material material = new Material(id, name);
                materials.add(material);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return materials;
    }

    public void setListMaterial() {
        ObservableList<Material> materials = getAllMaterial();
        List<String> list = new LinkedList<>();
        for (Material material : materials) {
            list.add(material.getName());
        }
        ObservableList<String> dataList = FXCollections.observableArrayList(list);

        if (material_select != null) {
            material_select.setItems(dataList);
        }
    }

    public void clickBtnSaveForm(MouseEvent mouseEvent) {

    }
    public void clickBtnAddMaterial() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Parent popContent = FXMLLoader.load(getClass().getResource("material-view.fxml"));
        Scene scene = new Scene(popContent);
        window.setScene(scene);
        window.setOnCloseRequest(action -> {
            setListMaterial();
        });
        window.showAndWait();
    }


    public void setImag(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        FileChooser open = new FileChooser();
        Stage stage = (Stage) form_root.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath();
            path = path.replace("\\", "\\\\");
            Image image = new Image(file.toURI().toString(), 200.0, 180.0, false, true);
            button.setText("");
            switch (button.getId()){
                case "button_img_1":
                    this.img_1.setImage(image);
                    pathImg1 = path;
                    break;
                case "button_img_2":
                    this.img_2.setImage(image);
                    pathImg2 = path;
                    break;
                case "button_img_3":
                    this.img_3.setImage(image);
                    pathImg3 = path;
                    break;
                case "button_img_4":
                    this.img_4.setImage(image);
                    pathImg4 = path;
                    break;
            }

        } else {
            System.out.println("NO DATA EXIST!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListMaterial();
        if (ConnectorDB() != null) {
            bdInfo.setText("База данных подключена");
            bdInfo.setStyle("-fx-text-fill: green");
        } else {
            bdInfo.setText("База данных не подключена");
            bdInfo.setStyle("-fx-text-fill: red");
        }

    }


}