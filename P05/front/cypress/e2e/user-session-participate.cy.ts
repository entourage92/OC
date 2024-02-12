describe('User Sessions', () => {
    beforeEach(() => {
        cy.login("User4@studio.com", "test!1234");
    });

    it('User Sessions', () => {
        cy.intercept(
            {
                method: 'POST', // Route all GET requests
                url: '/api/session/**', // that have a URL that matches '/users/*'
            } // and force the response to be: []
        ).as('participate')
        cy.intercept(
            {
                method: 'DELETE', // Route all GET requests
                url: '/api/session/**', // that have a URL that matches '/users/*'
            } // and force the response to be: []
        ).as('nolongerparticipate')

        cy.get('[routerlink="sessions"]').click()
        cy.getsessions();
        cy.get('.mat-card-actions > .mat-focus-indicator').first().click()
        cy.get('div.ng-star-inserted > .mat-focus-indicator').click()
        cy.wait('@participate').should((obj) => {
            expect(obj.response.statusCode).to.eq(200)
        });
        cy.get('button[color="warn"]').click()
        cy.wait('@nolongerparticipate').should((obj) => {
            expect(obj.response.statusCode).to.eq(200)
        });
    });
});



