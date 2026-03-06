# Ghibli App — Android Application


**VariantCode: GHIBLI-FILMS-MOD_E21_DEEP_LINK**

---

## Описание проекта

Приложение предоставляет интерфейс для просмотра каталога фильмов, персонажей и локаций студии Ghibli. Данные загружаются динамически из внешнего API.

### Основные возможности:
* **Экран списка (List):** Отображение элементов (фильмы, люди, локации) с поддержкой клиентской пагинации.
* **Экран деталей (Detail):** Загрузка подробной информации об объекте по его ID отдельным сетевым запросом.
* **Состояния (State):** Реализована обработка состояний загрузки (Loading), успешного отображения (Content) и ошибок (Error) с возможностью повторного запроса (Retry).

---

## Использованные эндпоинты API

**Base URL:** `https://ghibliapi.vercel.app/`

| Ресурс | Список (List) | Детали (Detail) |
| :--- | :--- | :--- |
| **Фильмы** | `GET /films` | `GET /films/{id}` |
| **Персонажи** | `GET /people` | `GET /people/{id}` |
| **Локации** | `GET /locations` | `GET /locations/{id}` |

---

## Скриншоты приложения

Здесь представлены основные экраны приложения:

| Главный экран | Список фильмов | Детали фильма |
|:---:|:---:|:---:|
| ![Home Screen](https://github.com/Evochkka/GhibliApp/raw/develop/screenshots/main.jpg) | ![List Screen](https://github.com/Evochkka/GhibliApp/raw/develop/screenshots/films.jpg) | ![Detail Screen](https://github.com/Evochkka/GhibliApp/raw/develop/screenshots/film_detail.jpg) |

| Экран локаций | Состояние ошибки | Индикатор загрузки |
|:---:|:---:|:---:|
| ![Locations](https://github.com/Evochkka/GhibliApp/raw/develop/screenshots/loc.jpg) | ![Error State](https://github.com/Evochkka/GhibliApp/raw/develop/screenshots/retry.jpg) | ![Loading State](https://github.com/Evochkka/GhibliApp/raw/develop/screenshots/load.jpg) |

---

## Технологический стек

* **Язык:** Kotlin + Coroutines (для асинхронности)
* **UI:** Jetpack Compose
* **Архитектура:** MVVM (Data / Domain / UI)
* **DI:** Hilt (Dagger2)
* **Сеть:** Retrofit 2 + OkHttp
* **Загрузка изображений:** Coil (с поддержкой Subcompose для отображения лоадеров)
* **Навигация:** Navigation Compose

---

## Реализация модификатора: MOD_E21_DEEP_LINK

Согласно варианту, в приложении реализована поддержка **Deep Links**.

**Логика работы:**
1. Приложение обрабатывает URI-схему `app://detail/{id}`.
2. Использован **UniversalDetailRouter** — специальный компонент, который решает проблему неопределенности типа ресурса в ссылке.
3. При открытии диплинка роутер запускает параллельные проверки (через `async/await`) к эндпоинтам фильмов, персонажей и локаций.
4. На основе успешного ответа API (HTTP 200) происходит автоматическая навигация на соответствующий экран деталей.

---

## Как запустить проект
1. Клонируйте репозиторий.
2. Откройте проект в Android Studio.
3. Дождитесь завершения синхронизации Gradle.
4. Запустите приложение на эмуляторе или реальном устройстве.
