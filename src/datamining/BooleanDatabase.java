package datamining;
import java.util.*;

import modelling.BooleanVariable;

public class BooleanDatabase {
    // ensemble d'items
    protected Set<BooleanVariable> setItem;
    // liste des transactions 
    protected List<Set<BooleanVariable>> listTransactions;

    public BooleanDatabase(Set<BooleanVariable> setItem){
        this.setItem = setItem;
        this.listTransactions = new ArrayList<>();

    }
    
    // permet d’ajouter un ensemble d'items 
    public void add(Set<BooleanVariable>transaction){
        this.setItem.addAll(transaction); 
        this.listTransactions.add(transaction);
    }

    public Set<BooleanVariable> getItems(){
        return this.setItem;
    }

    public List<Set<BooleanVariable>> getTransactions(){
        return this.listTransactions;
    }
}
