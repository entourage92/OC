describe('visite /me', () => {
  beforeEach(() => {
    cy.login();
  });

  it('visite me', () => {
    cy.intercept(
      {
        method: 'GET', // Route all GET requests
        url: 'api/user/**', // that have a URL that matches '/users/*'
      } // and force the response to be: []
    ).as('getUserdetail')

    cy.get('[routerlink="me"]').click()

    cy.wait('@getUserdetail').should((obj) => {
      const requestBody = obj.response.body;
      cy.get('.mat-card-content > div.ng-star-inserted > :nth-child(1)').should('have.text',"Name: "+requestBody.firstName+" "+requestBody.lastName.toUpperCase());
    });
  })
});


