package org.jtwig.web.resource;

import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WebResourceTest {

    @Test
    public void equalsSameInstance() throws Exception {
        WebResource underTest = new WebResource(Charset.defaultCharset(), "test");

        assertTrue(underTest.equals(underTest));
    }

    @Test
    public void equalsNull() throws Exception {
        WebResource underTest = new WebResource(Charset.defaultCharset(), "test");

        assertFalse(underTest.equals(null));
    }

    @Test
    public void equalsOtherType() throws Exception {
        WebResource underTest = new WebResource(Charset.defaultCharset(), "test");

        assertFalse(underTest.equals(new Object()));
    }

    @Test
    public void equalsOtherInstanceOtherLocation() throws Exception {
        WebResource underTest = new WebResource(Charset.defaultCharset(), "test");

        assertFalse(underTest.equals(new WebResource(Charset.defaultCharset(), "test1")));
    }

    @Test
    public void equalsOtherInstanceSameLocation() throws Exception {
        WebResource underTest = new WebResource(Charset.defaultCharset(), "test");

        assertTrue(underTest.equals(new WebResource(Charset.defaultCharset(), "test")));
    }

    @Test
    public void equalsOtherInstanceSameLocationOtherCharset() throws Exception {
        WebResource underTest = new WebResource(Charset.defaultCharset(), "test");

        assertTrue(underTest.equals(new WebResource(Charset.forName("ASCII"), "test")));
    }
}