package blocksworld;

import java.util.*;

import datamining.BooleanDatabase;
import modelling.BooleanVariable;
import modelling.Variable;
import bwgeneratordemo.Demo;

public class DataMining {
    protected int nbreBlock; 
    protected int nbrePile;
    protected PileBlock configMonde;
    private Set<BooleanVariable> items = new HashSet<>();
    protected Map<String, BooleanVariable> mapNomVariables = new HashMap<>(); // couple (etat 'nom' d'une variable , Variable ) .permet de mieu indexer mes variable à travers un nom

    public DataMining(int nbreBlock,int nbrePile){
        configMonde = new PileBlock(nbreBlock, nbrePile);
        initVariableOn();
        initVarOnTable();
        
    }
    //fonction qui initialise q'un bloc soit sur un autre 

    public void initVariableOn(){
        // je parcours toute les variables on_ de l'ensemble de variable de maconfiguration
        for (Variable on_i : configMonde.getVariableOn()) {
            for (Variable fixed_j : configMonde.getVariableFixedB()) {
                int i = PileBlock.getIndex(on_i);
                int j = PileBlock.getIndex(fixed_j);
                if(i==j){
                    continue; // on ne peut pas posser un bloc sur lui meme
                }

                String string = i+"_on_" +j;
                BooleanVariable i_on_j = new BooleanVariable(string);
                this.items.add(i_on_j);
                this.items.add((BooleanVariable) fixed_j);
                mapNomVariables.put(string, i_on_j);
                String nom = "fixed_"+j;
                mapNomVariables.put(nom, (BooleanVariable)fixed_j);
            }
            
        }
    }


    // fonction qui genere tous les blocs qui peuvent etre au plus bas niveau d'une pile 'onTable'
    // et initialise mon ensemble d'items
    public void initVarOnTable(){
        for(Variable on_i:configMonde.getVariableOn()){
            for (Variable free_j :configMonde.getVariableFreeP()) {
                int i = PileBlock.getIndex(on_i);
                int j = PileBlock.getIndex(free_j);
                String string = i+"_onTable_"+j;
                BooleanVariable i_onTable_j = new BooleanVariable(string);
                this.items.add(i_onTable_j);
                this.items.add((BooleanVariable) free_j);
                mapNomVariables.put(string, i_onTable_j);
                mapNomVariables.put("free_"+j, (BooleanVariable) free_j);
                
            }
        }
    }

    public Set<BooleanVariable> getTransactionOfVar(List<List<Integer>> transaction){
        int nbPile = transaction.size();
        Set<BooleanVariable> transactionVar = new HashSet<>();
        // je parcours chaque liste de transaction qui correspond à une pile du monde des blocks
        for(int p=0;p<nbPile;p++){
            // si ma pile est vide alors elle est sous la forme  free_ =true
            if (transaction.get(p).isEmpty()) {
                int numPile =-nbPile+p;
                String string = "free_"+numPile;
                // si mon ensemble d'items contient l'items de ma transaction alors je recupere la variable qui l'associe
                // sinon je cree une nouvelle BooleanVariable ayant pour nom l'items
                BooleanVariable freeVar = contains(string)? mapNomVariables.get(string): new BooleanVariable(string);
                transactionVar.add(freeVar);
                mapNomVariables.put(string, freeVar);
                // if (mapNomVariables.containsKey(string)) {
                //     BooleanVariable var = mapNomVariables.get(string);
                //     transactionVar.add(var);
                // }
                // else{
                //     BooleanVariable var = new BooleanVariable(string);
                //     transactionVar.add(var);
                // }

            }
            int nbBlock = transaction.get(p).size();
            for(int b=0;b<nbBlock;b++){
                int i = transaction.get(p).get(b);
                if (b==0) { // si mon bloc est au niveau le plus bas on ecris bloc_onTable_pile
                    String string = b+"_onTable_"+p;
                    BooleanVariable varOnTable = contains(string)? mapNomVariables.get(string) : new BooleanVariable(string);
                    // j'ajoute la variable booleen à mon ensemble de tansaction 
                    transactionVar.add(varOnTable);
                    // et aussi à ma map d'item
                    mapNomVariables.put(string, varOnTable);
                }
                // dans le cas ou une variable n'est pas en bas d'une pile 
                if (b< nbBlock - 1) {

                int j= transaction.get(p).get(b+1);
                String strOn = j+"_on_"+i;
                String strFixed = "fixed_"+i;
                // si mon ensemble d'items contient l'items de ma transaction alors je recupere la variable qui l'associe
                // sinon je cree une nouvelle BooleanVariable ayant pour nom l'items et je les ajoute à mon ensemble d'item et à ma map

                BooleanVariable j_on_i= contains(strOn)? mapNomVariables.get(strOn): new BooleanVariable(strOn);
                transactionVar.add(j_on_i);
                mapNomVariables.put(strOn, j_on_i);
                BooleanVariable fixed_i = contains(strFixed)? mapNomVariables.get(strFixed):new BooleanVariable(strFixed);
                transactionVar.add(fixed_i);
                mapNomVariables.put(strFixed, fixed_i);

                }
            }

        }
        return transactionVar;
    }

    public boolean contains(String str){
        return mapNomVariables.containsKey(str);
    }

    public BooleanDatabase creatDataBase(int nbTransaction){
        BooleanDatabase dataBase = new BooleanDatabase(this.items);
        for(int i=0;i<nbTransaction;i++){
            List<List<Integer>> transaction = Demo.getState(new Random());
            Set<BooleanVariable> instance = getTransactionOfVar(transaction);
            dataBase.add(instance);
        }
        return dataBase;
    }

    // public BooleanDatabase myDb (List<List<Integer>>pileBloc){
    //     BooleanDatabase dataBase = new BooleanDatabase(this.items);
    //     for(int i=0;i<pileBloc.size();i++){
    //         Set<BooleanVariable> instance = getTransactionOfVar(pileBloc);
    //         dataBase.add(instance);
    //     }
    //     return dataBase;

    // }

    public Set<BooleanVariable> getItems() {
        return items;
    }

}
