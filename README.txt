Franck Loick OLANGASSICKA ONDOUMBOU /22112035
DIARE youssouf / 22008756
Groupe 2A
Licence 3 Info
Ufr des Sciences
Unicaen

____________TP fil rouge __________________

______________________________<< Le monde des blocs>>______________________
Dans cette cette partie on devait implementer des algorithmes nous permettant de gerer des contraintes binaires ou uniares pour ensuite les utiliser dans le parcours des graphes pour la resolution de certains dans le domaine de l'IA.

_________________ ORGANISATION DES TACHES DANS LE BINOME_________________________

Afin de mieux aborder cette partie du fil rouge, nous avons divisé le travail en deux partie. Au départ, nous avons travillé sur l'initialisation des variables
afin de séparer les tâches. Ensuite, Franck LOick a traité la suite de la modélisation du monde de bloc. Donc tout ce qui concerne les contraites et la Planification.
Ensuite nous avons tous deux traité la partie sur les problèmes de satisfaction de contraintes.
Et youssouf a lui traité la parie sur l'extraction de connaissances.

_________________ ORGANISATION DES FICHIERS_________________________

Le dossier filRougeFinal contient trois dossiers: un dossier build pour récuperer les fichiers class, 
un dossier lib pour stocker les librairies nécéssaires et un dossier src dans le lequel il y a les packages et leurs classes respectives. 
Voici ci dessous une illustration de l'arborescance :

.
├───build
│   ├───blocksworld
│   ├───cp
│   ├───modelling
│   └───planning
├───lib
└───src
    ├───blocksworld
    │   └───main
    ├───cp
    ├───datamining
    ├───modelling
    └───planning

 
 _______________COMPILATION ET EXECUTION DES FICHIERS_______________
 
 Se rendre dans le repertoire :../ConstraintPlanner/
 
 1) Pour compiler les fichiers taper la commande :

javac -d build -cp "lib/*" src/blocksworld/main/*.java src/**/*.java


javac -d build/ -cp "lib/planningtests.jar;lib/modellingtests.jar;lib/cptests.jar;lib/dataminingtests.jar;lib/bwgenerator.jar" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })

 
 2) pour executer la class executable en restant dans le même terminale taper la commande:

(Sur Linux)
java -cp "build:lib/*" blocksworld.main.NomDUMain

(Sur windows)

java -cp "build;lib/planningtests.jar;lib/modellingtests.jar;lib/cptests.jar;lib/dataminingtests.jar;lib/bwgenerator.jar" blocksworld.main.MainVariableContrainte
java -cp "build;lib/planningtests.jar;lib/modellingtests.jar;lib/cptests.jar;lib/dataminingtests.jar;lib/bwgenerator.jar" blocksworld.main.MainPlanification
java -cp "build;lib/planningtests.jar;lib/modellingtests.jar;lib/cptests.jar;lib/dataminingtests.jar;lib/bwgenerator.jar" blocksworld.main.MainCp
java -cp "build;lib/planningtests.jar;lib/modellingtests.jar;lib/cptests.jar;lib/dataminingtests.jar;lib/bwgenerator.jar" blocksworld.main.MainDatamaning

 
 _________________ DIFFICULTÉES RENCONTRÉES ET PARTIE NON ABORDÉ_________________________
 
 Concernant les diffucultés, Nous avons bloquer le début de la création des variables , sur l'utilistaion de nos algorithmes déjà implementées.
 ET la partie 5, sur la librairie, n'a pas été traité par faute de temps et compréhension. De plus nous avons passé pas mal de temps afin de tester nos planificateurs
 qui renvoyaient à chaque fois des plans nuls peu importe la configuration donnée
 
 _________________ CAPACITÉS ACQUISSES_________________________
 
 Durant cette dernière partie de notre fil rouge, nous avons compris encore un peu plus les algorithmes de résolution de problèmes en l'appliquant sur 
 un cas très pratique où l'on pouvait avoir des résultats démontrant le processus de résolution.
 
 
 
