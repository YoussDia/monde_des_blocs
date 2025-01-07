package planning;

import modelling.*;
import java.util.*;

public class Main{
    public static void main(String[] args){
        //////BasicAction

        //Initialisation du domaine de valeurs
        Set<Object> domaine = new HashSet<>();
        domaine.add(1);
        domaine.add(2);
        domaine.add(3);
        domaine.add(4);

        //Conversion du set en arrayList pour récuperer les valeurs à partir des index
        List<Object> myList = new ArrayList<>(domaine);

        //Initialisation des variables
        Variable x = new Variable("x",domaine);
        Variable y = new Variable("y",domaine);
        Variable z = new Variable("z",domaine);
        Variable t = new Variable("t",domaine);

        System.out.println(x.getName() + "=" + x.getDomain());

        //Etat test pour apliquer une action basique
        Map<Variable, Object> etat = new HashMap<>();
        etat.put(x, myList.get(0)); // x = 1
        etat.put(y, myList.get(2)); // y = 3
        etat.put(z, myList.get(1)); // z = 2
        etat.put(t, myList.get(3)); // t = 4

        //Definitaion de la precondiation
        Map<Variable, Object> preCond = new HashMap<>();
        preCond.put(x,myList.get(0));
        preCond.put(z,myList.get(1));
        // System.out.println("preCondition =" + preCond);

        // Définition de l'effet de l'action
        Map<Variable, Object> effet = new HashMap<>();
        effet.put(x, myList.get(2)); // x devient 3
        effet.put(y, myList.get(2)); // y devient 3

        //Initialisation du cout à 1

        int cout = 1;

        // Application de l'action
        Action action = new BasicAction(preCond,effet,cout);
        Set<Action> actions = new HashSet<>();
        actions.add(action);
        System.out.println(action.successor(etat));


        ///////BasicGoal
        //Initialisation du domaine de valeurs
        Set<Object> domaine2 = new HashSet<>();
        domaine2.add("a");
        domaine2.add("b");
        domaine2.add("c");
        domaine2.add("d");
        domaine2.add("e");
        domaine2.add("f");
        domaine2.add("g");
        domaine2.add("h");

        //Initialisation des variables
        x = new Variable("x",domaine2);
        y = new Variable("y",domaine2);
        z = new Variable("z",domaine2);
        t = new Variable("t",domaine2);
        Variable u = new Variable("u",domaine2);

        myList = new ArrayList<>(domaine2);
        //Etat test pour apliquer une but basique
        Map<Variable, Object> etat1 = new HashMap<>();
        etat1.put(x, myList.get(0)); // x = "a"
        etat1.put(y, myList.get(1)); // y = "b"
        etat1.put(z, myList.get(2)); // z = "c"
        etat1.put(t, myList.get(3)); // t = "d"
        etat1.put(u, myList.get(4)); // u = "e"

        //but à atteindre
        Map<Variable, Object> but = new HashMap<>();
        but.put(x,myList.get(0));
        but.put(z,myList.get(2));
        but.put(t,myList.get(3));

        //Verfication si le but est atteint ou pas 
        BasicGoal goal = new BasicGoal(but);
        System.out.println(goal.isSatisfiedBy(etat1));

        ////// Appel de Dijkstra et A*

        // Création d'un état initial pour Dijkstra et A*
        // Corriger la réinitialisation des variables dans l'état initial
        Variable x2 = new Variable("x", domaine2);
        Variable y2 = new Variable("y", domaine2);
        Variable z2 = new Variable("z", domaine2);
        Variable t2 = new Variable("t", domaine2);

        Map<Variable, Object> initialState = new HashMap<>();
        initialState.put(x2, myList.get(1)); // x = "b"
        initialState.put(y2, myList.get(1)); // y = "b"
        initialState.put(z2, myList.get(3)); // z = "d"
        initialState.put(t2, myList.get(0)); // t = "a"

        // Création de l'ensemble des actions possibles
        Set<Action> allActions = new HashSet<>();
        allActions.add(action); // Ajouter l'action définie plus tôt

        // Test de Dijkstra
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(initialState, allActions, goal);
        dijkstraPlanner.activateNodeCount(true);
        List<Action> dijkstraPlan = dijkstraPlanner.plan();

        // Vérification que le plan n'est pas null
        if (dijkstraPlan != null) {
            // Affichage du plan de Dijkstra
            System.out.println("Plan Dijkstra :");
            for (Action a : dijkstraPlan) {
                System.out.println(a);
            }
            System.out.println("Nombre de nœuds explorés par Dijkstra : " + dijkstraPlanner.getNodeCount());
        } else {
            System.out.println("Le plan Dijkstra est null. Aucune solution trouvée.");
        }

        // Ajout d'autres actions si nécessaire
        // Action 2, Action 3, etc. pour enrichir l'exemple

    }
}