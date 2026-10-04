package Asmat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnfantRepository {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    private static File getFile() {
        File dir = new File(System.getProperty("user.home"), "AsmaTdata/data");

        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("Impossible de créer le dossier data");
        }

        return new File(dir, "enfants.json");
    }

    public static List<Enfant> load() {
        File file = getFile();

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {

            Type type = new TypeToken<List<Enfant>>() {}.getType();
            List<Enfant> enfants = gson.fromJson(reader, type);

            if (enfants == null) {
                return new ArrayList<>();
            }

            // 🔒 sécurité : éviter crash plus tard sur données corrompues
            for (Enfant e : enfants) {
                if (e == null) continue;

                if (e.getFiches() == null) continue;

                e.getFiches().values().forEach(map -> {
                    if (map == null) return;

                    map.values().removeIf(fp ->
                            fp == null ||
                                    fp.getMonth() <= 0 ||
                                    fp.getYear() <= 0
                    );
                });
            }

            return enfants;

        } catch (Exception e) {
            System.err.println("❌ Erreur lecture JSON → fichier ignoré (reset safe)");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void save(List<Enfant> enfants) {
        File file = getFile();

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(enfants, writer);
        } catch (IOException e) {
            System.err.println("❌ Erreur sauvegarde JSON");
            e.printStackTrace();
        }
    }
}