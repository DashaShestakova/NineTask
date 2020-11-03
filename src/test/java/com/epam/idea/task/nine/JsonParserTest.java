package com.epam.idea.task.nine;

import com.epam.idea.task.nine.entity.Ship;
import com.epam.idea.task.nine.exception.ParsingException;
import com.epam.idea.task.nine.parser.ActionType;
import com.epam.idea.task.nine.parser.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JsonParserTest {
    private static final String SHIP_DATA_FILE = "src/test/resources/input.json";

    @Test
    public void shouldParseJsonFileWhenDataValid() throws ParsingException {
        JsonParser jsonShipParser = new JsonParser();

        List<Ship> actual = jsonShipParser.parse(SHIP_DATA_FILE);

        Assert.assertEquals(2,actual.size());
        Ship first = actual.get(0);
        Assert.assertEquals("FIRST_SHIP",first.getName());
        Assert.assertEquals(2,first.getCapacity());
        Assert.assertEquals(4,first.getContainerAmount());
        Assert.assertEquals(ActionType.LOAD,first.getActionType());
        Ship second = actual.get(1);
        Assert.assertEquals("SECOND_SHIP",second.getName());
        Assert.assertEquals(3,second.getCapacity());
        Assert.assertEquals(2,second.getContainerAmount());
        Assert.assertEquals(ActionType.UNLOAD_LOAD,second.getActionType());
    }

}
