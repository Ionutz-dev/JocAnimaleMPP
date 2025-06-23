import React, {useState} from "react";
import Login from "./components/Login";
import GameBoard from "./components/GameBoard";
import Leaderboard from "./components/Leaderboard";
import AddConfig from "./components/AddConfig.jsx";

function App() {
    const [nickname, setNickname] = useState("");
    const [gameSession, setGameSession] = useState(null);

    return (
        <div style={{fontFamily: "sans-serif", padding: 20}}>
            <h1 style={{textAlign: "center"}}>Găsește animalul ascuns</h1>
            {!nickname ? (
                <Login setNickname={setNickname} setGameSession={setGameSession}/>
            ) : gameSession ? (
                <GameBoard
                    nickname={nickname}
                    gameSession={gameSession}
                    setGameSession={setGameSession}
                />
            ) : (
                <Login setNickname={setNickname} setGameSession={setGameSession}/>
            )}
            <Leaderboard/>
            <AddConfig />
        </div>
    );
}

export default App;
