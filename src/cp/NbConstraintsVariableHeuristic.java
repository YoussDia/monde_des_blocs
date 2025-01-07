package cp;

import java.util.*;

import modelling.Constraint;
import modelling.Variable;

public class NbConstraintsVariableHeuristic implements VariableHeuristic{
    protected Set<Constraint> ensembleContraintes;
    protected boolean plusOuMoins;

    public NbConstraintsVariableHeuristic(Set<Constraint> ensembleContraintes,boolean plusOuMoins){
        this.ensembleContraintes = ensembleContraintes;
        this.plusOuMoins = plusOuMoins;
    }


    @Override
    public Variable best(Set<Variable> variable, Map<Variable, Set<Object>> ensembleDomaine) {
        Variable maxi = null;
        Variable mini = null;
        int nbMaxi = Integer.MIN_VALUE;
        int nbMini = Integer.MAX_VALUE;
        for (Variable v : variable) {
            // Initialisation des compteurs à 0 pour chaque variable
            int compteur = 0;
            for (Constraint c : this.ensembleContraintes) {
                Set<Variable> porter = c.getScope();
                if (porter.contains(v)) {
                    compteur+=1;
                }
            }
            // Trouver la variable avec le maximum de contraintes
            if (compteur>nbMaxi) {
                nbMaxi = compteur;
                maxi = v;
            }
            // Trouver la variable avec le minimum de contraintes
            if (compteur<=nbMini) {
                nbMini = compteur;
                mini = v;
            }
        }
        if (this.plusOuMoins) {
            return maxi;
        }
        return mini;

    }
}
