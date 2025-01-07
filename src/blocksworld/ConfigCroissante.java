package blocksworld;

import modelling.*;
import java.util.*;

public class ConfigCroissante {

    protected int nbreBlock; // Nombre de blocs
    protected BlocksWorld mondeBloc; // Référence à l'objet BlocksWorld
    protected Set<Constraint> contraintes;

    // Constructeur qui prend un objet BlocksWorld en paramètre
    public ConfigCroissante(BlocksWorld mondeBloc) {
        this.mondeBloc = mondeBloc;
        this.nbreBlock = mondeBloc.nbreBlock;
        this.contraintes = new HashSet<>();
        contraintesCroissantes();
    }

    // Méthode pour créer des contraintes croissantes avec b piles
    public void contraintesCroissantes() {
        // Parcours des blocs
        for (int i = 0; i < nbreBlock; i++) {
            // Récupération du bloc actuel
            Variable blocActuel = mondeBloc.getVariableOnBloc(i);

            // Domaine valide pour blocActuel
            Set<Object> domaineValide = new HashSet<>();

            // Ajouter les piles comme options valides
            for (int p = -1; p >= -mondeBloc.nbrePile; p--) {
                domaineValide.add(p);
            }

            // Ajouter les blocs avec indices plus petits que i
            for (int j = 0; j < i; j++) {
                domaineValide.add(j);
            }

            // Créer la contrainte pour ce bloc
            Constraint implication = new Implication(
                    blocActuel, blocActuel.getDomain(), // Condition : blocActuel est positionné
                    blocActuel, domaineValide // Contrainte : blocActuel doit être posé dans domaineValide
            );

            contraintes.add(implication);
        }
    }

    // Méthode pour récupérer toutes les contraintes
    public Set<Constraint> getContraintes() {
        return this.contraintes;
    }

    // Validation des contraintes pour une instance spécifique
    public boolean validateConstraints(Map<Variable, Object> instance) {
        // Vérifier si toutes les contraintes sont satisfaites
        for (Constraint constraint : contraintes) {
            if (!constraint.isSatisfiedBy(instance)) {
                return false; // La configuration est invalide si une contrainte n'est pas satisfaite
            }
        }
        return true; // La configuration est valide si toutes les contraintes sont satisfaites
    }
}
