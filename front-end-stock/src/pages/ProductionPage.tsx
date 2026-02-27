import { useEffect } from "react"
import { fetchProduction } from "../features/production/productionSlice"
import { useAppDispatch, useAppSelector } from "../app/hooks"

export default function ProductionPage() {
  const dispatch = useAppDispatch()
  const { products, grandTotal, status, error } = useAppSelector(state => state.production)

  useEffect(() => {
    dispatch(fetchProduction())
  }, [dispatch])

  if (status === "loading") return <p>Loading...</p>
  if (status === "failed") return <p>{error}</p>

  return (
    <div className="container">
      <h1>Production Suggestion</h1>

      <table>
        <thead>
          <tr>
            <th>Product Name</th>
            <th>Quantity Possible</th>
            <th>Unit Price</th>
            <th>Total Value</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p) => (
            <tr key={p.productId}>
              <td>{p.productName}</td>
              <td>{p.quantityPossible}</td>
              <td>R$ {p.unitPrice.toFixed(2)}</td>
              <td>R$ {p.totalValue.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h3>Grand Total: R$ {grandTotal.toFixed(2)}</h3>
    </div>
  )
}
