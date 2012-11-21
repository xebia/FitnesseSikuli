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
        fixture.setSikuliScript("Test");
        System.out.println(fixture.sikuliScript.getAbsoluteFile().getParentFile());
        assertTrue("Pointing to right sikuliscriptsdir",fixture.sikuliScript.getAbsoluteFile().getParentFile().exists());
        assertEquals("Test.sikuli",fixture.sikuliScript.getName());
        assertTrue(fixture.imgPath("apple").endsWith(".png"));
        
    }

}
