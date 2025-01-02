import React from 'react';
import { Button } from '@/components/ui/button';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { ScrollArea } from '@/components/ui/scroll-area';
import { Scroll } from 'lucide-react';
import { GameHistory as GameHistoryType, Result } from '../../types/game';

interface GameHistoryProps {
    history: GameHistoryType[];
}

export const GameHistory: React.FC<GameHistoryProps> = ({ history }) => {
    const getResultColor = (result: Result) => {
        switch (result) {
            case 'WIN': return 'text-green-500';
            case 'LOSE': return 'text-red-500';
            case 'TIE': return 'text-yellow-500';
            default: return 'text-gray-900';
        }
    };

    return (
        <div className="flex justify-center">
            <Dialog>
                <DialogTrigger asChild>
                    <Button
                        variant="outline"
                        className="flex gap-2 rounded-2xl"
                    >
                        <Scroll className="w-4 h-4" />
                        Show History
                    </Button>
                </DialogTrigger>
                <DialogContent className="max-w-2xl bg-white">
                    <DialogHeader>
                        <DialogTitle>Game History</DialogTitle>
                    </DialogHeader>
                    <ScrollArea className="h-[400px] w-full rounded-md border p-4">
                        {history.slice().reverse().slice(0, 10).map((game, index) => (
                            <div
                                key={index}
                                className="mb-4 p-3 rounded-lg bg-gray-100 border"
                            >
                                <p className="font-medium">
                                    {game.playerName}: {game.playerAction}
                                    <span className="mx-2">vs</span>
                                    Bot: {game.botAction}
                                </p>
                                <p className={`font-semibold ${getResultColor(game.gameResult)}`}>Result: {game.gameResult}</p>
                            </div>
                        ))}
                    </ScrollArea>
                </DialogContent>
            </Dialog>
        </div>
    );
};