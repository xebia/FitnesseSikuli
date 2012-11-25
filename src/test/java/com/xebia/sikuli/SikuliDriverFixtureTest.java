package com.xebia.sikuli;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SikuliDriverFixtureTest {
    SikuliDriverFixture fixture;
    @Before
    public void setUp() throws Exception {
        fixture=new SikuliDriverFixture();
    }

    @Test
    public void testSettingScriptDir() {
        fixture.imageDir("Apple");
        System.out.println(fixture.imgDir.getAbsoluteFile().getParentFile());
        assertTrue("Pointing to right sikuliscriptsdir",fixture.imgDir.getAbsoluteFile().getParentFile().exists());
        assertEquals("Apple.sikuli",fixture.imgDir.getName());
        assertTrue(fixture.imgFileOrNone("apple").getName().endsWith(".png"));
        
    }

}
