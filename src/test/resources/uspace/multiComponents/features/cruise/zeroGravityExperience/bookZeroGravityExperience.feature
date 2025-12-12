#language: fr

Fonctionnalité: Réserver une expérience zéro gravité

    Scénario: Réservation inexistante
        Etant donné une réservation qui n'existe pas
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors l'erreur "TRAVELER_NOT_FOUND" est obtenue
        Et le code de statut 404 est obtenu

    Scénario: Voyageur inexistant
        Etant donné une réservation valide avec 1 adulte, 0 enfant et 0 senior
        Mais le voyageur n'a pas réservé
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors l'erreur "TRAVELER_NOT_FOUND" est obtenue
        Et le code de statut 404 est obtenu

    Scénario: Date de réservation après le départ de la croisière
        Etant donné une réservation valide avec 1 adulte, 0 enfant et 0 senior
        Et le voyageur est un "ADULT" de la réservation
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2085-04-08T12:30:00"
        Alors l'erreur "ZERO_GRAVITY_EXPERIENCE_BOOKING_TIME" est obtenue
        Et le code de statut 400 est obtenu

    Scénario: Voyageur a déjà réservé une expérience zéro gravité
        Etant donné une réservation valide avec 1 adulte, 0 enfant et 0 senior
        Et le voyageur est un "ADULT" de la réservation
        Et le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors l'erreur "ZERO_GRAVITY_EXPERIENCE_ALREADY_BOOKED" est obtenue
        Et le code de statut 400 est obtenu

    Scénario: Expérience zéro gravité complète
        Etant donné une expérience zéro gravité complète
        Et une réservation valide avec 1 adulte, 0 enfant et 0 senior
        Et le voyageur est un "ADULT" de la réservation
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors l'erreur "ZERO_GRAVITY_EXPERIENCE_FULL" est obtenue
        Et le code de statut 400 est obtenu

    Scénario: Adulte gagne un badge ZERO_G
        Etant donné une réservation valide avec 1 adulte, 0 enfant et 0 senior
        Et le voyageur est un "ADULT" de la réservation
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors le code de statut 200 est obtenu
        Et le voyageur a le badge "ZERO_G"

    Scénario: Senior gagne un badge ZERO_G et un badge STILL_GOT_IT
        Etant donné une réservation valide avec 0 adulte, 0 enfant et 1 senior
        Et le voyageur est un "SENIOR" de la réservation
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors le code de statut 200 est obtenu
        Et le voyageur a le badge "ZERO_G"
        Et le voyageur a le badge "STILL_GOT_IT"

    Scénario: Enfant réserve une expérience zéro gravité sans adulte ou senior de la même réservation
        Etant donné une réservation valide avec 0 adulte, 1 enfant et 0 senior
        Et le voyageur est un "CHILD" de la réservation
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors l'erreur "ZERO_GRAVITY_EXPERIENCE_CHILD_CRITERIA" est obtenue
        Et le code de statut 400 est obtenu

    Scénario: Enfant peut réserver une expérience zéro gravité avec un adulte de la même réservation et reçoit MINI_ZERO_G
        Etant donné une réservation valide avec 1 adulte, 1 enfant et 0 senior
        Et le voyageur est un "ADULT" de la réservation
        Et le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Et le voyageur est un "CHILD" de la réservation
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors le code de statut 200 est obtenu
        Et le voyageur a le badge "MINI_ZERO_G"

    Scénario: Enfant peut réserver une expérience zéro gravité avec un senior de la même réservation et reçoit MINI_ZERO_G
        Etant donné une réservation valide avec 0 adulte, 1 enfant et 1 senior
        Et le voyageur est un "SENIOR" de la réservation
        Et le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Et le voyageur est un "CHILD" de la réservation
        Quand le voyageur réserve une expérience zéro gravité en date et heure du "2084-04-08T12:30:00"
        Alors le code de statut 200 est obtenu
        Et le voyageur a le badge "MINI_ZERO_G"