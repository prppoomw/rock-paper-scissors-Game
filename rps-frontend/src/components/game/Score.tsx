import React from 'react';
import { Result } from '@/types/game.ts';

interface ScoreProps {
    score: number;
    result: Result | '';
}

export const Score: React.FC<ScoreProps> = ({ score, result }) => {
    const getResultColor = (result: Result | '') => {
        switch (result) {
            case 'WIN': return 'text-green-500';
            case 'LOSE': return 'text-red-500';
            case 'TIE': return 'text-yellow-500';
            default: return 'text-gray-900';
        }
    };

    return (
        <div className="text-center w-1/3">
            <div className={`text-4xl font-bold mb-4 ${getResultColor(result)}`}>
                {score}
            </div>
            <p className={`text-xl font-semibold ${getResultColor(result)}`}>
                {result}
            </p>
        </div>
    );
};