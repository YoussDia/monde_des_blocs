package cp;

import java.util.*;

import modelling.*;

public abstract class AbstractSolver implements Solver{
    

    protected Set<Variable> variables;
    protected Set<Constraint> contraintes;

    public AbstractSolver(Set<Variable> variable,Set<Constraint> contrainte){
        this.variables = variable;
        this.contraintes = contrainte;
    }

    // fonction qui si une affection Variable-Objet satisfait toutes les contraintes instancies
    // Et retourne Vrai si tel est le cas, Faux sinon
    public boolean isConsistent(Map<Variable,Object>affectation){
        //Recuperation de toutes les variables en les mettant dans un set 
        this.variables = affectation.keySet();
        for (Constraint c : this.contraintes) {
            if (this.variables.containsAll(c.getScope())) {
                // Verifie si une contrainte est satisfaite ou pas 
                if (! c.isSatisfiedBy(affectation)) {
                    return false;
                }
            }
        }
        return true;
    }

    
}
    

