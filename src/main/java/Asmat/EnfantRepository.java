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

    private static final String DOSSIER = "data";
    private static final String FICHIER = "enfants.json";

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    // ================== SAUVEGARDE ==================
    public static void save(List<Enfant> enfants) {
        try {
            File dir = new File(DOSSIER);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(dir, FICHIER);
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(enfants, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ================== CHARGEMENT ==================
    public static List<Enfant> load() {
        File file = new File(DOSSIER, FICHIER);
        if (!file.exists()) {
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
}
