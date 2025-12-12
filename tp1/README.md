# Instructions pour le TP1

## Mise en contexte

Le système **USpace** est une application de réservation de croisières spatiales.
Votre équipe est mandatée pour développer de nouvelles fonctionnalités dans l'application.

Quelques fonctionnalités sont déjà présente dans l'application qui vous est fournie :
1. Réserver une croisière
2. Consulter les détails d'une réservation
3. Consulter les détails d'une croisière

Vous pouvez consulter les détails des fonctionnalités présentes et les instructions d'exécution dans le fichier `README.md` à la racine du projet.

## Critères

1. Vous devez appliquer le Clean Code et les concepts vus en cours jusqu'à maintenant :
* Conception orientée objet
* Polymorphisme
* Inversion des dépendances
* Abstraction
* Etc!

2. Nous vous recommandons fortement d'utiliser le TDD pour les exercices.
3. Assurez-vous que l'application est toujours fonctionnel après vos modifications. 
Le code doit compiler et s'exécuter selon les instructions du `README` à la racine du projet.
**Une note de 0 sera donnée si l'application ne compile pas selon les instructions fournies.**
4. Assurez-vous que tous vos tests s'exécutent et passent avec `mvn test`.
**Une note de 0 sera donnée si les tests ne s'exécutent pas ou ne passent pas.**
5. Assurez-vous de tester unitairement tous les comportements que vous ajoutez ou modifiez.

## Exercices

Pour chacun des exercices, vous devez modifier directement le code. 
Vous pouvez effectuer les exercices dans n'importe quel ordre, mais il est recommandé de suivre l'ordre proposé:

1. [Ajouter une nouvelle planète à l'itinéraire](exercices/exercice-add-planet.md) (Difficulté: 5/5)
2. [Prendre en compte la catégorie de voyageur](exercices/exercice-traveler-category.md) (Difficulté: 4/5)
3. [Réservation d'une expérience zéro gravité](exercices/exercice-zero-gravity-experience.md) (Difficulté: 3/5)
4. [Implémenter les tests unitaires de PlanetValidatorVowelSystem](exercices/exercice-tests-planet-validator.md) (Difficulté: 1/3)