package datamining;

import java.util.NoSuchElementException;
import java.util.*;


import modelling.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner{
    protected BooleanDatabase base;

    public AbstractAssociationRuleMiner(BooleanDatabase base){
        this.base = base;
    }

    public static float frequency(Set<BooleanVariable>setItems,Set<Itemset> setItemset){
        // je parcours tous les items  de mon paramettre setItemset et je m'assure 
        //qu'il a un ensemble d'idems (Set<BooleanVariable>) egale à mon paramettre setItems,
        // je retourne sa frequence si oui ,ou une exeption sinon 
        for (Itemset item: setItemset) {
            if (setItems.equals(item.getItems())) {
                return item.getFrequency();
            }
        }
        throw new NoSuchElementException("cet Itemset n'est pas frequent");
    }

    //Cette méthode doit calculer la confiance d’une règle d’association, en utilisant 
    // les fréquences des ensembles d’items disponibles dans un ensemble d'itemsets passé en argument.
    public static float confidence(Set<BooleanVariable>premisse,Set<BooleanVariable>candidates,Set<Itemset>setItemsets){
        // frequence de la premisse 
        float freqPremisse = frequency(premisse, setItemsets);

        // frequence de l'union entre la premisse et la conclusion 
        Set<BooleanVariable> pUc = new HashSet<>(premisse);
        pUc.addAll(candidates);
        
        float freqPUc = frequency(pUc, setItemsets);
        return freqPUc/freqPremisse;
    }
    @Override
    public BooleanDatabase getDatabase() {
        return this.base;
    }
}
