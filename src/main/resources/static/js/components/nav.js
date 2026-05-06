const navBtnToggle = document.getElementById("navToggleBtn");
const nav = document.getElementById("nav");

navBtnToggle.addEventListener('click', () => {
    nav.classList.toggle("hideNav");
    document.body.classList.toggle("fullScreen");
});