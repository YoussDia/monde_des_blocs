package blocksworld.main;

import modelling.*;
import planning.*;
import java.util.*;

import blocksworld.BlockPlanner;
import blocksworld.HeuristicBlocDeplaces;

public class MainPlanification {

    public static void main(String[] args) {
        // Initialisation : création d'un monde avec 10 blocs et 3 piles
        BlockPlanner mondeBloc = new BlockPlanner(10, 3);
        System.out.println("Première configuration de base \n");

        mondeBloc.getVariableOnBloc(0).setValue(-1); // Place le bloc 0 sur la pile -1
        mondeBloc.getVariableOnBloc(1).setValue(-2); // Place le bloc 1 sur la pile -2
        mondeBloc.getVariableOnBloc(2).setValue(-3); // Place le bloc 2 sur la pile -3
        mondeBloc.getVariableOnBloc(3).setValue(0); // Place le bloc 3 sur la pile 0
        mondeBloc.getVariableOnBloc(4).setValue(1); // Place le bloc 4 sur la pile 1
        mondeBloc.getVariableOnBloc(5).setValue(2); // Place le bloc 5 sur la pile 2
        mondeBloc.getVariableOnBloc(6).setValue(3); // Place le bloc 6 sur la pile 3
        mondeBloc.getVariableOnBloc(7).setValue(4); // Place le bloc 7 sur la pile 4
        mondeBloc.getVariableOnBloc(8).setValue(5); // Place le bloc 8 sur la pile 5
        mondeBloc.getVariableOnBloc(9).setValue(8); // Place le bloc 9 sur la pile 8

        // Affecter des valeurs spécifiques aux variables `fixed`
        for (int i = 0; i < mondeBloc.nbreBlock; i++) {
            mondeBloc.getVariableFixedBloc(i).setValue(true); // Marquer tous les blocs comme fixés
        }

        // Affecter des valeurs spécifiques aux variables `free`
        mondeBloc.getVariableFreePile(-1).setValue(false);
        mondeBloc.getVariableFreePile(-2).setValue(false);
        mondeBloc.getVariableFreePile(-3).setValue(false);

        // Création de l'état initial
        Map<Variable, Object> etatInit = new HashMap<>();
        for (Map.Entry<String, Variable> entry : mondeBloc.getVariables().entrySet()) {
            etatInit.put(entry.getValue(), entry.getValue().getValue());
        }

        System.out.println("\nEtat initial de mes variables :");
        for (Map.Entry<Variable, Object> entry : etatInit.entrySet()) {
            System.out.println(entry.getKey().getName() + " : " + entry.getValue());
        }

        mondeBloc.getVariableOnBloc(0).setValue(-1); // Place le bloc 0 sur la pile -1
        mondeBloc.getVariableOnBloc(1).setValue(-2); // Place le bloc 1 sur la pile -2
        mondeBloc.getVariableOnBloc(2).setValue(-3); // Place le bloc 2 sur la pile -3
        mondeBloc.getVariableOnBloc(3).setValue(0); // Place le bloc 3 sur la pile 0
        mondeBloc.getVariableOnBloc(4).setValue(1); // Place le bloc 4 sur la pile 1
        mondeBloc.getVariableOnBloc(5).setValue(2); // Place le bloc 5 sur la pile 2
        mondeBloc.getVariableOnBloc(6).setValue(3); // Place le bloc 6 sur la pile 3
        mondeBloc.getVariableOnBloc(7).setValue(4); // Place le bloc 7 sur la pile 4
        mondeBloc.getVariableOnBloc(8).setValue(5); // Place le bloc 8 sur la pile 5
        mondeBloc.getVariableOnBloc(9).setValue(7); // Place le bloc 9 sur la pile 8

        // Définition de l'état final (objectif)
        Map<Variable, Object> etatFinal = new HashMap<>();
        for (Map.Entry<String, Variable> entry : mondeBloc.getVariables().entrySet()) {
            etatFinal.put(entry.getValue(), entry.getValue().getValue());
        }

        // for (Integer i = 0; i < mondeBloc.nbreBlock; i++) {
        // etatFinal.put(mondeBloc.getVariableOnBloc(i), i - 1);
        // etatFinal.put(mondeBloc.getVariableFixed(i), true);

        // }

        // for (int p = -mondeBloc.nbrePile; p < 0; p++) {
        // if (p != -1) {
        // etatFinal.put(mondeBloc.getVariableFreePile(p), true);
        // } else {
        // etatFinal.put(mondeBloc.getVariableFreePile(p), false);
        // }
        // }

        System.out.println("Etat final de mes variables :");
        for (Map.Entry<Variable, Object> entry : etatFinal.entrySet()) {
            System.out.println(entry.getKey().getName() + " : " + entry.getValue());
        }

        // Vérification de la satisfaction de l'objectif
        BasicGoal but = new BasicGoal(etatFinal);
        boolean objectifSatisfait = but.isSatisfiedBy(etatInit);
        System.out.println("\nLe but est-il satisfait ? " + objectifSatisfait);

        // Actions disponibles
        Set<Action> mesActions = mondeBloc.getActions();
        System.out.println(mesActions);

        // Planification avec l'algorithme BFS

        BFSPlanner bfsPlanner = new BFSPlanner(etatInit, mesActions, but);
        bfsPlanner.activateNodeCount(true);
        List<Action> planBFS = bfsPlanner.plan();
        System.out.println(planBFS);

        // Planification avec l'algorithme DFS

        DFSPlanner dfsPlanner = new DFSPlanner(etatInit, mesActions, but);
        dfsPlanner.activateNodeCount(true);
        List<Action> planDFS = dfsPlanner.plan();
        System.out.println(planDFS);

        // Planification avec l'algorithme DijkstraPlanner

        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(etatInit, mesActions, but);
        dijkstraPlanner.activateNodeCount(true);
        List<Action> plandijkstra = dijkstraPlanner.plan();
        System.out.println(plandijkstra);

        // Planification avec l'algorithme AStarPlanner

        Heuristic heuristic = new HeuristicBlocDeplaces(mondeBloc.nbreBlock, mondeBloc.getVariables());
        AStarPlanner aStarPlanner = new AStarPlanner(etatInit, mesActions, but, heuristic);
        aStarPlanner.activateNodeCount(true);
        List<Action> aStarPlan = aStarPlanner.plan();
        System.out.println(aStarPlan);

    }
}
