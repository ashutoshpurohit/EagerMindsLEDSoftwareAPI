/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eagerminds.eagermindsledsoft.pages.bean;

import javafx.beans.*;
import java.io.Serializable;

/**
 *
 * @author user
 */
public class PagePropertyBean extends BaseProgramPropertyBean {

    private int stayTime;
    private int inScreenSpeed;
    private int inScreenStyle;
    private int outScreenStyle;
    private int twinkleSpeed;
    private int twinkleTimes;
    private int playTimes;

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public int getInScreenSpeed() {
        return inScreenSpeed;
    }

    public void setInScreenSpeed(int inScreenSpeed) {
        this.inScreenSpeed = inScreenSpeed;
    }

    public int getInScreenStyle() {
        return inScreenStyle;
    }

    public void setInScreenStyle(int inScreenStyle) {
        this.inScreenStyle = inScreenStyle;
    }

    public int getOutScreenStyle() {
        return outScreenStyle;
    }

    public void setOutScreenStyle(int outScreenStyle) {
        this.outScreenStyle = outScreenStyle;
    }

    public int getTwinkleSpeed() {
        return twinkleSpeed;
    }

    public void setTwinkleSpeed(int twinkleSpeed) {
        this.twinkleSpeed = twinkleSpeed;
    }

    public int getTwinkleTimes() {
        return twinkleTimes;
    }

    public void setTwinkleTimes(int twinkleTimes) {
        this.twinkleTimes = twinkleTimes;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }
}
