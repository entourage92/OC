describe('edit a session', () => {
    beforeEach(() => {
        cy.login();
    });

    it('edit a session', () => {
        cy.intercept(
            {
                method: 'PUT', // Route all GET requests
                url: 'api/session/*', // that have a URL that matches '/users/*'
            } // and force the response to be: []
        ).as('editsession')
        cy.intercept(
            {
                method: 'GET', // Route all GET requests
                url: 'api/session/*', // that have a URL that matches '/users/*'
            } // and force the response to be: []
        ).as('visitesession')

        cy.get('[routerlink="sessions"]').click()
        cy.getsessions();
        cy.get('.mat-card-actions > .ng-star-inserted').first().click()
        // cy.get(':nth-child(2) > .mat-focus-indicator').click()

        cy.wait('@visitesession').should((obj) => {
            const requestBody = obj.response.body;
            cy.get('#mat-input-2').should('have.value', requestBody.name);
            cy.get('#mat-input-2').clear()
            cy.get('#mat-input-2').type(requestBody.name + " edited")
        });
        cy.get('mat-select').click()
        cy.get('#mat-option-0 > .mat-option-text').click()
        cy.get('.mt2 > [fxlayout="row"] > .mat-focus-indicator > .mat-button-wrapper').click()

        cy.wait('@editsession').should((obj) => {
            expect(obj.response.statusCode).to.eq(200)
        });
    });
});
