package blocksworld;

import modelling.*;
import java.util.*;

public class ConfigRegulier {

    protected int nbreBlock;  // Nombre de blocs
    protected BlocksWorld mondeBloc;  // Référence à l'objet BlocksWorld
    protected Set<Constraint> contraintes;  // Liste des contraintes à appliquer
    protected Map<Variable, Object> listeEcarts;  // Map des écarts des piles

    // Constructeur qui prend uniquement un objet BlocksWorld en paramètre
    public ConfigRegulier(BlocksWorld mondeBloc) {
        this.mondeBloc = mondeBloc;
        this.nbreBlock = mondeBloc.nbreBlock;
        this.contraintes = new HashSet<>();
        this.listeEcarts = new HashMap<>();  // Initialisation de la map vide
        initialiserEcarts();
        contraintesRegulieres();
    }

    // Méthode pour initialiser les écarts de manière interne
    public void initialiserEcarts() {
        // Exemple d'initialisation des écarts pour chaque pile
        for (int p = -mondeBloc.nbrePile; p < 0; p++) {
            Variable pile = new Variable("pile_" + p, Set.of(0, 1, 2, 3, 4));  // Domaine arbitraire
            Integer ecart = 3;  // Exemple : écart de 2 blocs entre les piles
            listeEcarts.put(pile, ecart);
        }
    }

    // Création des contraintes régulières pour chaque pile avec l'écart spécifié
    public void contraintesRegulieres() {
        // Parcours de chaque entrée dans listeEcarts pour obtenir la variable (pile ou bloc) et son écart
        for (Map.Entry<Variable, Object> entry : listeEcarts.entrySet()) {
            Variable variable = entry.getKey();
            Integer ecart = (Integer) entry.getValue();  // L'écart est un entier

            // Récupérer l'indice de la pile à partir de la variable (nom de la pile)
        String nomPile = variable.getName();
        int indicePile = Integer.parseInt(nomPile.split("_")[1]);  // Extraire l'indice de la pile à partir du nom

        // Obtenir les blocs associés à l'indice de la pile (en passant l'indice de la pile à la méthode)
        List<Variable> blocsPile = mondeBloc.getBlockVariablesInPile(indicePile);

            // Appliquer l'écart entre chaque bloc consécutif dans la pile
            for (int i = 0; i < blocsPile.size() - 1; i++) {
                Variable blocActuel = blocsPile.get(i);
                Variable blocSuivant = blocsPile.get(i + 1);

                // Définir le domaine du bloc suivant basé sur l'écart
                Set<Object> domaineBlocSuivant = Set.of(getBlocPosition(blocActuel) + ecart);

                // Créer une contrainte d'implication entre les deux blocs
                Constraint ecartContraintes = new Implication(blocActuel, blocActuel.getDomain(), blocSuivant, domaineBlocSuivant);
                contraintes.add(ecartContraintes);
            }
        }
    }

    // Méthode pour obtenir la position numérique d'un bloc
    private int getBlocPosition(Variable bloc) {
        Object position = bloc.getValue();  // Récupérer la valeur actuelle du bloc
        if (position instanceof Integer) {
            return (Integer) position;  // Retourner la position sous forme d'entier
        }
        throw new IllegalStateException("La position du bloc doit être un entier.");
    }

    // Méthode pour récupérer toutes les contraintes
    public Set<Constraint> getContraintes() {
        return this.contraintes;
    }

    // Validation des contraintes pour une instance spécifique
    public boolean validateConstraints(Map<Variable, Object> instance) {
        for (Constraint constraint : contraintes) {
            if (!constraint.isSatisfiedBy(instance)) {
                return false;  // Si une contrainte n'est pas satisfaite, la configuration est invalide
            }
        }
        return true;  // Si toutes les contraintes sont satisfaites, la configuration est valide
    }
}
