describe('visite a session', () => {
  beforeEach(() => {
    cy.login();
  });

  it('visite me', () => {
    cy.intercept(
      {
        method: 'GET', // Route all GET requests
        url: 'api/session/*', // that have a URL that matches '/users/*'
      } // and force the response to be: []
    ).as('visitesession')

    cy.get('[routerlink="sessions"]').click()
    cy.getsessions();
    cy.get(':nth-child(1) > .mat-card-actions > :nth-child(1)').first().click()

    cy.wait('@visitesession').should((obj) => {
      const requestBody = obj.response.body;
      cy.log(requestBody);
      cy.get('h1').should('have.text',requestBody.name);
    });
  });
});



