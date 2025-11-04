#!/bin/bash
set -e

# --- Colors ---
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[1;34m'
BOLD='\033[1m'
NC='\033[0m' # Reset

# --- Configuration ---
export WINEPREFIX=~/.wine32
WINE_EXEC="setarch i386 -R wine"
RPAL_EXE="./rpal.exe"
TEST_DIR="$(dirname "$0")/../tests"
CLI_JAR="$(dirname "$0")/../../rpal-cli/target/rpal-cli-jar-with-dependencies.jar"

all_passed=true

# --- Check for RPAL executable ---
if [[ ! -f "$RPAL_EXE" ]]; then
  echo -e "\033[0;31m❌ Error: rpal.exe not found in the current directory.\033[0m"
  echo "Please ensure rpal.exe is present in: $(pwd)"
  exit 1
fi

# --- Compile Java project via Maven ---
echo -e "${BLUE}${BOLD}Building RPAL project using Maven...${NC}"

pushd "$(dirname "$0")/../.." > /dev/null  # Move to repo root
if ! mvn clean package -pl rpal-cli -DskipTests; then
  echo -e "${RED}Maven build failed. Exiting.${NC}"
  popd > /dev/null
  exit 1
fi
popd > /dev/null  # Return to previous directory

# --- Verify JAR exists ---
if [[ ! -f "$CLI_JAR" ]]; then
  echo -e "${RED}❌ Error: CLI JAR not found at $CLI_JAR${NC}"
  echo "Make sure Maven built it successfully."
  exit 1
fi

echo -e "${BLUE}Running tests from: $TEST_DIR${NC}"

for input in "$TEST_DIR"/*; do
  if [[ -f "$input" ]]; then
    testname=$(basename "$input")
    echo -e "${BOLD}=== Test: $testname ===${NC}"

    for flag in "-ast" "-st"; do
      label="AST"
      if [ "$flag" == "-st" ]; then
        label="ST"
      fi
      echo -e "${BLUE}Checking for $label...${NC}"

      rm -f wine_output.txt java_output.txt wine_output_clean.txt java_output_clean.txt

      if ! $WINE_EXEC "$RPAL_EXE" "$flag" "$input" 2>&1 | grep -v 'Read access denied' > wine_output.txt; then
        echo -e "${YELLOW}Wine execution failed for $flag on $testname${NC}"
        continue
      fi

      if ! java --enable-preview -jar "$CLI_JAR" "$input" "$flag" > java_output.txt 2>&1; then
        echo -e "${YELLOW}Java execution failed for $flag on $testname${NC}"
        continue
      fi

      sed '/^wine:/d; /^0[0-9a-fA-F]*:err:/d; /^0[0-9a-fA-F]*:warn:/d' wine_output.txt | \
      sed 's/[[:space:]]*$//' > wine_output_clean.txt
      sed 's/[[:space:]]*$//' java_output.txt > java_output_clean.txt

      if ! diff -u wine_output_clean.txt java_output_clean.txt; then
        echo -e "${RED}Output mismatch for $testname with $flag${NC}"
        echo -e "${YELLOW}----- Wine output -----${NC}"
        cat wine_output.txt
        echo -e "${YELLOW}----- Java output -----${NC}"
        cat java_output.txt
        all_passed=false 
      else
        echo -e "${GREEN}Test $testname with $flag passed!${NC}"
      fi
      echo ""
    done
  fi
done

echo -e "${BLUE}Cleaning up temporary files...${NC}"
rm -f wine_output.txt java_output.txt wine_output_clean.txt java_output_clean.txt
rm -rf "$JAVA_BIN"

if [ "$all_passed" = false ]; then
  echo -e "${RED}${BOLD}Some tests failed.${NC}"
  exit 1
else
  echo -e "${GREEN}${BOLD}All tests passed!${NC}"
  exit 0
fi
