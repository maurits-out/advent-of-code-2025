package support;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class InputSupport {

    private static final String INPUT_PATH_TEMPLATE = "/day%02d.txt";

    public static <T> List<T> readAndParseLines(int day, Function<String, T> parser) {
        var uri = buildInputUri(day);
        try (var lines = Files.lines(Path.of(uri))) {
            return lines.map(parser).toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static List<String> readLines(int day) {
        var uri = buildInputUri(day);
        try {
            return Files.readAllLines(Path.of(uri));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static List<String> readSections(int day) {
        return Arrays.asList(readString(day).split("\\R\\R+"));
    }

    public static String readString(int day) {
        var uri = buildInputUri(day);
        try {
            return Files.readString(Path.of(uri));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static URI buildInputUri(int day) {
        var name = INPUT_PATH_TEMPLATE.formatted(day);
        try {
            return InputSupport.class.getResource(name).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("could not build input URI", e);
        }
    }
}
