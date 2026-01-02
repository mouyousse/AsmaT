package exo.exo4save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exo.exo4save.data;

import java.io.*;

public class DataManager {
    private final String filePath = "data.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Sauvegarder l'objet Data dans un fichier JSON
    public void save(data data) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Charger l'objet Data depuis le fichier JSON
    public data load() {
        File file = new File(filePath);
        if (!file.exists()) return new data(); // si le fichier n'existe pas

        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, data.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new data();
        }
    }
}
