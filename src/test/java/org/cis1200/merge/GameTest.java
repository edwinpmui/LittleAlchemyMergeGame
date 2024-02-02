package org.cis1200.merge;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.cis1200.merge.Main.getElementCollection;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void writeCollectionToFileMultiple() {
        Collection<Item> collection = new ArrayList<>();
        String filePath = "./src/test/java/org/cis1200/merge/GameTest.csv";
        Item water = new Item("water", "./files/1.png", null, null, true);
        Item fire = new Item("fire", "./files/2.png", null, null, true);
        collection.add(water);
        collection.add(fire);
        Main.writeCollectionToFile(collection, filePath, false);
        FileLineIterator li = new FileLineIterator(filePath);
        assertTrue(li.hasNext());
        assertEquals(li.next(), "water ./files/1.png null null true");
        assertTrue(li.hasNext());
        assertEquals(li.next(), "fire ./files/2.png null null true");
        assertFalse(li.hasNext());
    }

    @Test
    public void writeCollectionToFileSingleton() {
        Collection<Item> collection = new ArrayList<>();
        String filePath = "./src/test/java/org/cis1200/merge/GameTest.csv";
        Item water = new Item("water", "./files/1.png", null, null, true);
        Item fire = new Item("fire", "./files/2.png", null, null, true);
        collection.add(water);
        collection.add(fire);
        Main.writeCollectionToFile(collection, filePath, false);
        FileLineIterator li = new FileLineIterator(filePath);
        assertTrue(li.hasNext());
        assertEquals(li.next(), "water ./files/1.png null null true");
    }

    @Test
    public void writeCollectionToFileNull() {
        Collection<Item> collection = new ArrayList<>();
        String filePath = "./src/test/java/org/cis1200/merge/GameTest.csv";
        Main.writeCollectionToFile(collection, filePath, false);
        FileLineIterator li = new FileLineIterator(filePath);
        assertFalse(li.hasNext());
    }

    @Test
    public void retrieveItem() {
        Main.loadElementCollection("./src/test/java/org/cis1200/merge/GameTest.csv");
        Item water = new Item("water", "./files/1.png", null, null, true);
        assertEquals(water.getElementType(), Main.retrieveItem("water").getElementType());
    }

    @Test
    public void loadComponentsTest() {
        Main.loadElementCollection("./src/test/java/org/cis1200/merge/GameTest.csv");
        Item water = new Item("water", "./files/1.png", null, null, true);
        Item fire = new Item("fire", "./files/2.png", null, null, true);

        for (Item i : getElementCollection()) {
            if (i.getElementType().equals("water")) {
                assertEquals(water.getElementType(), i.getElementType());
            }

            if (i.getElementType().equals("fire")) {
                assertEquals(fire.getElementType(), i.getElementType());
            }
        }

    }
}
