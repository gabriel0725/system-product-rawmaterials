export interface RawMaterial {
  id?: number;
  code: string;
  name: string;
  stockQuantity: number;
  // opcional, para exibir as associações com produtos no front
  products?: {
    productId: number;
    productName: string;
    quantityRequired: number;
  }[];
}
