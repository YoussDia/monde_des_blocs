package datamining;

import java.util.*;

import modelling.BooleanVariable;

public  abstract class AbstractItemsetMiner implements ItemsetMiner{
    public  BooleanDatabase base;
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase b){
        this.base = b;
    }
    
    public float frequency(Set<BooleanVariable>items){
        int cpt =0;
        // parcourir chaque transaction dans la base de données de transactions
        for (Set<BooleanVariable>tansaction : this.base.getTransactions()) {
            //si la transaction courante contient tous les éléments du set "items",j'incremente le compteur
            if (tansaction.containsAll(items)) {
                cpt++;
            }
        }
        // calcule de la frequence 
        return (float) cpt/ (float) this.base.getTransactions().size();
    }

    @Override
    public BooleanDatabase getDatabase() {
        return this.base;
    }


    
}
