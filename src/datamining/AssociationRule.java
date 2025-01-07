package datamining;
import modelling.BooleanVariable;
import java.util.*;

public class AssociationRule {
    protected Set<BooleanVariable> premise;
    protected Set<BooleanVariable> conclusion;
    protected float frequence;
    protected float confiance;

    public AssociationRule(Set<BooleanVariable> premisse,Set<BooleanVariable> conclusion,float frequence,float confiance){
        this.premise = premisse;
        this.conclusion = conclusion;
        this.frequence = frequence;
        this.confiance = confiance;
    }
    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }
    public float getConfidence() {
        return confiance;
    }
    public float getFrequency(){
        return frequence;
    }
    public Set<BooleanVariable> getPremise() {
        return premise;
    }
    public String toString() {
        return "règle d'association : (premise = " +premise.toString()+
             " ,conclusion =" +conclusion.toString() + ", frequence =" + frequence
                + ", confiance =" + confiance +")";
    }
}

