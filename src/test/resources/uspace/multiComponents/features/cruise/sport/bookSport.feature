#language: fr

Fonctionnalité: Réserver une activité sportive

    Scénario: Le voyageur ne peut pas réserver à nouveau une activité sportive déjà réservée
        Etant donné une réservation valide avec 1 adulte, 0 enfant et 0 senior
        Et le voyageur est un "ADULT" de la réservation
        Quand le voyageur réserve le sport "Lunar Rock Climbing"
        Et le voyageur réserve le sport "Lunar Rock Climbing"
        Alors le code de statut 400 est obtenu

    Scénario: Le voyageur adulte gagne un bage SPORTY en réservant une activité sportive
        Etant donné une réservation valide avec 1 adulte, 0 enfant et 0 senior
        Et le voyageur est un "ADULT" de la réservation
        Quand le voyageur réserve le sport "Lunar Rock Climbing"
        Alors le code de statut 200 est obtenu
        Et le voyageur a le badge "SPORTY"