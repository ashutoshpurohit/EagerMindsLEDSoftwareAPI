/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft.pages;

import java.net.URL;
import java.util.HashMap;

import com.eagerminds.eagermindsledsoft.BasicDeviceDataInformation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import novj.platform.vxkit.common.bean.login.LoginResultBean;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.net.svolley.Request.IRequestBase;
import novj.publ.util.StringUtil;
import com.eagerminds.eagermindsledsoft.CommonFunctionalBase;
import com.eagerminds.eagermindsledsoft.MainPageController;

/**
 * FXML Controller class
 *
 * @author user
 */
public class LoginPageController {
    public MainPageController mainPageController = null;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_cancel;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_password;
    @FXML
    private Label l_tip;
    public static HashMap<String,String> loginedMap = new HashMap<>();

    @FXML
    private void btn_login_pressed(MouseEvent event) {
        l_tip.setVisible(false);
        if (tf_username.getText().isEmpty() ||
                pf_password.getText().isEmpty()) {
            l_tip.setText(CommonFunctionalBase.getResourceValue("input_username_and_password"));
            l_tip.setVisible(true);
            return;
        }

        if (null == deviceInfo) {
            l_tip.setText(CommonFunctionalBase.getResourceValue("device_info_not_exists"));
            l_tip.setVisible(true);
            return;
        }
        Login();
    }

    @FXML
    void btn_login_keypress(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            Login();
        }
    }

    private void Login() {
        NovaOpt.GetInstance().login(deviceInfo.getSn(), tf_username.getText(), pf_password
                .getText(), new OnResultListenerN<LoginResultBean, ErrorDetail>() {
            @Override
            public void onSuccess(IRequestBase requestBase, LoginResultBean response) {
                BasicDeviceDataInformation di = getDeviceInfo();
                Platform.runLater(() -> {

                    System.out.println("login ok");
                    toDeviceOptPage(di);
                    Stage stage = (Stage) btn_cancel.getScene().getWindow();
                    stage.close();
                    if ((!StringUtil.isEmpty(deviceInfo.getSn())&&!StringUtil.isEmpty(deviceInfo.getIpAddress()))) {
                        loginedMap.put(deviceInfo.getSn(),deviceInfo.getIpAddress());
                    }
                    mainPageController.searchScreen();
                });
            }

            @Override
            public void onError(IRequestBase requestBase, ErrorDetail error) {
                Platform.runLater(() -> {
                    System.out.println(error.description);
                    l_tip.setText(error.description);
                    l_tip.setVisible(true);
                });
            }
        });
    }

    @FXML
    private void btn_cancel_pressed(MouseEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void pf_password_keypressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Login();
        }
    }

    public void toDeviceOptPage(BasicDeviceDataInformation deviceInfo) {
        try {
            Stage primaryStage = new Stage();
            URL location = getClass().getResource("/com/eagerminds/eagermindsledsoft/DeviceTestPageToBeDeleted.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();
            DeviceTestPageToBeDeleted deviceOptPageController = fxmlLoader.getController();
            //Pass parameters
            deviceOptPageController.setDeviceInfo(deviceInfo);
            deviceOptPageController.init();
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle(CommonFunctionalBase.getResourceValue("terminal_control_panel"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setOnCloseRequest(event -> mainPageController.searchScreen());
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BasicDeviceDataInformation getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(BasicDeviceDataInformation deviceInfo) {
        this.deviceInfo = deviceInfo;
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(200);
//                    autoLogin();
//                }catch (Exception e){
//
//                }
//            }
//        });
    }

    private BasicDeviceDataInformation deviceInfo = null;

    private void autoLogin() {
        NovaOpt.GetInstance().login(deviceInfo.getSn(), "admin", "123456",
                new OnResultListenerN<LoginResultBean, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, LoginResultBean response) {
                        BasicDeviceDataInformation di = getDeviceInfo();
//                Contacts.loginedDeviceInfos.add(di);
                        Platform.runLater(() -> {
                            System.out.println("login ok");
                            toDeviceOptPage(di);
                            Stage stage = (Stage) btn_cancel.getScene().getWindow();
                            stage.close();
                            mainPageController.searchScreen();
                        });
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        Platform.runLater(() -> {
                            l_tip.setText(error.description);
                            l_tip.setVisible(true);
                        });
                    }
                });
    }

}
