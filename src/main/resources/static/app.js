function playMove(playerMove) {
    fetch('http://localhost:8080/api/rps-game/play', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            playerMove: playerMove,
        }),
    })
    .then(response => response.json())
    .then(data => {
        // Update UI with game result
        document.getElementById('player-move').textContent = `Your Move: ${playerMove}`;
        document.getElementById('opponent-move').textContent = `Opponent's Move: ${data.opponentMove}`;
        document.getElementById('result').textContent = `Result: ${data.result}`;

        // Update game history
        updateGameHistory();
    })
    .catch(error => console.error('Error:', error));
}

function updateGameHistory() {
    fetch('http://localhost:8080/api/rps-game/history')
        .then(response => response.json())
        .then(data => {
            displayGameHistory(data);
        })
        .catch(error => console.error('Error:', error));
}

function displayGameHistory(history) {
    const historyList = document.getElementById('history-list');
    historyList.innerHTML = '';

    history.forEach(game => {
        const listItem = document.createElement('li');
        listItem.textContent = `Player's Move: ${game.playerMove}, Opponent's Move: ${game.opponentMove}, Result: ${game.result}`;
        historyList.appendChild(listItem);
    });
}

// Fetch initial game history when the page loads
document.addEventListener('DOMContentLoaded', function () {
    updateGameHistory();
});
