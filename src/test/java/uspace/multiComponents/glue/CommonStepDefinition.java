package uspace.multiComponents.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import uspace.UspaceMain;
import uspace.api.ConfigurationServerRest;
import uspace.multiComponents.util.ResponseMemory;

public class CommonStepDefinition {
    @Before
    public void startServer() {
        ResponseMemory.clear();
        ConfigurationServerRest.useInMemoryRepositories();
        UspaceMain.runForTest();
        System.out.println("Server started");
    }

    @After
    public void stopServer() throws Exception {
        UspaceMain.stop();
        System.out.println("Server stopped");
    }

    @Alors("l'erreur {string} est obtenue")
    public void lErreurEstObtenue(String errorName) {
        ResponseMemory.getResponse().then().body("error", org.hamcrest.Matchers.equalTo(errorName));
    }

    @Alors("le code de statut {int} est obtenu")
    public void leCodeDeStatutEstObtenu(int code) {
        ResponseMemory.getResponse().then().statusCode(code);
    }

    @io.cucumber.java.fr.Etantdonné("un compte utilisateur avec le nom d'utilisateur {string}")
    public void unCompteUtilisateur(String username) {
        // Try to create the account - if it already exists, the API will return 400
        // We'll ignore that error since the account already exists
        // Use Mars since Earth accounts cannot be deleted per business rules
        io.restassured.response.Response response = uspace.multiComponents.util.APIHelper.createAccount(username,
                "Mars");
        int statusCode = response.getStatusCode();
        // Accept either 201 (created) or 400 (already exists)
        if (statusCode != 201 && statusCode != 400) {
            throw new RuntimeException("Unexpected status code when creating account: " + statusCode);
        }
    }

    @io.cucumber.java.fr.Quand("un compte utilisateur avec le nom d'utilisateur {string} est créé")
    public void unCompteUtilisateurEstCree(String username) {
        // Use Mars for accounts in create/delete tests since Earth accounts cannot be
        // deleted
        io.restassured.response.Response response = uspace.multiComponents.util.APIHelper.createAccount(username,
                "Mars");
        ResponseMemory.setResponse(response);
    }

    @io.cucumber.java.fr.Quand("un compte utilisateur avec le nom d'utilisateur {string} est supprimé")
    public void unCompteUtilisateurEstSupprime(String username) {
        io.restassured.response.Response response = uspace.multiComponents.util.APIHelper.deleteAccount(username);
        ResponseMemory.setResponse(response);
    }

    @io.cucumber.java.fr.Alors("le compte utilisateur avec le nom d'utilisateur {string} existe")
    public void leCompteUtilisateurExiste(String username) {
        io.restassured.response.Response response = uspace.multiComponents.util.APIHelper.getAccount(username);
        response.then().statusCode(200);
    }

    @io.cucumber.java.fr.Alors("le compte utilisateur avec le nom d'utilisateur {string} n'existe pas")
    public void leCompteUtilisateurNExistePas(String username) {
        io.restassured.response.Response response = uspace.multiComponents.util.APIHelper.getAccount(username);
        response.then().statusCode(404);
    }
}
