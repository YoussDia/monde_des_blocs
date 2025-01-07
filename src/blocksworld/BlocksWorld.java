package blocksworld;

import modelling.*;
import java.util.*;

public class BlocksWorld extends PileBlock {
    protected List<Constraint> contraintes;      // Liste pour stocker les contraintes

    // Le constructeur de BlocksWorld hérite du constructeur de PileBlock
    public BlocksWorld(int nbreBlock, int nbrePile) {
        // Appeler le constructeur de PileBlock pour initialiser les variables de base
        super(nbreBlock, nbrePile);
        this.contraintes = new ArrayList<>();

        // Initialiser les contraintes spécifiques à BlocksWorld
        initContraintes();
    }

    private void initContraintes() {
        for (int b1 = 0; b1 < nbreBlock; b1++) {
            for (int b2 = b1 + 1; b2 < nbreBlock; b2++) {
                Variable varOnB1 = variables.get("on_" + b1);
                Variable varOnB2 = variables.get("on_" + b2);
    
                if (varOnB1 != null && varOnB2 != null) { // Vérifier que les variables existent
                    Constraint uniquePosition = new DifferenceConstraint(varOnB1, varOnB2);
                    contraintes.add(uniquePosition);
                // } else {
                //     System.out.println("Erreur: Variables on_" + b1 + " ou on_" + b2 + " sont nulles.");
                }
            }
        }
    
        for (int b = 0; b < nbreBlock; b++) {
            Variable varFixedB = variables.get("fixed_" + b);
            for (int b2 = 0; b2 < nbreBlock; b2++) {
                if (b != b2) {
                    Variable varOnB2 = variables.get("on_" + b2);
    
                    if (varFixedB != null && varOnB2 != null) { // Vérifier que les variables existent
                        Constraint fixedIfOn = new Implication(varOnB2, Set.of(b), varFixedB, Set.of(true));
                        contraintes.add(fixedIfOn);
                    // } else {
                    //     System.out.println("Erreur: Variables fixed_" + b + " ou on_" + b2 + " sont nulles.");
                    }
                }
            }
        }
    
        for (int b = 0; b < nbreBlock; b++) {
            Variable varOnB = variables.get("on_" + b);
            for (int p = -nbrePile; p < 0; p++)  {
                Variable varFreeP = variables.get("free_" + p);
    
                if (varOnB != null && varFreeP != null) { // Vérifier que les variables existent
                    Constraint implicationFreeP = new Implication(varOnB, Set.of(-(p + 1)), varFreeP, Set.of(false));
                    contraintes.add(implicationFreeP);
                // } else {
                //     System.out.println("Erreur: Variables on_" + b + " ou free_" + p + " sont nulles.");
                }
            }
        }
    }
    


    // Méthode pour récupérer toutes les contraintes
    public List<Constraint> getContraintes() {
        return contraintes;
    }

    // Validation des contraintes pour une instance spécifique
    public boolean validateConstraints(Map<Variable, Object> instance) {
        for (Constraint constraint : contraintes) {
            if (!constraint.isSatisfiedBy(instance)) {
                return false;
            }
        }
        return true;
    }
}
