/**
 * Game Controller
 * Manages game state and coordinates API calls
 */

console.log('[GAME] game.js loaded');

class GameController {
    constructor() {
        this.api = new MockAPI();
        this.isProcessing = false;
    }

    /**
     * Initialize game
     */
    async init() {
        try {
            const response = await this.api.getGameStatus();
            this.updateUI(response);
            this.log('Система готова. Нажмите "Pradėti žaidimą"', 'info');
        } catch (error) {
            this.log('Klaida: ' + error.message, 'error');
        }
    }

    /**
     * Start new game
     */
    async startGame() {
        if (this.isProcessing) return;
        this.isProcessing = true;

        try {
            this.log('Pradedamas naujas žaidimas...', 'info');

            // POST /api/game/start
            const startResp = await this.api.startGame({
                playerName: 'Player',
                difficulty: 'MEDIUM',
                startingCash: 1000.0,
                assetId: 1
            });

            if (startResp.status === 201) {
                this.log(`✓ Žaidimas pradėtas! ID: ${startResp.data.gameId}`, 'success');

                // GET /api/assets/1
                const assetResp = await this.api.getAssetInfo(1);
                
                // GET /api/game/status
                const statusResp = await this.api.getGameStatus({ includeHistory: true });
                
                this.updateUI(statusResp, assetResp);
                this.enableButtons();
                
                this.log(`Ėjimas 1/${statusResp.data.maxTurns} | Balansas: $${statusResp.data.player.cash.toFixed(2)}`, 'info');
            }
        } catch (error) {
            this.log('Klaida: ' + error.message, 'error');
        } finally {
            this.isProcessing = false;
        }
    }

    /**
     * Buy shares
     */
    async buy(amount) {
        if (this.isProcessing) return;
        this.isProcessing = true;

        try {
            this.log(`Perkama ${amount} akcijų...`, 'info');

            // POST /api/actions/buy?assetId=1
            const response = await this.api.buyShares(amount, 1);

            if (response.status === 200) {
                this.log(
                    `✓ Nupirkta ${amount} akcijų už $${response.data.totalCost.toFixed(2)}`,
                    'success'
                );
                await this.refreshUI();
            } else {
                this.log(`✗ ${response.data.error}`, 'error');
            }
        } catch (error) {
            this.log('Klaida: ' + error.message, 'error');
        } finally {
            this.isProcessing = false;
        }
    }

    /**
     * Sell shares
     */
    async sell(amount) {
        if (this.isProcessing) return;
        this.isProcessing = true;

        try {
            this.log(`Parduodama ${amount} akcijų...`, 'info');

            // POST /api/actions/sell
            const response = await this.api.sellShares(amount);

            if (response.status === 200) {
                this.log(
                    `✓ Parduota ${amount} akcijų už $${response.data.totalRevenue.toFixed(2)}`,
                    'success'
                );
                await this.refreshUI();
            } else {
                this.log(`✗ ${response.data.error}`, 'error');
            }
        } catch (error) {
            this.log('Klaida: ' + error.message, 'error');
        } finally {
            this.isProcessing = false;
        }
    }

    /**
     * Hold (next turn)
     */
    async hold() {
        if (this.isProcessing) return;
        this.isProcessing = true;

        try {
            this.log('Laukiama...', 'info');

            // PATCH /api/game/turn
            const response = await this.api.nextTurn();

            if (response.status === 200) {
                const data = response.data;
                const symbol = data.priceChange >= 0 ? '↑' : '↓';
                
                this.log(
                    `Ėjimas ${data.turn} | Kaina: $${data.newPrice.toFixed(2)} ${symbol} ${Math.abs(data.priceChange).toFixed(2)}%`,
                    'info'
                );

                await this.refreshUI();

                // Check game end
                if (data.isGameEnd) {
                    await this.endGame();
                }
            } else {
                this.log(`Klaida: ${response.data.error}`, 'error');
            }
        } catch (error) {
            this.log('Klaida: ' + error.message, 'error');
        } finally {
            this.isProcessing = false;
        }
    }

    /**
     * End game
     */
    async endGame() {
        const gameId = this.api.gameState.gameId;
        
        this.log('=== ŽAIDIMAS BAIGTAS ===', 'success');
        
        const finalValue = this.api.gameState.player.cash + 
                          (this.api.gameState.player.shares * this.api.gameState.asset.currentPrice);
        const profit = finalValue - 1000;
        
        this.log(
            `Galutinė vertė: $${finalValue.toFixed(2)} | Pelnas: $${profit.toFixed(2)}`,
            profit >= 0 ? 'success' : 'error'
        );

        // DELETE /api/game/{gameId}
        const response = await this.api.deleteGame(gameId);
        if (response.status === 200) {
            this.log('Žaidimas užbaigtas serverio pusėje', 'info');
        }

        this.disableButtons();
        document.getElementById('startBtn').disabled = false;
    }

    /**
     * Refresh UI
     */
    async refreshUI() {
        const statusResp = await this.api.getGameStatus({ includeHistory: true });
        const assetResp = await this.api.getAssetInfo(1);
        this.updateUI(statusResp, assetResp);
    }

    /**
     * Update UI elements
     */
    updateUI(statusResp, assetResp = null) {
        const status = statusResp.data;

        // Update status
        document.getElementById('cash').textContent = `$${status.player.cash.toFixed(2)}`;
        document.getElementById('shares').textContent = status.player.shares;
        document.getElementById('turn').textContent = `${status.currentTurn}/${status.maxTurns}`;
        document.getElementById('portfolio').textContent = `$${status.portfolioValue.toFixed(2)}`;

        // Update asset info
        if (assetResp) {
            const asset = assetResp.data;
            document.getElementById('assetName').textContent = asset.name;
            document.getElementById('price').textContent = `$${asset.currentPrice.toFixed(2)}`;
            document.getElementById('sma').textContent = `$${asset.sma.toFixed(2)}`;
        }
    }

    /**
     * Log message
     */
    log(message, type = 'info') {
        const logDiv = document.getElementById('logContent');
        const timestamp = new Date().toLocaleTimeString('lt-LT');
        const prefix = type === 'success' ? '✓' : type === 'error' ? '✗' : 'ℹ';
        
        logDiv.textContent += `\n[${timestamp}] ${prefix} ${message}`;
        logDiv.scrollTop = logDiv.scrollHeight;
    }

    /**
     * Enable action buttons
     */
    enableButtons() {
        document.getElementById('startBtn').disabled = true;
        document.getElementById('buyBtn').disabled = false;
        document.getElementById('sellBtn').disabled = false;
        document.getElementById('holdBtn').disabled = false;
    }

    /**
     * Disable action buttons
     */
    disableButtons() {
        document.getElementById('buyBtn').disabled = true;
        document.getElementById('sellBtn').disabled = true;
        document.getElementById('holdBtn').disabled = true;
    }
}

// Export to global scope
window.GameController = GameController;