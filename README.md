# 🧵 Custom ThreadPool Assignment – Amiseq

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Complete-green.svg)](https://github.com/your-username/amiseq-threadpool-assignment)

## 📌 Overview

This project demonstrates a **custom Java thread pool implementation** built from scratch for a technical assignment with **Amiseq**. The implementation showcases concurrent programming concepts without relying on Java's built-in `ExecutorService` or thread pool APIs.

The system consists of two independent number sequence generators producing values at random intervals, with a console output printer that displays results in **LIFO (Last-In-First-Out)** order, all managed by a custom thread pool.

---

## ✨ Key Features

- 🔧 **Custom ThreadPool**: Built entirely from scratch without using Java's `ExecutorService`
- 🔢 **Dual Number Generators**: Two independent generators (`SERIES_A` and `SERIES_B`) producing sequences at random intervals
- 📋 **LIFO Output Processing**: Console printer displays results in Last-In-First-Out order
- 🏗️ **Clean Architecture**: Modular design using interfaces and concrete implementations
- 🔒 **Thread Safety**: Implemented using `ReentrantLock` and `AtomicBoolean` for safe concurrent operations
- ⚡ **Configurable**: Easy to adjust thread pool size, generation intervals, and output timing

---

## 🏛️ Architecture

### Project Structure
```
src/
├── com/Amiseq/ThreadPoolAssignment/
│   ├── Interfaces/
│   │   ├── IThreadPool.java           # Thread pool abstraction
│   │   ├── INumberGenerator.java      # Number generator interface
│   │   ├── IPrinter.java             # Output printer interface
│   │   └── IDataCollector.java       # Data collection interface
│   └── Classes/
│       ├── WorkerThreadPool.java     # Custom thread pool implementation
│       ├── NumSequenceGenerator.java # Number sequence generator
│       ├── ConsoleOutput.java        # Console output printer
│       ├── OutputCollection.java     # Thread-safe data collector
│       └── ThreadPool.java           # Main application class
```

### Component Overview

| Component | Responsibility |
|-----------|----------------|
| `WorkerThreadPool` | Custom thread pool managing worker threads and task queue |
| `NumSequenceGenerator` | Generates number sequences at configurable intervals |
| `ConsoleOutput` | Prints collected data in LIFO order with timing windows |
| `OutputCollection` | Thread-safe collection for sharing data between components |

---

## 🖥️ Sample Output

```
=== THREAD POOL STARTED ===
Starting Number Generator: SERIES_A
Starting Number Generator: SERIES_B
Starting Console Output Printer

=== PRINT WINDOW OPENED ===
OUTPUT: [SERIES_B] Generated: 25
OUTPUT: [SERIES_A] Generated: 18
OUTPUT: [SERIES_B] Generated: 20
OUTPUT: [SERIES_A] Generated: 12
OUTPUT: [SERIES_B] Generated: 15
OUTPUT: [SERIES_A] Generated: 8
OUTPUT: [SERIES_B] Generated: 10
OUTPUT: [SERIES_A] Generated: 5
OUTPUT: [SERIES_B] Generated: 5
OUTPUT: [SERIES_A] Generated: 2
=== PRINT WINDOW CLOSED ===

Shutting down thread pool...
=== THREAD POOL TERMINATED ===
```

📄 **Full output available in**: [`AmiseqThreadPoolAssignment Output.txt`](./AmiseqThreadPoolAssignment%20Output.txt)

---

## 🚀 Getting Started

### Prerequisites
- **Java 8+** installed on your system
- Basic understanding of concurrent programming concepts

### Installation & Execution

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/amiseq-threadpool-assignment.git
   cd amiseq-threadpool-assignment
   ```

2. **Compile the source code**
   ```bash
   javac -d out src/com/Amiseq/ThreadPoolAssignment/Interfaces/*.java src/com/Amiseq/ThreadPoolAssignment/Classes/*.java
   ```

3. **Run the application**
   ```bash
   java -cp out com.Amiseq.ThreadPoolAssignment.Classes.ThreadPool
   ```

### IDE Setup
You can also import and run the project in your favorite IDE:
- **IntelliJ IDEA**: Import as existing project
- **Eclipse**: Import as Java project
- **VS Code**: Open folder and run with Java extension

---

## ⚙️ Configuration

The application can be customized by modifying the following parameters in the main class:

```java
// Thread pool configuration
private static final int THREAD_POOL_SIZE = 4;

// Generator configuration
private static final int MIN_GENERATION_INTERVAL = 1000; // ms
private static final int MAX_GENERATION_INTERVAL = 3000; // ms

// Printer configuration
private static final int PRINT_WINDOW_DURATION = 15000; // ms
private static final int PRINT_INTERVAL = 1000; // ms
```

---

## 🔧 Technical Implementation

### Thread Safety Mechanisms
- **ReentrantLock**: Ensures thread-safe access to shared data structures
- **AtomicBoolean**: Provides lock-free boolean operations for shutdown signals
- **Concurrent Collections**: Safe data sharing between producer and consumer threads

### Design Patterns Used
- **Strategy Pattern**: Interfaces allow different implementations of generators and printers
- **Producer-Consumer Pattern**: Generators produce data while printer consumes it
- **Observer Pattern**: Components communicate through shared data collections

### Key Classes

#### WorkerThreadPool
```java
public class WorkerThreadPool implements IThreadPool {
    // Custom implementation managing:
    // - Worker thread lifecycle
    // - Task queue management
    // - Graceful shutdown handling
}
```

#### NumSequenceGenerator
```java
public class NumSequenceGenerator implements INumberGenerator {
    // Generates descending number sequences:
    // - Random intervals between generations
    // - Thread-safe data submission
    // - Configurable series identification
}
```

---

## 🧪 Testing

The application has been tested on:
- ✅ **Java 8** (Oracle JDK & OpenJDK)
- ✅ **Java 11** LTS
- ✅ **Java 17** LTS
- ✅ **Java 21** LTS

### Test Scenarios Covered
- Concurrent thread execution
- Thread-safe data collection
- Proper shutdown and cleanup
- LIFO output ordering
- Random interval generation

---

## 📊 Performance Characteristics

- **Memory Efficient**: Minimal object creation during runtime
- **CPU Optimized**: Lock contention minimized through careful synchronization
- **Scalable**: Thread pool size can be adjusted based on requirements
- **Responsive**: Non-blocking operations where possible

---

## 🤝 Contributing

This project was created as a technical assignment for Amiseq. However, suggestions and improvements are welcome:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/improvement`)
3. Commit your changes (`git commit -am 'Add improvement'`)
4. Push to the branch (`git push origin feature/improvement`)
5. Create a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📞 Contact

**Mahendra Sunil Waghchaure**
- 📧 **Email**: [mahendrawaghchaure.239@gmail.com](mailto:mahendrawaghchaure.239@gmail.com)
- 📞 **Phone**: +91 9763261836

---

## 🙏 Acknowledgments

- **Amiseq** for providing this interesting technical challenge
- Java documentation and concurrent programming resources
- Open source community for inspiration and best practices

---

<div align="center">
  <strong>⭐ If you found this project interesting, please consider giving it a star! ⭐</strong>
</div>
