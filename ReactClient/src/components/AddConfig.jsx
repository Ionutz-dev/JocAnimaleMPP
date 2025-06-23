import React, {useState} from "react";

export default function AddConfig() {
    const [row, setRow] = useState(1);
    const [col, setCol] = useState(1);
    const [animal, setAnimal] = useState("");
    const [imageUrl, setImageUrl] = useState("");

    const handleAdd = async (e) => {
        e.preventDefault();
        await fetch(
            `http://localhost:8080/api/exam/addConfig?row=${row}&col=${col}&animal=${encodeURIComponent(
                animal
            )}&imageUrl=${encodeURIComponent(imageUrl)}`,
            {method: "POST"}
        );
        alert("Config adăugat!");

        setRow(1);
        setCol(1);
        setAnimal("");
        setImageUrl("");
    };

    return (
        <form
            onSubmit={handleAdd}
            style={{
                marginTop: 40,
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                gap: 10,
                background: "#fafbfc",
                padding: 24,
                borderRadius: 8,
                boxShadow: "0 2px 8px rgba(0,0,0,0.05)",
                maxWidth: 400,
                marginLeft: "auto",
                marginRight: "auto"
            }}
        >
            <h3 style={{fontWeight: 600, marginBottom: 10}}>Adaugă animal/config nou</h3>
            <input
                type="number"
                min={1}
                max={3}
                value={row}
                onChange={(e) => setRow(Number(e.target.value))}
                required
                placeholder="Row"
                style={{
                    fontSize: 15, padding: 7, width: "100%", borderRadius: 4, border: "1px solid #ccc"
                }}
            />
            <input
                type="number"
                min={1}
                max={4}
                value={col}
                onChange={(e) => setCol(Number(e.target.value))}
                required
                placeholder="Col"
                style={{
                    fontSize: 15, padding: 7, width: "100%", borderRadius: 4, border: "1px solid #ccc"
                }}
            />
            <input
                type="text"
                value={animal}
                onChange={(e) => setAnimal(e.target.value)}
                required
                placeholder="Animal"
                style={{
                    fontSize: 15, padding: 7, width: "100%", borderRadius: 4, border: "1px solid #ccc"
                }}
            />
            <input
                type="text"
                value={imageUrl}
                onChange={(e) => setImageUrl(e.target.value)}
                required
                placeholder="Imagine URL"
                style={{
                    fontSize: 15, padding: 7, width: "100%", borderRadius: 4, border: "1px solid #ccc"
                }}
            />
            <button
                type="submit"
                style={{
                    fontSize: 15,
                    padding: "8px 24px",
                    background: "#2979ff",
                    color: "white",
                    border: "none",
                    borderRadius: 4,
                    fontWeight: 600,
                    cursor: "pointer",
                    marginTop: 4
                }}
            >
                Adaugă
            </button>
        </form>
    );
}
