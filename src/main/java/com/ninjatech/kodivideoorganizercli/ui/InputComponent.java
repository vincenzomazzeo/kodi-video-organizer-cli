package com.ninjatech.kodivideoorganizercli.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

import com.alee.laf.text.WebTextField;
import com.ninjatech.kodivideoorganizercli.input.InputHandler;

public class InputComponent extends WebTextField implements MouseListener, KeyListener {

    private static final long serialVersionUID = 1L;

    private InputHandler inputHandler;

    public InputComponent() {
        super();

        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        Caret caret = new DefaultCaret();
        caret.setBlinkRate(1000);
        setCaretColor(Color.GREEN);
        setCaret(caret);
        setFont(UI.DEFAULT_FONT.deriveFont(Font.BOLD));
        setFocusTraversalKeysEnabled(false);

        Arrays.stream(getMouseListeners())
              .forEach(this::removeMouseListener);
        Arrays.stream(getMouseMotionListeners())
              .forEach(this::removeMouseMotionListener);

        addMouseListener(this);
        addKeyListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        requestFocusInWindow();
        setCaretPosition(getText().length());
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}

    @Override
    public void keyTyped(KeyEvent event) {
        if (event.getModifiers() == 0) {
            String text = getText();
            switch (event.getKeyChar()) {
            case KeyEvent.VK_ENTER:
                this.setText("");
                this.inputHandler.handle(text);
                break;
            case KeyEvent.VK_TAB:
                this.inputHandler.handleTab(text);
                break;
            default:
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyChar() == KeyEvent.VK_TAB && (event.getModifiers() & InputEvent.CTRL_MASK) != 0) {
            this.inputHandler.handleCtrlTab(getText());
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getModifiers() == 0) {
            switch (event.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.inputHandler.handleUp();
                break;
            case KeyEvent.VK_DOWN:
                this.inputHandler.handleDown();
                break;
            default:
            }
        }
    }

    public void setInput(String input) {
        this.setText(input);
    }

    public void register(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

}
