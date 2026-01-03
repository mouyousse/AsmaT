package Asmat;

import java.util.HashMap;
import java.util.Map;

public class Enfant {
    private String id;
    private ConfigurationEnfant configuration;

    // année -> (mois -> fiche)
    private Map<Integer, Map<String , Fp>> fiches = new HashMap<>();

    public Enfant(ConfigurationEnfant configuration, String id) {
        this.configuration = configuration;
        this.id = id;
    }
    public Enfant(){

    }

    public ConfigurationEnfant getConfiguration() {
        return configuration;
    }

    public Fp getOrCreateFp(int year, Month month) {
        fiches.putIfAbsent(year, new HashMap<>());
        Map<String, Fp> moisMap = fiches.get(year);

        moisMap.putIfAbsent(month.name(), new Fp(month.name(), year));
        return moisMap.get(month.name());
    }

    public Map<Integer, Map<String, Fp>> getFiches() {
        return fiches;
    }
    public String getId() {
        return id;
    }

}

