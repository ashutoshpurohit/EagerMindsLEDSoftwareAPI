/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft.pages.play;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import novj.platform.vxkit.common.bean.programinfo.PlayProgramMediaInfoBean;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.publ.api.NovaOpt;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.net.svolley.Request.IRequestBase;
import novj.publ.util.JSONUtil;
import com.eagerminds.eagermindsledsoft.CommonFunctionalBase;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * FXML Controller class
 *
 * @author user
 */
public class ProgramMediaController {

    @FXML
    TextArea mediaMsg;

    public void getPlayProgramMediaInfo() {
        NovaOpt.GetInstance().getPlayProgramMediaInfo(CommonFunctionalBase.deviceOpt.getSn(), new OnResultListenerN<PlayProgramMediaInfoBean, ErrorDetail>() {
            @Override
            public void onSuccess(IRequestBase requestBase, PlayProgramMediaInfoBean response) {
                String responseStr = JSONUtil.toJsonString(response);
                showInfo(responseStr);
            }

            @Override
            public void onError(IRequestBase requestBase, ErrorDetail error) {
                showInfo("Error Code: " + error.errorCode + "\r\n" + "Error Description: " + error.description);
            }
        });
    }

    void showInfo(String msgInfo) {
        Platform.runLater(() -> {
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mediaMsg.appendText("[" + df.format(day) + "]" + "\r\n");
            mediaMsg.appendText(msgInfo + "\r\n");
        });
    }
}

