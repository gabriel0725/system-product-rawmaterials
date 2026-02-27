import { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../app/hooks'
import {
  fetchProducts,
  deleteProduct,
  updateProduct,
  updateProductMaterials
} from '../features/product/productSlice'
import { fetchRawMaterials } from '../features/rawMaterial/rawMaterialSlice'
import ProductForm from '../components/ProductForm'
import type { Product } from '../features/product/types'
import type { RawMaterial } from '../features/rawMaterial/types'
import {
  ButtonRed,
  ButtonBlue,
  ButtonGreen,
  Card,
  Container,
  Table,
  MaterialRow,
  MaterialName,
  MaterialQuantity,
  Tr,
  Thead,
  Th,
  Td,
  TdMaterials,
  MaterialsSection,
  MaterialsButtons,
  SelectedMaterialRow
} from '../styles/Layout'

export default function ProductsPage() {
  const dispatch = useAppDispatch()
  const products = useAppSelector((state) => state.product.items ?? [])
  const rawMaterials = useAppSelector(
    (state) => state.rawMaterial.rawMaterials ?? []
  )

  const [editing, setEditing] = useState<Product | null>(null)
  const [selectedMaterials, setSelectedMaterials] = useState<
    { rawMaterial: RawMaterial; quantity: string }[]
  >([])

  useEffect(() => {
    dispatch(fetchProducts())
    dispatch(fetchRawMaterials())
  }, [dispatch])

  useEffect(() => {
    if (editing) {
      setSelectedMaterials(
        editing.materials?.map((m) => ({
          rawMaterial: {
            id: m.rawMaterialId,
            name: m.rawMaterialName,
            stockQuantity: 0
          },
          quantity: m.requiredQuantity?.toString() ?? ''
        })) ?? []
      )
    } else {
      setSelectedMaterials([])
    }
  }, [editing])

  const handleMaterialChange = (id: number, quantity: string) => {
    setSelectedMaterials((prev) =>
      prev.map((m) => (m.rawMaterial.id === id ? { ...m, quantity } : m))
    )
  }

  const addMaterial = (material: RawMaterial) => {
    if (!selectedMaterials.find((m) => m.rawMaterial.id === material.id)) {
      setSelectedMaterials((prev) => [
        ...prev,
        { rawMaterial: material, quantity: '' }
      ])
    }
  }

  const removeMaterial = (id: number) => {
    setSelectedMaterials((prev) => prev.filter((m) => m.rawMaterial.id !== id))
  }

  const saveMaterialAssociations = () => {
  if (editing) {
    dispatch(
      updateProductMaterials({
        id: editing.id,
        materials: selectedMaterials.map((m) => ({
          rawMaterialId: m.rawMaterial.id,
          requiredQuantity: Number(m.quantity)
        }))
      })
    )

    setEditing(null)
    setSelectedMaterials([])
  }
}

console.log("PRODUCTS STATE:", products)

  return (
    <Container className="container">
      <h1>Products</h1>

      <Card>
        <ProductForm editing={editing} setEditing={setEditing} />
      </Card>

      {editing && (
        <MaterialsSection style={{ marginBottom: '20px' }}>
          <h3>Associate Raw Materials</h3>

          <MaterialsButtons style={{ display: 'flex', gap: '10px', flexWrap: 'wrap' }}>
            {rawMaterials.map((rm) => (
              <ButtonGreen
                key={rm.id}
                onClick={() => addMaterial(rm)}
                disabled={selectedMaterials.some(
                  (m) => m.rawMaterial.id === rm.id
                )}
              >
                {rm.name}
              </ButtonGreen>
            ))}
          </MaterialsButtons>

          {selectedMaterials.map((m) => (
            <SelectedMaterialRow
              key={m.rawMaterial.id}
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: '10px',
                marginTop: '5px'
              }}
            >
              <span>{m.rawMaterial.name}</span>
              <input
                type="number"
                min={0}
                placeholder="Quantity"
                value={m.quantity}
                onChange={(e) =>
                  handleMaterialChange(m.rawMaterial.id, e.target.value)
                }
              />
              <ButtonRed onClick={() => removeMaterial(m.rawMaterial.id)}>
                Remove
              </ButtonRed>
            </SelectedMaterialRow >
          ))}

          <ButtonBlue
            onClick={saveMaterialAssociations}
            style={{ marginTop: '10px' }}
          >
            Save Materials
          </ButtonBlue>
        </MaterialsSection>
      )}

      <Table>
        <Thead>
          <Tr>
            <Th>Code</Th>
            <Th>Name</Th>
            <Th>Price</Th>
            <Th>Materials/Quantity Needed</Th>
            <Th>Actions</Th>
          </Tr>
        </Thead>
        <tbody>
          {products.map((p) => (
            <Tr key={p.id}>
              <Td>{p.code}</Td>
              <Td>{p.name}</Td>
              <Td>R$ {p.price?.toFixed(2)}</Td>
              <TdMaterials>
                {p.materials?.length > 0 ? (
                  p.materials.map((m) => (
                    <MaterialRow key={m.rawMaterialId}>
                      <MaterialName>{m.rawMaterialName}</MaterialName>
                      <MaterialQuantity>{m.requiredQuantity}</MaterialQuantity>
                    </MaterialRow>
                  ))
                ) : (
                  <span>—</span>
                )}
              </TdMaterials>
              <Td>
                <ButtonBlue onClick={() => setEditing(p)}>
                  Edit/Add Material
                </ButtonBlue>
                <ButtonRed onClick={() => dispatch(deleteProduct(p.id))}>
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
