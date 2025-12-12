describe('Cabin Attributions', () => {
  beforeEach(() => {
    cy.visit('/')
  })

  it('displays cabin attributions table when cabins are present', () => {
    cy.intercept('GET', '/cruises/JUPITER_MOONS_EXPLORATION_2085/cabins?criteria=bookingDateTime', {
      statusCode: 200,
      body: {
        cabins: [
          { cabinId: 'A1', category: 'STANDARD', bookingId: 'booking1' },
          { cabinId: 'A2', category: 'STANDARD', bookingId: 'booking2' }
        ]
      }
    }).as('getCabins')

    cy.get('[data-cy="fetch-cabin-attribution"]').click()
    cy.wait('@getCabins')

    cy.get('table').should('exist')
    cy.get('[data-cy="cabin-attribution-details"]').should('have.length', 2)
    cy.get('.cabin-id-item').first().should('contain', 'A1')
    cy.get('.cabin-category-item').first().should('contain', 'STANDARD')
  })

  it('does not display table when no cabins', () => {
    cy.intercept('GET', '/cruises/JUPITER_MOONS_EXPLORATION_2085/cabins?criteria=bookingDateTime', {
      statusCode: 200,
      body: {
        cabins: []
      }
    }).as('getCabins')

    cy.get('[data-cy="fetch-cabin-attribution"]').click()
    cy.wait('@getCabins')

    cy.get('table').should('not.exist')
  })

  it('allows changing criteria and fetches with correct parameter', () => {
    cy.intercept('GET', '/cruises/JUPITER_MOONS_EXPLORATION_2085/cabins?criteria=travelers', {
      statusCode: 200,
      body: {
        cabins: [
          { cabinId: 'A1', category: 'STANDARD', bookingId: 'booking1' }
        ]
      }
    }).as('getCabinsByTravelers')

    cy.get('select').select('travelers')
    cy.get('[data-cy="fetch-cabin-attribution"]').click()
    cy.wait('@getCabinsByTravelers')

    cy.get('table').should('exist')
  })

  it('displays cabins in correct order', () => {
    cy.intercept('GET', '/cruises/JUPITER_MOONS_EXPLORATION_2085/cabins?criteria=bookingDateTime', {
      statusCode: 200,
      body: {
        cabins: [
          { cabinId: 'A1', category: 'STANDARD', bookingId: 'booking1' },
          { cabinId: 'A2', category: 'STANDARD', bookingId: 'booking2' },
          { cabinId: 'A3', category: 'STANDARD', bookingId: 'booking3' }
        ]
      }
    }).as('getCabins')

    cy.get('[data-cy="fetch-cabin-attribution"]').click()
    cy.wait('@getCabins')

    cy.get('.cabin-id-item').eq(0).should('contain', 'A1')
    cy.get('.cabin-id-item').eq(1).should('contain', 'A2')
    cy.get('.cabin-id-item').eq(2).should('contain', 'A3')
  })
})

