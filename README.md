# Stock Trading Simulator

A console-based stock trading game built with Java, demonstrating OOP principles and design patterns.

## 🎮 Features

- **Turn-based Trading**: Buy, Sell, or Hold stocks over 30 turns
- **Price Simulation**: Random walk price generation with multiple strategies
- **Technical Indicators**: Simple Moving Average (SMA) calculation
- **Portfolio Tracking**: Real-time cash and shares monitoring
- **Multiple Asset Types**: Stocks, Cryptocurrencies, Commodities
- **Observer Pattern**: Real-time price change notifications
- **Comprehensive Testing**: 35+ unit and integration tests

## 🏗️ Architecture

### Design Patterns Implemented

1. **Strategy Pattern** - Pluggable price generation strategies
   - `RandomWalkStrategy` - Standard random price movements
   - `TrendingStrategy` - Price trends with occasional reversals
   - `VolatileStrategy` - High volatility with spikes

2. **Factory Pattern** - Simplified asset creation
   - `AssetFactory.createStock()` - Create stock assets
   - `AssetFactory.createCrypto()` - Create cryptocurrency assets
   - `AssetFactory.createCommodity()` - Create commodity assets

3. **Observer Pattern** - Price change notifications
   - `PriceLogger` - Logs all price changes
   - `PriceAlert` - Alerts on threshold breaches
   - `PriceStatistics` - Tracks price statistics

### Core Classes

- **Player** - Manages cash and share portfolio
- **Asset** - Represents tradable securities
- **Market** - Generates price movements
- **PriceHistory** - Stores historical prices and calculates SMA
- **Game** - Orchestrates gameplay
- **ConsoleUI** - User interface

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- JUnit 4.13.2 (for running tests)

### Running the Game
```bash
# Compile
javac -d out src/main/java/com/trading/**/*.java

# Run
java -cp out com.trading.game.Main
```

### Running Tests
```bash
# Using IntelliJ IDEA
Control + Shift + R (Mac)
Ctrl + Shift + F10 (Windows/Linux)

# Or run individual test classes
```

## 🎯 How to Play

1. **Start Game**: Run `Main.java`
2. **Each Turn**:
   - View current price and SMA(10)
   - Choose action:
     - `B` - Buy shares (specify amount)
     - `S` - Sell shares (specify amount)
     - `H` - Hold position
3. **Win Condition**: Make profit after 30 turns
4. **Lose Condition**: Bankruptcy (no cash, no shares)

## 📊 Game Strategy Tips

- **Buy Low**: When price < SMA (below average)
- **Sell High**: When price > SMA (above average)
- **Risk Management**: Don't invest all cash at once
- **Watch Trends**: Use SMA to identify trends

## 🧪 Testing

- **Unit Tests**: 32 tests covering all core classes
- **Integration Tests**: 3 tests for full game simulation
- **Test Coverage**: All major functionality covered
- **TDD**: Initial Player class built with TDD methodology

## 📁 Project Structure
```
stock-trading-simulator/
├── src/
│   ├── main/java/com/trading/
│   │   ├── model/
│   │   │   ├── Player.java
│   │   │   ├── Asset.java
│   │   │   ├── Market.java
│   │   │   ├── PriceHistory.java
│   │   │   ├── AssetFactory.java
│   │   │   ├── AssetType.java
│   │   │   ├── strategy/
│   │   │   │   ├── PriceStrategy.java
│   │   │   │   ├── RandomWalkStrategy.java
│   │   │   │   ├── TrendingStrategy.java
│   │   │   │   └── VolatileStrategy.java
│   │   │   └── observer/
│   │   │       ├── PriceObserver.java
│   │   │       ├── PriceLogger.java
│   │   │       ├── PriceAlert.java
│   │   │       └── PriceStatistics.java
│   │   └── game/
│   │       ├── Game.java
│   │       ├── GameConfig.java
│   │       ├── ConsoleUI.java
│   │       └── Main.java
│   └── test/java/com/trading/
│       ├── model/
│       │   ├── PlayerTest.java
│       │   ├── AssetTest.java
│       │   ├── MarketTest.java
│       │   ├── PriceHistoryTest.java
│       │   ├── AssetFactoryTest.java
│       │   ├── strategy/
│       │   │   └── StrategyTest.java
│       │   └── observer/
│       │       └── ObserverTest.java
│       └── game/
│           ├── GameTest.java
│           └── IntegrationTest.java
├── .gitignore
└── README.md
```

## 🔧 Configuration

Edit `GameConfig.java` to customize:
```java
MAX_TURNS = 30;           // Number of turns
STARTING_CASH = 1000.0;   // Initial cash
STARTING_PRICE = 100.0;   // Initial stock price
VOLATILITY = 0.03;        // 3% price volatility
SMA_PERIOD = 10;          // SMA calculation period
```

## 📝 Development Notes

- **Part 1**: Core functionality with TDD
- **Part 2**: Refactoring with design patterns
- **Code Quality**: DRY principle, extracted methods, final keywords
- **Git Workflow**: Feature branches, descriptive commits

## 🎓 Learning Outcomes

- Object-Oriented Programming (OOP)
- Design Patterns (Strategy, Factory, Observer)
- Test-Driven Development (TDD)
- Unit Testing with JUnit
- Git version control
- Clean code principles

## 📄 License

Educational project for OOP course.

## 👤 Author

Moliugas - Object-Oriented Programming Course Project

## 🙏 Acknowledgments

- Built as coursework demonstrating software engineering fundamentals
- Implements real-world design patterns
- Comprehensive test coverage for reliability
