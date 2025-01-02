export type Action = 'ROCK' | 'PAPER' | 'SCISSORS';
export type Result = 'WIN' | 'LOSE' | 'TIE';

export interface GameState {
    playerMove: Action | '';
    opponentMove: Action | '';
    result: Result | '';
    score: number;
}

export interface GameHistory {
    id: number;
    playerName: string;
    playerAction: Action;
    botAction: Action;
    gameResult: Result;
    timestamp: string;
}

export interface GameResponse {
    playerName: string;
    playerAction: Action;
    botAction: Action;
    gameResult: Result;
    timestamp: string;
}