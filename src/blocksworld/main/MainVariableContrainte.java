package blocksworld.main;

// import planning.*;
import modelling.*;
import java.util.*;

import blocksworld.BlocksWorld;
import blocksworld.ConfigCroissante;
import blocksworld.ConfigRegulier;

public class MainVariableContrainte {

    public static void main(String[] args) {

        // Premier simulation

        System.out.println("Première configuration faite de façon aléatoire \n");

        BlocksWorld mondeBloc = new BlocksWorld(10, 3);

        // Affichage de toutes mes variables initialisés à création du monde de bloc

        // Affichage de toutes mes variables (on ,fixed,free)
        System.out.println("############# MES VARIABLES (ON FIXED FREE) ###############");
        System.out.println();
        for (Variable var : mondeBloc.getAllVariable()) {
            System.out.println(var);
        }

        // Affichage de mes variables On
        System.out.println();
        System.out.println("############# MES VARIABLES ON ###############");
        for (Variable var : mondeBloc.getVariableOn()) {
            System.out.println(var);
        }
        System.out.println();

        // Affichage de mes variables fixed
        System.out.println("############# MES VARIABLES FIXED ###############");
        for (Variable var : mondeBloc.getVariableFixedB()) {
            System.out.println(var);
        }
        System.out.println();

        // Affichage de mes variables free
        System.out.println("############# MES VARIABLES FREE ###############");
        for (Variable var : mondeBloc.getVariableFreeP()) {
            System.out.println(var);
        }
        System.out.println();

        // Mise en place de Ma configuration aléatoire du monde de bloque
        System.out.println(
                "---------------------------------CONFIGURATION D'UN MONDE ALEATOIRE -----------------------------");
        System.out.println();
        Random rand = new Random();

        for (int i = 0; i < mondeBloc.nbreBlock; i++) {
            Variable varOn = mondeBloc.getVariableOnBloc(i);
            int randomPile = rand.nextInt(mondeBloc.nbrePile) - mondeBloc.nbrePile;
            varOn.setValue(randomPile);
        }

        for (int i = 0; i < mondeBloc.nbreBlock; i++) {
            Variable varFixed = mondeBloc.getVariableFixedBloc(i);
            boolean isFixed = rand.nextBoolean(); // Randomly decide if the block is fixed
            varFixed.setValue(isFixed);
        }

        // Affichage de toutes mes variables (on ,fixed,free) pour un monde generer
        // aléatoirement
        System.out.println("############# MES VARIABLES (ON FIXED FREE) ###############");
        System.out.println();
        for (Variable var : mondeBloc.getAllVariable()) {
            System.out.println(var);
        }

        // liste des contraintes
        // List<Constraint> mesContraintes = mondeBloc.getContraintes();

        // Création d'un dictionnaire de variable
        Map<Variable, Object> instMap1 = new HashMap<>();
        for (Variable var : mondeBloc.getVariableOn()) {
            instMap1.put(var, var.getValue());
        }

        List<Constraint> mesContraintes = mondeBloc.getContraintes();
        System.out.println("\n Mes contraintes : \n" + mesContraintes);
        System.out.println("\n Mes contraintes sont-elles valides pour le monde aleatoire : "
                + mondeBloc.validateConstraints(instMap1));

        // Configuration satisfaisant mes contraintes

        // Affecter des valeurs spécifiques aux variables `on`
        // Par exemple, vous pouvez assigner des valeurs aux blocs pour les placer sur
        // des piles spécifiques.
        mondeBloc.getVariableOnBloc(0).setValue(-1); // Place le bloc 0 sur la pile -1
        mondeBloc.getVariableOnBloc(1).setValue(-2); // Place le bloc 1 sur la pile -2
        mondeBloc.getVariableOnBloc(2).setValue(-3); // Place le bloc 2 sur la pile -3
        mondeBloc.getVariableOnBloc(3).setValue(0); // Place le bloc 3 sur la pile 0
        mondeBloc.getVariableOnBloc(4).setValue(1); // Place le bloc 4 sur la pile 1
        mondeBloc.getVariableOnBloc(5).setValue(2); // Place le bloc 5 sur la pile 2
        mondeBloc.getVariableOnBloc(6).setValue(3); // Place le bloc 6 sur la pile 3
        mondeBloc.getVariableOnBloc(7).setValue(4); // Place le bloc 7 sur la pile 4
        mondeBloc.getVariableOnBloc(8).setValue(5); // Place le bloc 8 sur la pile 5
        mondeBloc.getVariableOnBloc(9).setValue(6); // Place le bloc 9 sur la pile 8

        // representant mon monde des blocs.

        System.out.println(
                "---------------------------------CONFIGURATION D'UN MONDE ALEATOIRE -----------------------------");
        // Affecter des valeurs spécifiques aux variables 'fixed'
        for (int i = 0; i < mondeBloc.nbreBlock; i++) {
            mondeBloc.getVariableFixedBloc(i).setValue(true); // Marquer tous les blocs comme fixés
        }

        // Affecter des valeurs spécifiques aux variables 'free'
        mondeBloc.getVariableFreePile(-1).setValue(false);
        mondeBloc.getVariableFreePile(-2).setValue(false);
        mondeBloc.getVariableFreePile(-3).setValue(false);

        // Création de l'instance map pour la validation des contraintes
        Map<Variable, Object> instMap2 = new HashMap<>();

        // Ajouter toutes les variables on avec leurs valeurs
        for (int i = 0; i < mondeBloc.nbreBlock; i++) {
            Variable varOn = mondeBloc.getVariableOnBloc(i);
            instMap2.put(varOn, varOn.getValue());
        }

        // Ajouter toutes les variables `fixed` avec leurs valeurs
        for (int i = 0; i < mondeBloc.nbreBlock; i++) {
            Variable varFixed = mondeBloc.getVariableFixedBloc(i);
            instMap2.put(varFixed, varFixed.getValue());
        }

        // Ajouter toutes les variables `free` avec leurs valeurs
        for (int p = -mondeBloc.nbrePile; p < 0; p++) {
            Variable varFree = mondeBloc.getVariableFreePile(p);
            instMap2.put(varFree, varFree.getValue());
        }

        System.out.println("Contenu de instMap2 avant la validation :");
        for (Map.Entry<Variable, Object> entry : instMap2.entrySet()) {
            System.out.println(entry.getKey().getName() + " : " + entry.getValue());
        }

        // Validation des contraintes avec l'instance `instMap2`
        boolean contraintesValidees = mondeBloc.validateConstraints(instMap2);
        System.out.println("Les contraintes de bases sont-elles validées ? " + contraintesValidees);

        // teste configuration regulière
        ConfigRegulier mondeRegulier = new ConfigRegulier(mondeBloc);

        boolean contraintesReguliereValidees = mondeRegulier.validateConstraints(instMap2);
        System.out.println(
                "Les contraintes réguliéres sont-elles validées ? " + mondeRegulier.validateConstraints(instMap1));
        System.out.println("Les contraintes réguliéres sont-elles validées ? " + contraintesReguliereValidees);

        ConfigCroissante mondeCroissant = new ConfigCroissante(mondeBloc);
        boolean contraintesCroisantesValidees = mondeCroissant.validateConstraints(instMap2);
        System.out.println("Les contraintes croissantes sont-elles validées pour [[2,5,8],[1,4,7],[0,3,6,9]] ? "
                + contraintesCroisantesValidees);

    }
}