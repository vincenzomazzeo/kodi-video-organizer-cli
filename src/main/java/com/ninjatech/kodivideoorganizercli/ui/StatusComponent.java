package com.ninjatech.kodivideoorganizercli.ui;

import java.awt.Color;
import java.awt.GridLayout;

import com.alee.extended.layout.ToolbarLayout;
import com.alee.extended.statusbar.WebMemoryBar;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

public class StatusComponent extends WebStatusBar {

    private static final long serialVersionUID = 1L;

    private final WebLabel basePath;
    private final WebLabel mode;
    private final WebLabel element;
    private final WebLabel subelement;

    public StatusComponent() {
        super();

        setUndecorated(true);
        setMargin(0, 0, 0, 20);

        WebMemoryBar memoryBar = new WebMemoryBar();
        memoryBar.setPreferredWidth(memoryBar.getPreferredSize().width + 20);
        add(memoryBar, ToolbarLayout.END);

        this.basePath = new WebLabel();
        this.basePath.setBoldFont();
        this.basePath.setForeground(Color.BLUE);
        this.basePath.setMargin(0, 5, 0, 5);
        this.mode = new WebLabel();
        this.mode.setBoldFont();
        this.mode.setForeground(Color.MAGENTA);
        this.mode.setMargin(0, 5, 0, 5);
        this.element = new WebLabel();
        this.element.setBoldFont();
        this.element.setForeground(Color.RED);
        this.element.setMargin(0, 5, 0, 5);
        this.subelement = new WebLabel();
        this.subelement.setBoldFont();
        this.subelement.setForeground(Color.RED);
        this.subelement.setMargin(0, 5, 0, 5);

        WebPanel container = new WebPanel(false);
        container.setLayout(new GridLayout(1, 4));
        container.add(this.basePath);
        container.add(this.mode);
        container.add(this.element);
        container.add(this.subelement);
        add(container, ToolbarLayout.FILL);
    }

    public void setBasePath(String basePath) {
        this.basePath.setText(basePath);
        this.basePath.setToolTipText(basePath);
    }

    public void setMode(String mode) {
        this.mode.setText(mode);
    }

    public void setElement(String element) {
        this.element.setText(element);
    }

    public void setSubelement(String subelement) {
        this.subelement.setText(subelement);
    }

}
