import type { Product } from "./types";

const API_URL = "http://localhost:8080";

export const fetchProductsAPI = async (): Promise<Product[]> => {
  const response = await fetch(`${API_URL}/products`);
  if (!response.ok) throw new Error("Error when searching for products");
  return response.json();
};

export const createProductAPI = async (product: Product): Promise<Product> => {
  const response = await fetch(`${API_URL}/products`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(product),
  });

  if (!response.ok) throw new Error("Error creating product");
  return response.json();
};

export const updateProductAPI = async (
  id: number,
  product: Product
): Promise<Product> => {
  const response = await fetch(`${API_URL}/products/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(product),
  });

  if (!response.ok) throw new Error("Error when updating produc");
  return response.json();
};

export const deleteProductAPI = async (id: number): Promise<void> => {
  const response = await fetch(`${API_URL}/products/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) throw new Error("Error when deleting product");
};
