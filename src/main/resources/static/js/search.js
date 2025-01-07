document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("searchInput");
    const resultsDropdown = document.getElementById("resultsDropdown");

    let debounceTimeout;
    let page = 1; // Номер текущей страницы
    let isLoading = false; // Проверка, чтобы избежать повторных запросов

    // Слушаем ввод текста
    searchInput.addEventListener("input", () => {
        clearTimeout(debounceTimeout);

        // Запускаем поиск через 500 мс после остановки ввода
        debounceTimeout = setTimeout(() => {
            const query = searchInput.value.trim();

            if (query.length > 0) {
                page = 1; // Сброс страницы при новом запросе
                fetchResults(query, page); // Загружаем новые результаты
            } else {
                resultsDropdown.classList.add("hidden");
            }
        }, 500);
    });

    // Обработчик прокрутки
    resultsDropdown.addEventListener('scroll', () => {
        // Если прокручено до конца списка
        if (resultsDropdown.scrollTop + resultsDropdown.clientHeight >= resultsDropdown.scrollHeight) {
            const query = searchInput.value.trim();
            if (query.length > 0 && !isLoading) {
                isLoading = true;
                page++; // Увеличиваем номер страницы
                fetchResults(query, page); // Загружаем дополнительные результаты
            }
        }
    });

    // Функция для загрузки результатов
    function fetchResults(query, page) {
        fetch(`/api/search?query=${encodeURIComponent(query)}&page=${page}`)
            .then((response) => response.json())
            .then((results) => {
                if (page === 1) {
                    resultsDropdown.innerHTML = ""; // Очистка для новой страницы
                }

                if (results.length > 0) {
                    resultsDropdown.classList.remove("hidden");

                    // Добавляем результаты
                    results.forEach((result) => {
                        const li = document.createElement("li");

                        // Рейтинг: IMDb > Kinopoisk > Рейтинг недоступен
                        let rating = result.rating || "N/A";

                        const movieLink = document.createElement("a");
                        movieLink.classList.add("movie-link");
                        movieLink.href = `/film/${result.filmId}`;  // Пример ссылки на страницу фильма

                        movieLink.innerHTML = `
                            <div class="poster-container">
                                <img src="${result.posterUrlPreview}" alt="${result.nameRu}">
                                <div class="rating-badge">${rating}</div>
                            </div>
                            <span>(${result.year}) ${result.nameRu}</span>
                        `;

                        li.appendChild(movieLink);
                        resultsDropdown.appendChild(li);
                    });
                } else {
                    resultsDropdown.classList.add("hidden");
                }
                isLoading = false;
            })
            .catch((error) => {
                console.error("Ошибка при поиске:", error);
                isLoading = false;
            });
    }

    // Прячем результаты при клике вне поиска
    document.addEventListener("click", (event) => {
        if (!searchInput.contains(event.target) && !resultsDropdown.contains(event.target)) {
            resultsDropdown.classList.add("hidden");
        }
    });
});
