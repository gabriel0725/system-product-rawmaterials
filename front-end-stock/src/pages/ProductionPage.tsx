import { useEffect } from "react"
import { fetchProduction } from "../features/production/productionSlice"
import { useAppDispatch, useAppSelector } from "../app/hooks"

import {
  Container,
  Table,
  Tr,
  Thead,
  Th,
  Td,
} from '../styles/Layout'

export default function ProductionPage() {
  const dispatch = useAppDispatch()
  const { products, grandTotal, status, error } = useAppSelector(state => state.production)

  useEffect(() => {
    dispatch(fetchProduction())
  }, [dispatch])

  if (status === "loading") return <p>Loading...</p>
  if (status === "failed") return <p>{error}</p>

  return (
    <Container className="container">
      <h1>Production Suggestion</h1>

      <Table>
        <Thead>
          <Tr>
            <Th>Product Name</Th>
            <Th>Quantity Possible</Th>
            <Th>Unit Price</Th>
            <Th>Total Value</Th>
          </Tr>
        </Thead>
        <tbody>
          {products.map((p) => (
            <Tr key={p.productId}>
              <Td>{p.productName}</Td>
              <Td>{p.quantityPossible}</Td>
              <Td>R$ {p.unitPrice.toFixed(2)}</Td>
              <Td>R$ {p.totalValue.toFixed(2)}</Td>
            </Tr>
          ))}
        </tbody>
      </Table>

      <h3>Grand Total: R$ {grandTotal.toFixed(2)}</h3>
    </Container>
  )
}
