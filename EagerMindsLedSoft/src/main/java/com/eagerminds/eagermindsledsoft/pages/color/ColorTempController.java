/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft.pages.color;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.net.svolley.Request.IRequestBase;
import com.eagerminds.eagermindsledsoft.CommonFunctionalBase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ColorTempController {
    @FXML
    ComboBox cbColorTemp;
    @FXML
    TextArea taMsg;

    public void init() {
        ObservableList<String> colorTempValues = FXCollections.observableArrayList("9300", "6500", "4700");
        cbColorTemp.setItems(colorTempValues);
        cbColorTemp.getSelectionModel().select(0);
    }

    @FXML
    void OnBtnSetColorTempClick(ActionEvent actionEvent) {
        int colorTempValue = 9300;
        if (cbColorTemp.getSelectionModel().getSelectedIndex() == 0) {
            colorTempValue = 9300;
        } else if (cbColorTemp.getSelectionModel().getSelectedIndex() == 1) {
            colorTempValue = 6500;
        } else if (cbColorTemp.getSelectionModel().getSelectedIndex() == 2) {
            colorTempValue = 4700;
        } else {
            return;
        }
        NovaOpt.GetInstance().setColorTemperature(CommonFunctionalBase.deviceOpt.getSn(), colorTempValue, new OnResultListenerN<Integer, ErrorDetail>() {
            @Override
            public void onSuccess(IRequestBase requestBase, Integer response) {
                showInfo("onSuccess！");
            }

            @Override
            public void onError(IRequestBase requestBase, ErrorDetail error) {
                showInfo("onError！" + error.description + "(" + error.errorCode + ")");
            }
        });
    }

    @FXML
    void OnBtnGetColorTempClick(ActionEvent actionEvent) {
        NovaOpt.GetInstance().getColorTemperature(CommonFunctionalBase.deviceOpt.getSn(), new OnResultListenerN<Integer, ErrorDetail>() {
            @Override
            public void onSuccess(IRequestBase requestBase, Integer response) {
                showInfo("onSuccess！");
                updateColorTemp(response.intValue());
            }

            @Override
            public void onError(IRequestBase requestBase, ErrorDetail error) {
                showInfo("onError！" + error.description + "(" + error.errorCode + ")");
            }
        });
    }

    void updateColorTemp(int colorTempValue) {
        Platform.runLater(() -> {
            switch (colorTempValue) {
                case 9300:
                    cbColorTemp.getSelectionModel().select(0);
                    break;
                case 6500:
                    cbColorTemp.getSelectionModel().select(1);
                    break;
                case 4700:
                    cbColorTemp.getSelectionModel().select(2);
                    break;
                default:
                    showInfo("Choose！" + colorTempValue);
                    break;
            }
        });
    }

    void showInfo(String msgInfo) {
        Platform.runLater(() -> {
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            taMsg.appendText("[" + df.format(day) + "]  " + msgInfo + "\r\n");
        });
    }

    void showDialog(Alert.AlertType alertType, String msgInfo) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setHeaderText(msgInfo);
            alert.setResult(ButtonType.OK);
            alert.showAndWait();
        });
    }
}
