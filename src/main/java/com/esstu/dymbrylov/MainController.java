package com.esstu.dymbrylov;

import com.esstu.dymbrylov.controllers.AdditiveController;
import com.esstu.dymbrylov.controllers.MaterialController;
import com.esstu.dymbrylov.controllers.ModalController;
import com.esstu.dymbrylov.model.Additive;
import com.esstu.dymbrylov.model.Material;
import com.esstu.dymbrylov.model.Samples;
import com.esstu.dymbrylov.services.AdditiveService;
import com.esstu.dymbrylov.utils.CreateFileImg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController extends AdditiveService implements Initializable {

    @FXML
    public TableView<DataTable> formTableView;

    @FXML
    public Pagination dataPagination;

    @FXML
    private TableColumn<DataTable, String> id_table;

    @FXML
    private TableColumn<DataTable, Integer> id_material_table;

    @FXML
    private TableColumn<DataTable, Integer> id_additive_table;

    @FXML
    private TableColumn<DataTable, String> layer_count_table;

    @FXML
    private TableColumn<DataTable, String> percent_table;

    MaterialController materialController = new MaterialController();
    AdditiveController additiveController = new AdditiveController();
    ModalController modalWindow = new ModalController();


    CreateFileImg createFileImg = new CreateFileImg();
    private Map<String,String> mapPathImg = new HashMap<>();
    private static final String insertImgName = "Выберите фото";
    public MainController() {
        mapPathImg.put("pathImg1","");
        mapPathImg.put("pathImg2","");
        mapPathImg.put("pathImg3","");
        mapPathImg.put("pathImg4","");
    }


    @FXML
    public TabPane root;
    @FXML
    public Tab form_root;
    @FXML
    public AnchorPane form_pane;
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
    private ComboBox<String> additive_select;

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
    private TextField id;

    @FXML
    private TextField procent;

    @FXML
    private Button save_form;

    @FXML
    private TextField layer_count;

    @FXML
    private Tab data_tab;

    // Добавляет данные материала
    public void setListMaterial() {
        ObservableList<Material> materials = materialController.getAllMaterial();
        List<String> list = new LinkedList<>();
        for (Material material : materials) {
            list.add(material.getName());
        }
        ObservableList<String> dataList = FXCollections.observableArrayList(list);

        if (material_select != null) {
            material_select.setItems(dataList);
        }
    }

    // Добавляет данные добавки
    public void setListAdditive() {
        ObservableList<Additive> additives = additiveController.getAllAdditive();
        List<String> list = new LinkedList<>();
        for (Additive additive : additives) {
            list.add(additive.getName());
        }
        ObservableList<String> dataList = FXCollections.observableArrayList(list);

        if (additive_select != null) {
            additive_select.setItems(dataList);
        }
    }


    // Модальное окно для добавления материала
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

    // Модальное окно для добавления добавки
    public void clickBtnAddAdditive() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Parent popContent = FXMLLoader.load(getClass().getResource("additive-view.fxml"));
        Scene scene = new Scene(popContent);
        window.setScene(scene);
        window.setOnCloseRequest(action -> {
            setListAdditive();
        });
        window.showAndWait();
    }

    // Вставить фото
    public void setImag(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        FileChooser open = new FileChooser();
        Stage stage = (Stage) form_pane.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath();
            path = path.replace("\\", "\\\\");
            Image image = new Image(file.toURI().toString(), 200, 180.0, false, true);
            button.setText("");
            switch (button.getId()){
                case "button_img_1":
                    this.img_1.setImage(image);
                    mapPathImg.put("pathImg1", path);
                    break;
                case "button_img_2":
                    this.img_2.setImage(image);
                    mapPathImg.put("pathImg2", path);
                    break;
                case "button_img_3":
                    this.img_3.setImage(image);
                    mapPathImg.put("pathImg3", path);
                    break;
                case "button_img_4":
                    this.img_4.setImage(image);
                    mapPathImg.put("pathImg4", path);
                    break;
            }
        } else {
            System.out.println("NO DATA EXIST!");
        }
    }

    // Сохраниение формы
    public void clickBtnSaveForm() {
        Integer id_material = materialController.getMaterialByName(material_select.getValue());
        Integer id_additive = additiveController.getAdditiveByName(additive_select.getValue());
        for (Map.Entry<String, String> entry: mapPathImg.entrySet()){
            String newValue = createFileImg.saveImgInFolder(entry.getValue()).getValue();
            mapPathImg.put(entry.getKey(),newValue);
        }
        Samples samples = new Samples(
                id.getText(), id_material, id_additive,
                layer_count.getText(), procent.getText(),
                mapPathImg.get("pathImg1"), mapPathImg.get("pathImg2"),
                mapPathImg.get("pathImg3"), mapPathImg.get("pathImg4")
        );
        Map.Entry<Boolean, String> response = setFormData(samples);
        if (response.getKey()) {
            modalWindow.showAlertInformation(response.getValue());
        } else {
            modalWindow.showAlertWarning(response.getValue());
        }

    }
    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 5;
    }

    public VBox createPage(int pageIndex) {
        ObservableList<DataTable> data = getAllSamples();
        int lastIndex = 0;
        int displace = data.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage();
        } else {
            lastIndex = data.size() / rowsPerPage() - 1;

        }

        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();

        for (int i = page; i < page + itemsPerPage(); i++) {
//            TableView<Person> table = new TableView<Person>();
            id_table.setCellValueFactory(new PropertyValueFactory<DataTable, String>("id"));
            id_material_table.setCellValueFactory(new PropertyValueFactory<DataTable, Integer>("id_material"));
            id_additive_table.setCellValueFactory(new PropertyValueFactory<DataTable, Integer>("id_additive"));
            layer_count_table.setCellValueFactory(new PropertyValueFactory<DataTable, String>("layer_count"));
            percent_table.setCellValueFactory(new PropertyValueFactory<DataTable, String>("percent"));

            if (lastIndex == pageIndex) {
                formTableView.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            } else {
                formTableView.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }

            box.getChildren().add(formTableView);
        }
        return box;

    }


    public void testclick(ActionEvent actionEvent) {
        SingleSelectionModel<Tab> selectionModel = root.getSelectionModel();
        selectionModel.select(data_tab);

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataPagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                if (pageIndex > getAllSamples().size() / rowsPerPage() + 1) {
                    return null;
                } else {
                    return createPage(pageIndex);
                }
            }
        });


        setListMaterial();
        setListAdditive();
        if (materialController.ConnectorDB() != null) {
            bdInfo.setText("База данных подключена");
            bdInfo.setStyle("-fx-text-fill: green");
        } else {
            bdInfo.setText("База данных не подключена");
            bdInfo.setStyle("-fx-text-fill: red");
        }

    }


}