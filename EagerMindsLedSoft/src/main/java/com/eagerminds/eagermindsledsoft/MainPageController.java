/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import com.eagerminds.eagermindsledsoft.BasicDeviceDataInformation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import novj.platform.vxkit.common.bean.search.SearchResult;
import novj.platform.vxkit.common.result.DefaultOnResultListener;
import novj.platform.vxkit.handy.api.SearchManager;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.util.StringUtil;
import com.eagerminds.eagermindsledsoft.pages.LoginPageController;
import com.eagerminds.eagermindsledsoft.DeviceViewModelMVCDataBind;

import static com.eagerminds.eagermindsledsoft.pages.LoginPageController.loginedMap;


/**
 *
 * @author user
 */
public class MainPageController {
    @FXML
    private TableView<BasicDeviceDataInformation> deviceTable;
    @FXML
    private TableColumn<BasicDeviceDataInformation, String> snColumn;
    @FXML
    private TableColumn<BasicDeviceDataInformation, String> deviceNameColumn;
    @FXML
    private TableColumn<BasicDeviceDataInformation, String> aliasNameColumn;
    @FXML
    private TableColumn<BasicDeviceDataInformation, String> ipAddressColumn;
    @FXML
    private TableColumn<BasicDeviceDataInformation, Button> connectBtnColumn;
    @FXML
    private Label labelTotal;
    @FXML
    private Label labelUnlogin;
    @FXML
    private Button btn_refresh;
    @FXML
    private ComboBox cb_select_language;
    //    private Map<String, String> loginStatus = new HashMap<>();
    @FXML
    private ListView listview;
    @FXML
    private Button btn_logout;
    private BasicDeviceDataInformation selectedDeviceInfo;

    private int count = 0;


    /**
     * search device
     */
    public void searchScreen() {
        searchScreen(null);
    }

    private void searchScreen(String remoteIp) {
        btn_logout.setDisable(true);
        DeviceViewModelMVCDataBind.devicesData.clear();
        setLabelTotal();
        setLabelUnlogin();

        NovaOpt.GetInstance().searchScreen(new SearchManager.OnScreenSearchListener() {
            @Override
            public void onSuccess(SearchResult searchResult) {
                BasicDeviceDataInformation deviceInfo = new BasicDeviceDataInformation();

                System.out.println(count++);

                deviceInfo.setSn(searchResult.sn);
                deviceInfo.setProductName(searchResult.productName);
                deviceInfo.setAliasName(searchResult.aliasName);
                deviceInfo.setIpAddress(searchResult.ipAddress);
                /**
                 * Prevent other host computers logging in to the terminal under the same LAN from causing problems to the terminal status when logging in to the SDK terminal
                 */
                if (!StringUtil.isEmpty(searchResult.sn)&&!StringUtil.isEmpty(searchResult.ipAddress)&&loginedMap.containsKey(searchResult.sn)&&loginedMap.containsValue(searchResult.ipAddress)) {
                    deviceInfo.setLogined(searchResult.logined);
                }else {
                    deviceInfo.setLogined(false);
                }
                deviceInfo.searchResult = searchResult;
                add2list(deviceInfo);
            }

            @Override
            public void onError(ErrorDetail errorDetail) {
                Platform.runLater(()->{
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, errorDetail.description);
                    errorAlert.setTitle(CommonFunctionalBase.getResourceValue("alert_title_error"));
                    errorAlert.setHeaderText(CommonFunctionalBase.getResourceValue("alert_title_error"));
                    errorAlert.showAndWait();
                });

            }
        }, remoteIp);
    }

    /**
     * Determine whether the specified terminal is already included in the local device list, because a machine with multiple network cards will send a broadcast to each network card by default.
     * So the same terminal may return multiple times
     *
     * @param deviceInfo
     * @return
     */
    private boolean containsSN(BasicDeviceDataInformation deviceInfo) {
        for (BasicDeviceDataInformation item : DeviceViewModelMVCDataBind.devicesData) {
            if (item.getSn().equals(deviceInfo.getSn())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add the returned terminal to the local list for unified maintenance
     *
     * @param deviceInfo
     */
    private void add2list(BasicDeviceDataInformation deviceInfo) {
        if (!containsSN(deviceInfo)) {
            DeviceViewModelMVCDataBind.devicesData.add(deviceInfo);
        }
        Platform.runLater(() -> {
            setLabelUnlogin();
            setLabelTotal();
        });
    }

    /**
     * Initialize Listview
     */
    private void initListView() {
        btn_logout.setDisable(true);
        listview.setItems(DeviceViewModelMVCDataBind.devicesData);
        listview.setCellFactory(e -> new ListCell<BasicDeviceDataInformation>() {
            @Override
            protected void updateItem(BasicDeviceDataInformation item, boolean empty) {
                super.updateItem(item, empty);
                if (null != item && !empty) {
                    HBox hb = new HBox();
                    Label tfSn = new Label();
                    tfSn.setFont(Font.font(14));
                    tfSn.setPrefSize(250, 30);
                    tfSn.setText(item.getSn());

                    Label tfName = new Label();
                    tfName.setFont(Font.font(14));
                    tfName.setPrefSize(150, 30);
                    tfName.setText(item.getProductName());

                    Label tfIp = new Label();
                    tfIp.setFont(Font.font(14));
                    tfIp.setPrefSize(250, 30);
                    tfIp.setText(item.getIpAddress());

                    Button btn = new Button();
                    btn.setId(item.getSn());
                    btn.setText(item.isLogined() ?
                            CommonFunctionalBase.getResourceValue("terminal_login_direct") :
                            CommonFunctionalBase.getResourceValue("terminal_connect"));
                    btn.setOnMousePressed(event -> {
                        Button btn1 = (Button) event.getSource();
                        BasicDeviceDataInformation info = DeviceViewModelMVCDataBind.getDeviceInfoBySN(btn1.getId());
                        System.out.println(info.getSn());
                        connectDevice(info);
                    });

                    Platform.runLater(() -> {
                        hb.getChildren().add(tfSn);
                        hb.getChildren().add(tfName);
                        hb.getChildren().add(tfIp);
                        hb.getChildren().add(btn);
                        hb.setAlignment(Pos.CENTER_LEFT);
                        setGraphic(hb);
                    });
                } else {
                    Platform.runLater(() -> setGraphic(null));
                }
            }
        });
        listview.getFocusModel().focusedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                                Number newValue) {
                if (newValue.intValue() >= 0) {
                    selectedDeviceInfo = DeviceViewModelMVCDataBind.devicesData.get(newValue.intValue());
                    if (selectedDeviceInfo.isLogined()) {
                        btn_logout.setDisable(false);
                    } else {
                        btn_logout.setDisable(true);
                    }
                } else {
                    selectedDeviceInfo = null;
                    btn_logout.setDisable(true);
                }
            }
        });
    }

    /**
     * Calculate the total number of devices
     */
    private void setLabelTotal() {
        labelTotal.setText(CommonFunctionalBase.getResourceValue("terminal_total") + DeviceViewModelMVCDataBind.devicesData.size());
    }

    /**
     * Count the number of unregistered devices
     */
    private void setLabelUnlogin() {
        int totalUnLogined = 0;
        for (BasicDeviceDataInformation deviceInfo : DeviceViewModelMVCDataBind.devicesData) {
            if (!deviceInfo.isLogined()) {
                totalUnLogined++;
            }
        }

        labelUnlogin.setText(CommonFunctionalBase.getResourceValue("terminal_not_logged") + (totalUnLogined >= 0 ? totalUnLogined : 0));
    }

    /**
     * Enter the login page
     *
     * @param deviceInfo
     */
    private void toLoginPage(BasicDeviceDataInformation deviceInfo) {
        try {
            Stage primaryStage = new Stage();
            URL location = getClass().getResource("/com/eagerminds/eagermindsledsoft/LoginPage.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();
            LoginPageController loginPageController = fxmlLoader.getController();
            //Pass parameters
            loginPageController.setDeviceInfo(deviceInfo);
            loginPageController.mainPageController = this;
            if (deviceInfo.isLogined()) {
                Platform.runLater(() -> {
                    loginPageController.toDeviceOptPage(deviceInfo);
                });
            } else {
                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setTitle(CommonFunctionalBase.getResourceValue("terminal_login"));
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BasicDeviceDataInformation curConnectDeviceInfo = null;

    /**
     * If you are already logged in, you will directly jump to the corresponding page, if you are not logged in, you will jump to the login page
     *
     * @param deviceInfo
     */
    private void connectDevice(BasicDeviceDataInformation deviceInfo) {
        curConnectDeviceInfo = deviceInfo;
        CommonFunctionalBase.deviceOpt = deviceInfo;
        if (deviceInfo.isLogined()) {
            toLoginPage(deviceInfo);
            return;
        }
//        printLog(deviceInfo.getSn() + "/" + deviceInfo.getIpAddress() + "/" + deviceInfo.searchResult.tcpPort+"/"+deviceInfo.searchResult.encodeType);
        NovaOpt.GetInstance().connectDevice(deviceInfo.searchResult, new DefaultOnResultListener() {
            @Override
            public void onSuccess(Integer response) {
                Platform.runLater(() -> {
                    toLoginPage(curConnectDeviceInfo);
                });
            }

            @Override
            public void onError(ErrorDetail error) {
                //tip
                Platform.runLater(() -> {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, error.description);
                    errorAlert.setTitle(CommonFunctionalBase.getResourceValue("alert_title_error"));
                    errorAlert.setHeaderText(null);
                    errorAlert.showAndWait();
                });
                curConnectDeviceInfo = null;
            }
        }, wrapper -> {
            Platform.runLater(() -> {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, CommonFunctionalBase.getResourceValue
                        ("terminal_disconnect"));
                errorAlert.setTitle(CommonFunctionalBase.getResourceValue("alert_title_error"));
                errorAlert.setHeaderText(null);
                errorAlert.showAndWait();
            });
            curConnectDeviceInfo = null;
        });
    }

    /**
     * logout
     *
     * @param event
     */
    @FXML
    protected void btn_logout(ActionEvent event) {
        if (null != selectedDeviceInfo) {
            boolean isLogout = NovaOpt.GetInstance().logOut(selectedDeviceInfo.getSn());
            if (isLogout) {
                BasicDeviceDataInformation deviceInfo = DeviceViewModelMVCDataBind.getDeviceInfoBySN(selectedDeviceInfo.getSn());
                if (null != deviceInfo) {
                    //Modify the logout button to be unavailable after logout
                    deviceInfo.setLogined(false);
                    loginedMap.remove(deviceInfo.getSn(),deviceInfo.getIpAddress());
                    //Recalculate the number of not logged in          
                    setLabelUnlogin();
                    //Update listview
                    initListView();
                }
            }
        }
    }

    /**
     * Refresh operation, resend UDP broadcast search screen
     *
     * @param event
     */
    @FXML
    protected void btn_refresh_pressed(ActionEvent event) {
        btn_refresh.setDisable(true);
        searchScreen();

        Timer timer = new Timer("btn_refresh_pressed_Timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                btn_refresh.setDisable(false);
                timer.cancel();
            }
        }, 3 * 1000);
    }

    /**
     * Specify IP search screen
     *
     * @param event
     */
    @FXML
    protected void btn_Specify_Ip(ActionEvent event) {
        setLabelTotal();
        setLabelUnlogin();
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle(CommonFunctionalBase.getResourceValue("refresh_terminal_list_by_ip"));
        tid.setHeaderText(null);
        tid.setContentText(CommonFunctionalBase.getResourceValue("refresh_terminal_list_by_ip"));
        tid.getDialogPane().getButtonTypes().remove(0, 2);
        ButtonType btConfirm = new ButtonType(CommonFunctionalBase.getResourceValue("confirm"),
                ButtonBar.ButtonData.OK_DONE);
        ButtonType btCancel = new ButtonType(CommonFunctionalBase.getResourceValue("reboot_cancel"),
                ButtonBar.ButtonData.CANCEL_CLOSE);
        tid.getDialogPane().getButtonTypes().addAll(btConfirm, btCancel);

        Optional<String> result = tid.showAndWait();
        //Method 1 Get input results
        if (!result.isPresent()) {
            System.out.println("Cancel input");
            return;
        }
        searchScreen(result.get());
    }

    /**
     * Restart the current page after changing the interface language
     */
    private void reStartPage() {
        try {
            Stage primaryStage = new Stage();
            URL location = getClass().getResource("MainPage.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();

            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle("EargerMinds ADL Plex");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            //Pass parameters
            MainPageController controller = fxmlLoader.getController();
            controller.initData();
            //region Query device
            controller.searchScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize data binding
     */
    public void initData() {
        ObservableList<String> languageOptions = FXCollections.observableArrayList(CommonFunctionalBase
                .getResourceValue("select_language_english"), CommonFunctionalBase.getResourceValue
                ("select_language_english"));
        cb_select_language.setItems(languageOptions);
        cb_select_language.getSelectionModel().select(Locale.ENGLISH == CommonFunctionalBase
                .DEFAULT_LOCALE ? 0 : 1);
        cb_select_language.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int index = cb_select_language.getSelectionModel().getSelectedIndex();
                if (0 == index) {
                    if (Locale.ENGLISH == CommonFunctionalBase.DEFAULT_LOCALE) {
                        return;
                    }
                    CommonFunctionalBase.DEFAULT_LOCALE = Locale.ENGLISH;
                } else if (1 == index) {
                    if (Locale.ENGLISH == CommonFunctionalBase.DEFAULT_LOCALE) {
                        return;
                    }
                    CommonFunctionalBase.DEFAULT_LOCALE = Locale.ENGLISH;
                }
                updatePorperties();
                reStartPage();
                Platform.runLater(() -> {
                    Stage stage = (Stage) btn_refresh.getScene().getWindow();
                    stage.close();
                });
            }
        });
//        bindData();
        initListView();
    }

    private void updatePorperties() {
        Properties properties = new Properties();
        String path = System.getProperty("user.dir") + File.separator + "configuration.properties";
        File config = new File(path);
        try {
            if (!config.exists()) {
                config.createNewFile();
            }
            InputStream is = new BufferedInputStream(new FileInputStream(path));
            properties.load(is);
            properties.setProperty("local_language", CommonFunctionalBase.DEFAULT_LOCALE.getLanguage());
            FileOutputStream fos = new FileOutputStream(path, false);
            properties.store(fos, null);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindData() {
        try {
            snColumn.setCellValueFactory(cellData -> cellData.getValue().snProperty());
            deviceNameColumn.setCellValueFactory(cellData -> cellData.getValue()
                    .productNameProperty());
            aliasNameColumn.setCellValueFactory(cellData -> cellData.getValue().aliasNameProperty
                    ());
            ipAddressColumn.setCellValueFactory(cellData -> cellData.getValue().ipAddressProperty
                    ());
            connectBtnColumn.setCellValueFactory(cellData -> cellData.getValue()
                    .connectBtnProperty());
            deviceTable.setItems(DeviceViewModelMVCDataBind.devicesData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void printLog(String log) {
        Platform.runLater(() -> {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION, log);
            errorAlert.setTitle("MSG");
            errorAlert.setHeaderText(null);
            errorAlert.showAndWait();
        });
    }
}
