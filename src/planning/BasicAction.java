package planning;
import modelling.*;
import java.util.*;



public class BasicAction implements Action{
    //Precondition
    protected Map<Variable, Object> preCond;
    //L'effet d'une action
    protected Map<Variable, Object> effet;
    // Le cout de l'action
    protected int cout;

    public BasicAction(Map<Variable, Object> preCond, Map<Variable, Object> effet, int cout){
        this.preCond = new HashMap<>(preCond);
        this.effet = effet;
        this.cout = cout;
    }

    //Fonction que vérifie si une action est applicable ou pas.
    public boolean isApplicable(Map<Variable, Object> etat){
        // Parcourir la Map avec Map.Entry
        for (Variable var : preCond.keySet()) {
            Object val = preCond.get(var);
            if (!etat.containsKey(var) || !etat.get(var).equals(val)) {
                return false;
            }
        }
        
        return true;
    } 

    //Fonction qui renvoie une liste du nouvel état après l'application de l'action
    public Map<Variable, Object> successor(Map<Variable, Object> etat){

        //Appel de la fonction pour verfier l'applicabilité de l'action
        if (!isApplicable(etat)) {
            throw new IllegalArgumentException("L'action n'est pas applicable à cet état.");
        }

        Map<Variable, Object>  nouvelleEtat= new HashMap<>(etat);
        //Parcours une Map d'effet sous forme de cle -> valeur et applique l'effet à notre precondition 
        for (Map.Entry<Variable, Object> entry : this.effet.entrySet()) {
            nouvelleEtat.put(entry.getKey(), entry.getValue());
        }

        return nouvelleEtat;
    }

    @Override
    public int getCost() {
        return this.cout;
    }

    @Override
    public String toString() {
        return "Action[preCond=" + this.preCond + ", effet=" + this.effet + ", coût=" + this.cout + "]";
    }

}