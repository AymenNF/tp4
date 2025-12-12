# Instructions pour les scénarios d'essais

Dans le TP4, lorsqu'il vous est demandé de faire un scénario d'essais et de l'implémenter, suivez les instructions suivantes.

### 1. Écrivez le scénario d'essais dans le fichier de questions spécifié par l'exercice. Suivez le gabarit.

Vous devez spécifier les essais des différentes catégories:

- Les tests **Fonction**
  - Cela inclus les tests unitaires de l'interface Vue JS. (avec Vitest)
- Les tests **Composant**
- Les tests **Multi-composants** (avec Cucumber)
- Les tests **End-to-end** (avec Cypress)

### 2. Implémenter les essais que vous avez décrits. Voici quelques indications pour les nouvelles catégories de tests:

- Les tests unitaires de l'interface Vue JS (avec Vitest) doivent être implémentés dans le dossier `webapp/src/components/__test__`.
    Pour rouler les tests unitaires de l'interface Vue JS, exécuter la commande suivante dans le dossier `webapp`:
    
    ```bash
    npm run test:unit
    ```

- Les tests **End-to-end** (avec Cypress) doivent être implémentés dans le dossier `webapp/cypress/e2e`.
  Pour rouler les tests bout en bout avec Cypress, exécuter la commande suivante à la racine du projet:

    Avec Windows:
    ```bash
    ./run-e2e.sh
    ```
    Ou avec Linux:
    ```bash
    source run-e2e.sh
    ```

### 3. Utilisation de l'interface utilisateur

Les instructions pour démarrer le serveur backend et le serveur frontend sont dans le fichier README.md à la racine du projet.
L'interface utilisateur fait appel à l'API du serveur backend, donc assurez-vous que le serveur backend est démarré avant d'utiliser l'interface utilisateur.

**P.S. Le script `run-e2e.sh` démarre le serveur backend et le serveur frontend avant d'exécuter les tests Cypress.**