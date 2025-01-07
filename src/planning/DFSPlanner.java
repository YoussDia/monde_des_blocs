package planning;
import modelling.*;
import java.util.*;

public class DFSPlanner implements Planner{
    
    //Attributs de la classe
    protected Map<Variable, Object> etatInit;
    protected Set<Action> actions;
    protected Goal but;

    //comteur de noeuds visités 
    protected int nodeCountVal;
    // Booleen qui permet d'activer le compteur 
    protected boolean activate;



    public DFSPlanner(Map<Variable, Object> etatInit, Set<Action> actions, Goal but){
        this.etatInit = etatInit;
        this.actions = actions;
        this.but = but;
        this.nodeCountVal = 0;
        this.activate = false;
    }

    //Initailisation des getters nécéssiares pour le parcours des etats

    public Map<Variable, Object> getInitialState(){
        return this.etatInit;
    }

    public Set<Action> getActions(){
        return this.actions;
    }

    public Goal getGoal(){
        return this.but;
    }


    // cette methode nous retourne un plan de type List<Action> 
    @Override
    public List<Action> plan(){
        Stack<Action> plan = new Stack<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(etatInit);

        List<Action> result = dFS(etatInit, plan, closed);
        if (result == null) {
            return null;
        }
        return result;
    }

    public List<Action> dFS(Map<Variable, Object> etat, Stack<Action> plan, Set<Map<Variable, Object>> closed){
        
        //Verifie si le but est satisfait par notre etat de depart et renvoie un plan  
        if(this.but.isSatisfiedBy(etat)){
            return new ArrayList<>(plan);
        }
        for (Action action : getActions()){
            if(action.isApplicable(etat)){
                Map<Variable, Object> next = action.successor(etat);
                
                //Incréménte le compteur lorsqu'il est activé
                if (this.activate) {
                    this.nodeCountVal++;
                }
            
                if(!closed.contains(next)){//Verifie si on déjà exploré cet état
                    plan.push(action);
                    closed.add(next);
                    List<Action> subPlan = dFS(next, plan, closed);//rappel de la fonction sur l'etat actuel pour explorer ce noeud
                    if (subPlan != null) {
                        return subPlan; 
                    }
                    plan.pop();
                }
            }
        } 
        return null; // Si aucun solution n'a été trouvé
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