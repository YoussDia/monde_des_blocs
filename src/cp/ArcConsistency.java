package cp;
import modelling.*;
import java.util.*;

public class ArcConsistency {
    protected Set<Constraint> ensembleContainte; 
    public ArcConsistency(Set<Constraint> mesConstraintes){
        
        for(Constraint c :mesConstraintes){
            int taille  = c.getScope().size();
            if (taille > 2 && taille < 1) {
                throw new IllegalArgumentException("Ni unaire ni binaire");
            }
        }
        this.ensembleContainte = mesConstraintes; 
    }

    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> ensembleDomaine){
        boolean r = true;
        // Map temporaire pour garder les assignations courantes
        Map<Variable, Object> tmp = new HashMap<>();
        for (Variable x : ensembleDomaine.keySet()) {
            // Stocker les valeurs qui ne satisfont pas les contraintes
            Set<Object> aRetirer = new HashSet<>();
            for (Object v : ensembleDomaine.get(x)) {
                tmp.put(x, v);

                for (Constraint c : this.ensembleContainte) {
                    // Si la contrainte unaire n'est pas satisfaite, marquer la valeur pour suppression
                    if (c.getScope().size()==1 && c.getScope().contains(x) && !c.isSatisfiedBy(tmp)) {
                        aRetirer.add(v);
                        
                    }
                    
                }
            }
            // Supprimer les valeurs marquées du domaine de x
            ensembleDomaine.get(x).removeAll(aRetirer);

            // Vérifier si un domaine devient vide après la suppression
            if (ensembleDomaine.get(x).isEmpty()) {
                r = false;
            }
        }

        // Retourner vrai si tous les domaines sont non vides
        return r;
    }


    
    public boolean revise(Variable v1,Set<Object>D1,Variable v2,Set<Object>D2){
        boolean del = false;
        Set<Object> aRetirer = new HashSet<>();
        
        for (Object  vi: D1) {
            boolean viable = false;
            for (Object vj : D2) {
                boolean toutSatisfait = true;
                for (Constraint c : this.ensembleContainte) {
                    if (c.getScope().size()==2 && c.getScope().contains(v1) && c.getScope().contains(v2)) {
                        
                        Map<Variable,Object> map = new HashMap<>();
                        map.put(v1, vi);
                        map.put(v2,vj);
                        if (!c.isSatisfiedBy(map)) {
                            toutSatisfait = false;
                            break;
                        }
                    }
                }
                if (toutSatisfait) {
                    viable= true;
                    break;
                }
            }
            if (!viable) {
                aRetirer.add(vi);
                del=true;
            }
        }
        D1.removeAll(aRetirer);
        return del;

    }

    public boolean ac1 (Map<Variable, Set<Object>> ensembleDomaine){
        if(!enforceNodeConsistency(ensembleDomaine)){
            return false;

        }
        boolean change = true;

        Set<Variable> mesVariables= ensembleDomaine.keySet();

        while (change) {
            change = false;
            for (Variable xi : mesVariables) {
                for (Variable xj : mesVariables) {
                    if (!(xi.equals(xj)) && revise(xi,ensembleDomaine.get(xi), xj,ensembleDomaine.get(xj))) {
                        change = true;
                    }
                }
                // Mettre à jour le domaine de x dans instance après les révisions
                ensembleDomaine.put(xi, ensembleDomaine.get(xi));
            }
        }
        for (Variable v : mesVariables) {
            if (ensembleDomaine.get(v).isEmpty()) {
                return false;
            }
        }
        return true;

    }

}
