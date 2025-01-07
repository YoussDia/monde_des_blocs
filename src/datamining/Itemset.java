package datamining;
import java.util.*;
import modelling.BooleanVariable;

public class Itemset {
    protected Set<BooleanVariable> setItem;
    protected float frequence;
    
    public Itemset(Set<BooleanVariable> setItem,float frequence){
        this.setItem = setItem;
        this.frequence = frequence;
    
    }

    public Set<BooleanVariable> getItems(){
        return this.setItem;
    }

    public float getFrequency(){
        return this.frequence;
    }
    public String toString() {
        return "("+ this.setItem.toString() + ", frequence=" + this.frequence + ")";
    }
}
