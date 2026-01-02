package Asmat;

import java.util.HashMap;
import java.util.Map;

public class Enfant {
    private String id;
    private ConfigurationEnfant configuration;

    // année -> (mois -> fiche)
    private Map<Integer, Map<Month, Fp>> fiches = new HashMap<>();

    public Enfant(ConfigurationEnfant configuration, String id) {
        this.configuration = configuration;
        this.id = id;
    }

    public ConfigurationEnfant getConfiguration() {
        return configuration;
    }

    public Fp getOrCreateFp(int year, Month month) {
        fiches.putIfAbsent(year, new HashMap<>());
        Map<Month, Fp> moisMap = fiches.get(year);

        moisMap.putIfAbsent(month, new Fp(month, year));
        return moisMap.get(month);
    }

    public Map<Integer, Map<Month, Fp>> getFiches() {
        return fiches;
    }
    public String getId() {
        return id;
    }
}

