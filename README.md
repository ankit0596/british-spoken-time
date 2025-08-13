# 🕰️ British Time API

## 📌 Overview
This Spring Boot application provides a REST API that converts digital time (in HH:mm format) into its spoken equivalent based on locale-specific conventions. The current implementation supports only British English formatting (e.g., "10:30" → "half past ten").

This project was developed as part of an interview assignment to demonstrate clean architecture, extensibility, and thoughtful design patterns in a real-world backend application.

## 🚀 Features
- Convert digital time into spoken phrases (e.g., "10:15" → "quarter past ten")
- Input validation with centralized exception handling
- Modular design using Strategy and Factory patterns
- Easily extensible to support multiple locales (e.g., German, French)
- Clear separation of concerns across layers
- REST API with structured response DTOs

## 🧠 Design Principles

### ✅ 1. Strategy Pattern
Time formatting logic is encapsulated in multiple `TimePhraseStrategy` implementations:
- **ExactHourStrategy** → "ten o'clock"
- **HalfPastStrategy** → "half past ten"
- **PastStrategy** → "twenty past ten"
- **ToStrategy** → "twenty to eleven"
- **SpecialCaseStrategy** → "midnight", "noon"

These strategies are selected dynamically based on the input time, allowing flexible and reusable formatting logic.

### ✅ 2. Abstract Factory Pattern
Each locale has its own `TimePhraseStrategyFactory` implementation (e.g., `BritishTimeStrategyFactory`) that determines which strategy to use for a given time.

This allows each locale to define its own rules for spoken time without affecting others.

### ✅ **3. Singleton Pattern**
`NumberToWordsConverter` is implemented as a thread-safe Singleton using **double-checked locking**:

- Stores mappings of numbers to their British spoken word equivalents.
- Ensures consistent, memory-optimized, and thread-safe access to conversion logic.
- Prevents unnecessary multiple object creations.

### ✅ **4. Builder Pattern**
`TimePhraseBuilder` provides a fluent API to construct spoken time phrases:

- `withMinute(String)` → Adds the minute word.
- `past()` / `to()` → Adds the correct preposition.
- `hour(String)` → Adds the hour word.
- `build()` → Produces the final phrase.

Improves readability, maintainability, and avoids messy string concatenations.

### ✅ 5. Service Factory
`TimeServiceFactory` dynamically resolves the appropriate `TimeConversionService` based on the locale.  
Example:
```java
TimeConversionService service = serviceFactory.getService("british");
```

### ✅ 6. Specialized Service Interfaces
Each locale-specific service (e.g., `BritishTimeService`) extends a common interface (`TimeConversionService`) and may define additional locale-specific methods.

### ✅ 7. Centralized Exception Handling
All exceptions are handled via a `GlobalExceptionHandler` using `@RestControllerAdvice`.  
Errors are returned as structured `Error` DTOs:
```json
{
  "error": "INVALID_TIME_FORMAT",
  "message": "Invalid time format. Expected HH:mm (e.g., 10:30)"
}
```

## 📦 Project Structure
```
src/main/java/com/example/spokentime/
├── SpokenTimeApplication.java
├── api/
│   └── BritishSpokenTimeController.java
├── dto/
│   ├── SpokenTimeResponse.java
│   └── Error.java
├── exception/
│   └── GlobalExceptionHandler.java
├── model/
│   └── LocaleType.java
├── service/
│   ├── TimeConversionService.java
│   ├── BritishTimeService.java
│   ├── BritishTimeServiceImpl.java
├── factory/
│   ├── TimeServiceFactory.java
│   ├── TimePhraseStrategyFactory.java
│   ├── BritishTimeStrategyFactory.java
├── strategy/
│   ├── TimePhraseStrategy.java
│   ├── ExactHourStrategy.java
│   ├── HalfPastStrategy.java
│   ├── PastStrategy.java
│   ├── ToStrategy.java
│   ├── SpecialCaseStrategy.java
├── util/
│   ├── NumberToWordsConverter.java
│   ├── TimePhraseBuilder.java
│   └── TimeUtils.java
```

## 📈 Extensibility

### 🌍 Add New Locale Support
1. Create a new `TimeConversionService` implementation (e.g., `GermanTimeServiceImpl`)
2. Create a corresponding `TimePhraseStrategyFactory` (e.g., `GermanStrategyFactory`)
3. Register both as Spring beans
4. Update `LocaleType` enum

### 🗣️ Add New Time Phrasing Rules
- Create a new `TimePhraseStrategy` implementation
- Update the relevant strategy factory

### 🧪 Add More Validation
- Extend `TimeParserUtil` to support more formats
- Add custom exceptions

### 📊 Add Observability
- Integrate Spring Actuator
- Add logging and tracing

## 📲 API Usage

**Endpoint:**
```
GET /api/time/spoken?time=10:30
```
**Response:**
```json
{
  "input": "10:30",
  "spokenTime": "half past ten"
}
```
**Error Response:**
```json
{
  "error": "INVALID_TIME_FORMAT",
  "message": "Invalid time format. Expected HH:mm (e.g., 10:30)"
}
```

## 🧪 Testing

Includes a basic `contextLoads()` test to verify Spring Boot startup.  
Additional **unit** and **integration tests** comprehensively cover:

- ✅ Strategy selection logic across various time phrasing styles (`to`, `past`, exact hour, etc.)
- 🏭 Factory and service resolution to ensure correct strategy instantiation
- 🌐 Controller endpoints for end-to-end request handling and response formatting
- 🧪 Edge cases and locale-specific phrasing to validate robustness

All tests are written using **JUnit**, with mock configurations and real service wiring where appropriate.  
Run tests with:

```bash
./gradlew test
```

## 📚 Future Enhancements
- Support for AM/PM and 12-hour formats
- Internationalization (i18n)
- Swagger/OpenAPI docs
- Role-based access control
- Caching
