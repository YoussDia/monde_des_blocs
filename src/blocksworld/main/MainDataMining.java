package blocksworld.main;

import java.util.Set;

import blocksworld.DataMining;
import datamining.Apriori;
import datamining.AssociationRule;
import datamining.BooleanDatabase;
import datamining.BruteForceAssociationRuleMiner;
import datamining.Itemset;
import modelling.BooleanVariable;

public class MainDataMining {
    public static void main(String[] args) {
        
        DataMining blockWorldData = new DataMining(12, 4);
        System.out.println(" ###################### TOUS LES ITEMS GENERER LORS DE LA CONSTRUCTION DU MONDE ######################");
        System.out.println();
        for (BooleanVariable var : blockWorldData.getItems()) {
            System.out.println(var);
        }
        System.out.println();

        // creation d'une base de donnee avec un certains nombre de transaction
        BooleanDatabase dataBase = blockWorldData.creatDataBase(10000);
        // creation d'une instance pour permettre l'extraction d'items frequents 
        Apriori apriori = new Apriori(dataBase);
		Set<Itemset> items = apriori.extract((float) 2/3);
        System.out.println("########################## TOUS LES ITEMS FREQUENTS LORS DE L'EXTRACTION ######################");
        System.out.println();
        for (Itemset item : items) {
			System.out.println(item);
		}

        System.out.println();

        System.out.println(" ###################### REGLES D'ASSOCIATION ######################");
        System.out.println();
        // instantiation d'une regle d'association 
        BruteForceAssociationRuleMiner reglesAssociation = new BruteForceAssociationRuleMiner(dataBase);
        // extraction des regles d'association 
        Set<AssociationRule> regle = reglesAssociation.extract((float) 2 / 3, (float) 95 / 100);
        for (AssociationRule r : regle) {
			System.out.println(r);
		}
    }
}
