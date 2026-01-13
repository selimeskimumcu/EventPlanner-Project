# Event Planner Microservices Project

## Overview
This project is a microservices-based Event Planner application deployed on Render.

## Deployment URL
**Gateway Service:** https://gateway-service-gur9.onrender.com

## Architecture
- **Discovery Service:** Eureka Server
- **Config Server:** Centralized Configuration
- **Gateway Service:** API Router & Entry Point
- **User Service:** User Management
- **Event Catalog Service:** Event Listings
- **Booking Service:** Reservation Management
- **Payment Service:** Payment Processing
- **Database:** PostgreSQL (Managed by Render)

## API Endpoints (via Gateway)
- `/user/**` -> User Service
- `/events/**` -> Event Catalog Service
- `/bookings/**` -> Booking Service
- `/payments/**` -> Payment Service
