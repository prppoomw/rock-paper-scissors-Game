// src/App.tsx

import React, { useState, useEffect } from 'react';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';
import { GameHeader } from './components/game/GameHeader';
import { GameBoard } from './components/game/GameBoard';
import { GameControls } from './components/game/GameControls';
import { GameHistory } from './components/game/GameHistory';
import { gameService } from './services/gameService';
import { GameState, GameHistory as GameHistoryType, Action } from './types/game';

const App: React.FC = () => {
    const [gameState, setGameState] = useState<GameState>({
        playerMove: '',
        opponentMove: '',
        result: '',
        score: 0
    });
    const [history, setHistory] = useState<GameHistoryType[]>([]);
    const [playerName, setPlayerName] = useState('Player');

    const fetchHistory = async () => {
        try {
            const data = await gameService.getHistory(playerName);
            setHistory(data);
            await updateScore();
        } catch (error) {
            console.error('Error fetching history:', error);
        }
    };

    const updateScore = async () => {
        try {
            const score = await gameService.getScore(playerName)
            setGameState(prev => ({ ...prev, score }));
        } catch (error) {
            console.error('Error fetching history:', error);
        }
    };

    const handlePlay = async (move: Action) => {
        try {
            const data = await gameService.playMove(move, playerName);
            setGameState(prev => ({
                ...prev,
                playerMove: data.playerAction,
                opponentMove: data.botAction,
                result: data.gameResult
            }));
            fetchHistory();
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleReset = async () => {
        try {
            await gameService.resetGame(playerName);
            setGameState({
                playerMove: '',
                opponentMove: '',
                result: '',
                score: 0
            });
            setHistory([]);
        } catch (error) {
            console.error('Error during reset:', error);
        }
    };

    useEffect(() => {
        fetchHistory();
    }, []);

    return (
        <div className="w-full max-w-6xl mx-auto p-4 space-y-4">
            <GameHeader
                playerName={playerName}
                onPlayerNameChange={setPlayerName}
                onReset={handleReset}
                onConfirm={fetchHistory}
            />

            <Card className="bg-gradient-to-br from-blue-50 to-purple-50 rounded-2xl">
                <CardHeader>
                    <CardTitle className="text-3xl text-center bg-clip-text text-transparent bg-gradient-to-r from-purple-600 to-blue-600">
                        Rock Paper Scissors
                    </CardTitle>
                </CardHeader>
                <CardContent>
                    <GameBoard
                        playerName={playerName}
                        playerMove={gameState.playerMove}
                        opponentMove={gameState.opponentMove}
                        result={gameState.result}
                        score={gameState.score}
                    />
                    <GameControls onPlay={handlePlay} />
                    <GameHistory history={history} />
                </CardContent>
            </Card>
        </div>
    );
};

export default App;