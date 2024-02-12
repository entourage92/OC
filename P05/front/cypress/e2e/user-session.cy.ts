describe('User Sessions', () => {
  beforeEach(() => {
    cy.login("User4@studio.com", "test!1234");
  });

  it('User Sessions', () => {
    cy.get('[routerlink="sessions"]').click()
    cy.getsessions();
  });
});



