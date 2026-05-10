# GacoSplit

## Overview

GacoSplit is a web-based application designed to simplify the process of splitting group dining expenses, specifically tailored for the Gacoan restaurant use case.

In group dining scenarios, one individual typically pays the full bill upfront, leading to manual calculations and potential inaccuracies when determining each participant's share. GacoSplit addresses this problem by providing a structured and automated calculation system.

---

## Problem Statement

Group dining often introduces several challenges:

* Manual calculation of expenses is time-consuming and error-prone
* Shared items create ambiguity in cost distribution
* Social friction may occur when requesting payments from others

---

## Solution

GacoSplit provides:

* A structured input system for personal and shared orders
* Automated calculation of individual payment responsibilities
* Transparent breakdown of all expenses per participant

---

## Key Features

* Add and manage participants in a session
* Input personal orders per individual
* Support for shared items distributed evenly across participants
* Automatic calculation of total and per-person payment
* Session reset and result export (copy to clipboard)

---

## System Architecture

This project follows a **monolithic architecture** using a single Spring Boot application that serves both backend logic and frontend resources.

### Backend

* Java with Spring Boot
* RESTful API for session and calculation handling
* Embedded database (H2 / SQLite)

### Frontend

* Server-rendered HTML
* Vanilla JavaScript for interactivity
* TailwindCSS for styling

---

## Project Structure

```id="m3wz3c"
src/
 └── main/
     ├── java/
     │   └── com/gacosplit/
     │       ├── controller/
     │       ├── service/
     │       ├── model/
     │       └── repository/
     └── resources/
         ├── templates/
         ├── static/
         │   ├── css/
         │   └── js/
         └── application.properties
```

---

## Core Calculation Logic

The calculation consists of two components:

1. **Personal Total**
   Sum of all items ordered individually.

2. **Shared Portion**
   Total shared items divided equally among all participants.

Final formula:

```id="hf9d5q"
AmountOwed = PersonalTotal + (SharedTotal / NumberOfParticipants)
```

---

## Getting Started

### Prerequisites

* Java 17+
* Maven

### Installation

Clone the repository:

```id="6yb9h9"
git clone https://github.com/fjrfthrrhmn/GacoSplit.git
```

Navigate to the project directory:

```id="x3p1p3"
cd GacoSplit
```

Run the application:

```id="y4gq1j"
./mvnw spring-boot:run
```

Access the application:

```id="gdx9b1"
http://localhost:8080
```

---

## Design Principles

* Simplicity over flexibility
* Fast interaction with minimal steps
* Mobile-first usage
* Clear and transparent calculation logic

---

## Limitations

* Shared items are split equally (no custom ratio)
* No authentication or persistent user accounts
* Designed for single-session usage (MVP scope)

---

## Future Improvements

* Session sharing via link or QR code
* Transaction history
* OCR-based receipt input
* Progressive Web App (PWA) support

---

## License

This project is developed for educational and prototyping purposes.
