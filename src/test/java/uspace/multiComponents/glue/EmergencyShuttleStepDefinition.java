package uspace.multiComponents.glue;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import uspace.multiComponents.util.APIHelper;
import uspace.multiComponents.util.ResponseMemory;

import static org.hamcrest.Matchers.*;

public class EmergencyShuttleStepDefinition {
    private static final String VALID_CRUISE_ID = "JUPITER_MOONS_EXPLORATION_2085";

    @Etantdonné("un membre d'équipage avec l'ID {string} et le nom {string} est ajouté à la croisière")
    public void unMembreDequipageAvecLIDEtLeNomEstAjouteALaCroisiere(String employeeId, String name) {
        APIHelper.addCrewMember(VALID_CRUISE_ID, employeeId, name);
    }

    @Quand("le manifeste de navettes d'urgence est obtenu")
    public void leManifesteDeNavettesDurgenceEstObtenu() {
        ResponseMemory.setResponse(APIHelper.getEmergencyShuttleManifest(VALID_CRUISE_ID));
    }

    @Quand("le manifeste de navettes d'urgence est obtenu pour la croisière {string}")
    public void leManifesteDeNavettesDurgenceEstObtenuPourLaCroisiere(String cruiseId) {
        ResponseMemory.setResponse(APIHelper.getEmergencyShuttleManifest(cruiseId));
    }

    @Alors("le manifeste de navettes d'urgence est retourné")
    public void leManifesteDeNavettesDurgenceEstRetourne() {
        ResponseMemory.getResponse().then()
            .statusCode(200)
            .body("emergencyShuttles", notNullValue())
            .body("cost", notNullValue());
    }

    @Alors("le coût total est calculé")
    public void leCoutTotalEstCalcule() {
        ResponseMemory.getResponse().then()
            .body("cost", greaterThanOrEqualTo(0));
    }

    @Alors("les voyageurs sont assignés en priorité aux RESCUE_SHIP")
    public void lesVoyageursSontAssignesEnPrioriteAuxRescueShip() {
        ResponseMemory.getResponse().then()
            .body("emergencyShuttles", notNullValue())
            .body("emergencyShuttles[0].type", equalTo("RESCUE_SHIP"));
    }

    @Alors("les navettes vides ne sont pas incluses dans le manifeste")
    public void lesNavettesVidesNeSontPasInclusesDansLeManifeste() {
        ResponseMemory.getResponse().then()
            .body("emergencyShuttles", notNullValue())
            .body("emergencyShuttles.findAll { it.travelers.size() == 0 && it.crewMembers.size() == 0 }.size()", equalTo(0));
    }
}

