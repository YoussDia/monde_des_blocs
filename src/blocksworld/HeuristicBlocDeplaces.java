package blocksworld;

import planning.*;
import modelling.*;
import java.util.*;

public class HeuristicBlocDeplaces implements Heuristic {

    private int nbreBlock;
    private Map<String, Variable> variables;

    public HeuristicBlocDeplaces(int nbreBlock, Map<String, Variable> variables) {
        this.nbreBlock = nbreBlock;
        this.variables = variables;
    }

    @Override
    public float estimate(Map<Variable, Object> etat) {
        int blocsDeplaces = 0;

        for (int b = 0; b < nbreBlock; b++) {
            Variable varOnB = variables.get("on_" + b);
            if ((Integer) etat.get(varOnB) != b) { // L'état de varOnB n'est pas la position correcte
                blocsDeplaces++;
            }
        }

        return blocsDeplaces;
    }

}
