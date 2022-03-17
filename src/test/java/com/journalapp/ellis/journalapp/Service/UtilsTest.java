package com.journalapp.ellis.journalapp.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class UtilsTest {

    @Test
    public void shouldReturnCorrectValueForInputString() {
        String json = "{ \n" +
                "    \"id\" : \" 1 \" \n" +
                "}";

        long l = Utils.getId(json);
        Assertions.assertEquals(1, l);
    }

    @Test
    public void shouldCorrectlyDelimitTags() {
        String toDelim = "Java, Python, Ruby,";

        List<String> tags = Utils.delimitTags(toDelim);
        assertThat(tags).hasSize(3).contains("Java", "Python", "Ruby");

    }
}
