package cp;

import java.util.*;

import modelling.Constraint;
import modelling.Variable;

public class HeuristicMACSolver extends AbstractSolver {
    protected ArcConsistency arcConsistency;
    protected Set<Variable> variable;
    protected Set<Constraint> contrainte;
    protected VariableHeuristic varHeuristic;
    protected ValueHeuristic valHeuristic;

    public HeuristicMACSolver(Set<Variable> variable,Set<Constraint> contrainte,VariableHeuristic varHeuristic,ValueHeuristic valHeuristic){
        super(variable, contrainte);
        this.varHeuristic = varHeuristic;
        this.valHeuristic = valHeuristic;
        this.arcConsistency = new ArcConsistency(contraintes);
    }   

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> ensembleDomaine = new HashMap<>();
        Map<Variable, Object> iPartielle = new HashMap<>();
        Set<Variable> vNonInstantiees = new HashSet<>(this.variables);
        
        // Boucle pour associer chaque variable avec son domaine de valeurs possibles
        for (Variable var : this.variables) {
            ensembleDomaine.put(var, var.getDomain());
        }
        //
        return heuristicMac(iPartielle, vNonInstantiees, ensembleDomaine);
        
    }

    //Methode recursive redefinissant l'algorithme MacSolver selon un heuristic donne
    public Map<Variable, Object> heuristicMac(Map<Variable, Object> iPartielle,Set<Variable>vNonInstantiees,Map<Variable, Set<Object>> ensembleDomaine){
        if (vNonInstantiees.isEmpty()) {
            return iPartielle;
        }
        else{
            if (! this.arcConsistency.ac1(ensembleDomaine)) {
                return null;
            }
            // choix d'une variable pas encore instanciée
            // choix de la meilleur variable de mon ensemble de dommaine ;
            Variable xi = this.varHeuristic.best(vNonInstantiees, ensembleDomaine);
            vNonInstantiees.remove(xi);
            // parcours d'une liste de variable melanger psoeudo aleatoirement 
            for (Object vi : this.valHeuristic.ordering(xi, ensembleDomaine.get(xi))) {
                Map<Variable, Object> N = new HashMap<>(iPartielle);
                N.put(xi, vi);

                if (isConsistent(N)) {
                    Map<Variable, Object> R = heuristicMac(N, vNonInstantiees, ensembleDomaine);
                    if (R!=null) {
                        return R;
                    }
                }
            }
            vNonInstantiees.add(xi);
            return null;
        }
    }
}
