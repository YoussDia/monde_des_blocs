package planning;
import modelling.*;
import java.util.*;

public interface Planner {
    //Fonction qui parcours et renvoie un plan
    List<Action> plan();
    
    //Renvoie l'etat initial
    Map<Variable, Object> getInitialState();

    //Renvoie une liste d'action
    Set<Action> getActions();

    //Fonction qui retourne le but ou l'ensemble de buts
    Goal getGoal();
}