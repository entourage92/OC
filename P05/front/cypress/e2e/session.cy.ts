describe('visite /session', () => {
  beforeEach(() => {
    cy.login();
  });

  it('visite me', () => {
    cy.get('[routerlink="sessions"]').click()
    cy.getsessions();
  });
});



