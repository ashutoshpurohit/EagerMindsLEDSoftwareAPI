/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft.pages.TimeZones;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.eagerminds.eagermindsledsoft.CommonFunctionalBase;

/**
 *
 * @author user
 */
public class TimeZones {
    List<TimeZone> timezones = new ArrayList<TimeZone>();

    public List<TimeZone> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<TimeZone> timezones) {
        this.timezones = timezones;
    }

    public List<TimeZone> LoadXML() {
        try {
            DocumentBuilderFactory dbFactory = null;
            DocumentBuilder db = null;
            Document document = null;
            dbFactory = DocumentBuilderFactory.newInstance();
            db = dbFactory.newDocumentBuilder();
            //document = db.parse("../pages/TimeZones/timeZones.xml");
            document = db.parse(CommonFunctionalBase.timeZonesXml);
            NodeList timeZones = document.getElementsByTagName("timezone");
            for(int i=0;i<timeZones.getLength();i++){
                org.w3c.dom.Node node = timeZones.item(i);
                NamedNodeMap namedNodeMap = node.getAttributes();
                String id = namedNodeMap.getNamedItem("id").getTextContent();
                TimeZone timeZone = new TimeZone();
                timeZone.setId(id);
                timeZone.setContent(node.getTextContent());
                if(null == timezones) {
                    timezones = new ArrayList<TimeZone>();
                }
                timezones.add(timeZone);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timezones;
    }
}

