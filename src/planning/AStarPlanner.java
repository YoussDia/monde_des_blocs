package planning;
import java.util.*;
import modelling.*;


public class AStarPlanner implements Planner{

    protected Map<Variable, Object> etatInit;
    protected Set<Action> actions;
    protected Goal but;
    protected Heuristic heuristic;

    // Attributs pour le comptage des nœuds
    protected int nodeCountVal;
    protected boolean activate;

    public AStarPlanner (Map<Variable, Object> etatInit, Set<Action> actions, Goal but, Heuristic heuristic){
        this.etatInit = etatInit;
        this.actions = actions;
        this.but = but;
        this.heuristic = heuristic;
        this.nodeCountVal = 0;
        this.activate = false;
    }

    public Map<Variable, Object> getInitialState(){
        return this.etatInit;
    }

    public Set<Action> getActions(){
        return this.actions;
    }

    public Goal getGoal(){
        return this.but;
    }

    // Méthode principale de planification utilisant l'algorithme A*
    @Override
    public List<Action> plan(){
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        //Set<Map<Variable, Object>> open = new HashSet<>();
        Map<Variable, Object> etat;
        PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(Comparator.comparingDouble(value::get));

        // Initialisation du premier état
        open.add(etatInit);
        father.put(etatInit,null);
        distance.put(etatInit, Float.valueOf(0));
        value.put(etatInit,distance.get(etatInit) + heuristic.estimate(etatInit));



        while(!open.isEmpty()){
            etat = open.poll();
            if (this.activate) {
                this.nodeCountVal++;
            }
            // etat = etatChoose(open,distance);
            // open.remove(etat);
            if (this.but.isSatisfiedBy(etat)) {
                return getAStarPlan(father, plan, etat);
            }
            // Explorer les actions applicables à l'état courant
            for (Action action : getActions()) {
                if (action.isApplicable(etat)) {
                    // Calculer l'état successeur
                    Map<Variable, Object> next = action.successor(etat);
                    if (!distance.containsKey(next)) {
                        distance.put(next, Float.POSITIVE_INFINITY);  // Initialiser la distance à l'infini si non présente
                    }
                    float newDist = distance.get(etat) + action.getCost();  // Calculer la nouvelle distance si l'action est prise
                    if (newDist < distance.get(next)) {
                        // Mise à jour de la distance, du père, et de l'action menant à cet état
                        distance.put(next, newDist);
                        value.put(next, newDist + heuristic.estimate(next));  // Calcul de la nouvelle valeur f = g + h
                        father.put(next, etat);  // Mise à jour de la relation père-fils
                        plan.put(next, action);  // Associer l'action qui mène à cet état
                        open.add(next);  // Ajouter l'état successeur à l'ensemble des états ouverts
                    }
                }
            }
        }
        // Si aucun plan n'a été trouvé, retourner null
        return null;
    }

    // Méthode pour reconstruire le plan après avoir trouvé le but
    public  List<Action> getAStarPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> planMap, Map<Variable, Object> etatBut) {
        List<Action> dijPlan = new LinkedList<>();
        Map<Variable, Object> etat = etatBut;
        while (father.get(etat) != null) {
            Action action = planMap.get(etat);
            if (action != null) {
                dijPlan.add(action);
            }
            etat = father.get(etat);
        }
        Collections.reverse(dijPlan); // Inverser le plan pour obtenir l'ordre correct
        return dijPlan;
    }

    // Méthode pour choisir l'état ayant la plus petite valeur f (g + h) dans l'ensemble des états ouverts
    public Map<Variable, Object> etatChoose(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> value) {
        Map<Variable, Object> minEtat = null;
        float minValue = Float.POSITIVE_INFINITY;

        // Parcourir tous les états ouverts et choisir celui avec la plus petite valeur heuristique
        for (Map<Variable, Object> iOpen : open) {
            float currentValue = value.get(iOpen);
            if (currentValue < minValue) {
                minValue = currentValue;
                minEtat = iOpen;
            }
        }
        return minEtat;
    }

    // Méthode pour activer ou désactiver le comptage des nœuds
    public void activateNodeCount(boolean activate){ 
        this.activate = activate;
    }
    // Méthode pour récupérer le nombre de nœuds explorés
    public int getNodeCount() {
        return this.nodeCountVal;
    }
        
}