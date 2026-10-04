package Asmat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sauvegarde / chargement de ParametresEntretien dans son propre fichier JSON.
 */
public class ParametresEntretienRepository {

    private static File getFile() {
        File appDir = new File(System.getProperty("user.home"), "AsmaTdata/data");


        return new File(appDir, "parametres_entretien.json");
    }

    public static ParametresEntretien load() {

        File f = getFile();

        // Premier lancement : le fichier n'existe pas
        if (!f.exists()) {

            ParametresEntretien parametres =
                    new ParametresEntretien(2.65, 0.425);

            // Création du JSON
            save(parametres);

            return parametres;
        }

        try {

            String content = new String(
                    Files.readAllBytes(f.toPath())
            );

            double coefficientBIndem =
                    extractDouble(
                            content,
                            "coefficientBIndem",
                            2.65
                    );

            double coefficientHIndem =
                    extractDouble(
                            content,
                            "coefficientHIndem",
                            0.425
                    );

            return new ParametresEntretien(
                    coefficientBIndem,
                    coefficientHIndem
            );

        } catch (IOException e) {

            e.printStackTrace();

            return new ParametresEntretien(2.65, 0.425);
        }
    }

    public static void save(ParametresEntretien parametres) {

        File f = getFile();

        String json =
                "{\n" +
                        "  \"coefficientBIndem\": "
                        + parametres.getCoefficientBIndem() + ",\n" +
                        "  \"coefficientHIndem\": "
                        + parametres.getCoefficientHIndem() + "\n" +
                        "}\n";

        try {

            Files.write(
                    f.toPath(),
                    json.getBytes()
            );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private static double extractDouble(
            String json,
            String key,
            double fallback) {

        Matcher m = Pattern
                .compile(
                        "\"" + key +
                                "\"\\s*:\\s*(-?[0-9]+(\\.[0-9]+)?)"
                )
                .matcher(json);

        if (m.find()) {
            return Double.parseDouble(m.group(1));
        }

        return fallback;
    }
}