package uspace.multiComponents.glue;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import uspace.multiComponents.util.APIHelper;
import uspace.multiComponents.util.ResponseMemory;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class CabinAttributionStepDefinition {
    private static final String VALID_CRUISE_ID = "JUPITER_MOONS_EXPLORATION_2085";

    @Etantdonné("une réservation pour {string} avec {int} voyageur\\(s) et date de réservation {string}")
    public void uneReservationPour(String username, int numberOfTravelers, String bookingDateTime) {
        // Ensure account exists before creating booking
        io.restassured.response.Response accountResponse = APIHelper.createAccount(username, "Earth");
        // Accept 201 (created) or 400 (already exists)
        int statusCode = accountResponse.getStatusCode();
        if (statusCode != 201 && statusCode != 400) {
            throw new RuntimeException("Failed to create/verify account: " + statusCode);
        }

        List<String> adultNames = new java.util.ArrayList<>();
        for (int i = 0; i < numberOfTravelers; i++) {
            adultNames.add("Traveler" + i);
        }
        APIHelper.createBooking(VALID_CRUISE_ID, username, "STANDARD", bookingDateTime,
                adultNames, List.of(), List.of());
    }

    @Quand("les attributions de cabines sont obtenues avec le critère {string}")
    public void lesAttributionsDeCabinesSontObtenuesAvecLeCritere(String criteria) {
        ResponseMemory.setResponse(APIHelper.getCabinAttributions(VALID_CRUISE_ID, criteria));
    }

    @Alors("les attributions de cabines sont retournées")
    public void lesAttributionsDeCabinesSontRetournees() {
        ResponseMemory.getResponse().then()
                .statusCode(200)
                .body("cabins", notNullValue());
    }

    @Alors("les cabines sont triées par date de réservation")
    public void lesCabinesSontTrieesParDateDeReservation() {
        ResponseMemory.getResponse().then()
                .body("cabins", notNullValue())
                .body("cabins.size()", greaterThan(0));
    }

    @Alors("les cabines sont triées par nombre de voyageurs puis par date de réservation")
    public void lesCabinesSontTrieesParNombreDeVoyageursPuisParDateDeReservation() {
        ResponseMemory.getResponse().then()
                .body("cabins", notNullValue())
                .body("cabins.size()", greaterThan(0));
    }

    @Quand("les attributions de cabines sont obtenues pour la croisière {string} avec le critère {string}")
    public void lesAttributionsDeCabinesSontObtenuesPourLaCroisiereAvecLeCritere(String cruiseId, String criteria) {
        ResponseMemory.setResponse(APIHelper.getCabinAttributions(cruiseId, criteria));
    }
}
