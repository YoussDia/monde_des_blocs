package modelling ;

import java.util.*;


public class Variable{
    public String nom;
    public Set<Object> domaine;
    private Object value;

    //Classe Varaible possédant un nom associer et un domaine
    public Variable(String nom,Set<Object> domaine){
        this.nom = nom;
        this.domaine = domaine;
        this.value  = null;
    }
    public String getName(){
        return nom;
    }
    public Set<Object> getDomain(){
        return domaine;
    }
    @Override
    public boolean equals(Object obj){
        //Verification pour savoir si l'objet est de type Variable
        if (!(obj instanceof Variable)) {
            return false;
        }
        //
        Variable var = (Variable)obj;
        return this.nom.equals(var.getName());
    }
    
    @Override
    public int hashCode(){
        return this.nom.hashCode();
    }

    public Object getValue() {
        return this.value;
    }

    // Définir une nouvelle valeur pour la variable
    public void setValue(Object value) {
        if (domaine.contains(value)) {  // Vérifie que la valeur est dans le domaine
            this.value = value;
        } else {
            throw new IllegalArgumentException("La valeur n'est pas dans le domaine de la variable.");
        }
    }

    @Override
    public String toString() {
        return "{name='" + this.nom + "', domain=" + this.domaine + ", value=" + this.value + "}\n";
    }

}
