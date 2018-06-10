package com.ninjatech.kodivideoorganizercli.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;

import com.ninjatech.kodivideoorganizercli.command.CommandInputChannel;
import com.ninjatech.kodivideoorganizercli.command.CommandOutputChannel;

public class Chooser<T> {

    public enum ChooserType {

        ABORT('a', ChooserResultType.ABORTED, "abort"),
        SKIP('s', ChooserResultType.SKIPPED, "skip");

        private final char c;
        private final ChooserResultType resultType;
        private final String label;

        private ChooserType(char c,
                            ChooserResultType resultType,
                            String label) {
            this.c = c;
            this.resultType = resultType;
            this.label = label;
        }

        private String getLabel() {
            return String.format("%s = %s", this.c, this.label);
        }

    }

    public enum ChooserResultType {

        ABORTED,
        CHOOSEN,
        SKIPPED;

    }

    @FunctionalInterface
    public interface ChooserFormatter<T> {

        public String format(T object);

    }

    public class ChooserResult {

        private final ChooserResultType type;
        private final T element;

        private ChooserResult(ChooserResultType type,
                              T element) {
            this.type = type;
            this.element = element;
        }

        public ChooserResultType getType() {
            return this.type;
        }

        public T getElement() {
            return this.element;
        }

    }

    private final Set<ChooserType> types;
    private final CommandInputChannel inputChannel;
    private final CommandOutputChannel outputChannel;
    private final ChooserFormatter<T> chooserFormatter;

    public Chooser(Set<ChooserType> types,
                   CommandInputChannel inputChannel,
                   CommandOutputChannel outputChannel,
                   ChooserFormatter<T> chooserFormatter) {
        this.types = types;
        this.inputChannel = inputChannel;
        this.outputChannel = outputChannel;
        this.chooserFormatter = chooserFormatter;
    }

    public ChooserResult execute(List<T> data, String type) throws Exception {
        do {
            String format = " [%0".concat(String.valueOf((int) (Math.log10(data.size()) + 1)))
                                  .concat("d] %s");
            for (int i = 0, n = data.size(); i < n; i++) {
                T object = data.get(i);
                this.outputChannel.appendLine(format,
                                              i + 1,
                                              this.chooserFormatter.format(object));
            }
            StringBuilder input = new StringBuilder();
            input.append("Choose ")
                 .append(type)
                 .append(" (")
                 .append(this.types.stream()
                                   .map(ChooserType::getLabel)
                                   .collect(Collectors.joining(",")))
                 .append(")");
            this.outputChannel.appendLine(input.toString());
            String choice = this.inputChannel.take();
            if (NumberUtils.isCreatable(choice)) {
                Integer choiceValue = NumberUtils.createInteger(choice);
                if (choiceValue > 0 && choiceValue <= data.size()) {
                    return new ChooserResult(ChooserResultType.CHOOSEN, data.get(choiceValue - 1));
                }
            }
            else if (choice.length() == 1) {
                ChooserType chooserType = this.types.stream()
                                                    .filter(e -> e.c == choice.charAt(0))
                                                    .findFirst()
                                                    .orElse(null);
                if (chooserType != null) {
                    return new ChooserResult(chooserType.resultType, null);
                }
            }
        }
        while (true);
    }

}
