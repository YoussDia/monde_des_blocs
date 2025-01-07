package blocksworld;

import planning.*;
import modelling.*;
import java.util.*;

public class HeuristicMouvementsNecessaires implements Heuristic {

    private int nbreBlock;
    private Map<String, Variable> variables;
    private Map<Variable, Object> goalState; // Ajout de l'état final

    // Constructeur permettant de passer les informations nécessaires
    public HeuristicMouvementsNecessaires(int nbreBlock, Map<String, Variable> variables,
            Map<Variable, Object> goalState) {
        this.nbreBlock = nbreBlock;
        this.variables = variables;
        this.goalState = goalState; // Stocker l'état final
    }

    @Override
    public float estimate(Map<Variable, Object> etat) {
        int movesNeeded = 0;

        // Parcourir chaque bloc
        for (int b = 0; b < nbreBlock; b++) {
            Variable varOnB = variables.get("on_" + b);
            Variable varFixedB = variables.get("fixed_" + b);

            // Vérifier si les variables existent
            if (varOnB == null || varFixedB == null) {
                System.err.println("Erreur : Variables manquantes pour le bloc " + b);
                continue;
            }

            Object currentPosition = etat.get(varOnB); // Position actuelle
            Object goalPosition = goalState.get(varOnB); // Position finale

            // Si la position actuelle est différente de la position finale
            if (!currentPosition.equals(goalPosition)) {
                movesNeeded++;

                // Si le bloc est fixe, ajouter une pénalité
                if (etat.get(varFixedB) != null && (Boolean) etat.get(varFixedB)) {
                    movesNeeded++;
                }
            }
        }

        return movesNeeded;
    }
}
