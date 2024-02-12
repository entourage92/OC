import * as faker from "faker";

describe('Create session', () => {
  beforeEach(() => {
    cy.login();
  });

  it('Create session', () => {
    cy.intercept(
      {
        method: 'POST', // Route all GET requests
        url: 'api/session', // that have a URL that matches '/users/*'
      } // and force the response to be: []
    ).as('Createsession')

    cy.get('.mat-card-header > .mat-focus-indicator').click()

    let name = faker.music.genre();

    cy.get('input[formControlName=name]').type(name)
    cy.get('input[formControlName=date]').type("1999-12-31")
    cy.get('mat-select').click()
    cy.get('#mat-option-0 > .mat-option-text').click()
    cy.get('textarea[formControlName=description]').type(faker.commerce.productDescription())
    cy.get('.mt2 > [fxlayout="row"] > .mat-focus-indicator').click
    cy.get('button[type="submit"]').click();

    cy.wait('@Createsession').should((obj) => {
      const requestBody = obj.response.body;
      expect(requestBody.name).to.eq(name)
    });

  })
});

