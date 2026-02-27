describe('Product Flow', () => {

  it('should create a product', () => {

    cy.visit('http://localhost:3000/products');

    cy.get('input[name="name"]').type('Cypress Product');
    cy.get('input[name="value"]').type('150');

    cy.get('button[type="submit"]').click();

    cy.contains('Cypress Product').should('exist');
  });

});
