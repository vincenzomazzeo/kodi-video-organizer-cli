package com.ninjatech.kodivideoorganizercli.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CommandRepository implements BeanPostProcessor {

    public enum Type {

        MAIN(false, "main"),
        TV_SHOW(true, "tv-show"),
        MOVIE(true, "movie");

        private final boolean printable;
        private final String desc;

        private Type(boolean printable,
                     String desc) {
            this.printable = printable;
            this.desc = desc;
        }

        public boolean isPrintable() {
            return this.printable;
        }

        public String getDesc() {
            return this.desc;
        }

        public static Type parse(String desc) {
            return Arrays.stream(values())
                         .filter(e -> e.desc.equals(desc))
                         .findFirst()
                         .orElse(null);
        }

    }

    private final Map<Type, Map<Command, AbstractCommand>> commands;
    private Type activeType;

    public CommandRepository() {
        this.commands = new EnumMap<>(Type.class);
        this.commands.put(Type.MAIN, new EnumMap<>(Command.class));
        this.activeType = Type.MAIN;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractCommand) {
            AbstractCommand command = (AbstractCommand) bean;
            command.getTypes()
                   .forEach(c -> {
                       Map<Command, AbstractCommand> commands = this.commands.get(c);
                       if (commands == null) {
                           commands = new EnumMap<>(Command.class);
                           this.commands.put(c, commands);
                       }
                       commands.put(command.getCommand(), command);
                   });
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    public Type getActiveType() {
        return this.activeType;
    }

    public void setActiveType(Type type) {
        this.activeType = type;
    }

    public AbstractCommand getCommand(Command command) {
        return this.commands.get(this.activeType)
                            .get(command);
    }

    public Set<Command> getAvailableCommands() {
        TreeSet<Command> result = new TreeSet<>(new Comparator<Command>() {

            @Override
            public int compare(Command c1,
                               Command c2) {
                return c1.getName()
                         .compareTo(c2.getName());
            }

        });
        result.addAll(this.commands.get(this.activeType)
                                   .keySet());
        return Collections.unmodifiableSortedSet(result);
    }

}
