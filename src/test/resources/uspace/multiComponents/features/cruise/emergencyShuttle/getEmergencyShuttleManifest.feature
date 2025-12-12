#language: fr

Fonctionnalité: Obtenir le manifeste de navettes d'urgence

    Scénario: Obtenir le manifeste de navettes d'urgence avec voyageurs et membres d'équipage
        Etantdonné un compte utilisateur avec le nom d'utilisateur "user1"
        Et une réservation pour "user1" avec 10 voyageur(s) et date de réservation "2084-01-01T10:00"
        Et un membre d'équipage avec l'ID "ABC123" et le nom "Crew Member 1" est ajouté à la croisière
        Quand le manifeste de navettes d'urgence est obtenu
        Alors le code de statut 200 est obtenu
        Et le manifeste de navettes d'urgence est retourné
        Et le coût total est calculé

    Scénario: Obtenir le manifeste avec priorité aux RESCUE_SHIP
        Etantdonné un compte utilisateur avec le nom d'utilisateur "user1"
        Et une réservation pour "user1" avec 60 voyageur(s) et date de réservation "2084-01-01T10:00"
        Quand le manifeste de navettes d'urgence est obtenu
        Alors le code de statut 200 est obtenu
        Et le manifeste de navettes d'urgence est retourné
        Et les voyageurs sont assignés en priorité aux RESCUE_SHIP

    Scénario: Obtenir le manifeste avec croisière inexistante
        Quand le manifeste de navettes d'urgence est obtenu pour la croisière "INVALID_CRUISE"
        Alors le code de statut 404 est obtenu
        Et l'erreur "CRUISE_NOT_FOUND" est obtenue

    Scénario: Obtenir le manifeste avec navettes vides exclues
        Etantdonné un compte utilisateur avec le nom d'utilisateur "user1"
        Et une réservation pour "user1" avec 50 voyageur(s) et date de réservation "2084-01-01T10:00"
        Quand le manifeste de navettes d'urgence est obtenu
        Alors le code de statut 200 est obtenu
        Et le manifeste de navettes d'urgence est retourné
        Et les navettes vides ne sont pas incluses dans le manifeste

