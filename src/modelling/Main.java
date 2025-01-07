package modelling;
import java.util.*;

public class Main{
    public static void main(String[] args) {
        
        //Creation d'un 1er domaine 
        Set<Object> domaine1 = new HashSet<>();
        domaine1.add(1);
        domaine1.add(2);

        //Creation d'un 2e domaine 
        Set<Object> domaine2 = new HashSet<>();
        domaine2.add(1);

        //Variables 
        Variable v1 = new Variable("a", domaine1);
        Variable v2 = new Variable("b", domaine2);

        // Afficher les domaines associés à chaque variable
        System.out.println("Domaine de v1 (" + v1.getName() +") : " + v1.getDomain());
        System.out.println("Domaine de v2 (" + v2.getName() +") : " + v2.getDomain());
        
        //Variable booleen
        // BooleanVariable v4 = new BooleanVariable("c");

        System.out.println(v1.equals(v2));
        System.out.println(v1.hashCode() == v2.hashCode());
        
        //Définition de l'instance 
        Map<Variable, Object> instance = new HashMap<>();
        Set<Object> domaineInst = new HashSet<>();
        domaineInst.add(1);
        Variable v3 = new Variable("c", domaineInst);
        instance.put(v3,domaineInst);
        instance.put(v1,domaine1);
        instance.put(v2,domaine2);

        //Verification des contraintes
        DifferenceConstraint cons1 = new DifferenceConstraint(v1,v2);
        System.out.println(cons1.getScope());
        System.out.println(cons1.isSatisfiedBy(instance));


        //Test des implications
        Map<Variable, Object> instance1 = new HashMap<>();
        Set<Object> domaineInst1 = new HashSet<>();
        domaineInst.add(1);
        instance1.put(v3,domaineInst1);
        // Implication implication = new Implication(v1,domaine1,v2,domaine2);
        // System.out.println(cons1.isSatisfiedBy(instance1));
    }
}