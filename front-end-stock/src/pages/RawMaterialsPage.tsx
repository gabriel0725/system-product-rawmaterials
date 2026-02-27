import { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../app/hooks'
import {
  deleteRawMaterial,
  fetchRawMaterials
} from '../features/rawMaterial/rawMaterialSlice'
import RawMaterialForm from '../components/RawMaterialForm'
import type { RawMaterial } from '../features/rawMaterial/types'

import {
  Container,
  Card,
  Title,
  Table,
  Thead,
  Th,
  Td,
  Tr,
  ButtonBlue,
  ButtonRed
} from '../styles/Layout'

export default function RawMaterialsPage() {
  const dispatch = useAppDispatch()

  const { rawMaterials, status, error } = useAppSelector(
    (state) => state.rawMaterial
  )

  const [editing, setEditing] = useState<RawMaterial | null>(null)

  useEffect(() => {
    dispatch(fetchRawMaterials())
  }, [dispatch])

  return (
    <Container>
      <Title>Raw Materials</Title>

      <Card>
        <RawMaterialForm editing={editing} setEditing={setEditing} />
      </Card>

      {status === 'loading' && <p>Loading...</p>}
      {status === 'failed' && <p>{error}</p>}

      <Table>
        <Thead>
          <tr>
            <Th>Code</Th>
            <Th>Name</Th>
            <Th>Stock</Th>
            <Th>Actions</Th>
          </tr>
        </Thead>
        <tbody>
          {rawMaterials.map((m) => (
            <Tr key={m.id}>
              <Td>{m.code}</Td>
              <Td>{m.name}</Td>
              <Td>{m.stockQuantity}</Td>
              <Td>
                <ButtonBlue onClick={() => setEditing(m)}>Edit</ButtonBlue>
                <ButtonRed
                  onClick={() => m.id && dispatch(deleteRawMaterial(m.id))}
                >
                  Delete
                </ButtonRed>
              </Td>
            </Tr>
          ))}
        </tbody>
      </Table>
    </Container>
  )
}
