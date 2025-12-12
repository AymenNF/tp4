# Instructions pour le TP2

## Mise en contexte

Le système **USpace** est une application de réservation de croisières spatiales.

Quelques fonctionnalités sont déjà présente dans l'application qui vous est fournie :
1. Réserver une croisière
2. Consulter les détails d'une réservation
3. Consulter les détails d'une croisière
4. **Toutes les fonctionnalités du TP1**

Vous pouvez consulter les détails des fonctionnalités originales et les instructions d'exécution dans le fichier `README.md` à la racine du projet.
Les détails des fonctionnalités du TP1 sont disponibles dans le fichier `README.md` du TP1.


## Critères

1. Vous devez appliquer le Clean Code et les concepts vus en cours jusqu'à maintenant :
* Conception orientée objet
* Polymorphisme
* Inversion des dépendances
* Abstraction
* **Principes SOLID**
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
Vous pouvez effectuer les exercices dans n'importe quel ordre, mais nous vous recommandons de suivre l'ordre proposé:

1. [RÉUSINAGE: Ajouter un membre d'équipage](exercices/exercice-refactor-add-crew.md) (Difficulté: 2/3)
2. [NOUVELLE FONCTIONNALITÉ: Ajouter une excursion sur une planète](exercices/exercice-feature-excursion.md) (Difficulté: 2/3)
3. [NOUVELLE FONCTIONNALITÉ: Voir le coût de la réservation](exercices/exercice-feature-cost.md) (Difficulté: 3/3)
4. [Mettre en place Hibernate avec une base de données H2](exercices/exercice-hibernate-h2.md) (Difficulté: 1/3)
5. [NOUVELLE FONCTIONNALITÉ: Ajouter un compte utilisateur](exercices/exercice-feature-account.md) (Difficulté: 1/3)

## Questions à répondre

Il vous est demandé de répondre à quelques questions en lien avec les exercices.

Vos réponses serviront à la correction de votre TP. Soyez clair et donnez des réponses complètes.

Répondez aux questions dans les espaces prévus dans chacun des fichiers.

* [Questions sur le réusinage: Ajouter un membre d'équipage](questions/questions-refactor-add-crew.md)
* [Questions sur la nouvelle fonctionnalité: Ajouter une excursion sur une planète](questions/questions-feature-excursion.md)
* [Questions sur la nouvelle fonctionnalité: Voir le coût de la réservation](questions/questions-feature-cost.md)
* [Questions sur la mise en place de Hibernate et H2](questions/questions-hibernate-h2.md)
* [Questions sur la nouvelle fonctionnalité: Ajouter un compte utilisateur](questions/questions-feature-account.md)