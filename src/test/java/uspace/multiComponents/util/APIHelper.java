package uspace.multiComponents.util;

import io.restassured.response.Response;
import uspace.UspaceMain;
import uspace.api.cruise.booking.traveler.zeroGravityExperienceRequest.ZeroGravityExperienceRequest;
import uspace.application.account.dtos.AccountDto;
import uspace.application.cruise.booking.dtos.newBooking.NewBookingDto;
import uspace.application.cruise.booking.dtos.newBooking.NewBookingTravelerDto;
import uspace.application.cruise.booking.traveler.dtos.SportBookingDto;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class APIHelper {
    public static Response bookZeroGravityExperience(String cruiseId, String bookingId, String travellerId, String dateTime) {
        ZeroGravityExperienceRequest zeroGravityExperienceRequest = new ZeroGravityExperienceRequest();
        zeroGravityExperienceRequest.experienceBookingDateTime = dateTime;

        String uri = "/cruises/" + cruiseId + "/bookings/" + bookingId + "/travelers/" + travellerId + "/zeroGravityExperiences";
        return makePostRequest(uri, zeroGravityExperienceRequest);
    }

    public static Response createBooking(String cruiseId, String validAccountUsername, String validCabinType, String validBookingDateTime,
                                         List<String> adultNames, List<String> childNames, List<String> seniorNames) {
        NewBookingDto newBookingDto = new NewBookingDto();
        newBookingDto.cabinType = validCabinType;
        newBookingDto.accountUsername = validAccountUsername;
        newBookingDto.bookingDateTime = validBookingDateTime;
        newBookingDto.travelers = new ArrayList<>();

        for (String adultName : adultNames) {
            newBookingDto.travelers.add(new NewBookingTravelerDto(adultName, "ADULT"));
        }

        for (String childName : childNames) {
            newBookingDto.travelers.add(new NewBookingTravelerDto(childName, "CHILD"));
        }

        for (String seniorName : seniorNames) {
            newBookingDto.travelers.add(new NewBookingTravelerDto(seniorName, "SENIOR"));
        }

        String uri = "/cruises/" + cruiseId + "/bookings";
        return makePostRequest(uri, newBookingDto);
    }

    public static Response createAccount(String accountUsername, String homePlanetName) {
        AccountDto accountDto = new AccountDto();
        accountDto.username = accountUsername;
        accountDto.homePlanetName = homePlanetName;

        String uri = "/accounts";
        return makePostRequest(uri, accountDto);
    }

    public static Response deleteAccount(String validAccountUsername) {
        String uri = "/accounts/" + validAccountUsername;
        return makeDeleteRequest(uri);
    }

    public static Response getBooking(String cruiseId, String bookingId) {
        String uri = "/cruises/" + cruiseId + "/bookings/" + bookingId;
        return makeGetRequest(uri);
    }

    public static Response bookSport(String cruiseId, String bookingId, String travelerId, String sport) {
        SportBookingDto sportDto = new SportBookingDto();
        sportDto.sport = sport;

        String uri = "/cruises/" + cruiseId + "/bookings/" + bookingId + "/travelers/" + travelerId + "/sports";
        return makePostRequest(uri, sportDto);
    }

    public static Response getAccount(String validAccountUsername) {
        String uri = "/accounts/" + validAccountUsername;
        return makeGetRequest(uri);
    }

    public static Response getCabinAttributions(String cruiseId, String criteria) {
        String uri = "/cruises/" + cruiseId + "/cabins?criteria=" + criteria;
        return makeGetRequest(uri);
    }

    public static Response addCrewMember(String cruiseId, String employeeId, String name) {
        uspace.application.cruise.crew.dtos.CrewMemberDto crewMemberDto = new uspace.application.cruise.crew.dtos.CrewMemberDto();
        crewMemberDto.employeeId = employeeId;
        crewMemberDto.name = name;

        String uri = "/cruises/" + cruiseId + "/crewMembers";
        return makePostRequest(uri, crewMemberDto);
    }

    public static Response getEmergencyShuttleManifest(String cruiseId) {
        String uri = "/cruises/" + cruiseId + "/emergencyShuttles";
        return makeGetRequest(uri);
    }

    private static Response makeGetRequest(String uri) {
        return given().port(UspaceMain.PORT)
                      .when().get(uri);
    }

    private static Response makePostRequest(String uri, Object body) {
        return given().header("Content-Type", "application/json")
                      .port(UspaceMain.PORT)
                      .body(body)
                      .when().post(uri);
    }

    private static Response makeDeleteRequest(String uri) {
        return given().port(UspaceMain.PORT)
               .when().delete(uri);
    }
}
