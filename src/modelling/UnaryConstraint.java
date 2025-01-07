package modelling;
import java.util.*;

public class UnaryConstraint implements Constraint{
    
    public Variable v;
    public Set<Object> S;


    public UnaryConstraint(Variable v, Set<Object> S){
        this.v = v;
        this.S = S;

    }
    
    @Override
    public Set<Variable> getScope(){
        Set<Variable> monSet = new HashSet<>();
        monSet.add(v);
        return monSet;

    }
    //Redefinition de la méthode isSastifiedBy  pour des containtes unaires
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instance){
        if (!instance.containsKey(v)){
            throw new IllegalArgumentException("les deux variables ne sont pas dans l'instance ");
        }

        return (S.contains(instance.get(v)));
    }

    @Override
    public String toString() {
        return "{ Contraintes unaires}";
    }
}