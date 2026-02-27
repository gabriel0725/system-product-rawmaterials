import { useState, useEffect } from "react"
import { createProduct, updateProduct } from "../features/product/productSlice"
import { useAppDispatch } from "../app/hooks"
import type { Product } from "../features/product/types"

interface Props {
  editing: Product | null
  setEditing: (value: Product | null) => void
}

interface ProductFormData {
  code: string
  name: string
  price: string // string para permitir campo vazio
}

export default function ProductForm({ editing, setEditing }: Props) {
  const dispatch = useAppDispatch()

  const [formData, setFormData] = useState<ProductFormData>({
    code: "",
    name: "",
    price: "",
  })

  // Sincroniza o form com o produto selecionado para edição
  useEffect(() => {
    if (editing) {
      setFormData({
        code: editing.code,
        name: editing.name,
        price: String(editing.price),
      })
    } else {
      setFormData({ code: "", name: "", price: "" })
    }
  }, [editing])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }))
  }

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    const priceNumber = formData.price.trim() === "" ? 0 : Number(formData.price)

    const productToSubmit = {
      code: formData.code,
      name: formData.name,
      price: priceNumber,
    }

    if (editing) {
      dispatch(
        updateProduct({
          id: editing.id!,
          product: productToSubmit,
        })
      )
      setEditing(null)
    } else {
      dispatch(createProduct(productToSubmit))
    }

    setFormData({ code: "", name: "", price: "" })
  }

  return (
    <form key={editing?.id ?? "new"} onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
      <input
        name="code"
        placeholder="Code"
        value={formData.code}
        onChange={handleChange}
        required
      />
      <input
        name="name"
        placeholder="Name"
        value={formData.name}
        onChange={handleChange}
        required
      />
      <input
        name="price"
        type="number"
        step="0.01"
        placeholder="Price"
        value={formData.price}
        onChange={handleChange}
      />
      <button type="submit">{editing ? "Update" : "Create"}</button>
      {editing && (
        <button type="button" onClick={() => setEditing(null)}>
          Cancel
        </button>
      )}
    </form>
  )
}
