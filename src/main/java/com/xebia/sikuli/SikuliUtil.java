package com.xebia.sikuli;

import java.io.File;

import org.sikuli.script.Region;

public class SikuliUtil {
    static void enlargeRegion(Region region, int size) {
        region.setX(region.getX() - size);
        region.setY(region.getY() - size);
        region.setW(region.getW() + size * 2);
        region.setH(region.getH() + size * 2);
    }
    
    public static File sikuliScript(String path) {
        File script=new File(path);
        if (! script.getName().contains(".")) {
            script=new File(script.getParent(),script.getName()+".sikuli");
        }
        return script;
    }
    public static String imgPath(File sikuliScriptDir, String imgName) {
        return new File(sikuliScriptDir, imgName).getPath();
    }
}
