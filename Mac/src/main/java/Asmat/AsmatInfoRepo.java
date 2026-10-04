package Asmat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AsmatInfoRepo {

    private static final String DOSSIER = System.getProperty("user.home") + "/AsmaTdata/data";

    private static final String FICHIER = DOSSIER + "/asmat_info.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static AsmatInfo load() {

        File file = new File(FICHIER);

        if (!file.exists()) {
            AsmatInfo info = informationsParDefaut();
            save(info);
            return info;
        }

        try (FileReader reader = new FileReader(file)) {

            AsmatInfo info = gson.fromJson(reader, AsmatInfo.class);

            if (info == null) {
                return informationsParDefaut();
            }

            return info;

        } catch (IOException e) {
            e.printStackTrace();
            return informationsParDefaut();
        }
    }

    public static void save(AsmatInfo info) {

        File dossier = new File(DOSSIER);

        if (!dossier.exists()) {
            dossier.mkdirs();
        }

        try (FileWriter writer = new FileWriter(FICHIER)) {
            gson.toJson(info, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static AsmatInfo informationsParDefaut() {
        return new AsmatInfo(
                "STITI",
                "Leila",
                "21,rue ernest mayer 59800 Lille",
                "06 15 80 63 16",
                "509862"
        );
    }
}