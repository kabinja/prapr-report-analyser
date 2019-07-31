package lu.uni.prapr;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class Helpers {
    public static File getResourceFile(String name) {
        URL resource = Helpers.class.getClassLoader().getResource(name);

        if (resource == null) {
            fail("failed to locate resource");
        }


        try {
            return Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            fail("failed to load resource");
        }

        return null;
    }
}
