package datamining;
import java.util.*;

import modelling.BooleanVariable;


public class Apriori extends AbstractItemsetMiner {
    public Apriori(BooleanDatabase base){
        super(base);
    }


    
    @Override
    public Set<Itemset> extract(float frequenceMin) {
        // initialisation des singletons 
        Set<Itemset> itemsFrequent = new HashSet<>();
        itemsFrequent.addAll(frequentSingletons(frequenceMin));
        List<SortedSet<BooleanVariable>> frequentParNiveau = new LinkedList<>();

        for (Itemset item : itemsFrequent) {
            SortedSet<BooleanVariable> singleton = new TreeSet<>(COMPARATOR);
            singleton.addAll(item.getItems());
            // j'ajoute tous les singletons pour un niveau k=1
            frequentParNiveau.add(singleton);
        }
        // je passe à la taille suivante des ensembles frequents càd k+1
        while (!frequentParNiveau.isEmpty() && frequentParNiveau.get(0).size()<this.base.getItems().size()) {
            List<SortedSet<BooleanVariable>> nCourant = new LinkedList<>();

            for (int i=0;i<frequentParNiveau.size();i++) {
                for(int j=i+1;j<frequentParNiveau.size();j++){
                    // combinaison des ensensembles de taille k pour avoir l'ensemble k+1
                    SortedSet<BooleanVariable> combinationSet = Apriori.combine(frequentParNiveau.get(i),frequentParNiveau.get(j));
                    
                    if(combinationSet!=null){
                        // calcule de la fréquence de mon candidat qui est l'ensemble de taille k+1
                        float freqCourante = this.frequency(combinationSet);
                        if (freqCourante >= frequenceMin) {
                            // j'ajoute le candidat le plus frequent au niveau courant
                            nCourant.add(combinationSet);

                            Itemset itemNSuivant = new Itemset(combinationSet, freqCourante);
                            itemsFrequent.add(itemNSuivant);
                        }
                    }
                }
            }
            frequentParNiveau = nCourant;
        }
        return itemsFrequent;
    }

    

    public Set<Itemset> frequentSingletons(float frequenceMin) {
        Set<Itemset> singleton = new HashSet<>();
        // parcours chaque items de ma base 
        for(BooleanVariable BooleanVariable: this.base.getItems()) {
            // je met l'item que je parcours dans un set pour cree des singletons et je calcule sa frequence .
            float freqBV = this.frequency(Set.of(BooleanVariable));
            if(frequenceMin <= freqBV ) {// si la frequance de mon singleton est superieur ou egale à la frequence minimal
                //je l'ajoute à mon ensemble de singleton. 
                Itemset itemSet  = new Itemset(Set.of(BooleanVariable), freqBV);
                singleton.add(itemSet);
            }
        }

        return singleton;
    }

    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable>set1 ,SortedSet<BooleanVariable>set2){
        
        if (set1.size() != set2.size() || set1.isEmpty()|| // si un des deux ensemble est vide on revoie aussi null 
            ! set1.headSet(set1.last()).equals(set2.headSet(set2.last()))||
            set1.last().equals(set2.last())) {
            return null;
        }

        // Combine les ensembles en un seul
        SortedSet<BooleanVariable> set1Set2 = new TreeSet<>(COMPARATOR);
        set1Set2.addAll(set1);
        // comme le dernier element de set1 est different de celui du set2 donc on l'ajoute 
        set1Set2.add(set2.last());
        return set1Set2;
    }

    public static boolean allSubsetsFrequent(Set<BooleanVariable> setItems, Collection<SortedSet<BooleanVariable>> itemsFrequent) {
        // Creation des sous enssemble à partir des element de l'ensemble de depart
        for (BooleanVariable item : setItems) {
            
            SortedSet<BooleanVariable> sousEnsemble = new TreeSet<>(COMPARATOR);
            sousEnsemble.addAll(setItems);
            sousEnsemble.remove(item);

            // Vérifie si le sous-ensemble est dans la collection de sous-ensembles fréquents
            if (!itemsFrequent.contains(sousEnsemble)) {
                return false;
            }
        }
        // Si tous les sous-ensembles sont présents dans frequentSubsets, retourne true
        return true;
    }
} 
    
