package maow.caffeinated;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import maow.caffeinated.api.Caffeinated;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main {
    private static OptionSet options;
    private static String packageName;
    private static Path inputFile;
    private static Path outputDir;

    public static void main(String[] args) throws IOException {
        args(args);
        if (inputFile != null) {
            if (outputDir == null) {
                Caffeinated.print(packageName, inputFile);
            } else {
                Caffeinated.write(packageName, inputFile, outputDir);
            }
        }
    }

    private static void args(String[] args) {
        final OptionParser parser = new OptionParser();

        final OptionSpec<Void> helpSpec = parser.accepts("h", "View help and information about this program.")
                .forHelp();

        final OptionSpec<String> fileSpec = parser.accepts("f", "Specify an input file for the preprocessor to read.")
                .withRequiredArg()
                .required();

        final OptionSpec<String> writeSpec = parser.accepts("w", "Write processed file to a specified directory.")
                .withRequiredArg();

        final OptionSpec<String> packageSpec = parser.accepts("p", "Specify a package name.")
                .withRequiredArg();

        final OptionSpec<String> indentSpec = parser.accepts("i", "Specify an indent string.")
                .withRequiredArg();

        options = parser.parse(args);

        arg(helpSpec, v -> {
            try {
                parser.printHelpOn(System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        arg(fileSpec, s -> {
            final Path path = Paths.get(s);
            if (Files.exists(path)) {
                inputFile = path;
                return;
            }
            System.err.println("ERR: Input file does not exist.");
        });

        arg(writeSpec, s -> {
            final Path path = Paths.get(s);
            if (Files.exists(path)) {
                outputDir = path;
                return;
            }
            System.err.println("ERR: Output directory does not exist.");
        });

        arg(packageSpec, s -> packageName = s);

        arg(indentSpec, Caffeinated::setIndent);
    }

    private static <T> void arg(OptionSpec<T> spec, Consumer<T> consumer) {
        if (options != null && options.has(spec)) {
            final T value = spec.value(options);
            consumer.accept(value);
        }
    }
}
