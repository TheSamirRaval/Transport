package com.transport.model;

/**
 * Created by SAM on 27/3/20.
 */
public class DrawerItem {
    private int id;
    private int title;
    private int unPressedIcon;
    private int pressedIcon;

    public DrawerItem(int id, int title, int unPressedIcon, int pressedIcon) {
        this.id = id;
        this.title = title;
        this.unPressedIcon = unPressedIcon;
        this.pressedIcon = pressedIcon;
    }

    public int getId() {
        return id;
    }

    public int getTitle() {
        return title;
    }

    public int getUnPressedIcon() {
        return unPressedIcon;
    }

    public int getPressedIcon() {
        return pressedIcon;
    }
}