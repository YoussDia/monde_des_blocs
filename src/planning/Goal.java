package planning;
import java.util.*;
import modelling.*;


public interface Goal{
    //Méthode pour verifier si l'instance verifier le but 
    boolean isSatisfiedBy(Map<Variable, Object> monObj);
}