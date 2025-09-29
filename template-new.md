# Creating a New Template

This document provides a guide for creating a new language/framework
template that follows the established patterns in this repository.

## Overview

Each template implements a simple REST API with identical behaviour across
all languages and frameworks. The goal is to keep implementations as simple
as possible while maintaining consistency.

## API Specification

### Endpoints

#### GET /hello

- **Response**: `{"name": "World!"}` (or current stored value)
- **Status Code**: 200 OK
- **Content-Type**: application/json

#### POST /hello?name={value}

- **Parameter**: `name` as query parameter (not JSON body)
- **Response**: Empty body
- **Status Codes**:
  - 201 Created - when name is provided
  - 400 Bad Request - when name is missing or empty
- **Content-Type**: application/json (even for empty response)

## Implementation Checklist

### 1. Project Structure

Create the following directory structure:

```text
template-{language}-{framework}/
+-- README.md
+-- start.bat
+-- .gitignore             # Language-specific ignore patterns
+-- {dependency-file}      # e.g., pom.xml, package.json, requirements.txt
+-- src/                   # or app/ or similar
|   +-- Hello.{ext}        # Domain class
|   +-- Main.{ext}         # or app.{ext} - HTTP server setup
+-- test(s)/               # or spec/
    +-- HelloTest.{ext}    # or test_hello.{ext}
    +-- HelloAppTest.{ext} # or test_hello_app.{ext}
```

### 2. Domain Class (Hello)

Create a simple class with:

```pseudocode
class Hello:
    - private field: name = "World!"
    - public method: getName() -> string
    - public method: setName(name: string) -> void
```

**Key Points:**

- Initialize name to "World!" by default
- No validation needed
- Keep it simple - just a getter and setter
- Some implementations add a `nameAsJson()` helper method

### 3. HTTP Server (Main/App)

Implement the web server with:

```pseudocode
- Create single Hello instance (mutable, shared state)
- Route: GET /hello
  - Return JSON: {"name": hello.getName()}
  - Set Content-Type: application/json
- Route: POST /hello
  - Read query parameter "name"
  - If present: hello.setName(name), return 201
  - If missing/empty: return 400
  - Set Content-Type: application/json
```

**Key Points:**

- Use query parameters for POST, not JSON body
- Single shared Hello instance (not thread-safe is OK)
- Minimal error handling (only check for missing name)
- Always set Content-Type to application/json

### 4. Unit Test (HelloTest)

Create unit test for Hello class:

```pseudocode
Test: "get" or "get_name"
  - Create Hello instance
  - Assert getName() returns "World!"
```

**Naming Conventions by Language:**

- Java: `@Test void get()`
- Python: `def test_get_name()`
- Go: `func TestGetName(t *testing.T)`
- JavaScript/TypeScript: `it('get', ...)`
- PHP: `@test public function get()` or `public function testGet()`

### 5. Integration Test (HelloAppTest)

Create HTTP integration tests:

```pseudocode
Test 1: "get" or "test_get_name"
  - GET /hello
  - Assert status 200
  - Assert Content-Type is application/json
  - Assert response body: {"name": "World!"}

Test 2: "update" or "test_set_name"
  - POST /hello?name=Peter
  - Assert status 201
  - GET /hello
  - Assert response body: {"name": "Peter"}
```

**Test Framework Examples:**

- Java: JUnit 5 + REST Assured
- Python: pytest + Flask test client
- Go: testing + httptest
- TypeScript: Jest/Mocha + supertest

### 6. Start Script (start.bat)

Create a one-line batch file:

```batch
{command to run the application}
```

**Examples:**

- Maven: `mvn compile exec:java`
- Python: `python main.py`
- Node.js: `npm start` (with optional node_modules check)
- PHP: `php -S 127.0.0.1:8000 -t {public-dir}`
- Go: `go run main.go`

### 7. .gitignore File

Create a language-specific .gitignore file.

### 8. README.md

Create README with:

```markdown
# Rogue-Template

This is a {Language} project.

Uses [{Framework name and description}]({framework-url}).

To create an environment with all dependencies:
    {setup commands if needed}

The project uses [{Test Framework}]({test-framework-url}) for testing.

To run the tests:
    {test command}

To start the application:
    {start command}
```

### 9. Dependency File

Create appropriate dependency management file:

- **Java**: `pom.xml` with framework, test, and logging dependencies
- **Python**: `requirements.txt` or `environment.yml`
- **JavaScript/TypeScript**: `package.json` with scripts and dependencies
- **Go**: `go.mod` with module name and dependencies
- **PHP**: `composer.json` with framework and test dependencies

## Port Assignment

Choose an available port:

- 3000 - Clojure/Compojure (taken)
- 8080 - Go/HTTP (taken)
- 8010 - Io/Yown (taken)
- 8080 - Java/Micronaut (taken)
- 4567 - Java/Spark (taken)
- 8000 - PHP/Lumen (taken)
- 6000 - Python/FastAPI (taken)
- 5000 - Python/Flask (taken)
- 5010 - TS/Express (taken)
- Suggested: 8081, 9000

## Simplicity Guidelines

### DO

- Use minimal dependencies (framework + test library only)
- Keep state in memory (single Hello instance)
- Use simple query parameters for POST
- Return consistent JSON format
- Follow existing test naming patterns
- Create clear separation between domain and HTTP layers
- Support older language versions for compatibility
- Include comprehensive .gitignore file
- Add GitHub workflow for CI/CD

### DON'T

- Add database or file persistence
- Implement authentication or authorization
- Add complex validation beyond checking for missing name
- Use JSON body for POST request
- Add logging beyond basic startup message
- Make it thread-safe (unless the framework requires it)
- Add configuration files beyond dependency management
- Use cutting-edge language features that limit compatibility
- Forget to test with multiple language versions

## Testing Your Implementation

1. **Default State Test**:

   ```bash
   GET /hello -> {"name": "World!"}
   ```

2. **Update Test**:

   ```bash
   POST /hello?name=Alice -> 201 Created
   GET /hello -> {"name": "Alice"}
   ```

3. **Missing Parameter Test**:

   ```bash
   POST /hello -> 400 Bad Request
   ```

## Common Pitfalls to Avoid

1. **Using JSON body instead of query parameters** - Always use query params for POST
2. **Forgetting Content-Type header** - Always set to application/json
3. **Over-engineering** - Keep it as simple as possible
4. **Wrong test names** - Follow the established naming convention
5. **Missing start.bat** - Always include for consistency
6. **Complex error handling** - Only handle missing name parameter

## Validation Checklist

Before considering your template complete:

- [ ] GET /hello returns {"name": "World!"} on fresh start
- [ ] POST /hello?name=Test returns 201 and updates the name
- [ ] POST /hello without name returns 400
- [ ] All responses have Content-Type: application/json
- [ ] Unit test passes (Hello class test)
- [ ] Integration tests pass (HTTP endpoint tests)
- [ ] start.bat runs the application
- [ ] README.md includes setup, test, and run instructions
- [ ] File structure matches the pattern
- [ ] No unnecessary dependencies or complexity

## Example Implementation Order

1. Set up project with dependency file
2. Create Hello domain class
3. Write HelloTest unit test
4. Implement HTTP server (Main/App)
5. Write HelloAppTest integration tests
6. Create start.bat
7. Write README.md
8. Test everything end-to-end
9. Simplify and remove any unnecessary code

## Reference Implementations

For examples, see:

- **Simplest**: `template-python-flask` - Minimal code, clear structure
- **Well-tested**: `template-java-spark` - Good test examples
- **Modern**: `template-ts-express` - TypeScript with async/await
- **Standard library**: `template-go-http` - Using only standard library where possible

## Version Support

### Language Version Requirements

Templates should support older, stable versions to ensure broad compatibility:

- **Java**: Support Java 8+ (LTS versions: 8, 11, 17, 21)
  - Use Java 8 features only (no var, no text blocks)
  - Maven compiler source/target: 1.8
- **Python**: Support Python 3.8+
  - Avoid newer syntax (no match/case, no walrus operator in 3.8)
  - Type hints are optional
- **Go**: Support Go 1.16+
  - Use go.mod with appropriate version
  - Avoid generics (1.18+) for broader compatibility
- **Node.js**: Support Node 14+ (or current LTS)
  - Specify engines in package.json
- **PHP**: Support PHP 7.4+
  - Avoid PHP 8-only features unless necessary

## GitHub Workflow CI/CD

### Workflow File Structure

Create `.github/workflows/template-{language}-{framework}.yml`:

```yaml
name: Test template-{language}-{framework}

on:
  push:
    paths:
      - 'template-{language}-{framework}/**'
      - '.github/workflows/template-{language}-{framework}.yml'
  pull_request:
    paths:
      - 'template-{language}-{framework}/**'
      - '.github/workflows/template-{language}-{framework}.yml'

jobs:
  build:
    runs-on: ubuntu-latest

    strategy:
      matrix:
        {language}-version: [{version-list}]  # Test multiple versions
    
    steps:
    - name: Check out the repo
      uses: actions/checkout@v3

    - name: Set up {Language}
      uses: actions/setup-{language}@v3
      with:
        {language}-version: ${{ matrix.{language}-version }}
    
    - name: Cache dependencies  # Optional, for faster builds
      # Add caching configuration

    - name: Build and test
      working-directory: template-{language}-{framework}
      run: {test-command}

    - name: Smoke test
      working-directory: template-{language}-{framework}
      run: |
        {start-app-in-background} &
        sleep 1
        wget -qO ./response.json http://localhost:{port}/hello
        wget -q --method POST http://localhost:{port}/hello?name=Peter
        wget -qO ./response2.json http://localhost:{port}/hello
        pkill -f "{process-name}"
        sleep 1
        cat response.json
        cat response2.json
```
