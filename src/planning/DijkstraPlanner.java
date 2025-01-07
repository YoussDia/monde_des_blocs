package planning;
import modelling.*;
import java.util.*;

public class DijkstraPlanner implements Planner{
    
    // Attributs de la classe
    protected Map<Variable, Object> etatInit;
    protected Set<Action> actions;
    protected Goal but;

    //comteur de noeuds visités 
    protected int nodeCountVal;
    // Booleen qui permet d'activer le compteur 
    protected boolean activate;

    public DijkstraPlanner(Map<Variable, Object> etatInit, Set<Action> actions, Goal but){
        this.etatInit = etatInit;
        this.actions = actions;
        this.but = but;
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

    // Méthode principale de planification utilisant l'algorithme de Dijkstra
    @Override
    public List<Action> plan(){
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        // Set<Map<Variable, Object>> open = new HashSet<>();
        Map<Variable, Object> etat;


        PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

        // Initialisation du premier état
        distance.put(etatInit, 0.0f);
        open.add(etatInit);
        father.put(etatInit, null);


        while(!open.isEmpty()){
            etat = open.poll();
            if (this.activate) {
                this.nodeCountVal++;
            }
            // etat = etatChoose(open,distance);
            // open.remove(etat);

            // Vérifie si l'état courant satisfait le but, et si oui, reconstruit le plan
            if (but.isSatisfiedBy(etat)) {
                return getDijkstraPlan(father, plan, etat);
            }

            // Parcours des actions applicables à l'état courant
            for (Action action : getActions()) {
                if (action.isApplicable(etat)) {
                    // Calculer l'état successeur et mettre à jour les distances
                    Map<Variable, Object> next = action.successor(etat);
                    if (!distance.containsKey(next)) {
                        distance.put(next, Float.POSITIVE_INFINITY);  // Initialiser la distance à l'infini si elle n'est pas déjà présente
                    }
                    float newDist = distance.get(etat) + action.getCost();  // Calculer la nouvelle distance si l'action est prise
                    if (newDist < distance.get(next)) {
                        // Mise à jour de la distance, de la relation père-fils, et de l'action menant à l'état
                        distance.put(next, newDist);
                        father.put(next, etat);
                        plan.put(next, action);
                        open.add(next);  // Ajouter le successeur à la PriorityQueue
                    }
                }
            }
        }

        // Si aucun plan n'est trouvé, retourner null
        return null;
    }

    // Méthode pour reconstruire le plan à partir de la relation père-fils
    public  List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> planMap, Map<Variable, Object> etatBut) {
        List<Action> dijPlan = new LinkedList<>();
        Map<Variable, Object> etat = etatBut;
        while (father.get(etat) != null) {
            Action action = planMap.get(etat);
            if (action != null) {
                dijPlan.add(action);
            }
            etat = father.get(etat);
        }
        Collections.reverse(dijPlan); // on ajoute les actions qui nous menne au but une fois que on les a toute 
        return dijPlan;               // parcourue d'ou le besoins de renverser notre liste d'action pour les avoirs dans l'ordre.
    }

    // Méthode pour choisir l'état avec la plus petite distance dans l'ensemble ouvert (non utilisée ici car une PriorityQueue est utilisée)
    public Map<Variable, Object> etatChoose(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> distance) {
        Map<Variable, Object> minEtat = null;
        float minDistance = Float.POSITIVE_INFINITY;

        // Parcourt les états ouverts pour trouver celui avec la plus petite distance
        for (Map<Variable, Object> iOpen : open) {
            float distanceCur = distance.get(iOpen);
            if (distanceCur < minDistance) {
                minDistance = distanceCur;
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