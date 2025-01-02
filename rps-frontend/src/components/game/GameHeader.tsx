import React from 'react';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';

interface GameHeaderProps {
    playerName: string;
    onPlayerNameChange: (name: string) => void;
    onReset: () => void;
    onConfirm: () => void;
}

export const GameHeader: React.FC<GameHeaderProps> = ({
                                                          playerName,
                                                          onPlayerNameChange,
                                                          onReset, onConfirm,
                                                      }) => {
    return (
        <div className="flex justify-between items-center mb-6 ">
            <div className="flex items-center space-x-4">
                <Input
                    type="text"
                    placeholder="Enter your name"
                    value={playerName}
                    onChange={(e) => onPlayerNameChange(e.target.value)}
                    className="rounded-2xl border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
                />
                <Button
                    onClick={() => onConfirm()}
                    className="bg-green-600 text-white px-4 py-2 rounded-2xl shadow-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-400 transition-all duration-300"
                >
                    Change
                </Button>
            </div>
            <Button onClick={onReset} variant="destructive"
                    className="bg-red-600 text-white px-4 py-2 rounded-2xl shadow-md hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-400 transition-all duration-300"
            >
                Reset Game
            </Button>
        </div>
    );
};