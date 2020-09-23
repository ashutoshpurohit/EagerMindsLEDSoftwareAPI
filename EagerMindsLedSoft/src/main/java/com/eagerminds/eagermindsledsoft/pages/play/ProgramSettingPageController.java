/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft.pages.play;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eagerminds.eagermindsledsoft.BasicDeviceDataInformation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import novj.platform.vxkit.common.bean.programinfo.*;
import novj.platform.vxkit.handy.api.ProgramSendManager;
import novj.publ.api.ErrorCode;
import novj.publ.api.NovaOpt;
import novj.publ.api.actions.ProgramManager;
import novj.publ.api.beans.NormalTextBean;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.util.FileUtils;
import novj.publ.util.JSONUtil;
import novj.publ.util.StringUtil;
import com.eagerminds.eagermindsledsoft.CommonFunctionalBase;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.DCLOCK_NAME;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.DISPLAY_RATIO;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.GIF_NAME;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.PAGE_NAME;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.PICTURE_NAME;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.PROGRAM_LOCATION;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.STREAM_NAME;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.TEXT_NAME;
import static com.eagerminds.eagermindsledsoft.CommonFunctionalBase.VIDEO_NAME;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ProgramSettingPageController {
    public BasicDeviceDataInformation getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(BasicDeviceDataInformation deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    private BasicDeviceDataInformation deviceInfo = null;
    private static final String OPEN_PROGRAM_PTAH = File.separator + "Nova" + File.separator + "program";

    @FXML
    Button btn_create_playlist;
    @FXML
    Button btn_open_playlist;
    @FXML
    Button btn_publish_playlist;
    @FXML
    Button btn_publish_playlist_direct;
    @FXML
    Button btn_publish_insertplay;
    @FXML
    TextArea ta_info;


    @FXML
    TreeView<String> treeview;

    /*
    Picture parameter setting control
     */
    //Picture parameter setting control parent layout
    @FXML
    VBox vbox_picture_setting;
    @FXML
    TextField tf_x_pos;
    @FXML
    TextField tf_y_pos;
    @FXML
    TextField tf_pic_width;
    @FXML
    TextField tf_pic_height;
    @FXML
    TextField tf_pic_play_times;
    @FXML
    TextField tf_pic_play_duration;
    @FXML
    Button btn_save_picture_params;

    /*
    Video parameter setting control
     */
    @FXML
    VBox vbox_video_setting;
    @FXML
    TextField tf_video_x_pos;
    @FXML
    TextField tf_video_y_pos;
    @FXML
    TextField tf_video_width;
    @FXML
    TextField tf_video_height;
    @FXML
    TextField tf_video_play_times;
    @FXML
    Button btn_save_video_params;

    /*
    GIF parameter setting control
     */
    @FXML
    VBox vbox_gif_setting;
    @FXML
    TextField tf_gif_x_pos;
    @FXML
    TextField tf_gif_y_pos;
    @FXML
    TextField tf_gif_width;
    @FXML
    TextField tf_gif_height;
    @FXML
    TextField tf_gif_play_times;
    @FXML
    TextField tf_gif_play_duration;
    @FXML
    ColorPicker cp_gif_bgcolor;
    @FXML
    Button btn_save_gif_params;

    /*
    Text parameter setting control
     */
    @FXML
    VBox vbox_text_setting;
    @FXML
    TextField tf_text_x_pos;
    @FXML
    TextField tf_text_width;
    @FXML
    TextField tf_text_y_pos;
    @FXML
    TextField tf_text_height;
    @FXML
    ComboBox cb_text_font;
    @FXML
    TextField tf_text_fontsize;
    @FXML
    ColorPicker cp_text_fgcolor;
    @FXML
    ColorPicker cp_text_bgcolor;
    @FXML
    TextField tf_text_content;
    @FXML
    Button btn_save_text_params;

    /*
    Digital clock
     */
    @FXML
    VBox vbox_dclock_setting;
    @FXML
    TextField tf_dclock_x_pos;
    @FXML
    TextField tf_dclock_width;
    @FXML
    TextField tf_dclock_y_pos;
    @FXML
    TextField tf_dclock_height;
    @FXML
    TextField tf_dclock_play_duration;
    @FXML
    Button btn_save_dclock_params;
    @FXML
    CheckBox cb_multiple_line;
    @FXML
    CheckBox cb_dclock_4year;
    @FXML
    CheckBox cb_dclock_year;
    @FXML
    CheckBox cb_dclock_month;
    @FXML
    CheckBox cb_dclock_day;
    @FXML
    CheckBox cb_dclock_hour;
    @FXML
    CheckBox cb_dclock_minute;
    @FXML
    CheckBox cb_dclock_second;
    @FXML
    CheckBox cb_dclock_week;
    @FXML
    CheckBox cb_dclock_time_style;
    @FXML
    CheckBox cb_dclock_show_moraft;
    @FXML
    TextField tf_dclock_text_font;
    @FXML
    TextField tf_dclock_text_size;

    /*
    stream media
     */
    @FXML
    VBox vbox_stream_setting;
    @FXML
    TextField tf_stream_x, tf_stream_y, tf_stream_width, tf_stream_height;
    @FXML
    TextField tf_stream_times, tf_stream_duration, tf_stream_volume, tf_stream_address;
    @FXML
    TextField tf_stream_protocol, tf_stream_media_type;

    @FXML
    VBox vbox_insertplay_setting;
    @FXML
    TextField tf_start_year, tf_start_month, tf_start_day, tf_start_hour, tf_start_minute, tf_start_second;
    @FXML
    TextField tf_end_year, tf_end_month, tf_end_day, tf_end_hour, tf_end_minute, tf_end_second;
    @FXML
    ColorPicker cp_program_bgcolor;


    private int mCurrentClickPageIndex = 0;
    private int mCurrentClickMediaParentIndex = 0;
    private int mCurrentClickMediaIndex = 0;
    private Map<Integer, Integer> mPageIdMap = new HashMap<>();
    private Map<Integer, Map<Integer, Integer>> mWidgetIdMap = new HashMap<>();

    private TreeItem<String> treeRoot;
    private boolean insertPlay = false;

    public void showInfo(String sInfo) {
        Platform.runLater(() -> {
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ta_info.appendText("[" + df.format(day) + "]  " + sInfo + "\r\n");
        });
    }

    //page parameter initialization
    public void initData() {
        showPageOrMediaSetting(-1);
        initPictureData();
        initVideoData();
        initGifData();
        initTextData();
        initDclockData();
        initInsertPlay();
    }

    private void initInsertPlay() {
        ObservableList<String> fontOptions = FXCollections.observableArrayList(CommonFunctionalBase
                        .getResourceValue("yes"),
                CommonFunctionalBase.getResourceValue("no"));
        cb_bgprogram_play.setItems(fontOptions);
        cb_bgprogram_play.getSelectionModel().select(0);
    }


    private void initPictureData() {
        tf_x_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_x_pos.setText(oldValue);
            }
        });
        tf_y_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_y_pos.setText(oldValue);
            }
        });
        tf_pic_width.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_pic_width.setText(oldValue);
            }
        });
        tf_pic_height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_pic_height.setText(oldValue);
            }
        });
        tf_pic_play_times.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || tf_pic_play_times.getText().length() == 0 ||
                    Long.parseLong(tf_pic_play_times.getText()) == 0 || Long.parseLong
                    (tf_pic_play_times.getText()) > Integer.MAX_VALUE) {
                tf_pic_play_times.setText(oldValue);
            }
        });
        tf_pic_play_duration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || tf_pic_play_duration.getText().length() == 0 ||
                    Long.parseLong(tf_pic_play_duration.getText()) == 0 || Long.parseLong
                    (tf_pic_play_duration.getText()) > Integer.MAX_VALUE) {
                tf_pic_play_duration.setText(oldValue);
            }
        });
    }

    private void initVideoData() {
        tf_video_x_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_video_x_pos.setText(oldValue);
            }
        });
        tf_video_y_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_video_y_pos.setText(oldValue);
            }
        });
        tf_video_width.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_video_width.setText(oldValue);
            }
        });
        tf_video_height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_video_height.setText(oldValue);
            }
        });
        tf_video_play_times.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || tf_video_play_times.getText().length() == 0 ||
                    Long.parseLong(tf_video_play_times.getText()) == 0 || Long.parseLong
                    (tf_video_play_times.getText()) > Integer.MAX_VALUE) {
                tf_video_play_times.setText(oldValue);
            }
        });
    }

    private void initGifData() {
        tf_gif_x_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_gif_x_pos.setText(oldValue);
            }
        });
        tf_gif_y_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_gif_y_pos.setText(oldValue);
            }
        });
        tf_gif_width.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_gif_width.setText(oldValue);
            }
        });
        tf_gif_height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_gif_height.setText(oldValue);
            }
        });
        tf_gif_play_times.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || tf_gif_play_times.getText().length() == 0 ||
                    Long.parseLong(tf_gif_play_times.getText()) == 0 || Long.parseLong
                    (tf_gif_play_times.getText()) > Integer.MAX_VALUE) {
                tf_gif_play_times.setText(oldValue);
            }
        });
        tf_gif_play_duration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || tf_gif_play_duration.getText().length() == 0 ||
                    Long.parseLong(tf_gif_play_duration.getText()) == 0 || Long.parseLong
                    (tf_gif_play_duration.getText()) > Integer.MAX_VALUE) {
                tf_gif_play_duration.setText(oldValue);
            }
        });
    }

    private void initTextData() {
        tf_text_x_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_text_x_pos.setText(oldValue);
            }
        });

        tf_text_width.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_text_width.setText(oldValue);
            }
        });
        tf_text_y_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_text_y_pos.setText(oldValue);
            }
        });
        tf_text_height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_text_height.setText(oldValue);
            }
        });
        tf_text_fontsize.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || tf_text_fontsize.getText().length() == 0 ||
                    Long.parseLong(tf_text_fontsize.getText()) == 0 || Long.parseLong
                    (tf_text_fontsize.getText()) > Integer.MAX_VALUE) {
                tf_text_fontsize.setText(oldValue);
            }
        });
        // Choose font
        ObservableList<String> fontOptions = FXCollections.observableArrayList(CommonFunctionalBase
                        .getResourceValue("widget_text_st"),
                CommonFunctionalBase.getResourceValue("widget_text_wryh"));
        cb_text_font.setItems(fontOptions);
        cb_text_font.getSelectionModel().select(0);
    }

    private void initDclockData() {
        tf_dclock_x_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_gif_x_pos.setText(oldValue);
            }
        });
        tf_dclock_y_pos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_gif_y_pos.setText(oldValue);
            }
        });
        tf_dclock_width.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_gif_width.setText(oldValue);
            }
        });
        tf_dclock_height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf_gif_height.setText(oldValue);
            }
        });
        tf_dclock_play_duration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || tf_gif_play_duration.getText().length() == 0 ||
                    Long.parseLong(tf_gif_play_duration.getText()) == 0 || Long.parseLong
                    (tf_gif_play_duration.getText()) > Integer.MAX_VALUE) {
                tf_gif_play_duration.setText(oldValue);
            }
        });


        cb_multiple_line.setSelected(true);
        cb_dclock_4year.setSelected(true);
        cb_dclock_year.setSelected(true);
        cb_dclock_month.setSelected(true);
        cb_dclock_day.setSelected(true);
        cb_dclock_hour.setSelected(true);
        cb_dclock_minute.setSelected(true);
        cb_dclock_second.setSelected(true);
        cb_dclock_week.setSelected(true);
        cb_dclock_time_style.setSelected(true);
        cb_dclock_show_moraft.setSelected(true);

        cb_dclock_hour.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    cb_dclock_time_style.setDisable(false);
                    cb_dclock_show_moraft.setDisable(true);
                } else {
                    cb_dclock_time_style.setSelected(false);
                    cb_dclock_time_style.setDisable(true);
                    cb_dclock_show_moraft.setSelected(false);
                    cb_dclock_show_moraft.setDisable(true);
                }
            }
        });
        cb_dclock_time_style.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    cb_dclock_show_moraft.setDisable(false);
                } else {
                    cb_dclock_show_moraft.setSelected(false);
                    cb_dclock_show_moraft.setDisable(true);
                }
            }
        });
        cb_dclock_year.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    cb_dclock_4year.setDisable(false);
                } else {
                    cb_dclock_4year.setSelected(false);
                    cb_dclock_4year.setDisable(true);
                }
            }
        });

    }

    @FXML
    private void onCreatePlaylist(MouseEvent me) {
        if (null != treeview.getRoot()) {
            return;
        }
        List<String> choices = new ArrayList<>();
        choices.add(CommonFunctionalBase.getResourceValue("create_normal_program"));
        choices.add(CommonFunctionalBase.getResourceValue("create_insert_program"));

        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(CommonFunctionalBase.getResourceValue(
                "create_normal_program"), choices);
        choiceDialog.setHeight(150);
        choiceDialog.setWidth(150);
        choiceDialog.setTitle(CommonFunctionalBase.getResourceValue("select_action_type"));
        choiceDialog.setContentText(null);
        choiceDialog.setHeaderText(null);
        Optional<String> selectResult = choiceDialog.showAndWait();
        if (!selectResult.isPresent()) {
            return;
        }
        if (CommonFunctionalBase.getResourceValue("create_insert_program").equals(selectResult.get())) {
            insertPlay = true;
            btn_publish_playlist.setDisable(true);
            btn_publish_insertplay.setDisable(false);
        } else {
            insertPlay = false;
            btn_publish_insertplay.setDisable(true);
            btn_publish_playlist.setDisable(false);
        }

        TextInputDialog tid = new TextInputDialog();
        tid.setTitle(CommonFunctionalBase.getResourceValue("alert_title_confirm"));
        tid.setHeaderText(CommonFunctionalBase.getResourceValue("program_name"));
        tid.setContentText(CommonFunctionalBase.getResourceValue("input_program_name"));
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
        if (!result.get().matches("^[0-9a-zA-Z]{2,10}$")) {
            showInfo(CommonFunctionalBase.getResourceValue("program_name_format_error"));
            return;
        }

        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(CommonFunctionalBase.getResourceValue("program_save_path"));
        File direciory = dc.showDialog(btn_create_playlist.getScene().getWindow());
        if (null == direciory) {
            return;
        }
        PROGRAM_LOCATION = direciory.getAbsolutePath();
        treeRoot = new TreeItem<>(result.get());
        treeview.setRoot(treeRoot);
        treeRoot.setExpanded(true);
        //create program
        NovaOpt.GetInstance().createProgram(treeRoot.getValue(), 512, 512);
        setTreeviewListener();
    }


    private void setTreeviewListener() {
        EventHandler<MouseEvent> eventEventHandler = event -> {
            Node node = event.getPickResult().getIntersectedNode();
            // Accept clicks only on node cells, and not on empty spaces of the TreeView
            if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText()
                    != null)) {
                TreeItem<String> clickTreeItem = treeview.getSelectionModel().getSelectedItem();
                TreeItem<String> parentClickItem = clickTreeItem.getParent();
                String name = clickTreeItem.getValue();
                String parentName = null;
                if (null != parentClickItem) {
                    parentName = parentClickItem.getValue();
                }
                if (event.getButton().name().equals("SECONDARY")) {
                    //right click
                    String finalName = name;
                    String finalParentName = parentName;
                    MenuItem pageAdd = new MenuItem(CommonFunctionalBase.getResourceValue("add_page_to_program"));
                    int childrenSize = clickTreeItem.getChildren().size();
                    int childClickItemIndex = childrenSize > 0 ? (Integer.parseInt(clickTreeItem
                            .getChildren().get(childrenSize - 1).getValue().split("_")[1])) + 1 : 1;
                    pageAdd.setOnAction(event1 -> {
                        showPageOrMediaSetting(-1);
                        TreeItem<String> pageItem = new TreeItem<>(PAGE_NAME + "_" +
                                childClickItemIndex);
                        pageItem.setExpanded(true);
                        clickTreeItem.getChildren().addAll(pageItem);
                        mPageIdMap.put(childClickItemIndex, NovaOpt.GetInstance().addPage());
                    });
                    MenuItem pageDelete = new MenuItem(CommonFunctionalBase.getResourceValue("delete_widget"));
                    pageDelete.setOnAction(event1 -> {
                        showPageOrMediaSetting(-1);
                        if (finalName.startsWith(PAGE_NAME)) {
                            int pageIndex = Integer.parseInt(finalName.split("_")[1]);
                            NovaOpt.GetInstance().deletePage(mPageIdMap.get(pageIndex));
                            clickTreeItem.getChildren().remove(0, clickTreeItem.getChildren()
                                    .size());
                            parentClickItem.getChildren().remove(clickTreeItem);
                            if (mWidgetIdMap.containsKey(pageIndex)) {
                                //Traverse all widgets under the deleted page and delete them togethe
                                for (Map.Entry<Integer, Integer> entry : mWidgetIdMap.get
                                        (pageIndex).entrySet()) {
                                    NovaOpt.GetInstance().deleteWidget(mPageIdMap.get(pageIndex),
                                            entry.getValue());
                                }
                                //Delete all widget id records corresponding to page from the map
                                mWidgetIdMap.remove(pageIndex);
                            }
                            // Delete the record of page id from the map
                            mPageIdMap.remove(pageIndex);

                        } else {
                            int pageIndex = Integer.parseInt(finalParentName.split("_")[1]);
                            int mediaIndex = Integer.parseInt(finalName.split("_")[1]);
                            parentClickItem.getChildren().remove(clickTreeItem);
                            //Delete the widget corresponding to the specified pageid and mediaid
                            NovaOpt.GetInstance().deleteWidget(mPageIdMap.get(pageIndex),
                                    mWidgetIdMap.get(pageIndex).get(mediaIndex));
                            //Delete the id record corresponding to the widget to be deleted from the map
                            mWidgetIdMap.get(pageIndex).remove(mediaIndex);
                        }
                    });
                    MenuItem picAdd = new MenuItem(CommonFunctionalBase.getResourceValue("add_pic_to_page"));
                    picAdd.setOnAction(event13 -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Images(*.jpg,*.png)",
                                        "*.jpg", "*.png"));
                        File chooserFile = fileChooser.showOpenDialog
                                (btn_create_playlist.getScene().getWindow());
                        if (null == chooserFile) {
                            return;
                        }

                        showPageOrMediaSetting(-1);
                        clickTreeItem.getChildren().addAll(new TreeItem<>(PICTURE_NAME +
                                "_" + childClickItemIndex));
                        int pageIndex = Integer.parseInt(finalName.split("_")[1]);
                        addWidget(mPageIdMap.get(pageIndex), pageIndex, childClickItemIndex,
                                ProgramManager.WidgetMediaType.PICTURE, chooserFile
                                        .getAbsolutePath());
                    });
                    MenuItem videoAdd = new MenuItem(CommonFunctionalBase.getResourceValue("add_video_to_page"));
                    videoAdd.setOnAction(event12 -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.getExtensionFilters().addAll(new FileChooser
                                .ExtensionFilter(
                                "Videos(*.mp4,*.wmv,*.avi,*.rmvb)",
                                "*.mp4",
                                "*.wmv",
                                "*.avi",
                                "*.rmvb"));
                        File chooserFile = fileChooser.showOpenDialog
                                (btn_create_playlist.getScene().getWindow());
                        if (null == chooserFile) {
                            return;
                        }
                        showPageOrMediaSetting(-1);
                        clickTreeItem.getChildren().addAll(new TreeItem<>(VIDEO_NAME +
                                "_" + childClickItemIndex));

                        int pageIndex = Integer.parseInt(finalName.split("_")[1]);
                        addWidget(mPageIdMap.get(pageIndex), pageIndex, childClickItemIndex,
                                ProgramManager.WidgetMediaType.VIDEO, chooserFile
                                        .getAbsolutePath());
                    });
                    MenuItem gifAdd = new MenuItem(CommonFunctionalBase.getResourceValue("add_gif_to_page"));
                    gifAdd.setOnAction(event14 -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter
                                ("GIF(*.gif)", "*.gif"));
                        File chooserFile = fileChooser.showOpenDialog
                                (btn_create_playlist.getScene().getWindow());
                        if (null == chooserFile) {
                            return;
                        }
                        showPageOrMediaSetting(-1);
                        clickTreeItem.getChildren().addAll(new TreeItem<>(GIF_NAME +
                                "_" + childClickItemIndex));
                        int pageIndex = Integer.parseInt(finalName.split("_")[1]);
                        addWidget(mPageIdMap.get(pageIndex), pageIndex, childClickItemIndex,
                                ProgramManager.WidgetMediaType.GIF, chooserFile
                                        .getAbsolutePath());
                    });

                    MenuItem textAdd = new MenuItem(CommonFunctionalBase.getResourceValue("add_text_to_page"));
                    textAdd.setOnAction(event15 -> {
                        showPageOrMediaSetting(-1);
                        clickTreeItem.getChildren().addAll(new TreeItem<>(TEXT_NAME +
                                "_" + childClickItemIndex));
                        int pageIndex = Integer.parseInt(finalName.split("_")[1]);
                        addWidget(mPageIdMap.get(pageIndex), pageIndex, childClickItemIndex,
                                ProgramManager.WidgetMediaType.ARCH_TEXT, new NormalTextBean
                                        (CommonFunctionalBase.getResourceValue("widget_text_filled_content")));
                    });

                    MenuItem dClockAdd = new MenuItem(CommonFunctionalBase.getResourceValue("add_digital_clock"));
                    dClockAdd.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            showPageOrMediaSetting(-1);
                            clickTreeItem.getChildren().addAll(new TreeItem<>(DCLOCK_NAME + "_" + childClickItemIndex));
                            int pageIndex = Integer.parseInt(finalName.split("_")[1]);
                            addWidget(mPageIdMap.get(pageIndex), pageIndex, childClickItemIndex,
                                    ProgramManager.WidgetMediaType.DIGITAL_CLOCK, new MetaDataDigitalClockV2());
                        }
                    });

                    MenuItem streamAdd = new MenuItem(CommonFunctionalBase.getResourceValue("add_stream_media"));
                    streamAdd.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            showPageOrMediaSetting(-1);
                            clickTreeItem.getChildren().addAll(new TreeItem<>(STREAM_NAME + "_" + childClickItemIndex));
                            int pageIndex = Integer.parseInt(finalName.split("_")[1]);
                            addWidget(mPageIdMap.get(pageIndex), pageIndex, childClickItemIndex,
                                    ProgramManager.WidgetMediaType.STREAM, new MetaDataStream());
//                                    
                        }
                    });

                    ContextMenu cm = new ContextMenu();
                    if (name.equals(treeRoot.getValue()) && null == parentClickItem) {
                        cm.getItems().addAll(pageAdd);
                    } else if (name.startsWith(PAGE_NAME)) {
                        cm.getItems().addAll(picAdd, videoAdd, gifAdd, textAdd, dClockAdd, streamAdd, pageDelete);
                    } else if (name.startsWith(PICTURE_NAME) ||
                            name.startsWith(VIDEO_NAME) ||
                            name.startsWith(GIF_NAME) ||
                            name.startsWith(TEXT_NAME) ||
                            name.startsWith(DCLOCK_NAME) ||
                            name.startsWith(STREAM_NAME)) {
                        cm.getItems().addAll(pageDelete);
                    }
                    cm.show(node, Side.BOTTOM, 0, 0);
                } else if (event.getButton().name().equals("PRIMARY")) {//click mouse left key
                    if (name.startsWith(PAGE_NAME)) {
                        showPageOrMediaSetting(-1);
                        mCurrentClickPageIndex = Integer.parseInt(name.split("_")[1]);
                    } else if (name.startsWith(PICTURE_NAME)) {
                        showPageOrMediaSetting(1);
                        mCurrentClickMediaParentIndex = Integer.parseInt(
                                parentName.split("_")[1]);
                        mCurrentClickMediaIndex = Integer.parseInt(name.split("_")[1]);
                        restorePictureConfig();
                    } else if (name.startsWith(VIDEO_NAME)) {
                        showPageOrMediaSetting(2);
                        mCurrentClickMediaParentIndex = Integer.parseInt(
                                parentName.split("_")[1]);
                        mCurrentClickMediaIndex = Integer.parseInt(name.split("_")[1]);
                        restoreVideoConfig();
                    } else if (name.startsWith(GIF_NAME)) {
                        showPageOrMediaSetting(3);
                        mCurrentClickMediaParentIndex = Integer.parseInt(
                                parentName.split("_")[1]);
                        mCurrentClickMediaIndex = Integer.parseInt(name.split("_")[1]);
                        restoreGifConfig();
                    } else if (name.startsWith(TEXT_NAME)) {
                        showPageOrMediaSetting(4);
                        mCurrentClickMediaParentIndex = Integer.parseInt(
                                parentName.split("_")[1]);
                        mCurrentClickMediaIndex = Integer.parseInt(name.split("_")[1]);
                        restoreTextConfig();
                    } else if (name.startsWith(DCLOCK_NAME)) {
                        showPageOrMediaSetting(5);
                        mCurrentClickMediaParentIndex = Integer.parseInt(parentName.split("_")[1]);
                        mCurrentClickMediaIndex = Integer.parseInt(name.split("_")[1]);
                        restoreDClockConfig();
                    } else if (name.startsWith(STREAM_NAME)) {
                        showPageOrMediaSetting(6);
                        mCurrentClickMediaParentIndex = Integer.parseInt(parentName.split("_")[1]);
                        mCurrentClickMediaIndex = Integer.parseInt(name.split("_")[1]);
                        restoreStreamConfig();
                    } else if (name.equals(treeRoot.getValue()) && null == parentClickItem) {
                        if (insertPlay) {
                            showPageOrMediaSetting(0);
                        }
                    }
                } else if (event.getButton().name().equals("MIDDLE")) {//click mouse middle
                    //no action at present
                }
            }
        };
        treeview.addEventHandler(MouseEvent.MOUSE_CLICKED, eventEventHandler);
    }


    @FXML
    protected void onOpenProgram(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select program path");
        File direciory = directoryChooser.showDialog(btn_open_playlist.getScene().getWindow());
        if (null == direciory) {
            return;
        }
        String programPath = direciory.getAbsolutePath();
        int code = NovaOpt.GetInstance().readProgram(programPath);
        if (ErrorCode.Err_None == code) {
            PlaySolutionRelation playSolutionRelation =
                    NovaOpt.GetInstance().getPlaySolutionRelation();
            if (null != playSolutionRelation) {
                if (playSolutionRelation.getRelations().get(0).getTaskId() == 88888888) {
                    insertPlay = true;
                    btn_publish_playlist.setDisable(true);
                    btn_publish_insertplay.setDisable(false);
                } else {
                    insertPlay = false;
                    btn_publish_insertplay.setDisable(true);
                    btn_publish_playlist.setDisable(false);
                }
            }
            Playlist playlist = NovaOpt.GetInstance().getPlayList();
            if (null != playlist) {
                if (programPath.contains(OPEN_PROGRAM_PTAH)) {
                    PROGRAM_LOCATION = programPath.substring(0, programPath.indexOf(OPEN_PROGRAM_PTAH));
                } else {
                    PROGRAM_LOCATION = programPath;
                }
                showPageOrMediaSetting(-1);
                treeRoot = new TreeItem<>(playlist.getName());
                treeview.setRoot(treeRoot);
                treeRoot.setExpanded(true);
                setTreeviewListener();
                List<Scene> scenes = playlist.getSceneItems();
                for (int pageIndex = 1; pageIndex <= scenes.size(); pageIndex++) {
                    TreeItem<String> pageItem = new TreeItem<>(PAGE_NAME + "_" + pageIndex);
                    pageItem.setExpanded(true);
                    treeview.getRoot().getChildren().addAll(pageItem);
                    mPageIdMap.put(pageIndex, scenes.get(pageIndex - 1).getId());
                    List<WidgetContainer> widgetContainerList =
                            scenes.get(pageIndex - 1).getPage().getWidgetContainers();
                    for (int widgetIndex = 1; widgetIndex <= widgetContainerList.size(); widgetIndex++) {
                        WidgetContainer widgetContainer = widgetContainerList.get(widgetIndex - 1);

                        String widgetType =
                                widgetContainer.getContents().getWidgets().get(0).getType();
                        switch (widgetType) {
                            case MediaType.PICTURE:
                                pageItem.getChildren().addAll(new TreeItem<>(PICTURE_NAME +
                                        "_" + widgetIndex));
                                break;
                            case MediaType.VIDEO:
                                pageItem.getChildren().addAll(new TreeItem<>(VIDEO_NAME +
                                        "_" + widgetIndex));
                                break;
                            case MediaType.GIF:
                                pageItem.getChildren().addAll(new TreeItem<>(GIF_NAME +
                                        "_" + widgetIndex));
                                break;
                            case MediaType.ARCH_TEXT:
                                pageItem.getChildren().addAll(new TreeItem<>(TEXT_NAME +
                                        "_" + widgetIndex));
                                Object textobject =
                                        widgetContainer.getContents().getWidgets().get(0).getMetadata();
                                MetaDataArchText metaDataArchText =
                                        JSONUtil.parseJsonstring(textobject.toString(), MetaDataArchText.class);
                                widgetContainer.getContents().getWidgets().get(0).setMetadata(metaDataArchText);
                                break;
                            case MediaType.DIGITAL_CLOCK:
                                pageItem.getChildren().addAll(new TreeItem<>(DCLOCK_NAME +
                                        "_" + widgetIndex));
                                Object dclockobject =
                                        widgetContainer.getContents().getWidgets().get(0).getMetadata();
                                MetaDataDigitalClockV2 metaDataDigitalClockV2 =
                                        JSONUtil.parseJsonstring(dclockobject.toString(), MetaDataDigitalClockV2.class);
                                widgetContainer.getContents().getWidgets().get(0).setMetadata(metaDataDigitalClockV2);
                                break;
                            case MediaType.STREAM_MEDIA:
                                pageItem.getChildren().addAll(new TreeItem<>(STREAM_NAME +
                                        "_" + widgetIndex));
                                Object streamObject =
                                        widgetContainer.getContents().getWidgets().get(0).getMetadata();
                                MetaDataStream metaDataStream =
                                        JSONUtil.parseJsonstring(streamObject.toString(), MetaDataStream.class);
                                widgetContainer.getContents().getWidgets().get(0).setMetadata(metaDataStream);
                                break;
                            default:
                                break;
                        }
                        if (mWidgetIdMap.containsKey(pageIndex)) {
                            mWidgetIdMap.get(pageIndex).put(widgetIndex,
                                    widgetContainer.getId());
                        } else {
                            Map<Integer, Integer> tempMap = new HashMap<>();
                            tempMap.put(widgetIndex, widgetContainer.getId());
                            mWidgetIdMap.put(pageIndex, tempMap);
                        }
                    }
                }
            }

            Constraints constraints = NovaOpt.GetInstance().getConstraints();
            if (null != constraints && constraints.getConstraints() != null && constraints.getConstraints().size() > 0) {
                ConstraintScheduler constraintScheduler = constraints.getConstraints().get(0);
                try {
                    String[] ymd = constraintScheduler.getStartTime().substring(0, constraintScheduler.getStartTime().indexOf('T')).split("-");
                    String[] hms = constraintScheduler.getStartTime().substring(constraintScheduler.getStartTime().indexOf('T') + 1,
                            constraintScheduler.getStartTime().indexOf('Z')).split(":");
                    tf_start_year.setText(ymd[0]);
                    tf_start_month.setText(ymd[1]);
                    tf_start_day.setText(ymd[2]);
                    tf_start_hour.setText(hms[0]);
                    tf_start_minute.setText(hms[1]);
                    tf_start_second.setText(hms[2]);
                    ymd = constraintScheduler.getEndTime().substring(0, constraintScheduler.getEndTime().indexOf('T')).split("-");
                    hms = constraintScheduler.getEndTime().substring(constraintScheduler.getEndTime().indexOf('T') + 1,
                            constraintScheduler.getEndTime().indexOf('Z')).split(":");
                    tf_end_year.setText(ymd[0]);
                    tf_end_month.setText(ymd[1]);
                    tf_end_day.setText(ymd[2]);
                    tf_end_hour.setText(hms[0]);
                    tf_end_minute.setText(hms[1]);
                    tf_end_second.setText(hms[2]);

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }
    }

    private void addWidget(int pageId, int pageIndex, int widgetIndex, ProgramManager
            .WidgetMediaType
            mediaType, Object param) {
        int widgetId = NovaOpt.GetInstance().addWidget(pageId, mediaType, param);
        if (mWidgetIdMap.containsKey(pageIndex)) {
            mWidgetIdMap.get(pageIndex).put(widgetIndex, widgetId);
        } else {
            Map<Integer, Integer> tempMap = new HashMap<>();
            tempMap.put(widgetIndex, widgetId);
            mWidgetIdMap.put(pageIndex, tempMap);
        }
    }


    /**
     * Publish the show to the card
     *
     * @param me
     */
    @FXML
    private void onPublishProgram(MouseEvent me) {
        //Publish the show to the card
        if (null == treeRoot) {
            return;
        }
        try {
            if (0 == NovaOpt.GetInstance().makeProgram(PROGRAM_LOCATION)) {
                try {
                    InetAddress ia = InetAddress.getLocalHost();
                    NovaOpt.GetInstance().startTransfer(deviceInfo.getSn(),
                            PROGRAM_LOCATION,
                            treeRoot.getValue(),
                            getLocalMac(ia),
                            true,
                            new ProgramSendManager.OnProgramTransferListener() {

                                @Override
                                public void onStarted() {
                                    showInfo(CommonFunctionalBase.getResourceValue("start_transfer"));
                                }

                                @Override
                                public void onTransfer(long l, long l1) {
                                    showInfo(CommonFunctionalBase.getResourceValue("publishing"));
                                }

                                @Override
                                public void onError(ErrorDetail errorDetail) {
                                    showInfo(CommonFunctionalBase.getResourceValue("publish_failed") +
                                            errorDetail.description);
                                }

                                @Override
                                public void onCompleted() {
                                    showInfo(CommonFunctionalBase.getResourceValue("complete_published"));
                                }

                                @Override
                                public void onAborted() {
                                    showInfo(CommonFunctionalBase.getResourceValue("publish_abort"));
                                }
                            });
                } catch (Exception ex) {
                    showInfo(ex.getMessage() + "|" + ex.getLocalizedMessage() + "|" + ex.hashCode
                            ());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showInfo(e.getMessage());
        }
    }

    /**
     * Publish the show to the card
     *
     * @param me
     */
    @FXML
    private void onPublishProgramDirect(MouseEvent me) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select path");
        File directory =
                directoryChooser.showDialog(btn_publish_playlist_direct.getScene().getWindow());
        if (null == directory) {
            return;
        }
        DirectoryChooser programDirectory = new DirectoryChooser();
        programDirectory.setTitle("Choose a show");
        File program =
                programDirectory.showDialog(btn_publish_playlist_direct.getScene().getWindow());
        if (null == program) {
            return;
        }

        try {
            try {
                InetAddress ia = InetAddress.getLocalHost();
                NovaOpt.GetInstance().startTransfer(deviceInfo.getSn(),
                        directory.getAbsolutePath(),
                        program.getName(),
                        getLocalMac(ia),
                        true,
                        new ProgramSendManager.OnProgramTransferListener() {

                            @Override
                            public void onStarted() {
                                showInfo(CommonFunctionalBase.getResourceValue("start_transfer"));
                            }

                            @Override
                            public void onTransfer(long l, long l1) {
                                showInfo(CommonFunctionalBase.getResourceValue("publishing"));
                            }

                            @Override
                            public void onError(ErrorDetail errorDetail) {
                                showInfo(CommonFunctionalBase.getResourceValue("publish_failed") +
                                        errorDetail.description);
                            }

                            @Override
                            public void onCompleted() {
                                showInfo(CommonFunctionalBase.getResourceValue("complete_published"));
                            }

                            @Override
                            public void onAborted() {
                                showInfo(CommonFunctionalBase.getResourceValue("publish_abort"));
                            }
                        });
            } catch (Exception ex) {
                showInfo(ex.getMessage() + "|" + ex.getLocalizedMessage() + "|" + ex.hashCode
                        ());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showInfo(e.getMessage());
        }
    }

    private void restorePictureConfig() {
        pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
        mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
                (mCurrentClickMediaIndex);
        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        Layout layout = widget.getLayout();
        tf_x_pos.setText(layout.getX().endsWith("%") ? "0" : layout.getX());
        tf_y_pos.setText(layout.getY().endsWith("%") ? "0" : layout.getY());
        tf_pic_width.setText(layout.getWidth().endsWith("%") ? "100" : layout.getWidth());
        tf_pic_height.setText(layout.getHeight().endsWith("%") ? "100" : layout.getHeight());
        tf_pic_play_times.setText(widget.getRepeatCount() + "");
        tf_pic_play_duration.setText(widget.getDuration() + "");
    }

    private int pageId;
    private int mediaId;

    @FXML
    private void onSavePictureParams(ActionEvent actionEvent) {
        try {
//            int pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
//            int mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
//                    (mCurrentClickMediaIndex);
            Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
            Layout layout = new Layout(tf_x_pos.getText(), tf_y_pos.getText(), tf_pic_width
                    .getText(), tf_pic_height.getText());
            widget.setDisplayRatio(DISPLAY_RATIO);
            widget.setLayout(layout);
            widget.setRepeatCount(Integer.parseInt(tf_pic_play_times.getText()));
            widget.setDuration(Integer.parseInt(tf_pic_play_duration.getText()));
            NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);
            showInfo(CommonFunctionalBase.getResourceValue("success_save_pic_configuration"));
        } catch (Exception ex) {
            showInfo(CommonFunctionalBase.getResourceValue("fail_save_pic_configuration") + ex.toString());
        }
    }

    @FXML
    protected void onSwitchPicture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images(*.jpg,*.png)",
                        "*.jpg", "*.png"));
        File chooserFile = fileChooser.showOpenDialog
                (btn_create_playlist.getScene().getWindow());
        if (null == chooserFile) {
            return;
        }
        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        widget.setOriginalDataSource(chooserFile.getAbsolutePath());
        widget.setFilesize(chooserFile.length());
        widget.setName(chooserFile.getName());
        String md5 = FileUtils.getMD5(chooserFile);
        String suffix = chooserFile.getName().substring(chooserFile.getName().lastIndexOf("."));
        widget.setDataSource(md5 + suffix);
        NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);

    }

    private void restoreVideoConfig() {
        pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
        mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
                (mCurrentClickMediaIndex);
        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        Layout layout = widget.getLayout();
        tf_video_x_pos.setText(layout.getX().endsWith("%") ? "0" : layout.getX());
        tf_video_y_pos.setText(layout.getY().endsWith("%") ? "0" : layout.getY());
        tf_video_width.setText(layout.getWidth().endsWith("%") ? "100" : layout.getWidth());
        tf_video_height.setText(layout.getHeight().endsWith("%") ? "100" : layout.getHeight());
        tf_video_play_times.setText(widget.getRepeatCount() + "");
    }

    @FXML
    protected void onSaveVideoParams(ActionEvent actionEvent) {
        try {
//            int pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
//            int mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
//                    (mCurrentClickMediaIndex);
            Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
            Layout layout = new Layout(tf_video_x_pos.getText(), tf_video_y_pos.getText(),
                    tf_video_width.getText(), tf_video_height.getText());
            widget.setDisplayRatio(DISPLAY_RATIO);
            widget.setLayout(layout);
            widget.setRepeatCount(Integer.parseInt(tf_video_play_times.getText()));
            NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);
            showInfo(CommonFunctionalBase.getResourceValue("success_save_video_configuration"));
        } catch (Exception ex) {
            showInfo(CommonFunctionalBase.getResourceValue("fail_save_video_configuration") + ex.toString());
        }

    }

    @FXML
    protected void onSwitchVideo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser
                .ExtensionFilter(
                "Videos(*.mp4,*.wmv,*.avi,*.rmvb)",
                "*.mp4",
                "*.wmv",
                "*.avi",
                "*.rmvb"));
        File chooserFile = fileChooser.showOpenDialog
                (btn_create_playlist.getScene().getWindow());
        if (null == chooserFile) {
            return;
        }

        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        widget.setOriginalDataSource(chooserFile.getAbsolutePath());
        widget.setFilesize(chooserFile.length());
        widget.setName(chooserFile.getName());
        String md5 = FileUtils.getMD5(chooserFile);
        String suffix = chooserFile.getName().substring(chooserFile.getName().lastIndexOf("."));
        widget.setDataSource(md5 + suffix);
        NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);
    }

    private void restoreGifConfig() {
        pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
        mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
                (mCurrentClickMediaIndex);
        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        Layout layout = widget.getLayout();
        tf_gif_x_pos.setText(layout.getX().endsWith("%") ? "0" : layout.getX());
        tf_gif_y_pos.setText(layout.getY().endsWith("%") ? "0" : layout.getY());
        tf_gif_width.setText(layout.getWidth().endsWith("%") ? "100" : layout.getWidth());
        tf_gif_height.setText(layout.getHeight().endsWith("%") ? "100" : layout.getHeight());
        tf_gif_play_duration.setText(widget.getDuration() + "");
        tf_gif_play_times.setText(widget.getRepeatCount() + "");
    }

    @FXML
    protected void onSwitchGif(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter
                ("GIF(*.gif)", "*.gif"));
        File chooserFile = fileChooser.showOpenDialog
                (btn_create_playlist.getScene().getWindow());
        if (null == chooserFile) {
            return;
        }

        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        widget.setOriginalDataSource(chooserFile.getAbsolutePath());
        widget.setFilesize(chooserFile.length());
        widget.setName(chooserFile.getName());
        String md5 = FileUtils.getMD5(chooserFile);
        String suffix = chooserFile.getName().substring(chooserFile.getName().lastIndexOf("."));
        widget.setDataSource(md5 + suffix);
        NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);
    }


    @FXML
    protected void onSaveGifParams(ActionEvent actionEvent) {
        try {
//            int pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
//            int mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
//                    (mCurrentClickMediaIndex);
            Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
            Layout layout = new Layout(tf_gif_x_pos.getText(), tf_gif_y_pos.getText(), tf_gif_width
                    .getText(), tf_gif_height.getText());
            widget.setDisplayRatio(DISPLAY_RATIO);
            widget.setLayout(layout);
            widget.setDuration(Integer.parseInt(tf_gif_play_duration.getText()));
//            widget.setBackgroundColor(cp_gif_bgcolor.getValue().toString());
            widget.setEnable(true);
            widget.setRepeatCount(Integer.parseInt(tf_gif_play_times.getText()));
            NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);
            showInfo(CommonFunctionalBase.getResourceValue("success_save_gif_configuration"));
        } catch (Exception ex) {
            showInfo(CommonFunctionalBase.getResourceValue("fail_save_gif_configuration") + ex.toString());
        }
    }

    private void restoreTextConfig() {
        pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
        mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
                (mCurrentClickMediaIndex);
        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        MetaDataArchText metaDataArchText = (MetaDataArchText) widget.getMetadata();
        ArchTextAttribute archTextAttribute =
                metaDataArchText.getContent().getTextAttributes().get(0);
        Layout layout = widget.getLayout();
        System.out.println(layout.getWidth() + "," + layout.getHeight() + "," + layout.getX() +
                "," + layout.getY());
        tf_text_x_pos.setText(layout.getX().endsWith("%") ? "0" : layout.getX());
        tf_text_y_pos.setText(layout.getY().endsWith("%") ? "0" : layout.getY());
        tf_text_width.setText(layout.getWidth().endsWith("%") ? "100" : layout.getWidth());
        tf_text_height.setText(layout.getHeight().endsWith("%") ? "100" : layout.getHeight());
        tf_text_fontsize.setText(archTextAttribute.getAttributes().getFont().getSize() + "");
        cb_text_font.getSelectionModel().select(null ==
                archTextAttribute.getAttributes().getFont().getFamily() ?
                CommonFunctionalBase.getResourceValue("widget_text_st") :
                archTextAttribute.getAttributes().getFont().getFamily().get(0));
        cp_text_bgcolor.setValue(Color.valueOf("0x" + archTextAttribute.getAttributes().getBackgroundColor().substring(3) + archTextAttribute.getAttributes().getBackgroundColor().substring(1, 3)));
        cp_text_fgcolor.setValue(Color.valueOf("0x" + archTextAttribute.getAttributes().getTextColor().substring(3) + archTextAttribute.getAttributes().getTextColor().substring(1, 3)));
        tf_text_content.setText(metaDataArchText.getContent().getParagraphs().get(0).getLines().get(0).getSegs().get(0).getContent());
    }

    @FXML
    protected void onSaveTextParams(ActionEvent actionEvent) {
        try {
//            int pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
//            int mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
//                    (mCurrentClickMediaIndex);
            Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
            Layout layout = new Layout(tf_text_x_pos.getText(), tf_text_y_pos.getText(),
                    tf_text_width.getText(), tf_text_height.getText());
            widget.setDisplayRatio(DISPLAY_RATIO);
            widget.setLayout(layout);
            widget.setEnable(true);


            MetaDataArchText metaDataArchText = (MetaDataArchText) widget.getMetadata();
            metaDataArchText.getContent().getDisplayStyle().getScrollAttributes().getEffects().setAnimation("MARQUEE_RIGHT");

            //PAGE_SWITCH directly switches the page, the text has no animation effect, and the SCROLL text scroll effect
//            metaDataArchText.getContent().getDisplayStyle().setType("PAGE_SWITCH");

            metaDataArchText.getContent().getDisplayStyle().getScrollAttributes().getEffects().setSpeed(2.0f);

            metaDataArchText.getContent().getParagraphs().get(0).setBackgroundColor(
                    "#" + cp_text_bgcolor.getValue().toString().substring(8) +
                            cp_text_bgcolor.getValue().toString().substring(2, 8));
            ArchTextAttribute archTextAttribute = metaDataArchText.getContent().getTextAttributes
                    ().get(0);
            if (tf_text_fontsize.getText().length() > 0) {
                int fontSize = Integer.parseInt(tf_text_fontsize.getText());
                if (fontSize > 0) {
                    archTextAttribute.getAttributes().getFont().setSize(fontSize);
                }
            }
            List<String> fontList = new ArrayList<>();
            fontList.add(cb_text_font.getSelectionModel().getSelectedItem().toString());
            archTextAttribute.getAttributes().getFont().setFamily(fontList);
            archTextAttribute.getAttributes().setBackgroundColor("#" + cp_text_bgcolor.getValue()
                    .toString()
                    .substring(8) + cp_text_bgcolor.getValue().toString().substring(2, 8));
            archTextAttribute.getAttributes().setTextColor("#" + cp_text_fgcolor.getValue()
                    .toString()
                    .substring(8) + cp_text_fgcolor.getValue().toString().substring(2, 8));


            List<ArchLine> lines = new ArrayList<>();
            ArchLine line = new ArchLine();
            //region segs
            List<ArchSeg> segs = new ArrayList<>();
            String content = tf_text_content.getText();
            if (null != content && !content.isEmpty()) {
                String[] arry = content.split("\r\n");
                for (String str : arry) {
                    ArchSeg seg = new ArchSeg();
                    seg.setAttributeKey(0);
                    seg.setContent(str);
                    segs.add(seg);
                }
            }
            //endregion
            line.setSegs(segs);
            lines.add(line);
            metaDataArchText.getContent().getParagraphs().get(0).setLines(lines);
//            metaDataArchText.getContent().getParagraphs().get(0).setBackgroundColor("#ff000000");

            widget.setMetadata(metaDataArchText);
            NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);
            showInfo(CommonFunctionalBase.getResourceValue("success_save_text_configuration"));
        } catch (Exception ex) {
            showInfo(CommonFunctionalBase.getResourceValue("fail_save_text_configuration") + ex.toString());
        }
    }


    private void restoreDClockConfig() {
        pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
        mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
                (mCurrentClickMediaIndex);
        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        Layout layout = widget.getLayout();
        tf_dclock_x_pos.setText(layout.getX().endsWith("%") ? "0" : layout.getX());
        tf_dclock_y_pos.setText(layout.getY().endsWith("%") ? "0" : layout.getY());
        tf_dclock_width.setText(layout.getWidth().endsWith("%") ? "100" : layout.getWidth());
        tf_dclock_height.setText(layout.getHeight().endsWith("%") ? "100" : layout.getHeight());
        tf_dclock_play_duration.setText(widget.getDuration() + "");
        MetaDataDigitalClockV2 metaDataDigitalClockV2 = (MetaDataDigitalClockV2) widget.getMetadata();
        TextAttributes textAttributes = metaDataDigitalClockV2.getTextAttributes();
        if (null != textAttributes.getFont().getFamily() && textAttributes.getFont().getFamily().size() > 0) {
            tf_dclock_text_font.setText(textAttributes.getFont().getFamily().get(0));
        }
        tf_dclock_text_size.setText(textAttributes.getFont().getSize() + "");
        String regular = metaDataDigitalClockV2.getRegular();
        //$MM/$dd/$yyyy\\n$E\\n$N&#160;$hh:$mm:$ss
        cb_dclock_month.setSelected(regular.contains("$MM"));
        cb_dclock_day.setSelected(regular.contains("$dd"));
        if (regular.contains("$yyyy")) {
            cb_dclock_4year.setSelected(true);
            cb_dclock_year.setSelected(true);
        } else if (regular.contains("$yy")) {
            cb_dclock_year.setSelected(true);
            cb_dclock_4year.setSelected(false);
        } else {
            cb_dclock_year.setSelected(false);
            cb_dclock_4year.setSelected(false);
            cb_dclock_4year.setDisable(true);
        }
        cb_multiple_line.setSelected(regular.contains("\n"));
        if (regular.contains("$HH")) {
            cb_dclock_hour.setSelected(true);
            cb_dclock_time_style.setSelected(false);
            cb_dclock_show_moraft.setSelected(false);
            cb_dclock_show_moraft.setDisable(true);
        } else if (regular.contains("$hh")) {
            cb_dclock_hour.setSelected(true);
            cb_dclock_time_style.setSelected(true);
            cb_dclock_show_moraft.setSelected(regular.contains("$N"));
        } else {
            cb_dclock_hour.setSelected(false);
            cb_dclock_time_style.setSelected(false);
            cb_dclock_time_style.setDisable(true);
            cb_dclock_show_moraft.setSelected(false);
            cb_dclock_show_moraft.setDisable(true);
        }
        cb_dclock_week.setSelected(regular.contains("$E"));
        cb_dclock_minute.setSelected(regular.contains("$mm"));
        cb_dclock_second.setSelected(regular.contains("$ss"));
    }

    @FXML
    private void onSaveDClockParams(ActionEvent actionEvent) {
        try {
//            int pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
//            int mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
//                    (mCurrentClickMediaIndex);
            Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
            Layout layout = new Layout(tf_dclock_x_pos.getText(), tf_dclock_y_pos.getText(), tf_dclock_width
                    .getText(), tf_dclock_height.getText());
            widget.setDisplayRatio(DISPLAY_RATIO);
            widget.setLayout(layout);
            widget.setDuration(Integer.parseInt(tf_dclock_play_duration.getText()));
            MetaDataDigitalClockV2 metaDataDigitalClockV2 = (MetaDataDigitalClockV2) widget.getMetadata();
            String dateRegular = "";
            String weekRegular = "";
            String timeRegular = "";
            if (cb_dclock_month.isSelected()) {
                dateRegular = "$MM";
            }
            if (cb_dclock_day.isSelected()) {
                dateRegular = dateRegular + "/$dd";
            }
            if (cb_dclock_year.isSelected()) {
                if (cb_dclock_4year.isSelected()) {
                    dateRegular = dateRegular + "/$yyyy";
                } else {
                    dateRegular = dateRegular + "/$yy";
                }
            }
            if (cb_dclock_week.isSelected()) {
                weekRegular = "$E";
            }

            if (cb_dclock_hour.isSelected()) {
                if (cb_dclock_time_style.isSelected()) {
                    if (cb_dclock_show_moraft.isSelected()) {
                        timeRegular = "$N&#160;";
                    }
                    timeRegular = timeRegular + "$hh";
                } else {
                    timeRegular = timeRegular + "$HH";
                }
            }
            if (cb_dclock_minute.isSelected()) {
                timeRegular = timeRegular + ":$mm";
            }
            if (cb_dclock_second.isSelected()) {
                timeRegular = timeRegular + ":$ss";
            }

            if (cb_multiple_line.isSelected()) {
                metaDataDigitalClockV2.setRegular(dateRegular + "\n" + weekRegular + "\n" + timeRegular);
            } else {
                metaDataDigitalClockV2.setRegular(dateRegular + "&#160;" + weekRegular + "&#160;" + timeRegular);
            }

            TextAttributes textAttributes = metaDataDigitalClockV2.getTextAttributes();
            List<String> fonts = new ArrayList<>();
            fonts.add(tf_dclock_text_font.getText());
            textAttributes.getFont().setFamily(fonts);

            textAttributes.getFont().setSize(Integer.parseInt(tf_dclock_text_size.getText()));

            widget.setMetadata(metaDataDigitalClockV2);
            NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);
            showInfo(CommonFunctionalBase.getResourceValue("success_save_dclock_configuration"));
        } catch (Exception ex) {
            showInfo(CommonFunctionalBase.getResourceValue("fail_save_dclock_configuration") + ex.toString());
        }
    }


    private void restoreStreamConfig() {
        pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
        mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
                (mCurrentClickMediaIndex);
        Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
        Layout layout = widget.getLayout();
        tf_stream_x.setText(layout.getX().endsWith("%") ? "0" : layout.getX());
        tf_stream_y.setText(layout.getY().endsWith("%") ? "0" : layout.getY());
        tf_stream_width.setText(layout.getWidth().endsWith("%") ? "100" : layout.getWidth());
        tf_stream_height.setText(layout.getHeight().endsWith("%") ? "100" : layout.getHeight());
        MetaDataStream metaDataStream = (MetaDataStream) widget.getMetadata();
        tf_stream_volume.setText(metaDataStream.getModelData().getVolume() + "");
        tf_stream_protocol.setText(metaDataStream.getModelData().getProtocolType());
        tf_stream_media_type.setText(metaDataStream.getModelData().getStreamType());
        tf_stream_address.setText(metaDataStream.getModelData().getSrc());
        tf_stream_times.setText(widget.getRepeatCount() + "");
        tf_stream_duration.setText(widget.getDuration() + "");
    }

    @FXML
    private void onSaveStreamParams(ActionEvent actionEvent) {
        if (StringUtil.isEmpty(tf_stream_x.getText()) ||
                StringUtil.isEmpty(tf_stream_y.getText()) ||
                StringUtil.isEmpty(tf_stream_width.getText()) ||
                StringUtil.isEmpty(tf_stream_height.getText()) ||
                StringUtil.isEmpty(tf_stream_times.getText()) ||
                StringUtil.isEmpty(tf_stream_duration.getText()) ||
                StringUtil.isEmpty(tf_stream_address.getText()) ||
                StringUtil.isEmpty(tf_stream_volume.getText()) ||
                StringUtil.isEmpty(tf_stream_protocol.getText()) ||
                StringUtil.isEmpty(tf_stream_media_type.getText())) {

            showInfo("Parameter error");
            return;
        }
        try {
//            int pageId = mPageIdMap.get(mCurrentClickMediaParentIndex);
//            int mediaId = mWidgetIdMap.get(mCurrentClickMediaParentIndex).get
//                    (mCurrentClickMediaIndex);
            Widget widget = NovaOpt.GetInstance().getWidgetParam(pageId, mediaId);
            Layout layout = new Layout(tf_stream_x.getText(), tf_stream_y.getText(),
                    tf_stream_width.getText(), tf_stream_height.getText());
            widget.setDisplayRatio(DISPLAY_RATIO);
            widget.setLayout(layout);
            widget.setDuration(Integer.parseInt(tf_stream_duration.getText()));
            widget.setRepeatCount(Integer.parseInt(tf_stream_times.getText()));
            MetaDataStream metaDataStream = (MetaDataStream) widget.getMetadata();
            metaDataStream.getModelData().setSrc(tf_stream_address.getText());
            metaDataStream.getModelData().setProtocolType(tf_stream_protocol.getText());
            metaDataStream.getModelData().setStreamType(tf_stream_media_type.getText());
            metaDataStream.getModelData().setVolume(Integer.parseInt(tf_stream_volume.getText()));

            widget.setMetadata(metaDataStream);
            NovaOpt.GetInstance().setWidgetParam(pageId, mediaId, widget);
            showInfo(CommonFunctionalBase.getResourceValue("success_save_stream_configuration"));
        } catch (Exception ex) {
            showInfo(CommonFunctionalBase.getResourceValue("fail_save_stream_configuration") + ex.toString());
        }
    }

    @FXML
    ComboBox cb_bgprogram_play;

    @FXML
    protected void onActionPublishInsertPlay(ActionEvent actionEvent) {
        if (null == treeRoot) {
            return;
        }
        try {
            String colorbg = cp_program_bgcolor.getValue().toString();
            colorbg = "#" + colorbg.substring(8) + colorbg.substring(2, 8);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(tf_start_year.getText()),
                    Integer.parseInt(tf_start_month.getText()) - 1,
                    Integer.parseInt(tf_start_day.getText()),
                    Integer.parseInt(tf_start_hour.getText()),
                    Integer.parseInt(tf_start_minute.getText()),
                    Integer.parseInt(tf_start_second.getText()));

            long starttime = calendar.getTimeInMillis();
            calendar.set(Integer.parseInt(tf_end_year.getText()),
                    Integer.parseInt(tf_end_month.getText()) - 1,
                    Integer.parseInt(tf_end_day.getText()),
                    Integer.parseInt(tf_end_hour.getText()),
                    Integer.parseInt(tf_end_minute.getText()),
                    Integer.parseInt(tf_end_second.getText()));
            long endtime = calendar.getTimeInMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("start=" + sdf.format(starttime) + ",end=" + sdf.format(endtime));
            if (0 == NovaOpt.GetInstance().makeInsertPlayProgram(PROGRAM_LOCATION, colorbg,
                    starttime, endtime - starttime)) {
                try {
                    InetAddress ia = InetAddress.getLocalHost();
                    NovaOpt.GetInstance().startInsertplayTransfer(CommonFunctionalBase.deviceOpt.getSn(),
                            PROGRAM_LOCATION,
                            treeRoot.getValue(),
                            getLocalMac(ia),
                            false,
                            cb_bgprogram_play.getSelectionModel().getSelectedIndex() == 0 ? true : false,
                            new ProgramSendManager.OnProgramTransferListener() {

                                @Override
                                public void onStarted() {
                                    showInfo(CommonFunctionalBase.getResourceValue("start_transfer"));
                                }

                                @Override
                                public void onTransfer(long l, long l1) {
                                    showInfo(CommonFunctionalBase.getResourceValue("publishing"));
                                }

                                @Override
                                public void onError(ErrorDetail errorDetail) {
                                    showInfo(CommonFunctionalBase.getResourceValue("publish_failed") +
                                            errorDetail.description);
                                    showInfo("If the interrupt release fails, it may be necessary to confirm whether the terminal has the interrupt function enabled.");
                                }

                                @Override
                                public void onCompleted() {
                                    showInfo(CommonFunctionalBase.getResourceValue("complete_published"));
                                }

                                @Override
                                public void onAborted() {
                                    showInfo(CommonFunctionalBase.getResourceValue("publish_abort"));
                                }
                            });
                } catch (Exception ex) {
                    showInfo(ex.getMessage() + "|" + ex.getLocalizedMessage() + "|" + ex.hashCode
                            ());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showInfo(e.getMessage());
        }
    }


    /**
     * Get MAC address
     *
     * @param ia
     * @return
     * @throws SocketException
     */
    private static String getLocalMac(InetAddress ia) throws SocketException {
        //Get network card, get address
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            return sb.toString().toUpperCase();
        } else if (osName.startsWith("Linux")) {
            String command = "/bin/sh -c ifconfig -a";
            try {
                StringBuilder sBuilder = new StringBuilder();
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(process
                        .getInputStream()));
                String line;
                while (null != (line = br.readLine())) {
                    if (line.indexOf("ether") > 0) {
                        String address = line.split("ether")[1].trim()
                                .split("txqueuelen")[0].trim();
                        String[] macSplit = address.split(":");
                        for (int i = 0; i < macSplit.length; i++) {
                            if (0 != i) {
                                sBuilder.append("-");
                            }
                            if (macSplit[i].length() == 1) {
                                sBuilder.append("0" + macSplit[i]);
                            } else {
                                sBuilder.append(macSplit[i]);
                            }
                        }
                        break;
                    }
                }
                br.close();
                return sBuilder.toString().toUpperCase();
            } catch (Exception e) {

            }
        }
        return "";
    }

    /**
     * 0-show pageSetting,1-show pictureSetting,2-show videosetting,3-show gifsetting,
     * 4-show textsetting，other-hide all
     *
     * @param type
     */
    private void showPageOrMediaSetting(int type) {
        vbox_insertplay_setting.setVisible(0 == type ? true : false);
        vbox_insertplay_setting.setManaged(0 == type ? true : false);
        vbox_picture_setting.setVisible(1 == type ? true : false);
        vbox_picture_setting.setManaged(1 == type ? true : false);
        vbox_video_setting.setVisible(2 == type ? true : false);
        vbox_video_setting.setManaged(2 == type ? true : false);
        vbox_gif_setting.setVisible(3 == type ? true : false);
        vbox_gif_setting.setManaged(3 == type ? true : false);
        vbox_text_setting.setVisible(4 == type ? true : false);
        vbox_text_setting.setManaged(4 == type ? true : false);
        vbox_dclock_setting.setVisible(5 == type ? true : false);
        vbox_dclock_setting.setManaged(5 == type ? true : false);
        vbox_stream_setting.setVisible(6 == type ? true : false);
        vbox_stream_setting.setManaged(6 == type ? true : false);

    }
}
