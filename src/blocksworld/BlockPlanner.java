package blocksworld;

import planning.*;
import modelling.*;
import java.util.*;

public class BlockPlanner  extends PileBlock{

    protected Set<Action> actions;

    private Heuristic heuristic;
    
    public BlockPlanner(int nbreBlock, int nbrePile) {
        super(nbreBlock, nbrePile);
        // this.nbreBlock = nbreBlock;
        // this.nbrePile = nbrePile;
        // this.heuristic = heuristic; // Initialisation de l'heuristique
        
        // this.variables = getVariables();
        this.actions = new HashSet<>();
        
        initActions();
    }

    private Map<Variable, Object> createPreconditions(Variable varOnB, Variable varFixedB, Variable varFixedB1, Variable varFixedB2, int b, int b1, int b2, int p) {
        Map<Variable, Object> preCond = new HashMap<>();
        
        // Condition 1 : Le bloc b est au-dessus du bloc b1
        preCond.put(varOnB, b1);
        
        // Condition 2 : Le bloc b est déplaçable (fixed_b est false)
        preCond.put(varFixedB, false);
        
        // Condition 3 : La position de destination (b'') est libre
        preCond.put(varFixedB2, false);

        // Optionnel : La pile p est libre
        if (p >= 0) {
            Variable varFreeP = variables.get("free_" + p);
            preCond.put(varFreeP, true);
        }
        
        return preCond;
    }

    private Map<Variable, Object> createEffects(Variable varOnB, Variable varFixedB, Variable varFixedB1, Variable varFixedB2, int b, int b1, int b2, int p) {
        Map<Variable, Object> effet = new HashMap<>();
        
        // Effet 1 : Le bloc b est maintenant au-dessus du bloc b2
        effet.put(varOnB, b2);
        
        // Effet 2 : Le bloc b1 devient libre (fixed_b1 devient false)
        effet.put(varFixedB1, false);
        
        // Effet 3 : Le bloc b2 devient occupé (fixed_b2 devient true)
        effet.put(varFixedB2, true);

        // Effet 4 : La pile p devient occupée (free_p devient false)
        if (p >= 0) {
            Variable varFreeP = variables.get("free_" + p);
            effet.put(varFreeP, false);
        }
        
        return effet;
    }


    
    private void initActions() {
        // Action 1 : Déplacer un bloc b du dessus d’un bloc b' vers le dessus d’un bloc b''
        for (int b = 0; b < nbreBlock; b++) {
            Variable varOnB = variables.get("on_" + b);
            Variable varFixedB = variables.get("fixed_" + b);

            for (int b1 = 0; b1 < nbreBlock; b1++) {
                if (b == b1) continue;  // Un bloc ne peut pas être placé sur lui-même

                // Variable varOnB1 = variables.get("on_" + b1);
                Variable varFixedB1 = variables.get("fixed_" + b1);

                for (int b2 = 0; b2 < nbreBlock; b2++) {
                    if (b == b2 || b1 == b2) continue; // Éviter les répétitions

                    Variable varFixedB2 = variables.get("fixed_" + b2);

                    // Création des préconditions pour l'action
                    Map<Variable, Object> preCond = createPreconditions(varOnB, varFixedB, varFixedB1, varFixedB2, b, b1, b2, -1);

                    // Création des effets pour l'action
                    Map<Variable, Object> effet = createEffects(varOnB, varFixedB, varFixedB1, varFixedB2, b, b1, b2, -1);

                    // Définition du coût de l'action (par exemple, un coût fixe de 1)
                    int cout = 1;

                    // Création de l'action avec coût
                    Action deplacerBlocVersBloc = new BasicAction(preCond, effet, cout);
                    actions.add(deplacerBlocVersBloc);
                }
            }
        }

        // Action 2 : Déplacer un bloc b du dessus d’un bloc b' vers une pile vide p
        for (int b = 0; b < nbreBlock; b++) {
            Variable varOnB = variables.get("on_" + b);
            Variable varFixedB = variables.get("fixed_" + b);

            for (int b1 = 0; b1 < nbreBlock; b1++) {
                if (b == b1) continue;  // Un bloc ne peut pas être placé sur lui-même

                Variable varFixedB1 = variables.get("fixed_" + b1);

                for (int p = 0; p < nbrePile; p++) {
                    // Création des préconditions pour l'action
                    Map<Variable, Object> preCond = createPreconditions(varOnB, varFixedB, varFixedB1, null, b, b1, -1, p);

                    // Création des effets pour l'action
                    Map<Variable, Object> effet = createEffects(varOnB, varFixedB, varFixedB1, null, b, b1, -1, p);

                    // Définition du coût de l'action (par exemple, un coût fixe de 1)
                    int cout = 1;

                    // Création de l'action avec coût
                    Action deplacerBlocVersPile = new BasicAction(preCond, effet, cout);
                    actions.add(deplacerBlocVersPile);
                }
            }
        }


         // Action 3 : Déplacer un bloc b du dessous d’une pile p vers le dessus d’un bloc b′
        for (int b = 0; b < nbreBlock; b++) {
            Variable varOnB = variables.get("on_" + b);
            Variable varFixedB = variables.get("fixed_" + b);

            for (int p = 0; p < nbrePile; p++) {
                // Variable varFreeP = variables.get("free_" + p);

                for (int b1 = 0; b1 < nbreBlock; b1++) {
                    if (b == b1) continue;  // Un bloc ne peut pas être placé sur lui-même

                    Variable varFixedB1 = variables.get("fixed_" + b1);
                    // Variable varOnB1 = variables.get("on_" + b1);

                    // Vérifier que b est sous la pile p (pas au-dessus de la pile)
                    for (int b2 = 0; b2 < nbreBlock; b2++) {
                        if (b == b2 || b1 == b2) continue; // Éviter les répétitions

                        Variable varFixedB2 = variables.get("fixed_" + b2);

                        // Création des préconditions pour cette action
                        Map<Variable, Object> preCond = createPreconditions(varOnB, varFixedB, varFixedB1, varFixedB2, b, b1, b2, p);

                        // Création des effets pour cette action
                        Map<Variable, Object> effet = createEffects(varOnB, varFixedB, varFixedB1, varFixedB2, b, b1, b2, p);

                        // Définir le coût de l'action (par exemple, un coût fixe de 1)
                        int cout = 1;  // Vous pouvez ajuster ce coût selon vos critères

                        // Création de l'action avec le coût
                        Action deplacerBlocVersBloc = new BasicAction(preCond, effet, cout);
                        actions.add(deplacerBlocVersBloc);
                    }
                }
            }
        }



        // Action 4 : Déplacer un bloc b du dessous d’une pile p vers une pile vide p′
        for (int b = 0; b < nbreBlock; b++) {
            Variable varOnB = variables.get("on_" + b);
            Variable varFixedB = variables.get("fixed_" + b);

            for (int p = 0; p < nbrePile; p++) {
                Variable varFreeP = variables.get("free_" + p);

                // Vérifier que p est vide
                for (int p2 = 0; p2 < nbrePile; p2++) {
                    if (p == p2) continue; // On ne peut pas déplacer un bloc vers la même pile

                    Variable varFreeP2 = variables.get("free_" + p2);

                    // Création des préconditions pour cette action
                    Map<Variable, Object> preCond = createPreconditions(varOnB, varFixedB, varFreeP, varFreeP2, b, p, -1, p2);

                    // Création des effets pour cette action
                    Map<Variable, Object> effet = createEffects(varOnB, varFixedB, varFreeP, varFreeP2, b, p, -1, p2);

                    // Définir le coût de l'action (par exemple, un coût fixe de 1)
                    int cout = 1;  // Vous pouvez ajuster ce coût selon vos critères

                    // Création de l'action avec le coût
                    Action deplacerBlocVersPile = new BasicAction(preCond, effet, cout);
                    actions.add(deplacerBlocVersPile);
                }
            }
        }



    }

    // Utilisation de l'heuristique pour estimer le coût
    public float getEstimatedCost(Map<Variable, Object> etat) {
        return heuristic.estimate(etat);
    }

    
    // Méthode pour récupérer toutes les actions
    public Set<Action> getActions() {
        return actions;
    }
}
