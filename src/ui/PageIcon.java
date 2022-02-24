package ui;

import tools.ImageInput;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PageIcon implements Icon {
    File file;
    int x1;
    int y1;
    int width;
    int height;

    public PageIcon(File file, int x, int y, int width, int height) {
        this.x1 = x;
        this.y1 = y;
        this.file = file;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawImage(new ImageInput(file).getImage(),x1,y1,width,height,null);
    }

    @Override
    public int getIconWidth() {
        return 0;
    }

    @Override
    public int getIconHeight() {
        return 0;
    }
}
