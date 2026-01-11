package Asmat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnfantRepository {

    private static final String DOSSIER = "AsmaTdata/data"; // sous le home utilisateur
    private static final String FICHIER = "enfants.json";

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    // ================== CHEMIN DU FICHIER ==================
    private static File getFile() {
        String userHome = System.getProperty("user.home"); // fonctionnera sur tous les OS
        File dir = new File(userHome, DOSSIER);
        if (!dir.exists()) dir.mkdirs();
        return new File(dir, FICHIER);
    }

    // ================== CHARGEMENT ==================
    public static List<Enfant> load() {
        File file = getFile();

        // Si le fichier n'existe pas, crée un fichier vide
        if (!file.exists()) {
            save(new ArrayList<>()); // crée un fichier JSON vide
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<List<Enfant>>() {}.getType();
            List<Enfant> enfants = gson.fromJson(reader, type);
            return enfants != null ? enfants : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ================== SAUVEGARDE ==================
    public static void save(List<Enfant> enfants) {
        File file = getFile();
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(enfants, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
