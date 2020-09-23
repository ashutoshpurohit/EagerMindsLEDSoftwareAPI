package com.eagerminds.eagermindsledsoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import novj.publ.api.NovaOpt;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            initValue();
            
            
            URL location = getClass().getResource("MainPage.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("ADL EagerMinds Software");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            NovaOpt.GetInstance().initialize(2);
            MainPageController controller = fxmlLoader.getController();
            controller.initData();
            //region    Query device
            controller.searchScreen();

            
            
            /*
            URL location = getClass().getResource("ProgramSettingPage.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("ADL EagerMinds Software");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            */
            
            /*
            URL location = getClass().getResource("/com/eagerminds/eagermindsledsoft/LoginPage.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("ADL EagerMinds Software");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            */
            
            
            /*
            URL location = getClass().getResource("/com/eagerminds/eagermindsledsoft/DeviceTestPageToBeDeleted.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("ADL EagerMinds Software");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            */
            
            //endregion
        } catch (Exception ex) {
            showInfo(ex.getMessage());
        }
    }

    public void initValue() {
        try {
            String curPath = System.getProperty("user.dir");
            CommonFunctionalBase.timeZonesXml = curPath + File.separator + "timeZones.xml";
            File config = new File(System.getProperty("user.dir") + File.separator +
                    "configuration.properties");
            if (config.exists()) {
                FileReader fr = new FileReader(config);
                BufferedReader br = new BufferedReader(fr);
                Properties properties = new Properties();
                properties.load(br);
                if (Locale.ENGLISH.getLanguage().equals(properties.getProperty("local_language"))) {
                    CommonFunctionalBase.DEFAULT_LOCALE = Locale.ENGLISH;
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showInfo(String sInfo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, sInfo);
        alert.setHeaderText(null);
        alert.setTitle(CommonFunctionalBase.getResourceValue("alert_title_confirm"));
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
    
    public static void main(String[] args) {
        launch();
    }
    
    public static int convertToInt(byte[] data) {
        int result = 0;
        int bt = 0;
        for (int i = data.length - 1; i >= 0; i--) {
            result += (data[i] & 0xff) << bt;
            bt += 8;
        }

        return result;
    }

}