import { vi } from "vitest"

//Mock mantendo o reducer original
vi.mock("../features/production/productionSlice", async () => {
  const actual = await vi.importActual<any>(
    "../features/production/productionSlice"
  )

  return {
    ...actual,
    default: actual.default, // mantém reducer
    fetchProduction: () => ({
      type: "production/fetchProduction/fulfilled",
      payload: {
        products: [],
        grandTotal: 0,
      },
    }),
  }
})

import { screen } from "@testing-library/react"
import ProductionPage from "./ProductionPage"
import { renderWithProviders } from "../test/utils"

test("renders production page", () => {
  renderWithProviders(<ProductionPage />)

  expect(
    screen.getByText(/production suggestion/i)
  ).toBeInTheDocument()
})
