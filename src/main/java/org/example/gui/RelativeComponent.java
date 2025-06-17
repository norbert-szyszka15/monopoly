package org.example.gui;

import javax.swing.*;

public class RelativeComponent {
    public final JComponent component;
    public final float xPercent, yPercent, widthPercent, heightPercent;

    public RelativeComponent(JComponent component, float xPercent, float yPercent, float widthPercent, float heightPercent) {
        this.component = component;
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
    }

    public void updateBounds(int parentWidth, int parentHeight) {
        int x = Math.round(xPercent * parentWidth);
        int y = Math.round(yPercent * parentHeight);
        int width = Math.round(widthPercent * parentWidth);
        int height = Math.round(heightPercent * parentHeight);
        component.setBounds(x, y, width, height);
    }
}
