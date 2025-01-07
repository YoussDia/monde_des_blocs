package planning;
import modelling.*;
import java.util.*;

public interface Action{
    /**Fonction que vérifie si une action est applicable ou pas.
     * @param monObl est un etat
     * @return vrai si une acction est aplicable à un etat et faux sinon
    */
    boolean isApplicable(Map<Variable, Object> monObj);

    /**Fonction qui renvoie une liste du nouvel état après l'application de l'action
    *@param monObj est un etat 
    *@return le successeur de mon etat courant 
    */
    Map<Variable, Object> successor(Map<Variable, Object> monObj);

    //Fonction que renvoie le coût de l'action
    int getCost();
}