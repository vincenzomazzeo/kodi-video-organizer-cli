package com.ninjatech.kodivideoorganizercli.ui;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import com.alee.laf.text.WebTextPane;
import com.ninjatech.kodivideoorganizercli.output.OutputMessageBuilder;

public class OutputComponent extends WebTextPane {

    private static final long serialVersionUID = 1L;

    public OutputComponent() {
        setBackground(Color.BLACK);
        setEditable(false);
        setFocusable(false);
    }

    public void append(OutputMessageBuilder builder) {
        builder.build()
               .forEach(e -> append(e.getText(), e.getColor(), e.getBold()));
    }

    public void append(String line, Object... args) {
        append(OutputMessageBuilder.outputMessageBuilder()
                                   .text(String.format(line, args)));
    }

    public void appendLine(String line, Object... args) {
        append(OutputMessageBuilder.outputMessageBuilder()
                                   .textLine(String.format(line, args)));
    }

    public void appendSystemLine(String line, Object... args) {
        append(OutputMessageBuilder.outputMessageBuilder()
                                   .brightWhite()
                                   .bold()
                                   .textLine(String.format(line, args)));
    }

    public void appendError(String line, Object... args) {
        append(OutputMessageBuilder.outputMessageBuilder()
                                   .brightRed()
                                   .bold()
                                   .textLine(String.format(line, args)));
    }

    public void appendError(Exception e) {
        append(OutputMessageBuilder.outputMessageBuilder()
                                   .brightRed()
                                   .bold()
                                   .textLine(String.format("Error: %s\n", e.getMessage())));
    }

    public void appendEmptyLine() {
        append("\n", Color.BLACK, false);
    }

    private void append(String text, Color color, boolean bold) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        javax.swing.text.StyleConstants.setBold(attributeSet, bold);
        javax.swing.text.StyleConstants.setForeground(attributeSet, color);
        javax.swing.text.StyleConstants.setFontSize(attributeSet, UI.DEFAULT_FONT.getSize());

        int documentLength = getDocument().getLength();
        try {
            getDocument().insertString(documentLength, text, attributeSet);
            setCaretPosition(getDocument().getLength());
        }
        catch (BadLocationException e) {}
    }

}
