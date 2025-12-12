package uspace.multiComponents.glue;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import io.restassured.response.Response;
import uspace.multiComponents.util.APIHelper;
import uspace.multiComponents.util.ResponseMemory;

public class AccountStepDefinition {
    private static final String VALID_HOME_PLANET_NAME = "Terre";

    @Etantdonné("un compte utilisateur avec le nom d'utilisateur {string}")
    public void unCompteUtilisateur(String username) {
        APIHelper.createAccount(username, VALID_HOME_PLANET_NAME);
    }


    @Quand("un compte utilisateur avec le nom d'utilisateur {string} est créé")
    public void unCompteUtilisateurEstCree(String username) {
        ResponseMemory.setResponse(APIHelper.createAccount(username, VALID_HOME_PLANET_NAME));
    }


    @Quand("un compte utilisateur avec le nom d'utilisateur {string} est supprimé")
    public void leCompteUtilisateurEstSupprime(String username) {
        ResponseMemory.setResponse(APIHelper.deleteAccount(username));
    }

    @Alors("le compte utilisateur avec le nom d'utilisateur {string} n'existe pas")
    public void leCompteUtilisateurNExistePas(String username) {
        Response accountResponse = APIHelper.getAccount(username);
        accountResponse.then().statusCode(404);
    }

    @Alors("le compte utilisateur avec le nom d'utilisateur {string} existe")
    public void leCompteUtilisateurExiste(String username) {
        Response accountResponse = APIHelper.getAccount(username);
        accountResponse.then().statusCode(200);
    }
}
