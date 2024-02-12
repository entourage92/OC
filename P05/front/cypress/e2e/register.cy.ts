import * as faker from "faker";

const email = faker.internet.email()
const password = "aaaaaa"

describe('Login spec', () => {
  it('Register successfull', () => {
    cy.intercept(
      {
        method: 'POST',
        url: '/api/auth/register',
      }).as('register')

    cy.visit('/register')
    cy.get('input[formcontrolname=firstName]').type(faker.name.firstName())
    cy.get('input[formcontrolname=lastName]').type(faker.name.lastName())
    cy.get('input[formcontrolname=email]').type(email)
  //  cy.get('input[formcontrolname=password]').type(`${password}{enter}{enter}`)
    cy.get('input[formcontrolname=password]').type(password)

    cy.get('form').submit()
    cy.url().should('include', '/login')
    cy.wait('@register').should((obj) => {
      console.log(obj.response)
      cy.log(obj.response)
      const requestBody = obj.response.body;
      expect(requestBody.message).to.eq('User registered successfully!')
    });
  })
/*
  afterEach(() => {
    cy.login(email, password);
  });

  */
});
