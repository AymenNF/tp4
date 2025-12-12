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
}
