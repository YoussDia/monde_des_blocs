package cp;
import java.util.*;
import modelling.Variable;

public interface ValueHeuristic {
    public List<Object> ordering(Variable variable, Set<Object> domaine);   
}
