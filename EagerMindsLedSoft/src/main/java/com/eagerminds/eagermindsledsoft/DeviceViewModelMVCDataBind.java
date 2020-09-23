/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft;

import com.eagerminds.eagermindsledsoft.BasicDeviceDataInformation;
import com.eagerminds.eagermindsledsoft.DevicePolicy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;          

/**
 *
 * @author user
 */
public class DeviceViewModelMVCDataBind {
    public static ObservableList<DevicePolicy> policyInfos = FXCollections.observableArrayList();
    public static ObservableList<BasicDeviceDataInformation> devicesData = FXCollections.observableArrayList();

    public static BasicDeviceDataInformation getDeviceInfoBySN(String sn) {
        if(!sn.isEmpty()){
            for (BasicDeviceDataInformation info:devicesData) {
                if(info.getSn().equals(sn)){
                    return info;
                }
            }
        }

        return null;
    }

}

