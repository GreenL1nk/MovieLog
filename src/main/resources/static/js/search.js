document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("searchInput");
    const resultsDropdown = document.getElementById("resultsDropdown");

    let debounceTimeout;
    let page = 1;
    let isLoading = false;

    searchInput.addEventListener("input", () => {
        clearTimeout(debounceTimeout);

        debounceTimeout = setTimeout(() => {
            const query = searchInput.value.trim();

            if (query.length > 0) {
                page = 1;
                fetchResults(query, page);
            } else {
                resultsDropdown.classList.add("hidden");
            }
        }, 500);
    });

    resultsDropdown.addEventListener('scroll', () => {
        if (resultsDropdown.scrollTop + resultsDropdown.clientHeight >= resultsDropdown.scrollHeight) {
            const query = searchInput.value.trim();
            if (query.length > 0 && !isLoading) {
                isLoading = true;
                page++;
                fetchResults(query, page);
            }
        }
    });

    function fetchResults(query, page) {
        fetch(`/api/search?query=${encodeURIComponent(query)}&page=${page}`)
            .then((response) => response.json())
            .then((results) => {
                if (page === 1) {
                    resultsDropdown.innerHTML = "";
                }

                if (results.length > 0) {
                    resultsDropdown.classList.remove("hidden");

                    results.forEach((result) => {
                        const li = document.createElement("li");

                        let rating = result.rating || "N/A";

                        const movieLink = document.createElement("a");
                        movieLink.classList.add("movie-link");
                        movieLink.href = `/film/${result.filmId}`;

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

    document.addEventListener("click", (event) => {
        if (!searchInput.contains(event.target) && !resultsDropdown.contains(event.target)) {
            resultsDropdown.classList.add("hidden");
        }
    });
});
