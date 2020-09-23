/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft.pages.play;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import novj.platform.vxkit.common.bean.TransferInfo;
import novj.platform.vxkit.common.result.OnResultListenerN;
import novj.publ.api.NovaOpt;
import novj.publ.api.beans.ProgramInfoBean;
import novj.publ.net.exception.ErrorDetail;
import novj.publ.net.svolley.Request.IRequestBase;
import novj.publ.util.StringUtil;
import com.eagerminds.eagermindsledsoft.CommonFunctionalBase;

/**
 * FXML Controller class
 *
 * @author user
 */
public class PlayControlPageController {
    @FXML
    ListView<String> listview;
    @FXML
    TextArea action_info;
    private int mSelectIndex = 0;
    private List<TransferInfo.ProgramInfo> mProgramInfos;
    private String identifier;
    @FXML
    Button btn_play_program, btn_stop_program, btn_pause_program, btn_restore_program,
            btn_delete_program;

    public void initData() {
        changeBtnStatus(-1, 1);
        getPrograms();
        listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                mSelectIndex = listview.getSelectionModel().getSelectedIndex();
                if (mSelectIndex >= 0) {
                    identifier = mProgramInfos.get(mSelectIndex).getIdentifier();
                   
                    btn_delete_program.setDisable(false);
                    changeBtnStatus(mProgramInfos.get(mSelectIndex).getStatusCode(), 0);
                }
            }
        });
    }

    private void changeBtnStatus(int statusCode, int type) {
        if (0 == type) {
            btn_play_program.setDisable(0 == statusCode ? false : (1 == statusCode ? true : false));
            btn_stop_program.setDisable(0 == statusCode ? true : (1 == statusCode ? false : true));
            btn_pause_program.setDisable(0 == statusCode ? true : (1 == statusCode ? false : true));
            btn_restore_program.setDisable(0 == statusCode ? true : (1 == statusCode ? true :
                    false));
            btn_delete_program.setDisable(0 == statusCode ? false : (1 == statusCode ? false :
                    false));
        } else if (1 == type) {
            btn_play_program.setDisable(true);
            btn_stop_program.setDisable(true);
            btn_pause_program.setDisable(true);
            btn_restore_program.setDisable(true);
            btn_delete_program.setDisable(true);
        }
    }

    private void getPrograms() {
        NovaOpt.GetInstance().getProgramInfo(CommonFunctionalBase.deviceOpt.getSn(), new
                OnResultListenerN<TransferInfo.ProgramInfos, ErrorDetail>() {

                    @Override
                    public void onSuccess(IRequestBase requestBase, TransferInfo.ProgramInfos
                            response) {
                        mProgramInfos = response.getProgramInfos();
                        List<String> strs = new ArrayList<>();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (TransferInfo.ProgramInfo info : mProgramInfos) {
                            strs.add(info.getName());
                            stringBuilder.append("{name:"+info.getName()+",statusCode:"+info.getStatusCode()+"}");
                        }
                        showActionInfo(stringBuilder.toString());
                        ObservableList<String> list = FXCollections.observableArrayList(strs);
                        Platform.runLater(() -> listview.setItems(list));
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showActionInfo(CommonFunctionalBase.getResourceValue("get_programlist_failed") + "：" +
                                error.errorCode + "-" + error.description);
                    }
                });
    }

    
    @FXML
    protected void onPlayProgram(ActionEvent ae) {
        if (StringUtil.isEmpty(identifier)) {
            return;
        }
        NovaOpt.GetInstance().startPlay(CommonFunctionalBase.deviceOpt.getSn(), identifier, new
                OnResultListenerN<Integer, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Integer response) {
                        changeBtnStatus(1, 0);
                        showActionInfo(CommonFunctionalBase.getResourceValue("play_start_successed"));
                        mProgramInfos.clear();
                        getPrograms();
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showActionInfo(CommonFunctionalBase.getResourceValue("play_start_failed") + "：" +
                                error.errorCode + "-" + error.description);
                    }
                });
    }

   
    @FXML
    protected void onStopProgram(ActionEvent ae) {
        if (StringUtil.isEmpty(identifier)) {
            return;
        }
        NovaOpt.GetInstance().stopPlay(CommonFunctionalBase.deviceOpt.getSn(), identifier, new
                OnResultListenerN<Integer, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Integer response) {
                        changeBtnStatus(0, 0);
                        showActionInfo(CommonFunctionalBase.getResourceValue("play_stop_successed"));
                        mProgramInfos.clear();
                        getPrograms();
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showActionInfo(CommonFunctionalBase.getResourceValue("play_stop_failed") + "：" +
                                error.errorCode + "-" + error.description);
                    }
                });
    }

   
    @FXML
    protected void onPauseProgram(ActionEvent ae) {
        if (StringUtil.isEmpty(identifier)) {
            return;
        }
        NovaOpt.GetInstance().pausePlay(CommonFunctionalBase.deviceOpt.getSn(), identifier, new
                OnResultListenerN<Integer, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Integer response) {
                        changeBtnStatus(2, 0);
                        showActionInfo(CommonFunctionalBase.getResourceValue("play_pause_successed"));
                        mProgramInfos.clear();
                        getPrograms();
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showActionInfo(CommonFunctionalBase.getResourceValue("play_pause_failed") + "：" +
                                error.errorCode + "-" + error.description);
                    }
                });
    }

    
    @FXML
    protected void onRestoreProgram(ActionEvent ae) {
        if (StringUtil.isEmpty(identifier)) {
            return;
        }
        NovaOpt.GetInstance().resumePlay(CommonFunctionalBase.deviceOpt.getSn(), identifier, new
                OnResultListenerN<Integer, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Integer response) {
                        changeBtnStatus(1, 0);
                        showActionInfo(CommonFunctionalBase.getResourceValue("play_resume_successed"));
                        mProgramInfos.clear();
                        getPrograms();
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showActionInfo(CommonFunctionalBase.getResourceValue("play_resume_failed") + "：" +
                                error.errorCode + "-" + error.description);
                    }
                });
    }

   
    @FXML
    protected void onDeleteProgram(ActionEvent ae) {
        if (StringUtil.isEmpty(identifier)) {
            return;
        }
        ProgramInfoBean bean = new ProgramInfoBean();
        bean.setName(mProgramInfos.get(mSelectIndex).getName());
        bean.setIdentifier(identifier);
        List<ProgramInfoBean> beans = new ArrayList<>();
        beans.add(bean);
        NovaOpt.GetInstance().deletePlayList(CommonFunctionalBase.deviceOpt.getSn(), beans, new
                OnResultListenerN<Integer, ErrorDetail>() {
                    @Override
                    public void onSuccess(IRequestBase requestBase, Integer response) {
                        showActionInfo(CommonFunctionalBase.getResourceValue("delete_program_successed"));
                        changeBtnStatus(-1, 1);
                        mProgramInfos.clear();
                        mSelectIndex = -1;
                        identifier = "";
                        getPrograms();
                    }

                    @Override
                    public void onError(IRequestBase requestBase, ErrorDetail error) {
                        showActionInfo(CommonFunctionalBase.getResourceValue("delete_program_failed") + "："
                                + error.errorCode + "-" + error.description);
                    }
                });
    }

   
    private void showActionInfo(String info) {
        Platform.runLater(() -> {
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            action_info.appendText("[" + df.format(day) + "]  " + info + "\r\n");
        });
    }
}
