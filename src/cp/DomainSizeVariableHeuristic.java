package cp;

import java.util.*;

import modelling.Constraint;
import modelling.Variable;

public class  DomainSizeVariableHeuristic implements VariableHeuristic{
    protected Set<Constraint> ensembleContraintes;
    // Preference des variables avec le plus grand domaine ( true) ou avec le plus petit domaine ( false)
    protected boolean Preference;

    public DomainSizeVariableHeuristic(boolean Preference){
        this.Preference = Preference;
    }
    @Override
    public Variable best(Set<Variable> variable, Map<Variable, Set<Object>> ensembleDomaine) {
        
        Variable maxi = null;
        Variable mini = null;
        int nbMaxi = Integer.MIN_VALUE;
        int nbMini = Integer.MAX_VALUE;
        // Parcourir les variables et comparer la taille de leur domaine
        for (Variable v : variable) {
            int tailleDomaineV = ensembleDomaine.get(v).size();
            if (tailleDomaineV>nbMaxi) {
                nbMaxi = tailleDomaineV;
                maxi = v;
            } 
            if (tailleDomaineV<=nbMini) {
                nbMini = tailleDomaineV;
                mini = v;
            }
        }
        // Choisir la variable en fonction de la taille du domaine et de la préférence
        if (this.Preference) {
            return maxi;
        }
        return mini;

    }
}
