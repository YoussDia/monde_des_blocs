package cp;

import modelling.Constraint;
import modelling.Variable;
import java.util.*;

public class MACSolver extends AbstractSolver{
    protected ArcConsistency arcConsistency;

    public  MACSolver(Set<Variable> variable,Set<Constraint> contrainte){
        super(variable, contrainte);
        this.arcConsistency = new ArcConsistency(contrainte);
    }

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Object> iPartielle = new HashMap<>();
        LinkedList<Variable>vNonInstantiees = new LinkedList<>(this.variables);
        Map<Variable, Set<Object>> ensembleDomaine = new HashMap<>();
        for (Variable var : this.variables) {
            ensembleDomaine.put(var, var.getDomain());
        }
        return mac(iPartielle, vNonInstantiees, ensembleDomaine);
        
    }
    public Map<Variable, Object> mac(Map<Variable, Object> iPartielle,LinkedList<Variable>vNonInstantiees,Map<Variable, Set<Object>> ensembleDomaine){
        if (vNonInstantiees.isEmpty()) {
            return new HashMap<>(iPartielle);
        }
        else{
            if (! this.arcConsistency.ac1(ensembleDomaine)) {
                return null;
            }
            Variable xi = vNonInstantiees.poll();
            for (Object vi : ensembleDomaine.get(xi)) {
                Map<Variable, Object> N = new HashMap<>(iPartielle) ;
                N.put(xi, vi); // Assignez la valeur à la variable
                if (isConsistent(N)) {
                    Map<Variable, Object> R = mac(N,vNonInstantiees,ensembleDomaine);
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
