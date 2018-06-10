package com.ninjatech.kodivideoorganizercli.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.WindowConstants;

import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.ninjatech.kodivideoorganizercli.CLI;

public class UI extends WebFrame implements WindowListener {

    private static final long serialVersionUID = 1L;

    protected static final Font DEFAULT_FONT = new Font("Courier New", Font.PLAIN, 16);

    private final InputComponent input;

    public UI(StatusComponent status,
              InputComponent input,
              OutputComponent output) {
        this.input = input;

        addWindowListener(this);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImages(WebLookAndFeel.getImages());
        setShowTitleComponent(false);
        setAttachButtons(true);
        setSize(1300, 700);
        setLocationRelativeTo(null);

        WebPanel container = new WebPanel(new BorderLayout());
        container.setUndecorated(true);
        container.setMargin(20, 0, 0, 0);
        ComponentMoveAdapter.install(container, this);

        WebScrollPane scrollPane = new WebScrollPane(output, false, false);
        scrollPane.setMargin(0, 0, 0, 0);
        scrollPane.getWebHorizontalScrollBar()
                  .setBlockIncrement(10);

        container.add(scrollPane, BorderLayout.CENTER);
        container.add(this.input, BorderLayout.SOUTH);

        setFocusTraversalPolicy(new FixedFocusTraversalPolicy(this.input));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(container, BorderLayout.CENTER);
        getContentPane().add(status, BorderLayout.SOUTH);
    }

    @Override
    public void windowOpened(WindowEvent event) {}

    @Override
    public void windowClosing(WindowEvent event) {
        CLI.exit();
    }

    @Override
    public void windowClosed(WindowEvent event) {}

    @Override
    public void windowIconified(WindowEvent event) {}

    @Override
    public void windowDeiconified(WindowEvent event) {}

    @Override
    public void windowActivated(WindowEvent event) {
        this.input.requestFocusInWindow();
    }

    @Override
    public void windowDeactivated(WindowEvent event) {}

}
