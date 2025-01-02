import React from 'react';
import { GameIcons } from './GameIcons';
import { Score } from './Score';
import {Action, Result} from '@/types/game.ts';

interface GameBoardProps {
    playerName: string;
    playerMove: Action | '';
    opponentMove: Action | '';
    result: string;
    score: number;
}

export const GameBoard: React.FC<GameBoardProps> = ({
                                                        playerName,
                                                        playerMove,
                                                        opponentMove,
                                                        result,
                                                        score,
                                                    }) => {
    return (
        <div className="flex justify-between items-center mb-8">
            <div className="text-center space-y-2 w-1/3">
                <h3 className="text-xl font-semibold text-purple-600">{playerName}</h3>
                <div className="flex justify-center">
                    {playerMove && GameIcons[playerMove]}
                </div>
            </div>

            <Score score={score} result={result as Result} />

            <div className="text-center space-y-2 w-1/3">
                <h3 className="text-xl font-semibold text-blue-600">Bot</h3>
                <div className="flex justify-center">
                    {opponentMove && GameIcons[opponentMove]}
                </div>
            </div>
        </div>
    );
};