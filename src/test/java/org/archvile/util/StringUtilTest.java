package org.archvile.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class StringUtilTest {

    @Test
    public void testIsEmptyShouldReturnTrueIfPassedNull() throws Exception {
        assertTrue(StringUtil.isEmpty(null));
    }

    @Test
    public void testIsEmptyShouldReturnTrueIfPassedEmptyString() throws Exception {
        assertTrue(StringUtil.isEmpty(""));
    }

    @Test
    public void testIsEmptyShouldReturnTrueIfPassedLongEmptyString() throws Exception {
        assertTrue(StringUtil.isEmpty("    "));
    }

}