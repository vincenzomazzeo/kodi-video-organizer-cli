package com.ninjatech.kodivideoorganizercli.input;

import org.apache.commons.lang3.StringUtils;

import com.ninjatech.kodivideoorganizercli.command.CommandHandler;
import com.ninjatech.kodivideoorganizercli.expander.ExpanderHandler;
import com.ninjatech.kodivideoorganizercli.ui.InputComponent;

public class InputHandler {

    private final InputComponent inputComponent;
    private final ExpanderHandler expanderHandler;
    private final History history;
    private CommandHandler commandHandler;
    private boolean historyEnabled;

    public InputHandler(InputComponent inputComponent,
                        ExpanderHandler expanderHandler) {
        this.inputComponent = inputComponent;
        this.expanderHandler = expanderHandler;
        this.history = new History();
        this.historyEnabled = true;

        this.inputComponent.register(this);
    }

    public void registerCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void handle(String input) {
        if (this.historyEnabled) {
            if (StringUtils.isNotBlank(input)) {
                this.history.add(input);
            }
            else {
                this.history.resetPointer();
            }
        }
        this.commandHandler.notifyInput(input);
    }

    public void handleDown() {
        if (this.historyEnabled) {
            this.inputComponent.setInput(this.history.getNext());
        }
    }

    public void handleUp() {
        if (this.historyEnabled) {
            String previous = this.history.getPrevious();
            if (StringUtils.isNotBlank(previous)) {
                this.inputComponent.setInput(previous);
            }
        }
    }

    public void handleTab(String input) {
        String expandedInput = this.expanderHandler.expand(input);
        if (expandedInput != null) {
            this.inputComponent.setInput(expandedInput);
        }
    }

    public void handleCtrlTab(String input) {
        this.expanderHandler.printChoices(input);
    }

    public void setHistoryEnabled(boolean enabled) {
        this.historyEnabled = enabled;
    }

}
