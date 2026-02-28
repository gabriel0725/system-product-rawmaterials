import type { ProductionResponse } from "./types";

const API_URL = import.meta.env.VITE_API_URL;

export const fetchProductionAPI = async (): Promise<ProductionResponse> => {
  const response = await fetch(`${API_URL}/production/suggestion`);
  if (!response.ok) throw new Error("Error when fetching production");
  return response.json();
};
