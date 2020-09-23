/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eagerminds.eagermindsledsoft;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.eagerminds.eagermindsledsoft.BasicDeviceDataInformation;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

/**
 *
 * @author Samanvay Rawal EagerMinds
 */
public class CommonFunctionalBase {
    public static BasicDeviceDataInformation deviceOpt;
    public static int NOW_PLAYLIST_ID;
    public static final String DISPLAY_RATIO = "CUSTOM";
    public static final String PLAYLIST_NAME = "play";
    public static final String PAGE_NAME = "page";
    public static final String PICTURE_NAME = "pic";
    public static final String VIDEO_NAME = "video";
    public static final String GIF_NAME = "gif";
    public static final String TEXT_NAME = "text";
    public static final String DCLOCK_NAME = "dclock";
    public static final String STREAM_NAME = "stream";

    public static String PROGRAM_LOCATION = "C:/EagerMindsData/";
//    public static List<DeviceInfo> loginedDeviceInfos = new ArrayList<>();
    public static String timeZonesXml = null;
    public static Locale DEFAULT_LOCALE = Locale.ENGLISH;
    public static final String BASE_RESOURCE = "resource";
    //public static final String BASE_RESOURCE = "com.eagerminds.eagermindsledsoft.eargerminds";
//    public static Locale DEFAULT_LOCALE = Locale.ENGLISH;


    public static FXMLLoader getFxmlLoader(URL location) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        //ResourceBundle.getBundle("eargerminds_en.properties");
        fxmlLoader.setResources(ResourceBundle.getBundle(BASE_RESOURCE,CommonFunctionalBase.DEFAULT_LOCALE));
        return fxmlLoader;
    }

    public static String getResourceValue(String key){
        return ResourceBundle.getBundle(BASE_RESOURCE, CommonFunctionalBase.DEFAULT_LOCALE)
                .getString(key);
    }

}
