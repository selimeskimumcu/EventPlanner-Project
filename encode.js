
const diagrams = {
    arch: `graph TD
    User((User/Client)) -->|HTTPS/REST| Gateway[API Gateway - 8080]
    
    subgraph Infrastructure
        Gateway -->|Register/Fetch| Eureka[Discovery Service - 8761]
        Config[Config Server - 8888] -.->|Config| Gateway
        Config -.->|Config| UserService
        Config -.->|Config| CatalogService
        Config -.->|Config| BookingService
        Config -.->|Config| PaymentService
    end

    subgraph Business Services
        Gateway -->|/auth/**| UserService[User Service]
        Gateway -->|/events/**| CatalogService[Event Catalog Service]
        Gateway -->|/bookings/**| BookingService[Booking Service]
        Gateway -->|/payments/**| PaymentService[Payment Service]
        
        BookingService -->|Feign Client| PaymentService
        BookingService -->|Feign Client| CatalogService
    end

    subgraph Persistence
        UserService --> DB[(PostgreSQL)]
        CatalogService --> DB
        BookingService --> DB
        PaymentService --> DB
    end`,

    seq: `sequenceDiagram
    participant U as User
    participant G as API Gateway
    participant B as Booking Service
    participant E as Event Catalog
    participant P as Payment Service

    U->>G: POST /bookings (eventId, userId)
    G->>B: Route Request
    B->>E: GET /events/{id} (Check Existence)
    E-->>B: 200 OK (Event Details)
    
        B->>P: POST /payments (Process Payment)
        P-->>B: Payment Successful (Transaction ID)
        B->>B: Save Booking (CONFIRMED)
        B-->>G: 200 OK (Booking Details)
        G-->>U: Booking Confirmation`,

    erd: `erDiagram
    USERS ||--o{ BOOKINGS : makes
    EVENTS ||--o{ BOOKINGS : includes
    BOOKINGS ||--|| PAYMENTS : generates

    USERS {
        UUID id PK
        String name
        String email "Unique"
        String passwordHash
        String role "Default: USER"
    }

    EVENTS {
        UUID id PK
        String title
        String description
        String category
        DateTime dateTime
        String location
        Integer capacity
        Integer availableSeats
    }

    BOOKINGS {
        UUID id PK
        UUID userId FK
        UUID eventId FK
        DateTime bookingDate
        String status
        Boolean paymentStatus
    }

    PAYMENTS {
        UUID id PK
        UUID bookingId FK
        Double amount
        String status
        DateTime paymentDate
        String transactionId
    }`
};

function encode(state) {
  const json = JSON.stringify(state);
  const buffer = Buffer.from(json);
  return buffer.toString('base64');
}

// Mermaid Ink format: https://mermaid.ink/img/<base64_of_{code:string}>
// Actually simpler: standard base64 of the string works for 'img' endpoint usually?
// Let's check pako? standard mermaid.ink expects base64 encoded JSON STATE: {code: "..."}
// OR base64 of the raw string?
// Referring to docs: https://mermaid.ink/img/BASE64_CODE
// Where BASE64_CODE is just the graph definition base64 encoded.

console.log("ARCH_URL=https://mermaid.ink/img/" + Buffer.from(diagrams.arch).toString('base64'));
console.log("SEQ_URL=https://mermaid.ink/img/" + Buffer.from(diagrams.seq).toString('base64'));
console.log("ERD_URL=https://mermaid.ink/img/" + Buffer.from(diagrams.erd).toString('base64'));
