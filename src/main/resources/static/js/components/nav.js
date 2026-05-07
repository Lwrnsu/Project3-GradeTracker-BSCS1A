const navUsername = document.getElementById("username");
const navBtnToggle = document.getElementById("navToggleBtn");
const nav = document.getElementById("nav");

navUsername.innerText = sessionStorage.getItem('username');

function toggleNav() {
    nav.classList.toggle("hideNav");
    document.body.classList.toggle("fullScreen");
}

function switchTabs(tabName) {
    if (tabName === "home") window.location.href = "/home";
    if (tabName === "account") window.location.href = "/account";
}

document.addEventListener('click', (event) => {
    if(event.target.matches("#navToggleBtn") || event.target.matches("#navImg")) toggleNav();
    if(event.target.matches("#homeNav")) switchTabs("home");
    if(event.target.matches("#accountNav")) switchTabs("account");
});
