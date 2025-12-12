# Instructions pour le TP3

## Mise en contexte

Le système **USpace** est une application de réservation de croisières spatiales.

Quelques fonctionnalités sont déjà présente dans l'application qui vous est fournie :
1. Réserver une croisière
2. Consulter les détails d'une réservation
3. Consulter les détails d'une croisière
4. **Toutes les fonctionnalités du TP1** (disponibles dans le fichier `README.md` du TP1)
5. **Toutes les fonctionnalités du TP2** (disponibles dans le fichier `README.md` du TP2)

Vous pouvez consulter les détails des fonctionnalités originales et les instructions d'exécution dans le fichier `README.md` à la racine du projet.


## Critères

1. Vous devez appliquer le Clean Code et les concepts vus en cours jusqu'à maintenant :
* Conception orientée objet
* Polymorphisme
* Inversion des dépendances
* Abstraction
* Principes SOLID
* **Pyramide des tests**
* Etc!

2. Nous vous recommandons fortement d'utiliser le TDD pour les exercices.
3. Assurez-vous que l'application est toujours fonctionnel après vos modifications. Cela inclut toutes les fonctionnalités du TP1, du TP2 et celles de la base du projet.
   Le code doit compiler et s'exécuter selon les instructions du `README` à la racine du projet.
   **Une note de 0 sera donnée si l'application ne compile pas selon les instructions fournies.**
4. Assurez-vous que tous vos tests s'exécutent et passent avec `mvn test`.
   **Une note de 0 sera donnée si les tests ne s'exécutent pas ou ne passent pas.**
5. Assurez-vous de tester unitairement tous les comportements que vous ajoutez ou modifiez.

## Exercices

Pour chacun des exercices, vous devez modifier directement le code.
Vous pouvez effectuer les exercices dans n'importe quel ordre, mais il est recommandé de suivre l'ordre proposé:

1. [Scénario d'essais: Supprimer un compte utilisateur](exercices/exercice-delete-account.md) (Difficulté: 4/5)
2. [Scénario d'essais: Réserver une activité sportive](exercices/exercice-book-sports.md) (Difficulté: 5/5)
3. [Scénario d'essais: Créer un compte utilisateur](exercices/exercice-create-account.md) (Difficulté: 3/5)
4. [Test composant: Accès aux données avec H2](exercices/exercice-test-composant-h2.md) (Difficulté: 2/5)
5. [Test Cucumber: Réservation d'une expérience zéro gravité](exercices/exercice-cucumber-zero-g.md) (Difficulté: 2/5)
6. [Exécuter les différentes sortes de tests séparément](exercices/exercice-execution.md) (Difficulté: 1/5)

## Questions

Il vous est demandé de répondre à quelques questions en lien avec les exercices.

Répondez aux questions dans les espaces prévus dans chacun des fichiers.

1. [Scénario d'essais: Supprimer un compte utilisateur](questions/question-delete-account.md)
2. [Scénario d'essais: Réserver une activité sportive](questions/question-book-sports.md)
3. [Scénario d'essais: Créer un compte utilisateur](questions/question-create-account.md)
4. [Test composant avec H2](questions/question-test-composant-h2.md)
5. [Exécution des différents tests](questions/question-execution.md)
