# Instructions pour le TP4

## Mise en contexte

Le système **USpace** est une application de réservation de croisières spatiales.

Quelques fonctionnalités sont déjà présente dans l'application qui vous est fournie :
1. Réserver une croisière
2. Consulter les détails d'une réservation
3. Consulter les détails d'une croisière
4. **Toutes les fonctionnalités du TP1** (disponibles dans le fichier `README.md` du TP1)
5. **Toutes les fonctionnalités du TP2** (disponibles dans le fichier `README.md` du TP2)
5. **Toutes les fonctionnalités du TP3** (disponibles dans le fichier `README.md` du TP3)

Vous pouvez consulter les détails des fonctionnalités originales et les instructions d'exécution dans le fichier `README.md` à la racine du projet.


## Critères

1. Vous devez appliquer le Clean Code et les concepts vus en cours :
* Conception orientée objet
* Polymorphisme
* Inversion des dépendances
* Abstraction
* Principes SOLID
* Pyramide des tests
* Etc!

2. Nous vous recommandons fortement d'utiliser le TDD pour les exercices.
3. Assurez-vous que l'application est toujours fonctionnel après vos modifications. Cela inclut toutes les fonctionnalités du TP1, du TP2 et celles de la base du projet.
   Le code doit compiler et s'exécuter selon les instructions du `README` à la racine du projet.
   **Une note de 0 sera donnée si l'application ne compile pas selon les instructions fournies.**
4. Assurez-vous que tous vos tests du backend s'exécutent et passent avec `mvn test`.
   **Une note de 0 sera donnée si les tests ne s'exécutent pas ou ne passent pas.**
5. Assurez-vous que tous vos tests unitaires de l'interface s'exécutent et passent avec `npm run test:unit`.
   **Une note de 0 sera donnée si les tests ne s'exécutent pas ou ne passent pas.**
6. Assurez-vous que tous vos tests Cypress s'exécutent et passent avec le script `run-e2e.sh`.
   **Une note de 0 sera donnée si les tests ne s'exécutent pas ou ne passent pas.**
7. Assurez-vous de tester unitairement tous les comportements que vous ajoutez ou modifiez.

## Interface utilisateur
Ce TP comprend une interface utilisateur qu'il vous faudra tester selon les critères d'acceptation de chaque exercice.

Les instructions pour démarrer le serveur backend et le serveur frontend sont dans le fichier `README.md` à la racine du projet.

L'interface utilisateur fait appel à l'API du serveur backend, donc assurez-vous que le serveur backend est démarré avant d'utiliser l'interface utilisateur.

**P.S. Le script `run-e2e.sh` démarre le serveur backend et le serveur frontend avant d'exécuter les tests Cypress.**

## Exercices
Ce travail comprend deux exercices récapitulatifs. 

Pour chacun des exercices, vous devez modifier directement le code.
Vous pouvez effectuer les exercices dans n'importe quel ordre, mais il est recommandé de suivre l'ordre proposé:

1. [Voir l'attribution des cabines](exercices/exercice-cabins-attributions.md)
2. [Obtenir le manifeste des navettes d'urgence](exercices/exercice-emergency-shuttle.md)

## Questions

Il vous est demandé de répondre à quelques questions en lien avec les exercices.

Répondez aux questions dans les espaces prévus dans chacun des fichiers.

1. [Questions pour l'exercice de l'attribution des cabines](questions/question-cabins-attribution.md)
2. [Questions pour l'exercice des navettes d'urgence](questions/question-emergency-shuttle.md)
