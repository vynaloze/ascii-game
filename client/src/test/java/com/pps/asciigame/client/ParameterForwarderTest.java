package com.pps.asciigame.client;

import com.pps.asciigame.client.ui.utils.ParameterForwarder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterForwarderTest {
    private ParameterForwarder parameterForwarder;

    @Before
    public void setUp() {
        parameterForwarder = new ParameterForwarder();
    }

    @After
    public void tearDown() {
        parameterForwarder = null;
    }

    @Test
    public void shouldPassSingleParameter() {
        //given
        final Integer passedInteger = 1;
        //when
        parameterForwarder.pass(passedInteger, Integer.class);
        //then
        assertThat(parameterForwarder.get(Integer.class)).isEqualTo(1);
    }

    @Test
    public void shouldReplacePreviousParam() {
        //given
        final Integer passedInteger = 1;
        final String passedString = "foo";
        //when
        parameterForwarder.pass(passedInteger, Integer.class);
        parameterForwarder.pass(passedString, String.class);
        //then
        assertThat(parameterForwarder.get(String.class)).isEqualTo(passedString);
    }

    @Test
    public void shouldRemoveCorrectParam() {
        //given
        final Integer passedInteger = 1;
        final String passedString = "foo";
        parameterForwarder.pass(passedInteger, Integer.class);
        parameterForwarder.pass(passedString, String.class);
        //when
        parameterForwarder.remove(Integer.class);
        //then
        assertThat(parameterForwarder.get(Integer.class)).isNull();
    }
}
