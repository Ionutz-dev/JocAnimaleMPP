import React, {useState} from "react";

function Login({setNickname, setGameSession}) {
    const [input, setInput] = useState("");

    const handleStart = async (e) => {
        e.preventDefault();
        if (!input.trim()) return;
        const resp = await fetch(
            `http://localhost:8080/api/game/start?nickname=${encodeURIComponent(input.trim())}`,
            {method: "POST"}
        );
        if (resp.ok) {
            const data = await resp.json();
            setNickname(input.trim());
            setGameSession(data);
        } else {
            alert("Eroare la pornirea jocului.");
        }
    };

    return (
        <form
            onSubmit={handleStart}
            style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                gap: 12,
                marginTop: 40,
                background: "#f8f9fa",
                padding: 32,
                borderRadius: 8,
                boxShadow: "0 2px 8px rgba(0,0,0,0.06)",
                maxWidth: 400,
                marginLeft: "auto",
                marginRight: "auto"
            }}
        >
            <label style={{fontWeight: 600, fontSize: 18}}>
                Introdu numele tău (nickname):
            </label>
            <input
                type="text"
                value={input}
                onChange={(e) => setInput(e.target.value)}
                style={{
                    fontSize: 16,
                    padding: 8,
                    width: "100%",
                    borderRadius: 4,
                    border: "1px solid #ccc"
                }}
                required
                placeholder="Nume"
                autoFocus
            />
            <button
                type="submit"
                style={{
                    fontSize: 16,
                    padding: "8px 24px",
                    background: "#2979ff",
                    color: "white",
                    border: "none",
                    borderRadius: 4,
                    fontWeight: 600,
                    cursor: "pointer"
                }}
            >
                Începe jocul
            </button>
        </form>
    );
}

export default Login;
