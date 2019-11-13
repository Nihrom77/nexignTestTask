# Тестовое задание nexign

Приложение "Жизненный цикл абонента" - restfull приложение на Spring MVC.
На странице `localhost:8080` отображаются все абоненты и доступные операции.

### Сборка и запуск приложения:
Собрать приложение при помощи команды  `mvn clean install`

Запустить командой `java -jar target/phonesubscriber-0.0.1-SNAPSHOT.jar`

### Запросы:

1. Информация об абоненте - баланс, статус.
`http://localhost:8080/subscriber?msisdn=890955533535`

2. Call - совершить звонок.  
`localhost:8080//makecall?msisdn=890955533535`

3. SMS - отправить смс
`localhost:8080/sendsms?msisdn=890955533535`

4. Пополнить баланс. Выполняется в два этапа.
На странице `localhost:8080/replenishBalance?msisdn=890955533535` присутствует форма для ввода телефона и суммы пополнения.
А post запросом к `/replenishBalance` баланс пополняется.

Число звонков в сутки ограничено переменной maxCallNumInADay

Обратная связь от нашего технического эксперта:

Минусы:
- Нет тестов, добавлен только каркас

- (Исправлено) После пополнения баланса клиент остался в статусе BLOCKED

- Помещение начальных данных в БД зашито прямо в коде

- (Исправлено) Для статуса использовались две строковые константы, вместо Enum-а

- Подключил ломбок, но не использовал почему-то

- В класс Price разместил не связанные сущности (цена на звонки и смски), нарушение принципа SOLID

- В SubscriberRestController возвращается константа "{ \"error\": \"not found\"}"...

- (Исправлено)int b = s.getBalance();   - имена переменных не говорящие

- Даже если произошла ошибка, то REST возвращается код 200

- Осуществление звонка и отправка sms почему-то реализованы как GET-методы?

- Не увидел транзакций. Т.е. возможны случаи, когда звонок будет сохранен, а subscriber не обновлен.

Плюсы:
+ Дополнительно сделан простенький GUI, мелочь, но приятно

+ Классы откомментированы 
