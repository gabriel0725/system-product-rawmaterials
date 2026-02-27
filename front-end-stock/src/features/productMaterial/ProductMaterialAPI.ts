import type { ProductMaterial } from "./productMaterialSlice";

const API_URL = "http://localhost:8080/product-materials";

export async function create(data: {
  productId: number;
  materialId: number;
  quantity: number;
}): Promise<ProductMaterial> {

  const params = new URLSearchParams({
    productId: String(data.productId),
    materialId: String(data.materialId),
    quantity: String(data.quantity),
  });

  const response = await fetch(`${API_URL}?${params.toString()}`, {
    method: "POST",
  });

  if (!response.ok) {
    throw new Error("Failed to create association");
  }

  return response.json();
}

export async function update(
  id: number,
  quantity: number
): Promise<ProductMaterial> {

  const params = new URLSearchParams({
    quantity: String(quantity),
  });

  const response = await fetch(`${API_URL}/${id}?${params.toString()}`, {
    method: "PUT",
  });

  if (!response.ok) {
    throw new Error("Failed to update quantity");
  }

  return response.json();
}

export async function remove(id: number): Promise<void> {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("Failed to delete association");
  }
}
