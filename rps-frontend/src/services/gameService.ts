import { GameHistory, GameResponse, Action } from '../types/game';

const PRODUCER_API_URL = 'http://localhost:8088/api/rps-producer';
const CONSUMER_API_URL = 'http://localhost:8089/api/rps-consumer';

export const gameService = {
    async playMove(action: Action, playerName: string): Promise<GameResponse> {
        const response = await fetch(`${PRODUCER_API_URL}/play`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ playerName, action }),
        });
        return response.json();
    },

    async getHistory(playerName: string): Promise<GameHistory[]> {
        const response = await fetch(`${CONSUMER_API_URL}/history?playerName=${playerName}`);
        return response.json();
    },

    async resetGame(playerName: string): Promise<void> {
        await fetch(`${CONSUMER_API_URL}/reset?playerName=${playerName}`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
        });
    },

    async getScore(playerName: string): Promise<number> {
        const response = await fetch(`${CONSUMER_API_URL}/score?playerName=${playerName}`);
        const data = await response.json();
        return data?.score ?? 0;
    }
};