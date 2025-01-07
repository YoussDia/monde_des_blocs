package cp;

import java.util.*;
import modelling.Constraint;
import modelling.Variable;

public class BacktrackSolver extends AbstractSolver {
    public  BacktrackSolver(Set<Variable> variable,Set<Constraint> contrainte){
        super(variable, contrainte);
    }

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Object>solPartiel = new HashMap<>();
        LinkedList<Variable> listVar = new LinkedList<>(this.variables);
        return bt(solPartiel, listVar);
    }

    //Methode auxiliaire implementant l'algorithme de backtracking 
    public Map<Variable, Object> bt(Map<Variable, Object> solPartiel,LinkedList<Variable> listVar){
        //renvoie la solution partielle si la liste des variables est vide
        if (listVar.isEmpty()) {
            return solPartiel;
        }
        Variable var = listVar.poll();
        Map<Variable, Object> n = new HashMap<>(solPartiel);

        for (Object obj : var.getDomain()) {
            n.put(var, obj); //Associe chaque valeur à la variable
            if (isConsistent(n)) {
                Map<Variable, Object> r = bt(n, listVar); //Appel recursive pour sur la fonction et stocke le resultat dans r
                if (r!=null) {
                    return r;
                }
            }
        }
        listVar.add(var);
        return null;
    }
}
