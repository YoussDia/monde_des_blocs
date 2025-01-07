// package blocksworld;

// import java.util.Map;
// import java.util.Random;
// import java.util.Set;

// import cp.*;
// import modelling.Constraint;
// import modelling.Variable;
package blocksworld.main;

import java.util.*;

import blocksworld.BlocksWorld;
import blocksworld.ConfigCroissante;
import blocksworld.ConfigRegulier;
import cp.BacktrackSolver;
import cp.DomainSizeVariableHeuristic;
import cp.HeuristicMACSolver;
import cp.MACSolver;
import cp.NbConstraintsVariableHeuristic;
import cp.RandomValueHeuristic;
import cp.Solver;
import cp.ValueHeuristic;
import cp.VariableHeuristic;
import modelling.Constraint;
import modelling.Variable;

public class MainCp {
    public static void main(String[] args) {
        // creation d'une instance de BlocksWorld
        BlocksWorld blocksWorld = new BlocksWorld(10, 2);
        Set<Variable> variables = blocksWorld.getAllVariable();

        System.out.println();
        System.out.println(
                "########################## temps d'exection pour des contraintes regulières #####################");
        System.out.println();
        // Pour les contraintes régulières
        ConfigRegulier mondeRegulier = new ConfigRegulier(blocksWorld);
        Set<Constraint> mesConstraintesRegulieres = mondeRegulier.getContraintes();

        Solver backtrackSolver = new BacktrackSolver(variables, mesConstraintesRegulieres);
        Solver mACSolver = new MACSolver(variables, mesConstraintesRegulieres);
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());
        // VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(false);
        VariableHeuristic variableHeuristic2 = new NbConstraintsVariableHeuristic(mesConstraintesRegulieres, false);
        Solver heuristicMACSolver = new HeuristicMACSolver(variables, mesConstraintesRegulieres, variableHeuristic2,
                valueHeuristic);

        // Mesure du temps pour BacktrackSolver
        long startTime = System.nanoTime();
        Map<Variable, Object> solutionBacktrack = backtrackSolver.solve();
        long endTime = System.nanoTime();
        System.out.println("BacktrackSolver Execution Time: " + (float) (endTime - startTime) / 1_000_000 + " ms");

        // Mesure du temps pour MACSolver
        startTime = System.nanoTime();
        Map<Variable, Object> solutionMAC = mACSolver.solve();
        endTime = System.nanoTime();

        System.out.println("MACSolver Execution Time: " + (float) (endTime - startTime) / 1_000_000 + " ms");

        // Mesure du temps pour HeuristicMACSolver
        startTime = System.nanoTime();
        Map<Variable, Object> solutionHeuristicMAC = heuristicMACSolver.solve();
        endTime = System.nanoTime();
        System.out.println("HeuristicMACSolver Execution Time: " + (float) (endTime - startTime) / 1_000_000 + " ms");

        System.out.println();
        System.out.println(
                "########################## temps d'exection pour des contraintes croissantes #####################");
        System.out.println();

        // je cree une configuration de mon monde Pour les contraintes croissantes
        ConfigCroissante mondeCroissant = new ConfigCroissante(blocksWorld);
        // je recupère toute mes contraintes
        Set<Constraint> mesContraintesCroissante = mondeCroissant.getContraintes();

        Solver backtrackSolverC = new BacktrackSolver(variables, mesContraintesCroissante);
        Solver mACSolverC = new MACSolver(variables, mesContraintesCroissante);
        ValueHeuristic valueHeuristicRandom = new RandomValueHeuristic(new Random());
        VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(false);

        VariableHeuristic variableHeuristicPlusOuMoins = new NbConstraintsVariableHeuristic(mesConstraintesRegulieres,
                false);
        Solver heuristicMACSolver2 = new HeuristicMACSolver(variables, mesConstraintesRegulieres,
                variableHeuristicPlusOuMoins, valueHeuristicRandom);

        // Mesure du temps pour BacktrackSolver
        startTime = System.nanoTime();
        Map<Variable, Object> solutionCroissanteBt = backtrackSolver.solve();
        endTime = System.nanoTime();
        System.out.println("BacktrackSolver  Temps d'execution : " + (float) (endTime - startTime) / 1_000_000 + " ms");

        // Mesure du temps pour MACSolver
        startTime = System.nanoTime();
        Map<Variable, Object> solutionCroissanteMAC = mACSolver.solve();
        endTime = System.nanoTime();
        System.out.println("MACSolver Temps d'execution : " + (float) (endTime - startTime) / 1_000_000 + " ms");

        // Mesure du temps pour HeuristicMACSolver
        startTime = System.nanoTime();
        Map<Variable, Object> solutionCroissanteHeuristicMAC = heuristicMACSolver.solve();
        endTime = System.nanoTime();
        System.out.println("HeuristicMACSolver Execution Time: " + (float) (endTime - startTime) / 1_000_000 + " ms");

        System.out.println();
        System.out.println(
                "########################## temps d'exection pour des contraintes regulières et croissante #####################");
        System.out.println();

        // je cree une configuration de mon monde Pour les contraintes croissantes et
        // regulière

        // creation d'un ensemble qui contiendra mes containtes regulière et croissante
        Set<Constraint> contrainteReguliereEtCroissante = new HashSet<>();
        contrainteReguliereEtCroissante.addAll(mesConstraintesRegulieres);
        contrainteReguliereEtCroissante.addAll(mesContraintesCroissante);

        // solver pour le backtracking ,heuristiqueMac, MAC pour les contraintes
        // regulière et croissante
        Solver backtrackSolverCR = new BacktrackSolver(variables, contrainteReguliereEtCroissante);
        Solver mACSolverCR = new MACSolver(variables, contrainteReguliereEtCroissante);

        // instanciation des Heuristiques
        ValueHeuristic valueHeuristicRandomCR = new RandomValueHeuristic(new Random());
        VariableHeuristic variableHeuristicCR = new DomainSizeVariableHeuristic(false);
        VariableHeuristic variableHeuristicPlusOuMoinsCR = new NbConstraintsVariableHeuristic(mesConstraintesRegulieres,
                false);

        Solver heuristicMACSolverCR = new HeuristicMACSolver(variables, mesConstraintesRegulieres,
                variableHeuristicPlusOuMoinsCR, valueHeuristicRandomCR);
        // Mesure du temps pour BacktrackSolver
        startTime = System.nanoTime();
        Map<Variable, Object> solutionCroissanteRegBt = backtrackSolver.solve();
        endTime = System.nanoTime();
        System.out.println("BacktrackSolver  Temps d'execution : " + (float) (endTime - startTime) / 1_000_000 + " ms");

        // Mesure du temps pour MACSolver
        startTime = System.nanoTime();
        Map<Variable, Object> solutionCroissanteRegMAC = mACSolver.solve();
        endTime = System.nanoTime();
        System.out.println("MACSolver Temps d'execution : " + (float) (endTime - startTime) / 1_000_000 + " ms");

        // Mesure du temps pour HeuristicMACSolver
        startTime = System.nanoTime();
        Map<Variable, Object> solutionCroissanteRegHeuristicMAC = heuristicMACSolver.solve();
        endTime = System.nanoTime();
        System.out.println("HeuristicMACSolver Execution Time: " + (float) (endTime - startTime) / 1_000_000 + " ms");

    }
}