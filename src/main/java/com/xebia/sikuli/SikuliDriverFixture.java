package com.xebia.sikuli;

import java.io.File;
import java.util.Stack;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.TextTarget;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fit.Fixture;

/**
 * This fixture uses the Sikuli API to perform UI automation.
 * 
 * @author gvandieijen
 *
 */
public class SikuliDriverFixture extends Fixture {

    private static final Logger LOG = LoggerFactory.getLogger(SikuliDriverFixture.class);

    File imgDir=null;
    
    Stack<ScreenRegion> matches=new Stack<ScreenRegion>();
    private static Mouse mouse;
    private static Keyboard keyboard = new DesktopKeyboard();
    private int waitTimeMs=3000;
    private int highlightTime=3;
    
    public String sikuliScriptsDir = SikuliUtil.defaultScriptDir;
    
    private static DesktopScreenRegion screen;

    public SikuliDriverFixture() {
        mouse = new DesktopMouse();
        screen=new DesktopScreenRegion();
    }
    
    public boolean highlight(String imgOrText, int highlightTime) {
        Target t=fuzzyTarget(imgOrText);
        ScreenRegion r = currentRegion().find(t);
        if (r!=null) {
            Canvas canvas = new DesktopCanvas();
            canvas.addBox(r).display(highlightTime);
            return true;
        } else {
            return false;
        }
            
    }
    public boolean highlight(String imgOrText) {
        return highlight(imgOrText,highlightTime);
    }
    
    public Target fuzzyTarget(String imgOrText) {
        File imgFile = imgFileOrNone(imgOrText);
        if (imgFile!=null) {
            return new ImageTarget(imgFile);
        } else {
            return new TextTarget(imgOrText);
        }
    }

    protected File imgFileOrNone(String imgOrText) {
        if (! imgOrText.contains(".") ) {
            imgOrText=imgOrText+".png";
        }
        File imgFile;
        if (! imgOrText.startsWith(File.separator)) {
            imgFile=new File(imgDir,imgOrText);
        } else {
            imgFile=new File(imgOrText);
        }
        if (imgFile.exists())
            return imgFile;
        else
            return null;
    }
    
    public boolean click(String imgOrText) {
        Target t=fuzzyTarget(imgOrText);
        ScreenRegion r = currentRegion().find(t);
        if (r==null)
            return false;
        else {
            mouse.click(r.getCenter());
            return true;
        }
    }
    
    public void click() {
         mouse.click(currentRegion().getCenter());
    }
    
    public ScreenRegion currentRegion() {
        if (matches.isEmpty()) {
            return screen;
        } else {
            return matches.peek();
        }
    }
    
    private boolean maybeAddNewMatch(ScreenRegion region) {
        if (region!=null) {
            matches.push(region);
            return true;
        } else {
            return false;
        }
    }
    public boolean wait(String imgOrText)  {
        Target t=fuzzyTarget(imgOrText);
        return maybeAddNewMatch(currentRegion().wait(t,waitTimeMs));
    }
    
    public boolean type(String text) {
        keyboard.type(text);
        return true;
    }
    
    public boolean imageDir(String path) {
        File dir;
        dir=new File(path);
        LOG.debug("Trying "+dir.getAbsolutePath());
        if (! dir.exists()) {
            dir=new File(sikuliScriptsDir,path);
            LOG.debug("Trying "+dir.getAbsolutePath());
        }
        if (! dir.exists()) {
            dir=new File(dir.getParent(),dir.getName()+".sikuli");
            LOG.debug("Trying "+dir.getAbsolutePath());
        }
        if (dir.exists()) {
            imgDir=dir;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

}
