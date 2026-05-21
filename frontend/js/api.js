/**
 * Mock API Service for Stock Trading Simulator
 * Simulates REST API with full HTTP methods, headers, and parameters
 */

console.log('[API] api.js loaded');

class MockAPI {
    constructor() {
        // Base URL (simulated)
        this.baseURL = 'https://api.stocktrading.com/v1';
        
        // Auth token (simulated)
        this.authToken = 'Bearer mock_' + Math.random().toString(36).substr(2, 9);
        
        // Game state (simulates database)
        this.gameState = {
            gameId: null,
            isStarted: false,
            currentTurn: 0,
            maxTurns: 30,
            player: {
                cash: 1000.0,
                shares: 0
            },
            asset: {
                id: 1,
                name: 'AAPL',
                type: 'STOCK',
                currentPrice: 100.0,
                priceHistory: [100.0]
            },
            volatility: 0.03
        };

        // API call log
        this.apiLog = [];
    }

    /**
     * Simulates HTTP request with delay
     */
    async _request(method, endpoint, options = {}) {
        // Network delay simulation (100-300ms)
        const delay = Math.random() * 200 + 100;
        await new Promise(resolve => setTimeout(resolve, delay));

        // Build request object
        const request = {
            timestamp: new Date().toISOString(),
            method: method,
            endpoint: endpoint,
            url: `${this.baseURL}${endpoint}`,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': this.authToken,
                'X-API-Version': '1.0',
                ...options.headers
            },
            params: options.params || null,
            body: options.body || null
        };

        // Log the call
        this.apiLog.push(request);
        console.log(`[API ${method}] ${endpoint}`, request);

        return request;
    }

    /**
     * Creates response object
     */
    _response(status, data, request) {
        return {
            status: status,
            statusText: status === 200 ? 'OK' : status === 201 ? 'Created' : 'Error',
            data: data,
            headers: {
                'Content-Type': 'application/json',
                'X-Request-ID': 'req_' + Math.random().toString(36).substr(2, 9)
            },
            request: request
        };
    }

    // ==========================================
    // API ENDPOINTS
    // ==========================================

    /**
     * GET /api/game/status
     * Query params: includeHistory, format
     */
    async getGameStatus(queryParams = {}) {
        const endpoint = '/api/game/status';
        const params = {
            includeHistory: queryParams.includeHistory || false,
            format: 'json'
        };

        const req = await this._request('GET', endpoint, { params });

        const data = {
            gameId: this.gameState.gameId,
            isStarted: this.gameState.isStarted,
            currentTurn: this.gameState.currentTurn,
            maxTurns: this.gameState.maxTurns,
            player: { ...this.gameState.player },
            portfolioValue: this._calcPortfolio(),
            priceHistory: params.includeHistory ? this.gameState.asset.priceHistory : null
        };

        return this._response(200, data, req);
    }

    /**
     * GET /api/assets/{assetId}
     * Path param: assetId
     */
    async getAssetInfo(assetId) {
        const endpoint = `/api/assets/${assetId}`;
        const req = await this._request('GET', endpoint);

        // Simulated assets database
        const assets = {
            1: { id: 1, name: 'AAPL', type: 'STOCK', sector: 'Technology' },
            2: { id: 2, name: 'BTC', type: 'CRYPTO', sector: 'Cryptocurrency' },
            3: { id: 3, name: 'GOLD', type: 'COMMODITY', sector: 'Metals' }
        };

        const asset = assets[assetId] || assets[1];
        const data = {
            ...asset,
            currentPrice: this.gameState.asset.currentPrice,
            sma: this._calcSMA(),
            lastUpdate: new Date().toISOString()
        };

        return this._response(200, data, req);
    }

    /**
     * POST /api/game/start
     * Body: playerName, difficulty, startingCash, assetId
     */
    async startGame(config = {}) {
        const endpoint = '/api/game/start';
        
        const body = {
            playerName: config.playerName || 'Player',
            difficulty: config.difficulty || 'MEDIUM',
            startingCash: config.startingCash || 1000.0,
            assetId: config.assetId || 1
        };

        const req = await this._request('POST', endpoint, {
            body,
            headers: { 'X-Player-Session': 'session_' + Date.now() }
        });

        // Reset game state
        this.gameState.gameId = 'game_' + Math.random().toString(36).substr(2, 9);
        this.gameState.isStarted = true;
        this.gameState.currentTurn = 1;
        this.gameState.player.cash = body.startingCash;
        this.gameState.player.shares = 0;
        this.gameState.asset.currentPrice = 100.0;
        this.gameState.asset.priceHistory = [100.0];

        const data = {
            gameId: this.gameState.gameId,
            message: 'Game started successfully',
            initialState: {
                cash: this.gameState.player.cash,
                shares: this.gameState.player.shares,
                currentPrice: this.gameState.asset.currentPrice,
                turn: this.gameState.currentTurn
            }
        };

        return this._response(201, data, req);
    }

    /**
     * POST /api/actions/buy
     * Query params: assetId, timestamp
     * Body: amount, priceLimit
     */
    async buyShares(amount, assetId = 1) {
        const endpoint = '/api/actions/buy';
        
        const params = {
            assetId: assetId,
            timestamp: Date.now()
        };

        const body = {
            amount: amount,
            priceLimit: null
        };

        const req = await this._request('POST', endpoint, { params, body });

        // Validation
        const totalCost = amount * this.gameState.asset.currentPrice;
        if (totalCost > this.gameState.player.cash) {
            return this._response(400, {
                error: 'Insufficient funds',
                required: totalCost.toFixed(2),
                available: this.gameState.player.cash.toFixed(2)
            }, req);
        }

        // Execute buy
        this.gameState.player.cash -= totalCost;
        this.gameState.player.shares += amount;

        const data = {
            success: true,
            action: 'BUY',
            amount: amount,
            price: this.gameState.asset.currentPrice,
            totalCost: totalCost,
            remainingCash: this.gameState.player.cash,
            totalShares: this.gameState.player.shares
        };

        return this._response(200, data, req);
    }

    /**
     * POST /api/actions/sell
     * Body: amount, assetId
     */
    async sellShares(amount) {
        const endpoint = '/api/actions/sell';
        
        const body = {
            amount: amount,
            assetId: this.gameState.asset.id
        };

        const req = await this._request('POST', endpoint, {
            body,
            headers: { 'X-Trade-Type': 'MARKET' }
        });

        // Validation
        if (amount > this.gameState.player.shares) {
            return this._response(400, {
                error: 'Insufficient shares',
                requested: amount,
                available: this.gameState.player.shares
            }, req);
        }

        // Execute sell
        const totalRevenue = amount * this.gameState.asset.currentPrice;
        this.gameState.player.cash += totalRevenue;
        this.gameState.player.shares -= amount;

        const data = {
            success: true,
            action: 'SELL',
            amount: amount,
            price: this.gameState.asset.currentPrice,
            totalRevenue: totalRevenue,
            remainingCash: this.gameState.player.cash,
            totalShares: this.gameState.player.shares
        };

        return this._response(200, data, req);
    }

    /**
     * PATCH /api/game/turn
     * Body: action, skipValidation
     */
    async nextTurn() {
        const endpoint = '/api/game/turn';
        
        const body = {
            action: 'ADVANCE',
            skipValidation: false
        };

        const req = await this._request('PATCH', endpoint, { body });

        // Validation
        if (!this.gameState.isStarted) {
            return this._response(400, {
                error: 'Game not started'
            }, req);
        }

        if (this.gameState.currentTurn >= this.gameState.maxTurns) {
            return this._response(400, {
                error: 'Game ended',
                finalTurn: this.gameState.currentTurn
            }, req);
        }

        // Generate new price (Random Walk)
        const change = (Math.random() - 0.5) * 2 * this.gameState.volatility;
        const newPrice = this.gameState.asset.currentPrice * (1 + change);
        
        this.gameState.asset.currentPrice = Math.max(10, newPrice);
        this.gameState.asset.priceHistory.push(this.gameState.asset.currentPrice);
        this.gameState.currentTurn++;

        const data = {
            turn: this.gameState.currentTurn,
            newPrice: this.gameState.asset.currentPrice,
            priceChange: change * 100,
            sma: this._calcSMA(),
            isGameEnd: this.gameState.currentTurn >= this.gameState.maxTurns
        };

        return this._response(200, data, req);
    }

    /**
     * DELETE /api/game/{gameId}
     * Path param: gameId
     */
    async deleteGame(gameId) {
        const endpoint = `/api/game/${gameId}`;
        const req = await this._request('DELETE', endpoint);

        if (this.gameState.gameId !== gameId) {
            return this._response(404, {
                error: 'Game not found'
            }, req);
        }

        const finalStats = {
            finalCash: this.gameState.player.cash,
            finalShares: this.gameState.player.shares,
            finalValue: this._calcPortfolio(),
            turnsPlayed: this.gameState.currentTurn
        };

        // Reset state
        this.gameState.isStarted = false;
        this.gameState.gameId = null;

        const data = {
            message: 'Game deleted successfully',
            finalStats: finalStats
        };

        return this._response(200, data, req);
    }

    // ==========================================
    // HELPER METHODS
    // ==========================================

    _calcPortfolio() {
        return this.gameState.player.cash + 
               (this.gameState.player.shares * this.gameState.asset.currentPrice);
    }

    _calcSMA() {
        const history = this.gameState.asset.priceHistory;
        const period = Math.min(10, history.length);
        const slice = history.slice(-period);
        return slice.reduce((a, b) => a + b, 0) / period;
    }

    getLastCall() {
        return this.apiLog[this.apiLog.length - 1] || null;
    }

    clearLog() {
        this.apiLog = [];
    }
}

// Export to global scope
window.MockAPI = MockAPI;