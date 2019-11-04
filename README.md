# Тестовое задание nexign

Приложение "Жизненный цикл абонента" - restfull приложение на Spring MVC.

Запросы:

1. Call - совершить звонок.  
`localhost:8080/makecall?msisdn=89053255087`

2. SMS - отправить смс
`localhost:8080/sendsms?msisdn=89090365471`

3. Проверка баланса абонента.
`localhost:8080/balance?msisdn=89090365471`

4. Пополнить баланс
`localhost:8080/replenish?msisdn=89090365471&amount=100`

Число звонков в сутки ограничено переменной callNumInADay
