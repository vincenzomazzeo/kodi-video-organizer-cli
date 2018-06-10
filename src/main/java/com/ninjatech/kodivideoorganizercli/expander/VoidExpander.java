package com.ninjatech.kodivideoorganizercli.expander;

import com.ninjatech.kodivideoorganizercli.ui.OutputComponent;

public class VoidExpander extends AbstractExpander {

    protected VoidExpander(String input,
                           OutputComponent outputComponent) {
        super(input, outputComponent);
    }

    @Override
    protected String expand() {
        return null;
    }

    @Override
    protected void printChoices() {}

}
