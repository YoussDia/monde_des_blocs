DIARE youssouf / 22008756
Groupe 2A
Licence 3 Info
Ufr des Sciences
Unicaen

Développement d’un planificateur intelligent permettant d’obtenir une configuration cible dans un monde de blocs à partir d’un état initial
donné. Ce projet a combiné la programmation par contraintes, des algorithmes de recherche avancés, et des algorithmes de fouille de
données pour résoudre des problèmes d’agencement tout en respectant des règles prédéfinies

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

 
 
 
 
