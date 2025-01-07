package cp;
import java.util.*;
import modelling.Variable;

public class RandomValueHeuristic implements ValueHeuristic{
    protected Random gAleatoire ;
    public RandomValueHeuristic( Random gAleatoire ){
        this.gAleatoire = gAleatoire;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domaine) {
        List<Object> liste = new LinkedList<>(domaine);
        Collections.shuffle(liste, this.gAleatoire);
        return liste;
    }
    
}
