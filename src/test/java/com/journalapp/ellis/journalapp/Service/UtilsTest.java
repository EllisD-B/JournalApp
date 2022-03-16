package com.journalapp.ellis.journalapp.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void shouldReturnCorrectValueForInputString() {
        String json = "{ \n" +
                "    \"id\" : \" 1 \" \n" +
                "}";

        long l = Utils.getId(json);
        Assertions.assertEquals(1, l);
    }
}
