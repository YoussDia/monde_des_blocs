package planning;
import java.util.*;
import modelling.*;

// cette classe permet de representer les buts basiques :
//on appelle but basique un but spécifié par une instanciation partielle des variables.

public class BasicGoal implements Goal{

    protected Map<Variable, Object> but;

    public BasicGoal(Map<Variable, Object> but){
        this.but=but;
    }

    //cette fonction renvoie un booleen si un but est satisfait ou non
    //Un tel but sera satisfait par un état s si s affecte toutes les variables de l’instanciation à la bonne valeur
    public boolean isSatisfiedBy(Map<Variable, Object> etat){

        // Parcourir la Map avec Map.Entry
        for (Map.Entry<Variable, Object> entry : but.entrySet()) {
            Variable key = entry.getKey();   // Utilisation de getKey() pour récuperer la variable
            Object val = entry.getValue();   // Utilisation de getValue() pour récupérer la valeur
            if (!etat.containsKey(key) || !etat.get(key).equals(val)) {
                return false;
            }
        }
        
        return true;
    }
}