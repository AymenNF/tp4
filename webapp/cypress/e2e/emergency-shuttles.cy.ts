describe('Emergency Shuttles Manifest', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should display emergency shuttles manifest when data is available', () => {
    cy.intercept('GET', '/cruises/JUPITER_MOONS_EXPLORATION_2085/emergencyShuttles', {
      statusCode: 200,
      body: {
        cost: 200000,
        emergencyShuttles: [
          {
            type: 'RESCUE_SHIP',
            travelers: [
              { travelerId: 'traveler-1', travelerName: 'John Doe' },
              { travelerId: 'traveler-2', travelerName: 'Jane Doe' }
            ],
            crewMembers: [
              { employeeId: 'ABC123', crewMemberName: 'Crew Member 1' }
            ]
          },
          {
            type: 'STANDARD_SHUTTLE',
            travelers: [
              { travelerId: 'traveler-3', travelerName: 'Bob Smith' }
            ],
            crewMembers: []
          }
        ]
      }
    }).as('getManifest');

    cy.get('[data-cy="fetch-emergency-shuttles-manifest"]').click();
    cy.wait('@getManifest');

    cy.get('[data-cy="total-cost"]').should('contain', '200000');
    cy.get('[data-cy="emergency-shuttle"]').should('have.length', 2);
    cy.get('[data-cy="emergency-shuttle"]').first().should('contain', 'RESCUE_SHIP');
  });

  it('should display total cost correctly', () => {
    cy.intercept('GET', '/cruises/JUPITER_MOONS_EXPLORATION_2085/emergencyShuttles', {
      statusCode: 200,
      body: {
        cost: 150000,
        emergencyShuttles: [
          {
            type: 'RESCUE_SHIP',
            travelers: [],
            crewMembers: []
          }
        ]
      }
    }).as('getManifest');

    cy.get('[data-cy="fetch-emergency-shuttles-manifest"]').click();
    cy.wait('@getManifest');

    cy.get('[data-cy="total-cost"]').should('contain', '150000');
  });

  it('should make button red when manifest is empty', () => {
    cy.intercept('GET', '/cruises/JUPITER_MOONS_EXPLORATION_2085/emergencyShuttles', {
      statusCode: 200,
      body: {
        cost: 0,
        emergencyShuttles: []
      }
    }).as('getEmptyManifest');

    cy.get('[data-cy="fetch-emergency-shuttles-manifest"]').should('not.have.class', 'red-button');
    cy.get('[data-cy="fetch-emergency-shuttles-manifest"]').click();
    cy.wait('@getEmptyManifest');

    cy.get('[data-cy="fetch-emergency-shuttles-manifest"]').should('have.class', 'red-button');
  });

  it('should hide table when manifest is empty', () => {
    cy.intercept('GET', '/cruises/JUPITER_MOONS_EXPLORATION_2085/emergencyShuttles', {
      statusCode: 200,
      body: {
        cost: 0,
        emergencyShuttles: []
      }
    }).as('getEmptyManifest');

    cy.get('[data-cy="fetch-emergency-shuttles-manifest"]').click();
    cy.wait('@getEmptyManifest');

    cy.get('#manifest-details').should('not.exist');
  });
});

