# Система управления парковкой

REST API на Spring Boot для управления парковочным лотом: транспортные средства, парковочные места, сессии и тарифы.

---

## Как запустить проект

### Требования
- **Java 17** (JDK)
- **Docker Desktop** (для PostgreSQL)
- **Maven** (обёртка включена — установка не нужна)

### Шаги

**1. Склонировать репозиторий**
```bash
git clone <ссылка-на-репозиторий>
cd demo
```

**2. Запустить PostgreSQL через Docker Compose**
```bash
docker compose up -d
```
Поднимается контейнер PostgreSQL 17 на порту `5432`.

**3. Запустить приложение**

Windows:
```bash
.\mvnw.cmd spring-boot:run
```

Linux / macOS:
```bash
./mvnw spring-boot:run
```

Приложение запустится на **http://localhost:8080**

> Spring Boot автоматически обнаруживает запущенные Docker Compose сервисы — ручная настройка БД не требуется.

---

## Подключение PostgreSQL

Настройки подключения находятся в [`src/main/resources/application.properties`](src/main/resources/application.properties):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/parking_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Эти значения соответствуют [`compose.yaml`](compose.yaml). Для подключения к внешней БД — измените три указанных свойства и пропустите шаг `docker compose up`.

Схема базы данных создаётся автоматически при старте (`ddl-auto=update`).

---

## Необходимые настройки

Переменные окружения не требуются. Вся конфигурация находится в `application.properties`.

| Свойство | Значение по умолчанию | Описание |
|---|---|---|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/parking_db` | JDBC URL |
| `spring.datasource.username` | `postgres` | Пользователь БД |
| `spring.datasource.password` | `postgres` | Пароль БД |
| `server.port` | `8080` | HTTP порт |

---

## Swagger UI

После запуска приложения откройте:

**http://localhost:8080/swagger-ui.html**

Все эндпоинты сгруппированы по тегам. У каждого запроса есть предзаполненный пример — нажмите **«Try it out»** для тестирования.

---

## Рекомендуемый порядок тестирования

Для полной проверки функциональности следуйте этому порядку в Swagger:

### 1. Создать тарифы (обязательно перед выездом)
`POST /api/tariffs` — создать по одному для каждого типа ТС:
- `CAR` → 100 сом/час
- `MOTORCYCLE` → 50 сом/час
- `TRUCK` → 150 сом/час

### 2. Зарегистрировать транспортное средство
`POST /api/vehicles`

### 3. Создать парковочное место
`POST /api/parking-spots`
*(тип места должен совпадать с типом ТС)*

### 4. Въезд
`POST /api/parking-sessions/check-in`
Передать **номер госномера** и **UUID парковочного места**.

### 5. Просмотр активных сессий
`GET /api/parking-sessions/active`

### 6. Выезд
`POST /api/parking-sessions/check-out/{sessionId}`
Возвращает сессию с рассчитанной стоимостью.

---

## Сводная таблица эндпоинтов

| Тег | Метод | Путь | Описание |
|---|---|---|---|
| Транспорт | POST | `/api/vehicles` | Регистрация ТС |
| Транспорт | GET | `/api/vehicles` | Список всех ТС |
| Транспорт | GET | `/api/vehicles/{id}` | По ID |
| Транспорт | GET | `/api/vehicles/by-plate` | По госномеру |
| Транспорт | PUT | `/api/vehicles/{id}` | Изменить |
| Транспорт | DELETE | `/api/vehicles/{id}` | Удалить |
| Парковочные места | POST | `/api/parking-spots` | Создать место |
| Парковочные места | GET | `/api/parking-spots` | Список всех |
| Парковочные места | GET | `/api/parking-spots/{id}` | По ID |
| Парковочные места | GET | `/api/parking-spots/available` | Свободные места |
| Парковочные места | PUT | `/api/parking-spots/{id}` | Изменить |
| Парковочные места | DELETE | `/api/parking-spots/{id}` | Удалить |
| Сессии | POST | `/api/parking-sessions/check-in` | Въезд |
| Сессии | POST | `/api/parking-sessions/check-out/{id}` | Выезд |
| Сессии | GET | `/api/parking-sessions` | Все сессии |
| Сессии | GET | `/api/parking-sessions/{id}` | По ID |
| Сессии | GET | `/api/parking-sessions/active` | Активные сессии |
| Тарифы | POST | `/api/tariffs` | Создать тариф |
| Тарифы | GET | `/api/tariffs` | Список всех |
| Тарифы | GET | `/api/tariffs/by-type` | По типу ТС |
| Тарифы | PUT | `/api/tariffs/{id}` | Изменить |
| Тарифы | DELETE | `/api/tariffs/{id}` | Удалить |