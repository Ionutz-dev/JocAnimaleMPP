import React, {useEffect, useState} from "react";

function Leaderboard() {
    const [list, setList] = useState([]);

    useEffect(() => {
        const fetchLeaderboard = async () => {
            const resp = await fetch("http://localhost:8080/api/game/leaderboard");
            if (resp.ok) {
                setList(await resp.json());
            }
        };
        fetchLeaderboard();
        const interval = setInterval(fetchLeaderboard, 4000);
        return () => clearInterval(interval);
    }, []);

    return (
        <div style={{marginTop: 40, marginBottom: 40}}>
            <h2 style={{
                textAlign: "center",
                fontWeight: 700,
                letterSpacing: 0.2,
                marginBottom: 16
            }}>
                Clasament (Leaderboard)
            </h2>
            <table
                style={{
                    borderCollapse: "collapse",
                    margin: "0 auto",
                    minWidth: 500,
                    background: "#f5f7fa",
                    borderRadius: 6,
                    overflow: "hidden",
                    boxShadow: "0 2px 8px rgba(0,0,0,0.06)"
                }}
            >
                <thead>
                <tr style={{background: "#2979ff", color: "#fff"}}>
                    <th style={{border: "1px solid #ddd", padding: 8}}>Nume</th>
                    <th style={{border: "1px solid #ddd", padding: 8}}>Data</th>
                    <th style={{border: "1px solid #ddd", padding: 8}}>Încercări</th>
                    <th style={{border: "1px solid #ddd", padding: 8}}>Rezultat</th>
                </tr>
                </thead>
                <tbody>
                {list.map((g) => (
                    <tr key={g.id} style={{background: "#fff"}}>
                        <td style={{border: "1px solid #eee", padding: 8}}>{g.nickname}</td>
                        <td style={{border: "1px solid #eee", padding: 8}}>
                            {g.endTime?.replace("T", " ").substring(0, 16) || ""}
                        </td>
                        <td style={{border: "1px solid #eee", padding: 8}}>{g.attempts}</td>
                        <td
                            style={{
                                border: "1px solid #eee",
                                padding: 8,
                                color: g.won ? "#27ae60" : "#d32f2f",
                                fontWeight: 700
                            }}
                        >
                            {g.won ? "Găsit" : "Pierdere"}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default Leaderboard;
