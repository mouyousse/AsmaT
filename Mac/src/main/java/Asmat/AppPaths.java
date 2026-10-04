package Asmat;
import java.io.File;

public final class AppPaths {

    private static final String APP_NAME = "AsmaTT";

    private AppPaths() {
        // empêche l'instanciation
    }

    public static File getAppDirectory() {

        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        File appDir;

        if (os.contains("mac")) {
            // macOS : dossier officiel recommandé par Apple
            appDir = new File(userHome,
                    "Library/Application Support/" + APP_NAME);

        } else if (os.contains("win")) {
            // Windows : AppData/Roaming
            String appData = System.getenv("APPDATA");
            appDir = new File(appData, APP_NAME);

        } else {
            // Linux / autres
            appDir = new File(userHome, "." + APP_NAME.toLowerCase());
        }

        if (!appDir.exists()) {
            boolean created = appDir.mkdirs();
            if (!created) {
                throw new RuntimeException(
                        "Impossible de créer le dossier applicatif : "
                                + appDir.getAbsolutePath());
            }
        }

        return appDir;
    }
}
