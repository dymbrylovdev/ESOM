package com.esstu.dymbrylov;

import com.esstu.dymbrylov.controllers.AdditiveController;
import com.esstu.dymbrylov.controllers.MaterialController;
import com.esstu.dymbrylov.controllers.ModalController;
import com.esstu.dymbrylov.model.Additive;
import com.esstu.dymbrylov.model.Material;
import com.esstu.dymbrylov.model.Samples;
import com.esstu.dymbrylov.services.MainService;
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
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class MainController extends MainService implements Initializable {
    @FXML
    public Button btn_clean_form;
    int from = 0, to = 0;
    int itemPerPage = 4;

    @FXML
    private Button delete_additive_button;

    @FXML
    private Button delete_material_button;

    @FXML
    public Button btn_cancel_update_form;
    @FXML
    public Button btn_update_item;
    @FXML
    public TableColumn count;
    @FXML
    public TableColumn photoAfter;
    @FXML
    public TableColumn photoBefore;
    @FXML
    public TableColumn photoAfterTest;
    @FXML
    public TableColumn photoReverse;
    @FXML
    public Pagination pagination;

    @FXML
    public TableView formTableView;

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
    private Map<String, String> mapPathImg = new HashMap<>();
    private static final String insertImgName = "Выберите фото";

    public MainController() {
        mapPathImg.put("pathImg1", null);
        mapPathImg.put("pathImg2", null);
        mapPathImg.put("pathImg3", null);
        mapPathImg.put("pathImg4", null);
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
    private TextField percent;

    @FXML
    private Button save_form;

    @FXML
    private TextField layer_count;

    @FXML
    private Tab data_tab;

    @FXML
    private TextField filterAdditive;

    @FXML
    private TextField filterMaterial;

    @FXML
    private TextField filterSamples;


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


    // Сохраниение формы
    public void clickBtnSaveForm() {
        try {
            Samples samples = getSamplesForForm();
            Map.Entry<Boolean, String> response = saveFormData(samples);
            if (response.getKey()) {
                modalWindow.showAlertInformation(response.getValue());
                setDataInTable();
            } else {
                modalWindow.showAlertWarning(response.getValue());
                form_root.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            modalWindow.showError(e);
        }
    }

    // Обновить данные образца из таблицы
    public void clickBtnUpdateItemForm() {
        try {
            Samples samples = getSamplesForForm();
            Map.Entry<Boolean, String> response = updateFormData(samples);
            if (response.getKey()) {
                modalWindow.showAlertInformation(response.getValue());
                setDataInTable();
                cancelFormUpdate();
            } else {
                modalWindow.showAlertWarning(response.getValue());
                form_root.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            modalWindow.showError(e);
        }
    }

    // Загрузить данные в таблицу согласно параметрам
    public void setDataInTable() {
        pagination.setPageFactory(this::createPage);
        String strFilterSamples = filterSamples.getText();
        String strFilterMaterial = filterMaterial.getText();
        String strFilterAdditive = filterAdditive.getText();
        int count = getCountPage(strFilterSamples, strFilterMaterial, strFilterAdditive);
        percent_table.setCellValueFactory(new PropertyValueFactory<>("percent"));
        pagination.setPageCount((count / itemPerPage) + 1);
    }

    //Удаление образцов из таблицы
    public void deleteItemTable() {
        List<DataTable> items = formTableView.getSelectionModel().getSelectedItems();
        if (items.size() != 0) {
            Map.Entry<Boolean, String> result = deleteSamplesById(items);
            if (result.getKey()) {
                modalWindow.showAlertInformation(result.getValue());
            } else {
                modalWindow.showAlertWarning(result.getValue());
            }
            setDataInTable();
        } else {
            modalWindow.showAlertInformation("Выберите образец или образцы которые ходите удалить");
        }
    }


    //Обновить образец из таблицы
    public void updateItemTable() {
        List<DataTable> item = formTableView.getSelectionModel().getSelectedItems();
        if (item.size() != 0) {
            if (item.size() == 1) {
                setItemInFormRoot(item.get(0));
                SingleSelectionModel<Tab> selectionModel = root.getSelectionModel();
                selectionModel.select(form_root);
            } else {
                modalWindow.showAlertInformation("Выберите один образец");
            }
        } else {
            modalWindow.showAlertInformation("Выберите образец или образцы которые ходите обновить");
        }
    }

    // Уаление аэлементов из таблицы материалов
    public void deleteMaterialItem() {
        String item = material_select.getSelectionModel().getSelectedItem();
        Map.Entry<Boolean, String> res = materialController.deleteMaterialByName(item);
        if (res.getKey()) {
            modalWindow.showAlertInformation(res.getValue());
        } else {
            modalWindow.showAlertWarning(res.getValue());
        }
        setListMaterial();
        setDataInTable();
    }

    // Уаление аэлементов из таблицы материалов
    public void deleteAdditiveItem() {
        String item = additive_select.getSelectionModel().getSelectedItem();
        Map.Entry<Boolean, String> res = additiveController.deleteAdditiveByName(item);
        if (res.getKey()) {
            modalWindow.showAlertInformation(res.getValue());
        } else {
            modalWindow.showAlertWarning(res.getValue());
        }
        setListAdditive();
        setDataInTable();
    }

    //=========================================================================================================================

    public void filterDataTable() {
        setDataInTable();
    }

    public void clickResetFilter() {
        filterSamples.setText("");
        filterMaterial.setText("");
        filterAdditive.setText("");
        setDataInTable();
    }

    public Node createPage(Integer index) {
        String strFilterSamples = filterSamples.getText();
        String strFilterMaterial = filterMaterial.getText();
        String strFilterAdditive = filterAdditive.getText();
        from = index * itemPerPage;
        to = itemPerPage;
        formTableView.setItems(getAllSamples(from, to, strFilterSamples, strFilterMaterial, strFilterAdditive));
        return formTableView;
    }

    public void clearDataInForm() {
        setValueTable(null);
    }

    public void setItemInFormRoot(DataTable item) {
        setValueTable(item);
        btn_update_item.setStyle("visibility: visible");
        btn_cancel_update_form.setStyle("visibility: visible");

        save_form.setStyle("visibility: hidden");
        btn_clean_form.setStyle("visibility: hidden");
    }

    public void cancelFormUpdate() {
        setValueTable(null);
        SingleSelectionModel<Tab> selectionModel = root.getSelectionModel();
        selectionModel.select(data_tab);
        id.setDisable(false);
        btn_update_item.setStyle("visibility: hidden");
        btn_cancel_update_form.setStyle("visibility: hidden");
        save_form.setStyle("visibility: visible");
        btn_clean_form.setStyle("visibility: visible");
    }

    public void setValueTable(DataTable item) {
        if (item != null) {
            id.setText(item.getId());
            id.setDisable(true);
            percent.setText(item.getPercent());
            layer_count.setText(item.getLayerCount());
            additive_select.setValue(item.getNameAdditive());
            material_select.setValue(item.getNameMaterial());
            Image image1 = item.getPhotoAfter().getImage();
            Image image2 = item.getPhotoBefore().getImage();
            Image image3 = item.getPhotoAfterTest().getImage();
            Image image4 = item.getPhotoReverse().getImage();
            img_1.setImage(image1 != null ? new Image(image1.getUrl(), 288, 288, false, true) : null);
            img_2.setImage(image2 != null ? new Image(image2.getUrl(), 288, 288, false, true) : null);
            img_3.setImage(image3 != null ? new Image(image3.getUrl(), 288, 288, false, true) : null);
            img_4.setImage(image4 != null ? new Image(image4.getUrl(), 288, 288, false, true) : null);

            mapPathImg.put("pathImg1", image1 != null ? image1.getUrl() : null);
            mapPathImg.put("pathImg2", image2 != null ? image2.getUrl() : null);
            mapPathImg.put("pathImg3", image3 != null ? image3.getUrl() : null);
            mapPathImg.put("pathImg4", image4 != null ? image4.getUrl() : null);
        } else {
            id.setText("");
            percent.setText("");
            layer_count.setText("");
            additive_select.setValue(null);
            material_select.setValue(null);
            img_1.setImage(null);
            img_2.setImage(null);
            img_3.setImage(null);
            img_4.setImage(null);
            mapPathImg.put("pathImg1", null);
            mapPathImg.put("pathImg2", null);
            mapPathImg.put("pathImg3", null);
            mapPathImg.put("pathImg4", null);
        }

    }

    public Samples getSamplesForForm() {
        Integer id_material = materialController.getMaterialByName(material_select.getValue()) == null ?
                null : materialController.getMaterialByName(material_select.getValue()).getId();
        Integer id_additive = additiveController.getAdditiveByName(additive_select.getValue()) == null ?
                null : additiveController.getAdditiveByName(additive_select.getValue()).getId();
        for (Map.Entry<String, String> entry : mapPathImg.entrySet()) {
            if (entry.getValue() != null) {
                Map.Entry<Boolean, String> newValue = createFileImg.saveImgInFolder(entry.getValue());
                mapPathImg.put(entry.getKey(), newValue.getValue());
            }
        }
        Samples samples = new Samples(
                id.getText(), id_material, id_additive,
                layer_count.getText(), percent.getText(),
                mapPathImg.get("pathImg1"), mapPathImg.get("pathImg2"),
                mapPathImg.get("pathImg3"), mapPathImg.get("pathImg4")
        );
        return samples;
    }

    public void setImag(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        FileChooser open = new FileChooser();
        Stage stage = (Stage) form_pane.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath();
            path = path.replace("\\", "\\\\");
            Image image = new Image(file.toURI().toString(), 288, 288, false, true);
            button.setText("");
            switch (button.getId()) {
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
    //=========================================================================================================================
//    select *, additive.name as name_additive, material.name as name_material
//    from samples as s
//    LEFT JOIN additive  ON additive.id  = s.id_additive
//    LEFT JOIN material  ON material.id  = s.id_material

    public void downReport() {

        try {
            String parent = System.getProperty("user.dir") + "/report/mainReport.jrxml";
            final File dir = new File("C://temp");
            List list = getAllSamplesFromJasper();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(parent);
            JRFileVirtualizer virtualizer = new JRFileVirtualizer(5000, dir.getAbsolutePath());
            HashMap<String, Object> reportParametersMap = new HashMap<String, Object>();

            reportParametersMap.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, reportParametersMap, ConnectorDB());
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            e.printStackTrace();
            modalWindow.showError(e);
        }
    }

    //=========================================================================================================================


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        count.setPrefWidth(30);
        id_table.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_material_table.setCellValueFactory(new PropertyValueFactory<>("nameMaterial"));
        id_additive_table.setCellValueFactory(new PropertyValueFactory<>("nameAdditive"));
        layer_count_table.setCellValueFactory(new PropertyValueFactory<>("layerCount"));
        photoAfter.setPrefWidth(200);
        photoAfter.setCellValueFactory(new PropertyValueFactory<>("photoAfter"));
        photoBefore.setPrefWidth(200);
        photoBefore.setCellValueFactory(new PropertyValueFactory<>("photoBefore"));
        photoAfterTest.setPrefWidth(200);
        photoAfterTest.setCellValueFactory(new PropertyValueFactory<>("photoAfterTest"));
        photoReverse.setPrefWidth(200);
        photoReverse.setCellValueFactory(new PropertyValueFactory<>("photoReverse"));
        setDataInTable();
        setListMaterial();
        setListAdditive();
        formTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        if (materialController.ConnectorDB() != null) {
            bdInfo.setText("База данных подключена");
            bdInfo.setStyle("-fx-text-fill: #fff");
        } else {
            bdInfo.setText("База данных не подключена");
            bdInfo.setStyle("-fx-text-fill: red");
        }

    }


}