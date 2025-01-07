package blocksworld;

import modelling.*;
import java.util.*;

public class PileBlock {
    public int nbreBlock; // Nombre de blocs
    public int nbrePile; // Nombre de piles
    public Set<Variable> variableOnB; // Onb variables
    public Set<Variable> variableFixedB; // fixedb variables
    public Set<Variable> variableFreeP; // variables piles vide
    public Set<Variable> allVariable; // contiendra onb,fixedb,et les variables des piles
    public Map<String, Variable> variables; // cette map est une structure qui me premetra de recuperer une variable
                                               // à travers un nom

    public PileBlock(int nbreBlock, int nbrePile) {
        this.nbreBlock = nbreBlock;
        this.nbrePile = nbrePile;
        this.variables = new HashMap<>();
        initVariables();
    }

    // Initialisation du domaine pour 'on_b' (positions possibles du bloc)
    private Set<Object> initDomaineOn(int bloc) {
        Set<Object> domaineOn = new HashSet<>();
        for (int i = -nbrePile; i < nbreBlock; i++) { // Inclut les piles et les autres blocs
            if (i != bloc) { // Exclure le bloc lui-même
                domaineOn.add(i);
            }
        }
        return domaineOn;
    }

    // Initialisation des variables
    private void initVariables() {
        // Initialiser les variables 'on_b' pour chaque bloc
        this.variableOnB = new HashSet<>();
        this.variableFixedB = new HashSet<>();
        this.variableFreeP = new HashSet<>();
        this.allVariable = new HashSet<>();

        for (int b = 0; b < nbreBlock; b++) {
            Set<Object> domaineOn = initDomaineOn(b); // Domaine des positions possibles
            Variable varOnb = new Variable("on_" + b, domaineOn);
            this.variableOnB.add(varOnb);
            this.allVariable.addAll(this.variableOnB);
            variables.put(varOnb.getName(), varOnb); // Position du bloc
            // variables.put("fixed_" + b, new BooleanVariable("fixed_" + b)); // Etat
            // 'fixe' du bloc (booléen)

            Variable varFixed = new BooleanVariable("fixed_" + b);
            this.variableFixedB.add(varFixed);
            this.allVariable.addAll(this.variableFixedB);
            variables.put(varFixed.getName(), varFixed);
        }

        // Initialiser les variables 'free_p' pour chaque pile
        for (int p = -nbrePile; p < 0; p++) {
            Variable varFreeP = new BooleanVariable("free_" + p);
            this.variableFreeP.add(varFreeP);
            this.allVariable.addAll(this.variableFreeP);
            variables.put(varFreeP.getName(), varFreeP); // Etat 'libre' de la pile (booléen)
        }

        // for (int i = 0; i < nbreBlock; i++) {
        // if (variables.get("on_" + i) == null) {
        // System.out.println("Erreur: Variable on_" + i + " n'est pas initialisée.");
        // }
        // if (variables.get("fixed_" + i) == null) {
        // System.out.println("Erreur: Variable fixed_" + i + " n'est pas
        // initialisée.");
        // }
        // }

    }

    // methode pour recuperer tooute les variables on_
    public Set<Variable> getVariableOn() {
        return variableOnB;
    }

    // methode pour reccuperer toutes les variables fixed_
    public Set<Variable> getVariableFixedB() {
        return variableFixedB;
    }

    // methode pour recuperer toutes les variables free_
    public Set<Variable> getVariableFreeP() {
        return variableFreeP;
    }

    // Méthode pour récupérer toutes les variables sous la forme couple
    // (identifiant,variable)
    public Map<String, Variable> getVariables() {
        return variables;
    }

    // recupère toute les variables on fixed et free
    public Set<Variable> getAllVariable() {
        return allVariable;
    }

    // Méthode pour récupérer la variable 'on' d'un bloc spécifique
    public Variable getVariableOnBloc(int bloc) {
        return variables.get("on_" + bloc);
    }

    // Méthode pour récupérer la variable 'fixed' d'un bloc spécifique
    public Variable getVariableFixedBloc(int bloc) {
        return variables.get("fixed_" + bloc);
    }

    // Méthode pour récupérer la variable 'fixed' d'un bloc spécifique
    public Variable getVariableFreePile(int pile) {
        return variables.get("free_" + pile);
    }

    // methode pour recuperer l'index d'un bloc ou d'une pile
    // public int getIndex(Variable variable) {
    // String variableName = variable.getName(); // Récupérer le nom de la variable
    // if (variableName.startsWith("on_")) {
    // // Extraire l'index pour les variables "on_"
    // return Integer.parseInt(variableName.substring(3));
    // } else if (variableName.startsWith("fixed_")) {
    // // Extraire l'index pour les variables "fixed_"
    // return Integer.parseInt(variableName.substring(6));
    // } else if (variableName.startsWith("free_")) {
    // // Extraire l'index pour les variables "free_"
    // return Integer.parseInt(variableName.substring(5));
    // } else {
    // throw new IllegalArgumentException("Nom de variable invalide : " +
    // variableName);
    // }
    // }
    public static int getIndex(Variable var) {
        String varName = var.getName();
        String indexPart = varName.replaceAll("[^0-9]", ""); // Extraire les chiffres uniquement
        return Integer.parseInt(indexPart);
    }

    // Méthode pour récupérer les blocs dans une pile spécifique
    public List<Variable> getBlockVariablesInPile(int pileNumber) {
        List<Variable> blocksInPile = new ArrayList<>();

        // Vérifie que le numéro de la pile est valide
        if (pileNumber >= 0 || pileNumber < -nbrePile) {
            throw new IllegalArgumentException("Numéro de pile invalide : " + pileNumber);
        }

        int pileCode = -pileNumber - 1; // Convertir pileNumber en un indice valide

        for (int b = 0; b < nbreBlock; b++) {
            Variable onVariable = getVariableOnBloc(b);
            Object position = onVariable.getValue(); // Récupère la position actuelle du bloc

            if (position != null && position.equals(pileCode)) {
                addStackedBlocks(b, blocksInPile); // Ajouter récursivement les blocs empilés
            }
        }

        return blocksInPile;
    }

    // Ajoute récursivement les blocs empilés pour une pile donnée
    private void addStackedBlocks(int bloc, List<Variable> blocksInPile) {
        Variable currentBlockVariable = getVariableOnBloc(bloc);
        blocksInPile.add(currentBlockVariable);

        for (int b = 0; b < nbreBlock; b++) {
            Variable onVariable = getVariableOnBloc(b);
            Object position = onVariable.getValue();

            if (position != null && position.equals(bloc)) {
                addStackedBlocks(b, blocksInPile);
            }
        }
    }
}
