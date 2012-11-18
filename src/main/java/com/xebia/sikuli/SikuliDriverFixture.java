package com.xebia.sikuli;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sikuli.script.*;

import static com.xebia.sikuli.SikuliUtil.*;
public class SikuliDriverFixture {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(SikuliDriverFixture.class);

    public final Screen screen;
    private App app;
    private File sikuliScript=null;

    private long hightlightTime=2000;

    public SikuliDriverFixture() {
        screen = new Screen();
    }

    public void setObserveScanRate(float scanRate) {
        Settings.ObserveScanRate = scanRate;
    }

    public void setMoveMouseDelay(float delay) {
        Settings.MoveMouseDelay = delay;
    }
    
    public Region window() {
        if (app==null) {
            throw new FitSikException("no application is set yet");
        }        
        app.focus();
        return app.window();
    }
    public void highlight(String img) throws FindFailed {
        highlight(img,hightlightTime);
    }
    public void highlight(String img, long time) throws FindFailed {
        Match region = window().find(imgPath(sikuliScript,img));
        region.highlight(time);
        
    }

    public void focusApp(String appTitle) {
        app = App.focus(appTitle);
        if (app==null) {
            throw new FitSikException(appTitle+" is not active");
        }
        if (sikuliScript==null) {
            sikuliScript=new File(appTitle+".sikuli");
        }
     }
    
    public void setSikuliScript(String path) {
        File script=new File(path);
        if (! script.getName().contains(".")) {
            script=new File(script.getParent(),script.getName()+".sikuli");
        }
        sikuliScript=script;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

}
