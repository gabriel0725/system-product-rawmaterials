import { useEffect, useState } from "react"
import { useAppDispatch, useAppSelector } from "../app/hooks"
import { fetchProducts, deleteProduct, updateProduct } from "../features/product/productSlice"
import { fetchRawMaterials } from "../features/rawMaterial/rawMaterialSlice"
import ProductForm from "../components/ProductForm"
import type { Product } from "../features/product/types"
import type { RawMaterial } from "../features/rawMaterial/types"

export default function ProductsPage() {
  const dispatch = useAppDispatch()
  const products = useAppSelector(state => state.product.items ?? [])
  const rawMaterials = useAppSelector(state => state.rawMaterial.rawMaterials ?? [])

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
        editing.materials?.map(m => ({
          rawMaterial: {
            id: m.rawMaterialId,
            name: m.rawMaterialName,
            stockQuantity: 0
          },
          quantity: m.requiredQuantity?.toString() ?? ""
        })) ?? []
      )
    } else {
      setSelectedMaterials([])
    }
  }, [editing])

  const handleMaterialChange = (id: number, quantity: string) => {
    setSelectedMaterials(prev =>
      prev.map(m =>
        m.rawMaterial.id === id ? { ...m, quantity } : m
      )
    )
  }

  const addMaterial = (material: RawMaterial) => {
    if (!selectedMaterials.find(m => m.rawMaterial.id === material.id)) {
      setSelectedMaterials(prev => [...prev, { rawMaterial: material, quantity: "" }])
    }
  }

  const removeMaterial = (id: number) => {
    setSelectedMaterials(prev => prev.filter(m => m.rawMaterial.id !== id))
  }

  const saveMaterialAssociations = () => {
    if (editing) {
      dispatch(updateProduct({
        id: editing.id,
        product: {
          ...editing,
          materials: selectedMaterials.map(m => ({
          rawMaterial: { id: m.rawMaterial.id },
          requiredQuantity: Number(m.quantity)
        }))
        }
      }))
      setEditing(null)
      setSelectedMaterials([])
    }
  }

  return (
    <div className="container">
      <h1>Products</h1>

      <ProductForm editing={editing} setEditing={setEditing} />

      {editing && (
        <div style={{ marginBottom: "20px" }}>
          <h3>Associate Raw Materials</h3>

          <div style={{ display: "flex", gap: "10px", flexWrap: "wrap" }}>
            {rawMaterials.map(rm => (
              <button
                key={rm.id}
                onClick={() => addMaterial(rm)}
                disabled={selectedMaterials.some(m => m.rawMaterial.id === rm.id)}
              >
                {rm.name}
              </button>
            ))}
          </div>

          {selectedMaterials.map(m => (
            <div
              key={m.rawMaterial.id}
              style={{ display: "flex", alignItems: "center", gap: "10px", marginTop: "5px" }}
            >
              <span>{m.rawMaterial.name}</span>
              <input
                type="number"
                min={0}
                placeholder="Quantity"
                value={m.quantity}
                onChange={(e) => handleMaterialChange(m.rawMaterial.id, e.target.value)}
              />
              <button onClick={() => removeMaterial(m.rawMaterial.id)}>Remove</button>
            </div>
          ))}

          <button onClick={saveMaterialAssociations} style={{ marginTop: "10px" }}>
            Save Materials
          </button>
        </div>
      )}

      <table>
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Price</th>
            <th>Necessary Materials</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map(p => (
            <tr key={p.id}>
              <td>{p.code}</td>
              <td>{p.name}</td>
              <td>R$ {p.price?.toFixed(2)}</td>
              <td>
                {p.materials?.length > 0 ? (
                  p.materials.map(m => (
                    <div key={m.rawMaterialId}>
                      {m.rawMaterialName}: {m.requiredQuantity}
                    </div>
                  ))
                ) : (
                  <span>—</span>
                )}
              </td>
              <td>
                <button onClick={() => setEditing(p)}>Edit</button>
                <button onClick={() => dispatch(deleteProduct(p.id))}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
