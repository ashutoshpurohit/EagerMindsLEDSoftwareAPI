/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft.pages;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.eagerminds.eagermindsledsoft.BasicDeviceDataInformation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import novj.platform.vxkit.common.bean.FirmwareInfo;
import novj.platform.vxkit.common.bean.TimeZoneParam;
import novj.platform.vxkit.common.bean.Volume;
import novj.platform.vxkit.common.bean.monitor.DiskSizeBean;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.platform.vxkit.handy.api.ScreenShotManager;
import novj.platform.vxkit.handy.net.transfer.OnFileTransferListener;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.net.svolley.Request.IRequestBase;
import com.eagerminds.eagermindsledsoft.CommonFunctionalBase;
import com.eagerminds.eagermindsledsoft.pages.TimeZones.TimeZone;
import com.eagerminds.eagermindsledsoft.pages.TimeZones.TimeZones;
import com.eagerminds.eagermindsledsoft.pages.color.ColorTempController;
import com.eagerminds.eagermindsledsoft.pages.play.*;
/**
 * FXML Controller class
 *
 * @author user
 */

/**
 *  Main panel
 */
public class DeviceTestPageToBeDeleted {

    private BasicDeviceDataInformation deviceInfo = null;
    TimeZones timeZones = new TimeZones();
    List<TimeZone> listTimeZone = timeZones.LoadXML();

    @FXML
    private Label labelSN;
    @FXML
    private TextArea ta_show;
    @FXML
    private ComboBox cb_timezones;
    @FXML
    private TextField tf_time;
    @FXML
    Button btn_play_program;
    @FXML
    ToggleButton toggleBtn_Sync_Play;
    @FXML
    private Label device_sn;
    @FXML
    private Button btn_screenshot;
    @FXML
    private Button btn_video_source;
    @FXML
    private Button btn_update_apk;


    public BasicDeviceDataInformation getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(BasicDeviceDataInformation deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @FXML
    protected void btn_modify_pressed(ActionEvent event) {
        toCreatePage();
    }

    private void toCreatePage() {
        try {
            Stage primaryStage = new Stage();
            URL location = getClass().getResource("/com/eagerminds/eagermindsledsoft/ProgramSettingPage.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();
            ProgramSettingPageController programSettingPageController = fxmlLoader.getController();
            programSettingPageController.setDeviceInfo(this.getDeviceInfo());
            //Pass parameters
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle(CommonFunctionalBase.getResourceValue("terminal_program_editor"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            ProgramSettingPageController psgc = fxmlLoader.getController();
            psgc.initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        device_sn.setText(CommonFunctionalBase.getResourceValue("terminal_serialno") + "：");
        labelSN.setText(deviceInfo.getSn());
        //T1\T3 The board does not support external video sources
        if (deviceInfo.getProductName().equals("T1") ||
                deviceInfo.getProductName().equals("T3") ||
                deviceInfo.getProductName().equals("JT100")) {
            btn_video_source.setDisable(true);
        }
        //Initialize combobox data
        cb_timezones.setPromptText("");
        if (Locale.ENGLISH == CommonFunctionalBase.DEFAULT_LOCALE) {
            for (int i = 0; i < listTimeZone.size(); i++) {
                cb_timezones.getItems().add(listTimeZone.get(i).id);
            }
        }
        getCurrentTimeZone();
//        cb_timezones.getSelectionModel().select(0);
        //Initialization time display
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tf_time.setText(df.format(day));

        //Initialize the sync play button state
        NovaOpt.GetInstance().getSyncPlay(deviceInfo.getSn(), new OnResultListenerN<Boolean,
                ErrorDetail>() {

            @Override
            public void onSuccess(IRequestBase iRequestBase, Boolean aBoolean) {
                Platform.runLater(() -> {
                    if (aBoolean) {
                        toggleBtn_Sync_Play.setText(CommonFunctionalBase.getResourceValue(
                                "terminal_sync_playing_close"));
                    } else {
                        toggleBtn_Sync_Play.setText(CommonFunctionalBase.getResourceValue(
                                "terminal_sync_playing_open"));
                    }
                    toggleBtn_Sync_Play.setSelected(aBoolean);
                });
            }

            @Override
            public void onError(IRequestBase iRequestBase, ErrorDetail errorDetail) {
                showInfo(CommonFunctionalBase.getResourceValue("terminal_init_sync_failure"));
            }
        });
    }

    /**
     * Restart the device now
     *
     * @param event
     */
    @FXML
    protected void btn_reboot_pressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, CommonFunctionalBase.getResourceValue("reboot_soon")
                , new ButtonType(CommonFunctionalBase.getResourceValue("confirm"), ButtonBar
                .ButtonData.YES), new ButtonType(CommonFunctionalBase.getResourceValue("reboot_cancel"),
                ButtonBar.ButtonData.NO));
        alert.setTitle(CommonFunctionalBase.getResourceValue("alert_title_warning"));
        alert.setHeaderText(null);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        if (optionalButtonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            NovaOpt.GetInstance().reboot(deviceInfo.getSn(), new
                    OnResultListenerN<Integer, ErrorDetail>() {

                        @Override
                        public void onSuccess(IRequestBase requestBase, Integer response) {
                            showInfo("success " + CommonFunctionalBase.getResourceValue("terminal_reboot_now"));
                        }

                        @Override
                        public void onError(IRequestBase requestBase, ErrorDetail error) {
                            Platform.runLater(() -> {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR, error
                                        .description);
                                errorAlert.setTitle(CommonFunctionalBase.getResourceValue("alert_title_error"));
                                errorAlert.setHeaderText(null);
                                errorAlert.showAndWait();
                            });
                        }
                    });
        }
    }

    

    /**
     * Obtain software major version information and FPGA version information
     *
     * @param event
     */
    @FXML
    protected void btn_get_version_pressed(ActionEvent event) {
        NovaOpt.GetInstance().getVersionMessage(deviceInfo.getSn(), new
                OnResultListenerN<FirmwareInfo, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, FirmwareInfo response) {
                        String sInfo = CommonFunctionalBase.getResourceValue("terminal_version") + "    ";
                        sInfo += "FPGA: " + response.fpga + ", ";
                        sInfo += CommonFunctionalBase.getResourceValue("termianl_system_version") + ": " + response.model + ", ";
                        sInfo += CommonFunctionalBase.getResourceValue("terminal_main_version") +
                                ": " + response.mainVersion;
                        showInfo(sInfo);
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showInfo(error.description);
                    }
                });

    }

    /**
     * Get free memory
     *
     * @param event
     */
    @FXML
    protected void btn_get_memory_pressed(ActionEvent event) {
        NovaOpt.GetInstance().getAvailableMemoryData(deviceInfo.getSn(), new
                OnResultListenerN<Float, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Float aFloat) {
                        String sInfo = "";
                        sInfo += CommonFunctionalBase.getResourceValue("available_memory") + "：" + aFloat
                                .toString() + "%";
                        showInfo(sInfo);
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail errorDetail) {
                        showInfo(errorDetail.description);
                    }
                });
    }

    /**
     * Get current screen brightness
     *
     * @param event
     */
    @FXML
    protected void btn_get_env_bright_pressed(ActionEvent event) {
        NovaOpt.GetInstance().getEnvironmentBrightness(deviceInfo.getSn(), new
                OnResultListenerN<Integer, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Integer integer) {
                        showInfo(CommonFunctionalBase.getResourceValue("current_screen_brightness") + "：" +
                                integer.toString() + "Lux");
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail errorDetail) {
                        showInfo(CommonFunctionalBase.getResourceValue("current_screen_brightness")
                                + "：" + errorDetail.description);
                    }
                });
    }

    /**
     * Set time zone
     *
     * @param event
     */
    @FXML
    protected void btn_time_sync_pressed(ActionEvent event) {
        try {
            TimeZoneParam timeZoneParam = new TimeZoneParam();
            timeZoneParam.setTimeZone(getTimeZoneIdByContent(cb_timezones.getValue().toString()));
            NovaOpt.GetInstance().calibrateTime(deviceInfo.getSn(), timeZoneParam, new
                    OnResultListenerN<TimeZoneParam, ErrorDetail>() {
                        @Override
                        public void onSuccess(IRequestBase requestBase, TimeZoneParam timeZoneParam) {
                            String sInfo = "";
                            sInfo += CommonFunctionalBase.getResourceValue("terminal_sync_time")
                                    + "：" + CommonFunctionalBase.getResourceValue("terminal_timezone") + "-" +
                                    timeZoneParam.getTimeZone();
                            Date date = new Date(timeZoneParam.getUtcTimeMillis());
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sInfo += " " + CommonFunctionalBase.getResourceValue("terminal_datetime") + "-" +
                                    df.format(date);
                            showInfo(sInfo);
                        }

                        @Override
                        public void onError(IRequestBase requestBase, ErrorDetail errorDetail) {
                            showInfo(CommonFunctionalBase.getResourceValue("terminal_sync_time") + "：" + errorDetail
                                    .description);
                        }
                    });
        } catch (Exception e) {
            showInfo(e.getMessage());
        }
    }

    /**
     * Get current time and time zone
     *
     * @param event
     */
    @FXML
    protected void btn_time_get_pressed(ActionEvent event) {
        getCurrentTimeZone();
    }

    private void getCurrentTimeZone() {
        try {
            NovaOpt.GetInstance().getCurrentTimeAndZone(deviceInfo.getSn(), new
                    OnResultListenerN<TimeZoneParam, ErrorDetail>() {

                        @Override
                        public void onSuccess(IRequestBase iRequestBase,
                                              TimeZoneParam timeZoneParam) {
                            for (int i = 0; i < listTimeZone.size(); i++) {
                                if (timeZoneParam.getTimeZone().endsWith(listTimeZone.get(i).getId())) {
                                    final int x = i;
                                    Platform.runLater(() -> cb_timezones.getSelectionModel().select(x));
                                }
                            }
                            String sInfo = CommonFunctionalBase.getResourceValue("terminal_timezone") + "："
                                    + timeZoneParam.getTimeZone() +
                                    "; " + CommonFunctionalBase.getResourceValue("terminal_present_time") +
                                    "：" + timeZoneParam.getCurrentTime();

                            showInfo(sInfo);
                        }

                        @Override
                        public void onError(IRequestBase iRequestBase, ErrorDetail errorDetail) {
                            showInfo(CommonFunctionalBase.getResourceValue("terminal_get_time_failure"));
                        }
                    });
        } catch (Exception e) {
            showInfo(e.getMessage());
        }
    }

    /**
     * Sync play switch
     *
     * @param event
     */
    @FXML
    protected void toggleBtn_Sync_Play_pressed(ActionEvent event) {
        if (toggleBtn_Sync_Play.isSelected()) {
            //Set_ON
            NovaOpt.GetInstance().setSyncPlay(deviceInfo.getSn(), true, new OnResultListenerN() {
                @Override
                public void onSuccess(IRequestBase iRequestBase, Object o) {
                    showInfo(CommonFunctionalBase.getResourceValue("terminal_sync_playing_open"));
                    Platform.runLater(() -> {
                        toggleBtn_Sync_Play.setText(CommonFunctionalBase.getResourceValue(
                                "terminal_sync_playing_close"));
                    });
                }

                @Override
                public void onError(IRequestBase iRequestBase, Object o) {
                    Platform.runLater(() -> {
                        toggleBtn_Sync_Play.setSelected(false);
                    });
                    showInfo(CommonFunctionalBase.getResourceValue("terminal_sync_playing_open_failure"));
                }
            });
        } else {
            //Set_OFF
            NovaOpt.GetInstance().setSyncPlay(deviceInfo.getSn(), false, new OnResultListenerN() {
                @Override
                public void onSuccess(IRequestBase iRequestBase, Object o) {
                    showInfo(CommonFunctionalBase.getResourceValue("terminal_sync_playing_close"));
                    Platform.runLater(() -> {
                        toggleBtn_Sync_Play.setText(CommonFunctionalBase.getResourceValue(
                                "terminal_sync_playing_open"));
                    });
                }

                @Override
                public void onError(IRequestBase iRequestBase, Object o) {
                    Platform.runLater(() -> {
                        toggleBtn_Sync_Play.setSelected(true);
                    });
                    showInfo(CommonFunctionalBase.getResourceValue("terminal_sync_playing_close_failure"));
                }
            });
        }
    }

    /**
     * Get a screenshot of the currently playing content
     *
     * @param event
     */
    @FXML
    protected void btn_screenshot_pressed(ActionEvent event) {
        //file name
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String sFileName = deviceInfo.getSn() + "_" + df.format(day);

        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(CommonFunctionalBase.getResourceValue("snapshot_save_path"));
        File directory = dc.showDialog(btn_screenshot.getScene().getWindow());
        if (null == directory) {
            return;
        }
        String filePath = directory.getAbsolutePath();
        NovaOpt.GetInstance().downLoadScreenshot(deviceInfo.getSn(), deviceInfo.searchResult
                        .width, deviceInfo.searchResult.height,
                ScreenShotManager.PictureType.PICTURE_PNG,
                filePath, sFileName, new OnFileTransferListener() {
                    @Override
                    public void onTransferred(long l) {
                    }

                    @Override
                    public void onStartTransfer() {
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        showInfo(CommonFunctionalBase.getResourceValue("snapshot_download_successed"));
                    }

                    @Override
                    public void onError(ErrorDetail errorDetail) {
                        showInfo(CommonFunctionalBase.getResourceValue("snapshot_download_failed")
                                + "：" + errorDetail.description);
                    }
                });
    }

    /**
     * Play control
     *
     * @param ae
     */
    @FXML
    protected void onPlayControl(ActionEvent ae) {
        try {
            Stage primaryStage = new Stage();
            URL location = getClass().getResource("/com/eagerminds/eagermindsledsoft/PlayControlPage.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();

            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle(CommonFunctionalBase.getResourceValue("terminal_player_controller"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            PlayControlPageController playControlPageController = fxmlLoader.getController();
            playControlPageController.initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    /**
     * Timed play
     *
     * @param actionEvent
     */
    @FXML
    protected void btn_timing_play(ActionEvent actionEvent) {
        try {
            Stage primaryStage = new Stage();
            URL location = getClass().getResource("/com/eagerminds/eagermindsledsoft/TimingProgramPage.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();

            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle(CommonFunctionalBase.getResourceValue("timing_play"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

            TimingProgramPageController timingProgramPageController = fxmlLoader.getController();
            timingProgramPageController.init(getDeviceInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    /**
     * Select system upgrade
     */
    private void selectUpdateSystem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(CommonFunctionalBase.getResourceValue("terminal_update_system"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter
                ("nuzip(*.nuzip)", "*.nuzip"));
        File systemFile = fileChooser.showOpenDialog(btn_update_apk.getScene().getWindow());
        if (null == systemFile) {
            return;
        }
        NovaOpt.GetInstance().startUpdateSystem(deviceInfo.getSn(), systemFile, new OnFileTransferListener() {
            @Override
            public void onTransferred(long length) {
                showInfo("Issued: " + length + "byte");
            }

            @Override
            public void onStartTransfer() {
                showInfo("Start upgrading the system");
            }

            @Override
            public void onSuccess(Integer response) {
                showInfo("System upgrade successfully");
            }

            @Override
            public void onError(ErrorDetail error) {
                showInfo("System upgrade failed：" + error.description);
            }
        });
    }

    /**
     * Upgrade terminal software
     */
    private void selectUpdateSoftware() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(CommonFunctionalBase.getResourceValue("terminal_update_software"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter
                ("nuzip(*.nuzip)", "*.nuzip"));
        File systemFile = fileChooser.showOpenDialog(btn_update_apk.getScene().getWindow());
        if (null == systemFile) {
            return;
        }
        NovaOpt.GetInstance().startUpdateSoftware(deviceInfo.getSn(), systemFile, new OnFileTransferListener() {
            @Override
            public void onTransferred(long length) {
                showInfo("Issued: " + length + "byte");
            }

            @Override
            public void onStartTransfer() {
                showInfo("Start to upgrade the software");
            }

            @Override
            public void onSuccess(Integer response) {
                showInfo("Software upgrade successfully");
            }

            @Override
            public void onError(ErrorDetail error) {
                showInfo("Software upgrade failed：" + error.description);
            }
        });
    }

    
    /**
     *   Clear program files
     *
     * @param ae
     */
    @FXML
    protected void btn_clear_programs(ActionEvent ae) {
        List<String> choices = new ArrayList<>();
        choices.add(CommonFunctionalBase.getResourceValue("clear_all_programs"));
        choices.add(CommonFunctionalBase.getResourceValue("clear_all_other_programs"));

        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(CommonFunctionalBase.getResourceValue(
                "clear_all_programs"), choices);
        choiceDialog.setHeight(150);
        choiceDialog.setWidth(150);
        choiceDialog.setTitle(CommonFunctionalBase.getResourceValue("select_action_type"));
        choiceDialog.setContentText(null);
        choiceDialog.setHeaderText(null);
        Optional<String> result = choiceDialog.showAndWait();
        if (result.isPresent()) {
            if (CommonFunctionalBase.getResourceValue("clear_all_programs").equals(result.get())) {
                NovaOpt.GetInstance().clearAllPrograms(deviceInfo.getSn(), new OnResultListenerN<Integer, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Integer response) {
                        showInfo("Successfully delete all program files");
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showInfo(error.description);
                    }
                });
            } else {
                NovaOpt.GetInstance().clearOtherPrograms(deviceInfo.getSn(), new OnResultListenerN<Integer, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Integer response) {
                        showInfo("Successfully deleted other program files");
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showInfo(error.description);
                    }
                });
            }
        }
    }

    /**
     * 
Get storage space size
     *
     * @param actionEvent
     */
    @FXML
    protected void btnStorageData(ActionEvent actionEvent) {
        NovaOpt.GetInstance().getAvailableStorageData(deviceInfo.getSn(), new OnResultListenerN<DiskSizeBean, ErrorDetail>() {
            @Override
            public void onSuccess(IRequestBase requestBase, DiskSizeBean response) {
                String storageData =
                        CommonFunctionalBase.getResourceValue("get_storage_data") + ": Total-" + response.getDiskTotalSize() + "G，Free-" + response.getDiskAvailableSize() + "G";
                String external = "EXTERNAL";
                String internal = "LOCAL";
                int externalStorageIndex = 1;
                if (response.getStorageInfos().size() > 1) {
                    for (DiskSizeBean.StorageInfo storageInfo : response.getStorageInfos()) {
                        if (external.equals(storageInfo.getType())) {
                            storageData = storageData + "\n外部存储" + externalStorageIndex++ + ": Total-" +
                                    getSizeFormat(storageInfo.getDiskTotalSize()) + "，Free-" +
                                    getSizeFormat(storageInfo.getDiskAvailableSize());
                        }
                    }
                }
                showInfo(storageData);
            }

            @Override
            public void onError(IRequestBase requestBase, ErrorDetail error) {
                showInfo(error.description);
            }
        });
    }

    private DecimalFormat df = new DecimalFormat("#0.00");

    private String getSizeFormat(long size) {
        String sizeFormat;
        if (size < 1024) {
            sizeFormat = size + " Byte";
        } else if (size < (1024 * 1024)) {
            sizeFormat = (int) (size / (float) 1024) + " KB";
        } else if (size < (1024 * 1024 * 1024)) {
            sizeFormat = df.format(size / (float) (1024 * 1024)) + " MB";
        } else {
            sizeFormat = df.format(size / (float) (1024 * 1024 * 1024)) + " GB";
        }
        return sizeFormat;
    }

    /**
     * 
Get id based on time zone name
     *
     * @param content
     * @return
     */
    private String getTimeZoneIdByContent(String content) {
        for (int i = 0; i < listTimeZone.size(); i++) {
            if (listTimeZone.get(i).content.equals(content) || listTimeZone.get(i).id.equals(content)) {
                return listTimeZone.get(i).id;
            }
        }
        return "";
    }

    @FXML
    void OnBtnColorTempClick(ActionEvent actionEvent) {
        try {
            Stage primaryStage = new Stage();
            URL location = getClass().getResource("/fxml/ColorTemp.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();

            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle(CommonFunctionalBase.getResourceValue("btnColorTemp"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            ColorTempController controller = fxmlLoader.getController();
            controller.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void btn_program_media(ActionEvent event) {
        try {
            Stage primaryStage = new Stage();
            URL location = getClass().getResource("/com/eagerminds/eagermindsledsoft/ProgramMedia.fxml");
            FXMLLoader fxmlLoader = CommonFunctionalBase.getFxmlLoader(location);
            Parent root = fxmlLoader.load();
            ProgramMediaController programMediaController = fxmlLoader.getController();
            //programSettingPageController.setDeviceInfo(this.getDeviceInfo());
            //Pass parameters
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle(CommonFunctionalBase.getResourceValue("terminal_program_media"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(true);
            //ProgramSettingPageController psgc = fxmlLoader.getController();
            //psgc.initData();
            programMediaController.getPlayProgramMediaInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showInfo(String msgInfo) {
        Platform.runLater(() -> {
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ta_show.appendText("[" + df.format(day) + "]  " + msgInfo + "\r\n");
        });
    }
}
