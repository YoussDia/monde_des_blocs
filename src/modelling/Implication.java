package modelling;
import java.util.*;

public class Implication implements Constraint{
    
    public Variable v1;
    public Variable v2;
    public Set<Object> S1;
    public Set<Object> S2;


    public Implication(Variable v1, Set<Object> S1,  Variable v2, Set<Object> S2){
        // if (v1 == null || v2 == null) {
        //     throw new IllegalArgumentException("Les variables v1 et v2 ne peuvent pas être nulles");
        // }
        this.v1 = v1;
        this.S1 = S1;
        this.v2 = v2;
        this.S2 = S2;

    }

    @Override
    public Set<Variable> getScope(){
        Set<Variable> monSet = new HashSet<Variable>();
        monSet.add(v1);
        monSet.add(v2);
        return monSet;
    }
    //Redefinition de  la méthode isSatisfiedBy pour des implications
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instance){
        if ((!instance.containsKey(v1) || !instance.containsKey(v2))){
            // System.err.println("Variable manquante : " + 
            //            (!instance.containsKey(v1) ? v1.getName() : "") + " " + 
            //            (!instance.containsKey(v2) ? v2.getName() : ""));
            throw new IllegalArgumentException("les deux variables ne sont pas dans l'instance ");
        }

        return ((S1.contains(instance.get(v1)) && S2.contains(instance.get(v2))) || !(S1.contains(instance.get(v1))));
    }

    @Override
    public String toString() {
        return "{Implication}";
    }
}