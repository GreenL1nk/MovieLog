document.addEventListener("DOMContentLoaded", () => {
    const rows = document.querySelectorAll(".movies-container");

    rows.forEach(row => {
        const movies = row.querySelector(".movies");
        const leftBtn = row.querySelector(".scroll-btn.left");
        const rightBtn = row.querySelector(".scroll-btn.right");

        leftBtn.addEventListener("click", () => {
            movies.scrollBy({ left: -200, behavior: "smooth" });
        });

        rightBtn.addEventListener("click", () => {
            movies.scrollBy({ left: 200, behavior: "smooth" });
        });
    });
});
