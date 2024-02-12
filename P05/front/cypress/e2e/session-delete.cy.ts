describe('delete a session', () => {
  beforeEach(() => {
    cy.login();
  });

  it('delete a session', () => {
    cy.intercept(
      {
        method: 'DELETE', // Route all GET requests
        url: 'api/session/*', // that have a URL that matches '/users/*'
      } // and force the response to be: []
    ).as('deleteesession')

    cy.get('[routerlink="sessions"]').click()
    cy.getsessions();
    cy.get(':nth-child(1) > .mat-card-actions > :nth-child(1)').first().click()
    cy.get(':nth-child(2) > .mat-focus-indicator').click()

    cy.wait('@deleteesession').should((obj) => {
      expect(obj.response.statusCode).to.eq(200)
    });
  });
});
