package planning;
import modelling.*;
import java.util.*;

//cette interface declare une unique methode permettant d'extimer le cout 
//d'un plan d'optimal depuis un etat de depart 
public interface Heuristic{
    float estimate(Map<Variable, Object> etat);
}