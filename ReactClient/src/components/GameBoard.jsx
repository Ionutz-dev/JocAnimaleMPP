import React, {useState} from "react";

const rows = [1, 2, 3];
const cols = [1, 2, 3, 4];

function GameBoard({nickname, gameSession, setGameSession}) {
    const [tries, setTries] = useState([]);
    const [hint, setHint] = useState(null);
    const [finished, setFinished] = useState(false);

    const handleCellClick = async (row, col) => {
        if (finished) return;

        const resp = await fetch(
            `http://localhost:8080/api/game/guess?gameSessionId=${gameSession.id}&row=${row}&col=${col}`,
            {method: "POST"}
        );
        if (resp.ok) {
            const result = await resp.json();
            setTries([...tries, {row, col, result}]);
            setHint(result);
            if (result.result === "HIT" || tries.length === 2) {
                setFinished(true);
                setGameSession({...gameSession, won: result.result === "HIT"});
            }
        }
    };

    const isCellTried = (row, col) => tries.find((t) => t.row === row && t.col === col);

    const isHitCell = (row, col) => {
        const t = isCellTried(row, col);
        return t && t.result.result === "HIT";
    };

    return (
        <div style={{marginTop: 30}}>
            <div style={{marginBottom: 15, fontSize: 18}}>
                Salut, <b>{nickname}</b>! Alege o poziție pe tablă (maxim 3 încercări):
            </div>
            <table
                style={{
                    margin: "0 auto",
                    borderCollapse: "collapse",
                    marginBottom: 20,
                }}
            >
                <tbody>
                {rows.map((r) => (
                    <tr key={r}>
                        {cols.map((c) => (
                            <td
                                key={c}
                                onClick={() => handleCellClick(r, c)}
                                style={{
                                    border: "1px solid gray",
                                    width: 90,
                                    height: 90,
                                    textAlign: "center",
                                    fontSize: 22,
                                    cursor: finished ? "not-allowed" : "pointer",
                                    background: isCellTried(r, c) ? "#e0e0e0" : "#fff",
                                    verticalAlign: "middle",
                                }}
                            >
                                {isHitCell(r, c)
                                    ? (
                                        hint && hint.imageUrl
                                            ? <img
                                                src={hint.imageUrl}
                                                alt={hint.animal}
                                                style={{maxWidth: 80, maxHeight: 80, display: "block", margin: "0 auto"}}
                                            />
                                            : ""
                                    )
                                    : (isCellTried(r, c) ? <span style={{color: "#d32f2f"}}>✗</span> : "")
                                }
                            </td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
            {hint && (
                <div style={{fontWeight: "bold", fontSize: 18, marginTop: 10}}>
                    {hint.result === "HIT"
                        ? "Felicitări! Ai găsit animalul."
                        : `Hint: Uită-te spre ${hint.result}`}
                </div>
            )}
            {finished && hint && hint.result === "HIT" && (
                <div style={{color: "green", fontWeight: "bold", marginTop: 16}}>
                    Joc încheiat. Animal: <span style={{fontWeight: 600}}>{hint.animal}</span>
                </div>
            )}
            {finished && hint && hint.result !== "HIT" && (
                <div style={{color: "#d32f2f", fontWeight: "bold", marginTop: 16}}>
                    Joc încheiat. Nu ai găsit animalul.
                </div>
            )}
            <button
                style={{marginTop: 15}}
                onClick={() => window.location.reload()}
            >
                Reîncepe jocul
            </button>
        </div>
    );
}

export default GameBoard;
