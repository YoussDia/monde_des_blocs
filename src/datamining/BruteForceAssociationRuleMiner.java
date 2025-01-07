package datamining;

import java.util.*;

import modelling.BooleanVariable;

public  class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {
    
    public BruteForceAssociationRuleMiner (BooleanDatabase base){
        super(base);
    }

    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> setItems){
        Set<Set<BooleanVariable>> sousEnsemble = new HashSet<>();
        // cette variable va contenir tous les sous ensembles de taille compris entre  1 et 2**n -1
        Set<Set<BooleanVariable>> tailleCourante = new HashSet<>();
        // initialisation avec les singletons 
        for(BooleanVariable items : setItems) {

            tailleCourante.add(Set.of(items));
        }
        int tailleMax = (int) Math.pow(2, setItems.size()) - 1;
        while (sousEnsemble.size()<tailleMax) {
            sousEnsemble.addAll(tailleCourante);
            //taille suivante va contenir les sous ensemble de taille k+1 exemple {{a,b},{a,c}}
            Set<Set<BooleanVariable>> tailleSuivante = new HashSet<>();
            for (Set<BooleanVariable> itemk : tailleCourante) { 
                for (BooleanVariable item : setItems) {
                    //pour chaque sous-ensemble, j'ajoute un nouvel élément 
                    //de item qui n’est pas encore présent dans le sous-ensemble courant (tailleCourante) pour créer des sous-ensembles plus grands.
                    if (!itemk.contains(item)) {
                        Set<BooleanVariable> sousEnsemblek1 = new HashSet<>(itemk);
                        sousEnsemblek1.add(item);
                        tailleSuivante.add(sousEnsemblek1);
                    }
                }
            }
            tailleCourante  = tailleSuivante;

        }
        sousEnsemble.remove(setItems);//  on retire l'ensemble lui meme une fois tous les sous ensembles ajouté 
        return sousEnsemble;
    }

    @Override
    public Set<AssociationRule> extract(float frequenceMin, float confienceMin) {
        ItemsetMiner extracteur = new Apriori(this.base);
        Set<Itemset> frequentItemsets =  extracteur.extract(frequenceMin); // Obtenir les itemsets fréquents
        Set<AssociationRule> res = new HashSet<>();

        // Parcourt chaque itemset fréquent pour générer des règles d’association
        for (Itemset itemset : frequentItemsets) {
            Set<BooleanVariable> items = itemset.getItems();
            // avec le brut force je touve tous les sous ensembles possible de mes premises
            Set<Set<BooleanVariable>> allPremises = BruteForceAssociationRuleMiner.allCandidatePremises(items);

            for (Set<BooleanVariable> premise : allPremises) {
                // je calcule la conclusion qui est egale à l'ensemble de mes items - premise
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);
                // calcule de la confiance et la frequence de ma regle d'association 
                float confidence = AbstractAssociationRuleMiner.confidence(premise, conclusion, frequentItemsets);
                float frequency = AbstractAssociationRuleMiner.frequency(items, frequentItemsets);

                // Ajouter la règle si la confiance et la frequence sont au moins egale au minimal 
                if (confidence >= confienceMin && frequency >= frequenceMin) {
                    res.add(new AssociationRule(premise, conclusion, frequency, confidence));
                }
            }
        }

        return res;
    }

    
    

    
}
