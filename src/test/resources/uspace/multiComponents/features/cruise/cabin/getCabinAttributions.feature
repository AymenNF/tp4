#language: fr

Fonctionnalité: Obtenir les attributions de cabines

    Scénario: Obtenir les attributions de cabines par date de réservation
        Etantdonné un compte utilisateur avec le nom d'utilisateur "user1"
        Et une réservation pour "user1" avec 1 voyageur(s) et date de réservation "2084-01-01T10:00"
        Et un compte utilisateur avec le nom d'utilisateur "user2"
        Et une réservation pour "user2" avec 1 voyageur(s) et date de réservation "2084-02-01T10:00"
        Quand les attributions de cabines sont obtenues avec le critère "bookingDateTime"
        Alors le code de statut 200 est obtenu
        Et les attributions de cabines sont retournées
        Et les cabines sont triées par date de réservation

    Scénario: Obtenir les attributions de cabines par nombre de voyageurs
        Etantdonné un compte utilisateur avec le nom d'utilisateur "user1"
        Et une réservation pour "user1" avec 1 voyageur(s) et date de réservation "2084-01-01T10:00"
        Et un compte utilisateur avec le nom d'utilisateur "user2"
        Et une réservation pour "user2" avec 2 voyageur(s) et date de réservation "2084-02-01T10:00"
        Et un compte utilisateur avec le nom d'utilisateur "user3"
        Et une réservation pour "user3" avec 3 voyageur(s) et date de réservation "2084-03-01T10:00"
        Quand les attributions de cabines sont obtenues avec le critère "travelers"
        Alors le code de statut 200 est obtenu
        Et les attributions de cabines sont retournées
        Et les cabines sont triées par nombre de voyageurs puis par date de réservation

    Scénario: Obtenir les attributions avec croisière inexistante
        Quand les attributions de cabines sont obtenues pour la croisière "INVALID_CRUISE" avec le critère "bookingDateTime"
        Alors le code de statut 404 est obtenu
        Et l'erreur "CRUISE_NOT_FOUND" est obtenue

    Scénario: Obtenir les attributions avec critère invalide
        Quand les attributions de cabines sont obtenues pour la croisière "JUPITER_MOONS_EXPLORATION_2085" avec le critère "invalid"
        Alors le code de statut 400 est obtenu
        Et l'erreur "INVALID_CABIN_ATTRIBUTION_CRITERIA" est obtenue

