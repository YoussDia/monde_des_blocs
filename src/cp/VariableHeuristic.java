package cp;
import java.util.*;
import modelling.Variable;

public interface VariableHeuristic {
    public Variable best(Set<Variable> variable,Map<Variable, Set<Object>> ensembleDomaine);
}
