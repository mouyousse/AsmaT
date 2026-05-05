🧸 Asmat – App pour assistantes maternelles
Le projet

Asmat est une petite application desktop que j’ai développée en Java pour aider les assistantes maternelles à gérer leurs fiches de présence.

À la base, c’était un besoin concret (ma mère est assistante maternelle).
Elle utilisait des solutions payantes ou faisait tout à la main… donc l’idée était simple : faire un outil gratuit, simple et utilisable au quotidien.

Aujourd’hui, on est sur une version 1.0 fonctionnelle.
Ce n’est pas parfait, il reste des choses à améliorer, mais ça fait déjà gagner pas mal de temps.

Ce que permet l’application :
Créer des fiches de présence mensuelles
Gérer plusieurs enfants

Calculer automatiquement :
les heures
les indemnités
les repas
le salaire net
Ajouter/modifier les jours facilement
Exporter les fiches en PDF
Sauvegarder automatiquement les données

Tech utilisées :
Java
JavaFX (UI)
FXML (structure des vues)
Gson / JSON (sauvegarde locale)
PDFBox (génération PDF)
Maven (dépendances)
IntelliJ IDEA (dev)

Data :

Pas de base de données ici.

Tout est stocké en local dans un fichier JSON :

~/AsmaTdata/data/enfants.json

choix volontaire :

simple
pas de serveur
pas de coût

inconvénients :

moins vesatile

 Compatibilité :
 
 Mac (Apple Silicon)
 Mac (Intel)
 Windows possible

L’app est en Java donc portable, mais le packaging dépend de la machine.

Lancer le projet

En dev:

compilation : javac -encoding UTF-8 --module-path "C:\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml -cp "lib/*" -d bin src/main/java/Asmat/*.java
              jar cfm AsmaTT.jar manifest.mf -C bin . -C src/main/resources    
run : java  --module-path "C:\javafx-sdk-21.0.9\lib"  --add-modules javafx.controls,javafx.fxml  -jar AsmaTT.jar                                            

Ou via jpackage pour faire un .dmg sur macos

système x64 (intel) : AsmaTx-1.0.dmg
système arm (puce apple) : AsmaT-1.0.dmg

 Organisation rapide du code

architechture globalement :

Main → lance l’app
Enfant → modèle principal
ConfigurationEnfant → paramètres du contrat
Fp → fiche de présence (mois)
Presence → un jour
FpService → tous les calculs
EnfantController → logique UI
EnfantRepository → JSON (load/save)
pdf → génération PDF
 problèmes rencontrées 
Les calculs :

Entre :

les heures
les indemnités
les ajustements

j’ai dû séparer la logique dans FpService sinon c’était impossible.

 Le JSON

Au début :

crash au chargement
données incompatibles
comportements différents selon machine

Problèmes classiques :

pas de constructeur vide
champs final
objets imbriqués

corrigé petit à petit, mais ça reste fragile.

 Mac ARM vs Intel

 différences JVM + jpackage
 bugs révélés par l’environnement

 Packaging

jpackage problèmatique

erreurs sur les options JVM
différences entre versions Java
ARM vs x64
 Langue FR / EN

Même en forçant Locale.FRENCH :

parfois l'app était en anglais

dépend du runtime + packaging

 PDF / images

Chemins Windows → pas compatibles Mac

 obligé de revoir la gestion des ressources

Limites actuelles:
code améliorable
pas de base de données
UI perfectible
pas encore ultra robuste aux erreurs
pas pensé pour du multi-user

Évolutions possibles:
base de données (SQLite par exemple)
meilleure gestion des erreurs
UI plus propre
fonctionnalités en plus (stats, exports…)
version Windows packagée propre

Développement:

Projet fait en 2 mois :

Mois 1 :
interface
structure globale
classes principales
Mois 2 :
calculs
bugs
stabilisation

Pourquoi ce projet ?

Projet perso avant tout.

Le but n’était pas de faire quelque chose de parfait,
mais quelque chose d’utile, concret, utilisé dans la vraie vie
et surtout dans le but d'apprendre.

📌 Version

v1.0
