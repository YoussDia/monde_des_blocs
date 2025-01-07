package modelling;
import java.util.*;

public class BooleanVariable extends Variable{
    public BooleanVariable(String nom){
        super(nom,initDomaine());
        
    }
    private static Set<Object> initDomaine(){
        Set<Object> monSet = new HashSet<>();
        monSet.add(true);
        monSet.add(false);

        return monSet;
        
    }
    @Override
    public String toString() {
        return nom;
    }
}