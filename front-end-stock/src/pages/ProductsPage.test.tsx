import { screen } from '@testing-library/react'
import ProductsPage from './ProductsPage'
import { renderWithProviders } from '../test/utils'

test('renders products title', () => {
  renderWithProviders(<ProductsPage />)

  expect(screen.getByText(/products/i)).toBeInTheDocument()
})
