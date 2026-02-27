import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../app/hooks";
import {
  deleteRawMaterial,
  fetchRawMaterials,
} from "../features/rawMaterial/rawMaterialSlice";
import RawMaterialForm from "../components/RawMaterialForm";
import type { RawMaterial } from "../features/rawMaterial/types";

export default function RawMaterialsPage() {
  const dispatch = useAppDispatch();

  const { rawMaterials, status, error } = useAppSelector(
    (state) => state.rawMaterial
  );

  const [editing, setEditing] = useState<RawMaterial | null>(null);

  useEffect(() => {
    dispatch(fetchRawMaterials());
  }, [dispatch]);

  return (
    <div className="container">
      <h1>Raw Materials</h1>

      {/* Formulário de criação/edição */}
      <RawMaterialForm editing={editing} setEditing={setEditing} />

      {/* Status e erros */}
      {status === "loading" && <p>Loading...</p>}
      {status === "failed" && <p>{error}</p>}

      {/* Tabela de matérias-primas */}
     <table>
      <thead>
        <tr>
          <th>Code</th>
          <th>Name</th>
          <th>Stock Quantity</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {rawMaterials.map((m) => (
          <tr key={m.id ?? Math.random()}>
            <td>{m.code ?? ""}</td>
            <td>{m.name ?? ""}</td>
            <td>{m.stockQuantity ?? 0}</td>
            <td>
              <button onClick={() => setEditing(m)}>Edit</button>
              <button
                onClick={() => m.id && dispatch(deleteRawMaterial(m.id))}
              >
                Delete
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
    </div>
  );
}
