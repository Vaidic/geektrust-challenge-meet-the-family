# Solution for [Geektrust Backend Challenge - Meet The Family](https://www.geektrust.in/coding-problem/backend/family)

[![gradle-coverage](https://github.com/vaidic/geektrust-challenge-meet-the-family/actions/workflows/gradle-coverage.yml/badge.svg)](https://github.com/Vaidic/geektrust-challenge-meet-the-family/actions/workflows/gradle-coverage.yml)
[![codecov](https://codecov.io/gh/Vaidic/geektrust-challenge-meet-the-family/branch/main/graph/badge.svg?token=JG7TPKWSQF)](https://codecov.io/gh/Vaidic/geektrust-challenge-meet-the-family)
[![CodeFactor](https://www.codefactor.io/repository/github/vaidic/geektrust-challenge-meet-the-family/badge/main)](https://www.codefactor.io/repository/github/vaidic/geektrust-challenge-meet-the-family/overview/main)
[![DeepSource](https://deepsource.io/gh/Vaidic/geektrust-challenge-meet-the-family.svg/?label=active+issues&show_trend=true&token=Dfz5PcRjXpGyTVzeQEAaW5DI)](https://deepsource.io/gh/Vaidic/geektrust-challenge-meet-the-family/?ref=repository-badge) \
[![License](https://img.shields.io/github/license/Vaidic/geektrust-challenge-meet-the-family?style=plastic)](LICENSE)
![Java](https://img.shields.io/badge/OpenJDK-11-red) \
[![commits](https://badgen.net/github/commits/vaidic/geektrust-challenge-meet-the-family/main)](https://github.com/Vaidic/geektrust-challenge-meet-the-family/commits/main)
[![last-commit](https://badgen.net/github/last-commit/vaidic/geektrust-challenge-meet-the-family/main)](https://github.com/Vaidic/geektrust-challenge-meet-the-family/commits/main)
[![releases](https://badgen.net/github/release/Vaidic/geektrust-challenge-meet-the-family)](https://github.com/Vaidic/geektrust-challenge-meet-the-family/releases)

## Problem Statement

We need to create a pre-populated family tree, that supports -

1. Adding new Members to tree through `Mothers`
2. Determining all the people who hold a given relation with a member in the Family Tree.

## Assumptions

### From Problem Statement

1. Names are Unique.
2. New Members are added through `Mothers`.

### Additional Assumptions Made

1. Only two Genders (Male & Female) are supported.
2. A couple has a male & a female member.
3. Names are case-sensitive.
4. Family Tree is build left-to right in DFS order.

## Running the project

1. Download the [latest geektrust.jar]()
2. Execute the following command replacing _<path-to-inputfile>_ with the path for input file

```shell
java -jar geektrust.jar <path-to-inputfile>
```

**OR**

1. Download the source code.
2. Build the project using -

```shell
./gradlew clean build
```

3. Use the generated `jar` from `build/lib` folder and execute -

```shell
java -jar geektrust.jar <path-to-inputfile>
```


