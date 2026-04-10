JAVA ?= java
JAVAC ?= javac

SRC_DIR := src
BUILD_DIR := target/classes
MAIN_CLASS := jeuDeLaVie.src.JeuDeLaVie

SOURCES := $(shell find $(SRC_DIR) -type f -name '*.java')

.PHONY: all build run clean

all: build

build:
	@mkdir -p $(BUILD_DIR)
	$(JAVAC) -encoding UTF-8 -d $(BUILD_DIR) $(SOURCES)

run: build
	$(JAVA) -cp $(BUILD_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(BUILD_DIR)