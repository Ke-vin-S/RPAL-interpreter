# RPAL Interpreter

A Java-based interpreter for the RPAL (Right-reference Programming Algorithmic Language) language. This project provides a full pipeline for parsing, standardizing, compiling, and interpreting RPAL code, supporting both command-line and automated test execution.

---

## Features

- **Full RPAL Language Support**: Parses, standardizes, compiles, and interprets RPAL programs.
- **Command-Line Interface**: Easily run RPAL programs from the terminal.
- **Automated Testing**: Includes a suite of test cases for validation.
- **Modular Design**: Organized into clear, maintainable packages.
- **Extensible**: Easily add new operations, language features, or optimizations.

---

## Project Structure

```
src/
	Main.java                # Entry point for the interpreter
	Makefile                 # Build instructions
	automaton/               # Finite automaton for lexical analysis
	cli/                     # Command-line interface and file loading
	compiler/                # Compiler and interpreter core logic
	cse_machine/             # CSE (Control, Stack, Environment) machine implementation
		elements/              # Control and stack element types
		io/                    # Output handling
		operations/            # Binary, unary, and two-step operations
	parser/                  # RPAL parser and AST builder
	scanner/                 # Tokenization and lexical analysis
	standardizer/            # Standardization of parse trees
	tree/                    # AST and ST node structures
	utils/                   # Utility classes
```

---

## How It Works

1. **Scanning**: The `scanner` package tokenizes the input using a finite automaton.
2. **Parsing**: The `parser` package builds an Abstract Syntax Tree (AST) from tokens.
3. **Standardization**: The `standardizer` transforms the AST into a standardized form.
4. **Compilation**: The `compiler` package compiles the standardized tree into control structures.
5. **Interpretation**: The `cse_machine` executes the control structures using a stack-based environment model.

---

## Coding Methods & Design Principles

- **Object-Oriented Design**: Each language feature and interpreter component is encapsulated in its own class or package.
- **Visitor Pattern**: Used for traversing and transforming AST and control structures.
- **Separation of Concerns**: Lexing, parsing, standardization, compilation, and execution are cleanly separated.
- **Extensibility**: New operations or language constructs can be added with minimal changes to existing code.
- **Testability**: Modular design and a comprehensive test suite ensure reliability.

---

## Getting Started


1. **Build**: Use the provided `Makefile` to compile the project.
	```sh
	cd src
	make
	```

2. **Run**: Execute the interpreter with an RPAL source file and optional flags:
	```sh
	java Main <filename> [options]
	```
	**Options:**
	- `-ast`      Print the Abstract Syntax Tree (AST) after parsing
	- `-st`       Print the Standardized Tree (ST) after standardization
	- `-control`  Print the generated control structures
	- `-cse`      Print CSE (Control, Stack, Environment) machine states during execution

	**Example:**
	```sh
	java Main program.rpal -ast -st -control
	```

	The filename must be provided as the first non-flag argument. Flags can be combined in any order after the filename.

3. **Test**: Use the scripts in `test/interpreter/` to run automated tests.

---

## Why RPAL?

RPAL is a functional programming language used in academic settings to teach language design, parsing, and interpretation. This interpreter is ideal for:
- Students learning about compilers and interpreters
- Researchers exploring language implementation
- Anyone interested in functional programming

---

## Keywords

rpal, rpal interpreter, java, functional programming, compiler, interpreter, abstract syntax tree, parser, automaton, cse machine, language implementation, academic, education

---

## License

MIT License