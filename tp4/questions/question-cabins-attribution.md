# Scénarios de tests pour l'attribution de cabines

### 1. Élaborer la stratégie d'essais pour la fonctionnalité d'attribution de cabines

Pour faciliter la correction, suivez le gabarit ci-dessous.

- Expliquer pourquoi chaque test est dans sa catégorie.
- Expliquer pourquoi une catégorie est vide si c'est le cas.

**TESTS FONCTIONS:**

- **Test de CabinAttributionCriteria.fromString() avec critère valide**: Test unitaire de la logique de conversion d'une chaîne en enum. C'est un test fonction car il teste une fonction pure sans dépendances externes.

- **Test de CabinAttributionCriteria.fromString() avec critère invalide**: Test unitaire de la gestion d'erreur lors d'une conversion invalide. C'est un test fonction car il teste la validation d'entrée d'une fonction pure.

- **Test de CabinAttribution.assignCabins() avec critère bookingDateTime**: Test unitaire de la logique d'attribution des cabines triées par date de réservation. C'est un test fonction car il teste la logique métier pure sans dépendances externes.

- **Test de CabinAttribution.assignCabins() avec critère travelers**: Test unitaire de la logique d'attribution des cabines triées par nombre de voyageurs puis date. C'est un test fonction car il teste la logique métier pure.

- **Test de CabinAttribution.assignCabins() avec différents types de cabines**: Test unitaire vérifiant que les cabines sont correctement attribuées selon leur catégorie (STANDARD, DELUXE, SUITE). C'est un test fonction car il teste la logique de filtrage par type.

- **Test de CabinJsonReader.readCabins()**: Test unitaire de la lecture et parsing du fichier JSON des cabines. C'est un test fonction car il teste une fonction de lecture de fichier isolée.

- **Test unitaire Vue.js CabinsView - affichage du tableau**: Test unitaire vérifiant que le composant Vue affiche correctement le tableau de cabines quand des données sont présentes. C'est un test fonction car il teste le rendu d'un composant Vue isolé.

- **Test unitaire Vue.js CabinsView - masquage du tableau vide**: Test unitaire vérifiant que le composant Vue ne affiche pas le tableau quand il n'y a pas de cabines. C'est un test fonction car il teste la logique conditionnelle du rendu.

- **Test unitaire Vue.js CabinsView - sélection du critère**: Test unitaire vérifiant que le composant Vue permet de sélectionner le critère d'attribution. C'est un test fonction car il teste l'interaction avec les props/state du composant.

**TESTS COMPOSANT:**

- **Test API - obtenir les attributions avec critère bookingDateTime**: Test d'intégration de l'endpoint REST avec le service mocké. C'est un test composant car il teste l'intégration entre la ressource REST et le service applicatif.

- **Test API - obtenir les attributions avec critère travelers**: Test d'intégration de l'endpoint REST avec le service mocké. C'est un test composant car il teste l'intégration entre la ressource REST et le service applicatif.

- **Test API - erreur croisière non trouvée**: Test d'intégration vérifiant que l'erreur 404 est correctement retournée quand la croisière n'existe pas. C'est un test composant car il teste la gestion d'erreur au niveau de l'API.

- **Test API - erreur critère invalide**: Test d'intégration vérifiant que l'erreur 400 est correctement retournée quand le critère est invalide. C'est un test composant car il teste la validation au niveau de l'API.

- **Test API - erreur critère manquant**: Test d'intégration vérifiant que l'erreur 400 est correctement retournée quand le critère est manquant. C'est un test composant car il teste la validation des paramètres de requête.

**TESTS MULTI-COMPOSANTS (Cucumber):**

- **Scénario: Obtenir les attributions de cabines par date de réservation**: Test end-to-end vérifiant le flux complet depuis la création de réservations jusqu'à l'obtention des attributions. C'est un test multi-composants car il teste l'intégration de plusieurs composants (API, Service, Repository, Domaine).

- **Scénario: Obtenir les attributions de cabines par nombre de voyageurs**: Test end-to-end vérifiant le flux complet avec le critère de tri par voyageurs. C'est un test multi-composants car il teste l'intégration de plusieurs composants.

- **Scénario: Obtenir les attributions avec croisière inexistante**: Test end-to-end vérifiant la gestion d'erreur complète. C'est un test multi-composants car il teste l'intégration de la gestion d'erreur à travers tous les composants.

- **Scénario: Obtenir les attributions avec critère invalide**: Test end-to-end vérifiant la validation complète. C'est un test multi-composants car il teste l'intégration de la validation à travers tous les composants.

**TESTS END-TO-END (Cypress):**

- **Test E2E - affichage des cabines attribuées**: Test end-to-end vérifiant que l'interface utilisateur affiche correctement les cabines attribuées dans un tableau. C'est un test E2E car il teste l'intégration complète frontend-backend avec un navigateur réel.

- **Test E2E - changement de critère d'attribution**: Test end-to-end vérifiant que l'utilisateur peut changer le critère et obtenir les résultats correspondants. C'est un test E2E car il teste l'interaction utilisateur complète.

- **Test E2E - masquage du tableau quand aucune cabine**: Test end-to-end vérifiant que le tableau n'est pas affiché quand il n'y a pas de cabines attribuées. C'est un test E2E car il teste le comportement visuel de l'interface.

- **Test E2E - ordre des cabines dans le tableau**: Test end-to-end vérifiant que les cabines sont affichées dans le bon ordre selon le critère sélectionné. C'est un test E2E car il teste la cohérence des données affichées.
