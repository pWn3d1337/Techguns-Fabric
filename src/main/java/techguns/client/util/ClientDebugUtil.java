package techguns.client.util;

import techguns.client.particle.TGFX;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ClientDebugUtil {

    //TODO: Move to other file
    public static void reloadFXFiles() {
        String srcAssets = "../src/main/resources" + TGFX.FXLIST_DIR;
        String buildAssets = "../build/resources/main" + TGFX.FXLIST_DIR;

        for (String fxFile: TGFX.FXFILES) {
            Path srcPath = Paths.get(srcAssets + fxFile);
            Path dstPath = Paths.get(buildAssets + fxFile);

            assert(Files.exists(srcPath));
            assert(Files.exists(dstPath));

            try {
                Files.copy(srcPath, dstPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
