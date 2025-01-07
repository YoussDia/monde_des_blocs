package modelling;
import java.util.*;

public class DifferenceConstraint implements Constraint{
    
    public Variable v1;
    public Variable v2;


    public DifferenceConstraint(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;

    }
    @Override
    public Set<Variable> getScope(){
        Set<Variable> monSet = new HashSet<Variable>();
        monSet.add(v1);
        monSet.add(v2);
        return monSet;

    }
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instance){
        if ((!instance.containsKey(v1) || !instance.containsKey(v2))){
            throw new IllegalArgumentException("les deux variables ne sont pas dans l'instance ");
        }

        return (!(instance.get(v1).equals(instance.get(v2))));
    }

    // @Override
    // public String toString() {
    //     return "{ Difference contraintes}";
    // }
}
