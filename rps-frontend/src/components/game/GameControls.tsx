import React from 'react';
import { Button } from '@/components/ui/button';
import { Action } from '@/types/game.ts';
import {GameIcons} from "@/components/game/GameIcons.tsx";

interface GameControlsProps {
    onPlay: (move: Action) => void;
}

export const GameControls: React.FC<GameControlsProps> = ({ onPlay }) => {
    const moves: Action[] = ['ROCK', 'PAPER', 'SCISSORS'];

    return (
        <div className="flex justify-center gap-4 mb-6 ">
            {moves.map((move) => (
                <div key={move} className="flex flex-col items-center">
                    {GameIcons[move]}
                    <Button
                        key={move}
                        onClick={() => onPlay(move)}
                        className="rounded-2xl capitalize transform hover:scale-110 transition-transform duration-200 w-32"
                        variant="outline"
                    >
                        {move}
                    </Button>
                </div>
            ))}
        </div>
    );
};