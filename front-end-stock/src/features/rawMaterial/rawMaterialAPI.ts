import type { RawMaterial } from "./types";

const BASE_URL = import.meta.env.VITE_API_URL;
const API_URL = `${BASE_URL}/raw-materials`;


async function handleResponse(response: Response) {
  if (!response.ok) {
    throw new Error("API request failed");
  }
  return response.json();
}

export async function getAll(): Promise<RawMaterial[]> {
  const response = await fetch(API_URL);
  return handleResponse(response);
}

export async function create(data: RawMaterial): Promise<RawMaterial> {
  const response = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  return handleResponse(response);
}

export async function update(data: RawMaterial): Promise<RawMaterial> {
  const response = await fetch(`${API_URL}/${data.id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  return handleResponse(response);
}

export async function remove(id: number): Promise<void> {
  const response = await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("Failed to delete raw material");
  }
}
