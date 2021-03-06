# Work Rooms service
## Описание
Сервис позволяет в абстрактуню компанию добавлять/обновлять/удалять офисы, рабочие кабинеты, сотрудников.
Для чего используются 2 варианта эндпойнтов:
- Классический вариант Spring MVC
- Функциональный вариант Kotlin DSL

## Выставленные эндпойнты
- Офис
    - `GET /offices/all` - Получение информации обо всех доступных офисов компании
    - `GET /offices/{officeId}` - Получение информации о конкретном офисе компании по `officeId`
    - `POST /offices/add` - Добавление нового офиса в компанию
- Рабочий кабинет
    - `GET /rooms/all` - Получение информации обо всех доступных рабочих кабинетах
    - `GET /rooms/all/{officeId}` - Получение информации обо всех рабочих кабинетах в конкретном офисе по `officeId`
    - `GET /rooms/{roomNumber}` - Получен ие информации по конкретному рабочему кабинету по `roomNumber`
    - `POST /rooms/add` - Добавление нового рабочего кабинета
    - `PUT /rooms/{roomNumber}/{workersLimit}` - Обновление лимита количества сотрудников, которые могут быть размещены в рабочем кабинете
- Работники
    - `GET /workers/all` - Получение информации обо всех работниках компании
    - `GET /workers/{workerId}` - Получение информации о конкретном работнике компании по `workerId`
    - `GET /workers/active` - Получение информации обо всех работающих сотрудниках компании
    - `GET /workers/fired` - Получение информации обо всех уволенных сотрудниках компании
    - `GET /workers/remote` - Получение информации обо всех удаленных сотрудниках компании
    - `GET /workers/{officeId}/{roomNumber}` - Получение информации обо всех сотрудниках в конкретном офисе и конкретном рабочем кабинете
    - `POST /workers/add` - Добавление нового сотрудника компании
    - `PUT /workers/{workerId}/update` - Обновление информации конкретного сотрудника компании по `workerId`
    - `PUT /workers/{workerId}/fire` - Увольнение сотрудника компании по `workerId`

### P.S.
Так же эти эндпойнты выставлены с помощью Kotlin DSL. Чтобы обратиться к ним нужно добавить префикс `/handler` и использовать эндпойнты, описанные выше (например `GET /handler/offices/all`)