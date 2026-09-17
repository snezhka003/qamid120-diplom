# Дипломный проект по профессии «Инженер по тестированию»

## Тестовая документация

- [План тестирования](https://github.com/snezhka003/qamid120-diplom/blob/main/testDocumentation/Plan.md) по проверке и автоматизации приложения (`testDocumentation/Plan.md`).

- [Чек-лист](https://docs.google.com/spreadsheets/d/1xE6oUr9-SeurN3_9VY3d1skQQkNQ7AeYkwOGbOEw8Q8/edit?usp=sharing) c отметками о пройденных и не пройденных тестах (`testDocumentation/Check.xlsx`).

- [Тест-кейсы](https://docs.google.com/spreadsheets/d/1WS3Qg8ySSW5-AdegiRGk2JIqebN37PbZnpQT_xx3nts/edit?usp=sharing) для проверки приложения (`testDocumentation/Cases.xlsx`).

- [Баг-репорты](https://github.com/snezhka003/qamid120-diplom/issues) оформленные как `Issues`.

- [Allure-отчет](https://github.com/snezhka003/qamid120-diplom/blob/main/testDocumentation/allure-results.zip) с результатами прогона авто-тестов, запакованный в zip-архив (`testDocumentation/allure-results.zip`).

- [Ручной отчет о тестировании](https://github.com/snezhka003/qamid120-diplom/blob/main/testDocumentation/Result.md) с результатом сравнения времени проверки приложения по чек-листу руками и UI-тестами (`testDocumentation/Result.md`).

## Запуск авто-тестов в Android Studio
### 1. Условия для запуска авто-тестов
- **Java JDK 11:** Убедиться, что установлена JDK версии 11.
- **Android Studio:** Убедиться, что установлена программа Android Studio с настроенной файловой средой:
    * Добавлен путь до JAVA_HOME в переменные окружения.
    * Настроена переменная ANDROID_HOME, указан путь до SDK Android.
- **Эмулятор Android**: Убедиться, что в Android Studio установлен и настроен эмулятор Android с версией API 29.

### 2. Клонирование и настройка проекта
- Склонировать репозиторий проекта: `git clone https://github.com/snezhka003/qamid120-diplom`
- Открыть проект в Android Studio.
- Подождать завершения индексация и синхронизации проекта с Gradle.

### 3. Запуск авто-тестов
- В верхней части окна Android Studio, слева, выбрать вид отображения = Project.
- Развернуть структуру проекта и перейти в директорию `app/src/androidTest/java/ru.iteco.fmhandroid.ui/test`.
- Правой кнопкой мыши кликнуть на папку `test` и выбрать опцию «Run 'Tests in iteco.fmhandroid.ui'», чтобы запустить все тесты данного пакета.
  * Начнется процесс сборки и запуска тестов.
  * Прогресс выполнения будет отображаться в окне «Run».
- Для запуска отдельных тестов есть 2 варианта:
  1. Выбрать нужный тестовый класс и повторить предыдущий шаг.
  2. Открыть нужный тестовый класс:
     * слева от названия тестового класса нажать на иконку двойного знака запуска, чтобы запустить все тесты класса,
     * слева от описания отдельного теста нажать на иконку знака запуска, чтобы запустить конкретно этот тест.

### 4. Формирование Allure-отчёта
- Установить [Allure](https://allurereport.org/docs/v2/install/) на ваш ПК.
- Экспортировать результаты тестов с эмулятора/устройства в проект:
  * После выполнения всех автотестов откройте окно Device Explorer в Android Studio:
    * Первый способ (через меню): в меню Android Studio выберите View → Tool Windows → Device Explorer. Появится отдельное окно со списком подключённых устройств (в том числе эмуляторов).
    * Второй способ (быстрый): на панели инструментов в правой части окна есть кнопка Device Explorer — нажмите на неё, и окно откроется сразу.
  * В окне перейдите в директорию `/data/data/ru.iteco.fmhandroid.ui/files/allure-results`.
  * Кликните правой кнопкой мыши на папку `allure-results` и выберите опцию Save As....
  * В корне проекта создайте папку `allure-result` и сохраните в нее экспортированные шагом ранее файлы.
- Сгенерировать отчёт:
  * Перейдите в терминал и убедитесь, что находитесь в корневой директории проекта.
  * Выполните необходимую вам команду:
    ```bash
       # для быстрого анализа результатов тестирования
       # запускает временный веб-сервер, который динамически генерирует и показывает отчет на основе JSON-данных:
       allure serve
    
       # для генерации HTML-отчёта:
       allure generate allure-results -o allure-report
    
       # открывает сгенерированный HTML-отчёт в браузере:
       allure open allure-report
    ```
- Теперь можно просматривать результаты выполнения автотестов в удобном и наглядном формате Allure-отчёта.
