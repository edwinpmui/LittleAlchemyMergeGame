package org.cis1200.merge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import static java.lang.Boolean.parseBoolean;

public class Main implements Runnable {

    private static JFrame frame;
    static JPanel board;

    private static JLabel status;
    private static Collection<Item> elementCollection = new ArrayList<>();

    private static Collection<String> discoveredCollection = new TreeSet<>();

    public void run() {

        frame = new JFrame("Merge Game");
        frame.setVisible(true);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());
        frame.setFocusable(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                writeComponentsToFile(board, "./save/save1.csv", false);
                // writeCollectionToFile(elementCollection, "./save/save2.csv", false);
                System.exit(0);
            }

        });

        board = new JPanel();
        board.setBackground(Color.white);
        board.setLayout(null);
        frame.add(board, BorderLayout.CENTER);

        loadElementCollection("./save/save2.csv");

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);

        final JButton clearBoard = new JButton("Clear Board");
        clearBoard.addActionListener(e -> {
            board.removeAll();
            board.repaint();
        });
        control_panel.add(clearBoard);

        final JButton waterButton = new JButton("Add Water");
        waterButton.addActionListener(
                e -> addNewPhoto(
                        board, retrieveItem("water"), getRandom(frameWidth), getRandom(frameHeight)
                )
        );
        control_panel.add(waterButton);

        final JButton fireButton = new JButton("Add Fire");
        fireButton.addActionListener(
                e -> addNewPhoto(
                        board, retrieveItem("fire"), getRandom(frameWidth), getRandom(frameHeight)
                )
        );
        control_panel.add(fireButton);

        final JButton earthButton = new JButton("Add Earth");
        earthButton.addActionListener(
                e -> addNewPhoto(
                        board, retrieveItem("earth"), getRandom(frameWidth), getRandom(frameHeight)
                )
        );
        control_panel.add(earthButton);

        final JButton airButton = new JButton("Add Air");
        airButton.addActionListener(
                e -> addNewPhoto(
                        board, retrieveItem("air"), getRandom(frameWidth), getRandom(frameHeight)
                )
        );
        control_panel.add(airButton);

        final JButton button = getjButton();
        control_panel.add(button);

        status = new JLabel(discoveredCollection.size() + 4 + "/" + elementCollection.size());
        control_panel.add(status);

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                loadPhotos();
            }
        });
    }

    private static JButton getjButton() {
        JButton button = new JButton("How to Play");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("How to Play");
                frame.setVisible(true);
                frame.setSize(300, 300);
                frame.setLayout(new BorderLayout());
                frame.setFocusable(true);
                final JTextArea rules = new JTextArea(
                        "Drag elements together to discover new items! \n" +
                                "Discover all items to win! \n"
                                + "Add new base elements to the board using the buttons below.\n"
                                + "This game is inspired by Little Alchemy, " +
                                "and is strictly proof of concept. \n"
                                + "Not all elements have been added\n"
                );

                frame.add(rules);
            }
        });
        return button;
    }

    public static void loadElementCollection(String filePath) {
        FileLineIterator li = new FileLineIterator(filePath);
        while (li.hasNext()) {
            String[] elemString = li.next().split(" ");
            String elemType = elemString[0];
            String elemFilePath = elemString[1];
            boolean baseElem = parseBoolean(elemString[4]);
            Item parent1 = null;
            Item parent2 = null;
            for (Item i : elementCollection) {
                if (!(baseElem)) {
                    if (i.getElementType().equals(elemString[2])) {
                        parent1 = i;
                    }
                    if (i.getElementType().equals(elemString[3])) {
                        parent2 = i;
                    }
                }
            }
            Item newElem = new Item(elemType, elemFilePath, parent1, parent2, baseElem);
            elementCollection.add(newElem);
        }

    }

    public static void loadPhotos() {
        board.removeAll();

        FileLineIterator li = new FileLineIterator("./save/save1.csv");
        String temp = li.next().substring(1);
        String[] discoveredString = temp.split(" ");
        discoveredCollection.addAll(Arrays.asList(discoveredString));

        while (li.hasNext()) {
            String[] elemString = li.next().split(" ");
            String elemName = elemString[0];
            int x = Integer.parseInt(elemString[1]);
            int y = Integer.parseInt(elemString[2]);
            for (Item i : elementCollection) {
                if (i.getElementType().equals(elemName)) {
                    addNewPhoto(board, i, x, y);
                }
            }

        }

        board.repaint();
    }

    public static Item retrieveItem(String elemType) {
        for (Item i : elementCollection) {
            if (i.getElementType().equals(elemType)) {
                System.out.println(i.getElementType());
                return i;
            }
        }
        return null;
    }

    public static void addNewPhoto(JPanel board, Item item, int x, int y) {

        Image img = Toolkit.getDefaultToolkit().createImage(item.getFilePath());
        Item i = new Item(item);
        board.add(i);
        i.setImage(img);
        i.setAutoSize(true);
        i.setOverbearing(true);
        int delta = board.getWidth() / 20;

        i.setSize(delta, delta);
        i.setLocation(x, y);
        board.repaint();
    }

    public static int getRandom(int range) {
        int r = (int) (Math.random() * range);
        return r;
    }

    public static void writeComponentsToFile(
            JPanel board, String filePath,
            boolean append
    ) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            String discoveredItems = "";
            for (String i : discoveredCollection) {
                discoveredItems = discoveredItems + " " + i;
            }

            bw.write(discoveredItems);
            bw.newLine();

            for (Component elem : board.getComponents()) {
                String x = Integer.toString(elem.getX());
                String y = Integer.toString(elem.getY());
                String elemName = ((Item) elem).getElementType();
                String elemString = elemName + " " + x + " " + y;
                bw.write(elemString);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {

        }
    }

    public static void writeCollectionToFile(
            Collection<Item> collection, String filePath,
            boolean append
    ) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            for (Item i : collection) {
                // String discovered = Boolean.toString(i.getValue());
                String elemType = i.getElementType();
                String elemFilePath = i.getFilePath();
                String parent1;
                String parent2;
                if (!i.isBaseElem()) {
                    parent1 = i.getParent1().getElementType();
                    parent2 = i.getParent2().getElementType();
                } else {
                    parent1 = "null";
                    parent2 = "null";
                }
                String baseElem = Boolean.toString(i.isBaseElem());
                String elemString = elemType + " " + elemFilePath + " " + parent1 + " " + parent2
                        + " " + baseElem;
                bw.write(elemString);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {

        }
    }

    public static JPanel getBoard() {
        return board;
    }

    public static JLabel getStatus() {
        return status;
    }

    public static Collection<Item> getElementCollection() {
        return elementCollection;
    }

    public static Collection<String> getDiscoveredCollection() {
        return discoveredCollection;
    }
}
