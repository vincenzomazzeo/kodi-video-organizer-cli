package com.ninjatech.kodivideoorganizercli.command;

import javax.swing.SwingUtilities;

import com.ninjatech.kodivideoorganizercli.output.OutputMessageBuilder;
import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class CommandOutputChannel {

    private final OutputComponent outputComponent;

    protected CommandOutputChannel(OutputComponent outputComponent) {
        this.outputComponent = outputComponent;
    }

    public void append(OutputMessageBuilder builder) {
        SwingUtilities.invokeLater(() -> this.outputComponent.append(builder));
    }

    public void append(String line, Object... args) {
        SwingUtilities.invokeLater(() -> this.outputComponent.append(line, args));
    }

    public void appendLine(String line, Object... args) {
        SwingUtilities.invokeLater(() -> this.outputComponent.appendLine(line, args));
    }

    public void appendSystemLine(String line, Object... args) {
        SwingUtilities.invokeLater(() -> this.outputComponent.appendSystemLine(line, args));
    }

    public void appendError(String line, Object... args) {
        SwingUtilities.invokeLater(() -> this.outputComponent.appendError(line, args));
    }

    public void appendError(Exception e) {
        SwingUtilities.invokeLater(() -> this.outputComponent.appendError(e));
    }

    public void appendEmptyLine() {
        SwingUtilities.invokeLater(() -> this.outputComponent.appendEmptyLine());
    }

}
