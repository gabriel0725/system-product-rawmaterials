import { useState, useEffect } from 'react'
import { createProduct, updateProduct } from '../features/product/productSlice'
import { useAppDispatch } from '../app/hooks'
import type { Product } from '../features/product/types'
import { ButtonRed, ButtonBlue, StyledForm } from '../styles/Layout'

interface Props {
  editing: Product | null
  setEditing: (value: Product | null) => void
}

interface ProductFormData {
  code: string
  name: string
  price: string
}

export default function ProductForm({ editing, setEditing }: Props) {
  const dispatch = useAppDispatch()

  const [formData, setFormData] = useState<ProductFormData>({
    code: '',
    name: '',
    price: ''
  })

  // Sincroniza formulário quando entra em modo edição
  useEffect(() => {
    if (editing) {
      setFormData({
        code: editing.code ?? '',
        name: editing.name ?? '',
        price: editing.price != null ? String(editing.price) : ''
      })
    } else {
      setFormData({
        code: '',
        name: '',
        price: ''
      })
    }
  }, [editing])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }))
  }

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    const priceNumber =
      formData.price.trim() === '' ? 0 : Number(formData.price)

    const productToSubmit = {
      code: formData.code.trim(),
      name: formData.name.trim(),
      price: priceNumber
    }

    if (editing) {
      dispatch(
        updateProduct({
          id: editing.id,
          product: productToSubmit
        })
      )
      setEditing(null)
    } else {
      dispatch(createProduct(productToSubmit))
    }

    // Limpa formulário após envio
    setFormData({
      code: '',
      name: '',
      price: ''
    })
  }

  return (
    <StyledForm
      key={editing?.id ?? 'new'}
      onSubmit={handleSubmit}
      style={{ marginBottom: '20px' }}
    >
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
        min="0"
        placeholder="Price"
        value={formData.price}
        onChange={handleChange}
      />

      <ButtonBlue type="submit">
        {editing ? 'Update' : 'Create'}
      </ButtonBlue>

      {editing && (
        <ButtonRed
          type="button"
          onClick={() => setEditing(null)}
        >
          Cancel
        </ButtonRed>
      )}
    </StyledForm>
  )
}
