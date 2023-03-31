# sdet_practice

Объект тестирования:
https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager

Чек-лист:
1. Создание клиента (Customer)
2. Сортировка клиентов по имени (First Name)
3. Поиск клиента

## Case #1: Успешное добавление пользователя(выполнение перед всеми тестами)
Предусловие:
1. Открыть браузер
2. Перейти на страницу https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager

Шаги:
1. Нажать кнопку “Add Customer” в основном меню
2. Заполнить поле  First Name значением Petya
3. Заполнить поле Last Name значением Petrov
4. Заполнить поле Post Code значением 101000
5. Нажать кнопку  “Add Customer” для отправки формы

Ожидаемый результат:
Появилось всплывающее окно с надписью, начинающейся на: “Customer added successfully with customer id”

В таблице клиентов (нажать кнопку “Customers” в основном меню) появилась соответствующая запись


## Case #2: Добавление пользователя-дубликата
Предусловие:
1. Открыть браузер
2. Перейти на страницу https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager

Шаги:
1. Нажать кнопку “Add Customer” в основном меню
2. Заполнить поле  First Name значением Petya
3. Заполнить поле Last Name значением Petrov
4. Заполнить поле Post Code значением 101000
5. Нажать кнопку  “Add Customer” для отправки формы


Ожидаемый результат:
Появилось всплывающее окно с надписью: “Please check the details. Customer may be duplicate.”

## Case #3: Успешное открытие счета
Предусловие:
1. Открыть браузер
2. Перейти на страницу входа https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager

Шаги:
1. Нажать кнопку “Open Account” в основном меню
2. Выбрать аккаунт “Petya Petrov” в dropdown меню Customer
3. Выбрать аккаунт “Dollar” в dropdown меню Currency
4. Нажать кнопку  “Process” для отправки формы

Ожидаемый результат:
Появилось всплывающее окно с надписью, начинающейся на: “Account created successfully with account Number”

В таблице клиентов (нажать кнопку “Customers” в основном меню) присутствует соответствующая запись и поле Account Number заполнено

## Case #4: Сортировка клиентов по имени
Предусловие:
1. Открыть браузер
2. Перейти на страницу входа https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager

Шаги:
1. Нажать кнопку “Customers” в основном меню
2. Два раза нажать на поле “First Name”

Ожидаемый результат:
Имена в таблице отсортированы в алфавитном порядке

## Case #5: Поиск клиентов по имени
Предусловие:
1. Открыть браузер
2. Перейти на страницу входа https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager

Шаги:
1. Нажать кнопку “Customers” в основном меню
4. Ввести в строку поиска запрос “Petya”

Ожидаемый результат:
В строке поисковой выдачи присутствует строка со значением Petya
