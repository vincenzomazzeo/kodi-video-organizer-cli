package com.ninjatech.kodivideoorganizercli.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

public class FixedFocusTraversalPolicy extends FocusTraversalPolicy {

    private final Component component;

    protected FixedFocusTraversalPolicy(Component component) {
        this.component = component;
    }

    @Override
    public Component getComponentAfter(Container container, Component component) {
        return this.component;
    }

    @Override
    public Component getComponentBefore(Container container, Component component) {
        return this.component;
    }

    @Override
    public Component getFirstComponent(Container container) {
        return this.component;
    }

    @Override
    public Component getLastComponent(Container container) {
        return this.component;
    }

    @Override
    public Component getDefaultComponent(Container container) {
        return this.component;
    }

}
