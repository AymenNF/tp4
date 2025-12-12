# Scénarios de tests pour obtenir le manifeste de navettes d'urgence

### 1. Élaborer la stratégie d'essais pour la fonctionnalité d'obtention du manifeste de navettes d'urgence

Pour faciliter la correction, suivez le gabarit ci-dessous.

- Expliquer pourquoi chaque test est dans sa catégorie.
- Expliquer pourquoi une catégorie est vide si c'est le cas.

**TESTS FONCTIONS:**

- **Test de EmergencyShuttleAssigner.assignShuttles() - assignation prioritaire aux RESCUE_SHIP**: Test unitaire vérifiant que les voyageurs et membres d'équipage sont assignés en priorité aux vaisseaux de sauvetage avant les navettes standards. C'est un test fonction car il teste la logique métier pure d'assignation sans dépendances externes.

- **Test de EmergencyShuttleAssigner.assignShuttles() - remplir dernière navette avant nouvelle**: Test unitaire vérifiant que pour un même type de navette, la dernière navette est remplie avant d'en ouvrir une nouvelle. C'est un test fonction car il teste la logique métier pure de gestion des navettes.

- **Test de EmergencyShuttleAssigner.assignShuttles() - respect de la limite de RESCUE_SHIP**: Test unitaire vérifiant que le nombre maximum de vaisseaux de sauvetage est respecté (5 pour JUPITER_MOONS_EXPLORATION_2085). C'est un test fonction car il teste la logique de validation des limites.

- **Test de EmergencyShuttleAssigner.assignShuttles() - croisière vide**: Test unitaire vérifiant qu'une croisière sans voyageurs ni membres d'équipage retourne un manifeste vide. C'est un test fonction car il teste le comportement avec des données vides.

- **Test de EmergencyShuttleManifest.calculateTotalCost()**: Test unitaire vérifiant que le coût total est correctement calculé comme la somme des coûts des navettes utilisées. C'est un test fonction car il teste la logique de calcul pure.

- **Test de EmergencyShuttleManifest - exclusion des navettes vides**: Test unitaire vérifiant que les navettes vides ne sont pas incluses dans le manifeste. C'est un test fonction car il teste la logique de filtrage.

- **Test unitaire Vue.js EmergencyShuttlesView - affichage du manifeste**: Test unitaire vérifiant que le composant Vue affiche correctement le tableau de navettes quand des données sont présentes. C'est un test fonction car il teste le rendu d'un composant Vue isolé.

- **Test unitaire Vue.js EmergencyShuttlesView - bouton rouge pour manifeste vide**: Test unitaire vérifiant que le bouton "Get Manifest" devient rouge quand le manifeste obtenu est vide. C'est un test fonction car il teste la logique conditionnelle du rendu.

- **Test unitaire Vue.js EmergencyShuttlesView - affichage du coût total**: Test unitaire vérifiant que le composant Vue affiche correctement le coût total. C'est un test fonction car il teste l'affichage d'une valeur calculée.

**TESTS COMPOSANT:**

- **Test API - obtenir le manifeste avec croisière valide**: Test d'intégration de l'endpoint REST avec le service mocké. C'est un test composant car il teste l'intégration entre la ressource REST et le service applicatif.

- **Test API - erreur croisière non trouvée**: Test d'intégration vérifiant que l'erreur 404 est correctement retournée quand la croisière n'existe pas. C'est un test composant car il teste la gestion d'erreur au niveau de l'API.

- **Test API - manifeste vide**: Test d'intégration vérifiant que l'API retourne correctement un manifeste vide (liste vide, coût 0) quand il n'y a pas de navettes. C'est un test composant car il teste le comportement de l'API avec des données vides.

**TESTS MULTI-COMPOSANTS (Cucumber):**

- **Scénario: Obtenir le manifeste de navettes d'urgence avec voyageurs et membres d'équipage**: Test end-to-end vérifiant le flux complet depuis la création de réservations et l'ajout de membres d'équipage jusqu'à l'obtention du manifeste. C'est un test multi-composants car il teste l'intégration de plusieurs composants (API, Service, Repository, Domaine).

- **Scénario: Obtenir le manifeste avec priorité aux RESCUE_SHIP**: Test end-to-end vérifiant que les voyageurs sont assignés en priorité aux vaisseaux de sauvetage avant les navettes standards. C'est un test multi-composants car il teste l'intégration complète de la logique d'assignation.

- **Scénario: Obtenir le manifeste avec croisière inexistante**: Test end-to-end vérifiant la gestion d'erreur complète quand la croisière n'existe pas. C'est un test multi-composants car il teste l'intégration de la gestion d'erreur à travers tous les composants.

- **Scénario: Obtenir le manifeste avec navettes vides exclues**: Test end-to-end vérifiant que les navettes vides ne sont pas retournées dans le manifeste. C'est un test multi-composants car il teste l'intégration complète du filtrage.

**TESTS END-TO-END (Cypress):**

- **Test E2E - affichage du manifeste de navettes**: Test end-to-end vérifiant que l'interface utilisateur affiche correctement les navettes avec leurs voyageurs et membres d'équipage dans un tableau. C'est un test E2E car il teste l'intégration complète frontend-backend avec un navigateur réel.

- **Test E2E - affichage du coût total**: Test end-to-end vérifiant que l'interface utilisateur affiche correctement le coût total des navettes. C'est un test E2E car il teste l'affichage des données calculées dans l'interface.

- **Test E2E - bouton rouge pour manifeste vide**: Test end-to-end vérifiant que le bouton "Get Manifest" devient rouge quand le manifeste obtenu est vide. C'est un test E2E car il teste le comportement visuel de l'interface en réponse aux données.

- **Test E2E - masquage du tableau quand manifeste vide**: Test end-to-end vérifiant que le tableau n'est pas affiché quand le manifeste est vide. C'est un test E2E car il teste le comportement conditionnel de l'interface.
