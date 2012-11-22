package com.xebia.sikuli;

import java.io.File;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SikuliDriverFixture {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(SikuliDriverFixture.class);

    public final Screen screen;
    private App app;
    File sikuliScript=null;
    
    public Region currentRegion;

    private long hightlightTime=3;
    
    public String sikuliScriptsDir = SikuliUtil.defaultScriptDir;

    public SikuliDriverFixture() {
        screen = new Screen();
        currentRegion=screen;
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
    public void highlight(String imgOrText) throws FindFailed {
        highlight(imgPath(imgOrText),hightlightTime);
    }
    public void highlight(String img, long time) throws FindFailed {
        currentRegion = window().find(imgPath(img));
        currentRegion.highlight(time);
        
    }
   
    
    public void highlight() {
        currentRegion.highlight(hightlightTime);
    }

    public void focusApp(String appTitle) {
        app = App.focus(appTitle);
        if (app==null) {
            throw new FitSikException(appTitle+" is not active");
        }
        currentRegion=app.window();
        if (sikuliScript==null) {
            sikuliScript=new File(sikuliScriptsDir + File.separator+appTitle + ".sikuli");
        }
     }
    
    public String imgPath(String imgOrText) {
        if (! imgOrText.contains(".") ) {
            imgOrText=imgOrText+".png";
        }
        if (! imgOrText.startsWith(File.separator)) {
            imgOrText=new File(sikuliScript,imgOrText).getAbsolutePath();
        }
        return imgOrText;
    }
    
    public void click(String imgOrText) throws FindFailed {
        currentRegion.click(imgPath(imgOrText));
    }
    
    public void wait(String imgOrText) throws FindFailed {
        currentRegion.wait(imgPath(imgOrText));
    }
    
    public void type(String text) throws FindFailed {
        currentRegion.type(text);
    }
    
    public void setSikuliScript(String path) {
        File script;
        if (path.startsWith(File.separator))
            script=new File(path);
        else
            script=new File(sikuliScriptsDir,path);
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
