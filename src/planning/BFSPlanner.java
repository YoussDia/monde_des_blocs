package planning;

import modelling.*;
import java.util.*;

public class BFSPlanner implements Planner {

    // Attributs de la classe
    protected Map<Variable, Object> etatInit;
    protected Set<Action> actions;
    protected Goal but;

    // comteur de noeuds visités
    protected int nodeCountVal;
    // Booleen qui permet d'activer le compteur
    protected boolean activate;

    public BFSPlanner(Map<Variable, Object> etatInit, Set<Action> actions, Goal but) {
        this.etatInit = etatInit;
        this.actions = actions;
        this.but = but;
        this.nodeCountVal = 0;
        this.activate = false;
    }

    // Initailisation des getters nécéssiares pour le parcours des etats

    public Map<Variable, Object> getInitialState() {
        return this.etatInit;
    }

    public Set<Action> getActions() {
        return this.actions;
    }

    public Goal getGoal() {
        return this.but;
    }

    // Méthode principale de planification en largeur d'abord (BFS)
    @Override
    public List<Action> plan() {
        // Maps pour suivre les états visités et les actions associées
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> closed = new HashSet<>(); // Ensemble des états déjà explorés
        Queue<Map<Variable, Object>> open = new LinkedList<>(); // File pour gérer les états à explorer

        father.put(etatInit, null); // L'état initial n'a pas de père
        open.add(etatInit); // Ajouter l'état initial à la file des états à explorer

        // Si l'état initial satisfait déjà le but, retourner un plan vide (aucune
        // action n'est nécessaire)
        if (this.but.isSatisfiedBy(etatInit)) {
            return new ArrayList<>();
        }

        // Boucle principale d'exploration des états
        while (!open.isEmpty()) {
            Map<Variable, Object> etat = open.poll(); // Récupère et retire l'état en tête de file
            closed.add(etat); // Marquer l'état comme exploré

            // Incrémente le compteur de nœuds explorés si l'activation est active
            if (this.activate) {
                this.nodeCountVal++;
            }

            // Parcours des actions applicables à l'état actuel
            for (Action action : getActions()) {
                if (action.isApplicable(etat)) {
                    Map<Variable, Object> next = action.successor(etat); // Calculer l'état successeur

                    // Si l'état successeur n'a pas encore été exploré ou ajouté à la file
                    if (!closed.contains(next) && !open.contains(next)) {
                        open.add(next); // Ajouter l'état successeur à la file pour exploration future
                        father.put(next, etat); // Mettre à jour la relation père-fils pour reconstruire le plan
                        plan.put(next, action); // Stocker l'action associée à cet état

                        // Si l'état successeur satisfait le but, reconstruire et retourner le plan
                        if (this.but.isSatisfiedBy(next)) {
                            return constructBFSPlan(father, plan, next);
                        }
                    }
                }
            }
        }

        // Si aucun plan n'est trouvé, retourner null
        return null;
    }

    public List<Action> constructBFSPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
            Map<Map<Variable, Object>, Action> planMap, Map<Variable, Object> etatBut) {
        List<Action> plan = new ArrayList<>();
        Map<Variable, Object> etat = etatBut;
        while (father.get(etat) != null) {
            Action action = planMap.get(etat);
            plan.add(action);
            etat = father.get(etat);
        }
        // if (plan == null) {
        // // Si aucun plan n'a été trouvé, retourner null
        // return null;
        // }

        Collections.reverse(plan);
        return plan;
    }

    // Méthode pour activer ou désactiver le comptage des nœuds
    public void activateNodeCount(boolean activate) {
        this.activate = activate;
    }

    // Méthode pour récupérer le nombre de nœuds explorés
    public int getNodeCount() {
        return this.nodeCountVal;
    }

}