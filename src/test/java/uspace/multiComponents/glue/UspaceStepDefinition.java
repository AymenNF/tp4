package uspace.multiComponents.glue;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.restassured.response.Response;
import uspace.UspaceMain;
import uspace.multiComponents.util.APIHelper;
import uspace.multiComponents.util.ResponseMemory;

import java.util.Collections;
import java.util.List;

public class UspaceStepDefinition {
    private static final String CRUISE_ID = UspaceMain.DEFAULT_CRUISE_ID;
    private static final int ZERO_GRAVITY_EXPERIENCE_CAPACITY = UspaceMain.DEFAULT_ZERO_GRAVITY_EXPERIENCE_CAPACITY;
    private static final String VALID_ACCOUNT_USERNAME = "validAccountUsername";
    private static final String VALID_HOME_PLANET_NAME = "Terre";
    private static final String VALID_CABIN_TYPE = "DELUXE";
    private static final String VALID_BOOKING_DATE_TIME = "2084-01-25T12:00";
    private static final String VALID_ADULT_NAME = "John Doe";
    private static final String VALID_CHILD_NAME = "Baby george";
    private static final String VALID_SENIOR_NAME = "Old granny Jane";
    private String bookingId;
    private String travelerId;

    @Etantdonné("une réservation qui n'existe pas")
    public void uneReservationQuiNExistePas() {
        bookingId = "bookingIdNonExistent";
        travelerId = "travellerIdNonExistent";
    }

    @Etantdonné("une réservation valide avec {int} adulte, {int} enfant et {int} senior")
    public void uneReservationValide(int adultCount, int childCount, int seniorCount) {
        List<String> adultNames = Collections.nCopies(adultCount, VALID_ADULT_NAME);
        List<String> childNames = Collections.nCopies(childCount, VALID_CHILD_NAME);
        List<String> seniorNames = Collections.nCopies(seniorCount, VALID_SENIOR_NAME);

        bookingId = createValidBooking(adultNames, childNames, seniorNames);
    }

    @Etantdonné("le voyageur n'a pas réservé")
    public void leVoyageurNAPasReserve() {
        travelerId = "travellerIdNonExistent";
    }

    @Etantdonné("le voyageur est un {string} de la réservation")
    public void leVoyageurEstUnVoyageurDeLaReservation(String travelerCategory) {
        Response bookingResponse = APIHelper.getBooking(CRUISE_ID, bookingId);
        travelerId = bookingResponse.body().jsonPath().getString("travelers.find { it.category == '" + travelerCategory + "' }.id");
    }

    @Etantdonné("une expérience zéro gravité complète")
    public void uneExperienceZeroGraviteComplete() {
        List<String> adultNames = Collections.nCopies(ZERO_GRAVITY_EXPERIENCE_CAPACITY, VALID_ADULT_NAME);
        String bookingId = createValidBooking(adultNames, List.of(), List.of());
        Response bookingResponse = APIHelper.getBooking(CRUISE_ID, bookingId);

        for (int i = 0; i < ZERO_GRAVITY_EXPERIENCE_CAPACITY; i++) {
            String travellerId = bookingResponse.body().jsonPath().getString("travelers[" + i + "].id");
            APIHelper.bookZeroGravityExperience(CRUISE_ID, bookingId, travellerId, VALID_BOOKING_DATE_TIME);
        }
    }

    @Quand("le voyageur réserve une expérience zéro gravité en date et heure du {string}")
    public void leVoyageurReserveUneExperienceZeroGravite(String dateTime) {
        ResponseMemory.setResponse(APIHelper.bookZeroGravityExperience(CRUISE_ID, bookingId, travelerId, dateTime));
    }

    @Quand("le voyageur réserve le sport {string}")
    public void leVoyageurReserveLeSport(String sport) {
        ResponseMemory.setResponse(APIHelper.bookSport(CRUISE_ID, bookingId, travelerId, sport));
    }

    @Alors("le voyageur a le badge {string}")
    public void leVoyageurALeBadge(String badge) {
        Response bookingResponse = APIHelper.getBooking(CRUISE_ID, bookingId);
        bookingResponse.then().body("travelers.find { it.id == '" + travelerId + "' }.badges", org.hamcrest.Matchers.hasItem(badge));
    }

    private String createValidBooking(List<String> adultNames, List<String> childNames, List<String> seniorNames) {
        APIHelper.createAccount(VALID_ACCOUNT_USERNAME, VALID_HOME_PLANET_NAME);
        Response bookingResponse = APIHelper.createBooking(CRUISE_ID, VALID_ACCOUNT_USERNAME, VALID_CABIN_TYPE, VALID_BOOKING_DATE_TIME,
                                                           adultNames, childNames, seniorNames);
        return bookingResponse.getHeader("Location").split("bookings/")[1];
    }
}
