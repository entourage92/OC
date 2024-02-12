// ***********************************************
// This example namespace declaration will help
// with Intellisense and code completion in your
// IDE or Text Editor.
// ***********************************************
// declare namespace Cypress {
//   interface Chainable<Subject = any> {
//     customCommand(param: any): typeof customCommand;
//   }
// }
//
// function customCommand(param: any): void {
//   console.warn(param);
// }
//
// NOTE: You can use it like so:
// Cypress.Commands.add('customCommand', customCommand);
//
// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

declare namespace Cypress {
  interface Chainable<Subject = any> {
    login(email? : string, pw? : string): any;
  }
}

Cypress.Commands.add('login', (email? : string, pw? : string) => {
  cy.intercept('POST', '/api/auth/login').as('userlogin')

  cy.visit('/login')

  if (typeof email == 'undefined' || typeof pw == 'undefined'){
    email = "yoga@studio.com"
    pw = "test!1234"
  }

  cy.get('input[formControlName=email]').type(email)
  cy.get('input[formControlName=password]').type(`${pw}{enter}{enter}`)

  cy.wait('@userlogin').should((obj) => {
    console.log(obj);
    cy.log(obj);
    Cypress.env('token', obj.response.body.token);
  });
})

Cypress.Commands.add('getsessions', () => {
  it('Get all sessions', () => {
    const token = Cypress.env('token');
    const authorization = `bearer ${ token }`;
    console.log(authorization)
    const options = {
      method: 'GET',
      url: `/api/session`,
      headers: {
        authorization,
      }};
cy.log(authorization)
    cy.request(options)
      .its('status')
      .should('eq', 200);
  })
});

function beforeEach(arg0: () => void) {
  throw new Error("Function not implemented.");
}

