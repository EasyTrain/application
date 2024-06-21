# EasyTrain: Train Booking Application

## Project Description

In today's fast-paced world, travelers face numerous challenges when booking train tickets, including long queues, inconvenient booking hours, and a lack of real-time information on train availability and schedules. Our efficient and user-friendly train booking application streamlines this process, providing a convenient solution for travelers to book tickets anytime, anywhere, with up-to-date information, reducing stress and enhancing the overall travel experience.

## Getting Started

### Prerequisites

- Java 21+
- Maven
- PostgreSQL
- IntelliJ IDEA (recommended)

### Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/EasyTrain/application.git
    cd application
    ```

2. **Set up the PostgreSQL database:**
    - Install PostgreSQL.
    - Create a new database and configure it in `src/main/resources/application.properties`.

3. **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

4. **Run additional database scripts**
    - `src/main/resources/database/easeytrain_db_schema.sql`
    - `src/main/resources/database/et_station_data.sql`
    - `src/main/resources/database/ice stations.sql`

## Usage

1. **Access the application:**
    Open your web browser and navigate to `http://localhost:8081/easytrain`.

2. **Create an account and log in**
    - Select "Register".
    - Fill out the form and follow further steps.
    - Log in, select "Settings" and complete your profile.
    - Additionally you can edit your profile, change your email or delete your accout.

3. **Booking tickets:**
    - Go to "Home" or "Booking".
    - Select a station, your destination and a time. 
    - Select a train and book your tickets.
    - Pay directly or pay later under "Settings -> Journeys"

4. **Check the timetables:**
    - Go to "Timetable"
    - Select a station and a time

## Contribution
    - Gabriele Jansen
    - Calton Manhique
    - Jacques Navarro
    - Kai Prager 
