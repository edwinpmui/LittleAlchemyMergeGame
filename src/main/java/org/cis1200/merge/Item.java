package org.cis1200.merge;

import java.awt.*;

import static org.cis1200.merge.Main.*;

public class Item extends DraggableImageComponent implements Comparable<Item> {

    private String elementType;

    private String filePath;

    private Item parent1;

    private Item parent2;

    private boolean baseElem;

    Item(String name, String filePath, Item parent1, Item parent2, boolean baseElem) {
        this.elementType = name;
        this.filePath = filePath;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.baseElem = baseElem;
    }

    Item(Item i) {
        this.elementType = i.getElementType();
        this.filePath = i.getFilePath();
        this.parent1 = i.getParent1();
        this.parent2 = i.getParent2();
        this.baseElem = i.isBaseElem();
    }

    public String getElementType() {
        return elementType;
    }

    public String getFilePath() {
        return filePath;
    }

    public Item getParent1() {
        return parent1;
    }

    public Item getParent2() {
        return parent2;
    }

    public boolean isBaseElem() {
        return baseElem;
    }

    public boolean isParents(Item p1, Item p2) {
        for (Item i : getElementCollection()) {
            if ((!i.isBaseElem())) {
                if ((i.getParent1().getElementType().equals(p1.getElementType())
                        && i.getParent2().getElementType().equals(p2.getElementType()))
                        || (i.getParent2().getElementType().equals(p1.getElementType())
                                && i.getParent1().getElementType().equals(p1.getElementType()))) {
                    System.out.println(p1.getElementType());
                    return true;
                }
            }
        }

        return false;
    }

    public boolean within(Point pt, DraggableImageComponent obj1) {
        return (obj1.getX() <= pt.getX()) && (pt.getX() <= obj1.getX() + obj1.getWidth()) &&
                (obj1.getY() <= pt.getY()) && (pt.getY() <= obj1.getY() + obj1.getHeight());
    }

    public boolean collision(DraggableImageComponent obj1, DraggableImageComponent obj2) {
        Point tr = new Point(obj1.getX(), obj1.getY());
        Point tl = new Point(obj1.getX() + obj1.getWidth(), obj1.getY());
        Point br = new Point(obj1.getX(), obj1.getY() + obj1.getHeight());
        Point bl = new Point(obj1.getX() + obj1.getWidth(), obj1.getY() + obj1.getHeight());

        return (within(tr, obj2) || within(tl, obj2) || within(br, obj2) || within(bl, obj2));
    }

    public void merge(Item obj1) {
        if (collision(obj1, this) && isParents(this, obj1)) {
            getBoard().remove(obj1);
            getBoard().remove(this);

            for (Item i : getElementCollection()) {
                if (!(i.isBaseElem())) {
                    String iParent1 = i.getParent1().getElementType();
                    String iParent2 = i.getParent2().getElementType();
                    if (((this.getElementType().equals(iParent1))
                            && (obj1.getElementType().equals(iParent2)))
                            || ((this.getElementType().equals(iParent2))
                                    && (obj1.getElementType().equals(iParent1)))) {
                        addNewPhoto(
                                getBoard(), i, (obj1.getX() + this.getX()) / 2,
                                (obj1.getY() + this.getY()) / 2
                        );
                        getDiscoveredCollection().add(i.getElementType());
                    }
                }
            }
        }
    }

    @Override
    public int compareTo(Item o) {
        return 0;
    }
}
