describe('Login spec', () => {
  it('Login successfull', () => {
    cy.intercept('POST', '/api/auth/login').as('userlogin')

    cy.visit('/login')

    const email = "yoga@studio.com"
    const pw = "test!1234"

    cy.get('input[formControlName=email]').type(email)
    cy.get('input[formControlName=password]').type(`${pw}{enter}{enter}`)

    cy.wait('@userlogin').should((obj) => {
      console.log(obj);
      cy.log(obj);
      Cypress.env('token', obj.response.body.token);
    });

    cy.url().should('include', '/sessions')
    cy.getsessions();

  })
});
