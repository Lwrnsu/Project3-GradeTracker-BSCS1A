const form = document.getElementById("logInForm");
const signUpForm = document.getElementById('signUpForm');

form.addEventListener('submit', async function(event) {
    event.preventDefault();

    const request = {
        username: document.getElementById("logInUser").value,
        password: document.getElementById("logInPw").value
    }

    const api = await fetch("/user/logIn", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(request)
    })

    const result = await api.json();

    if (result.success) {
        sessionStorage.setItem('isLoggedIn', 'true');
        sessionStorage.setItem('username', result.data.username);
        sessionStorage.setItem('lastName', result.data.lastName);
        sessionStorage.setItem('firstName', result.data.firstName);
        sessionStorage.setItem('middleName', result.data.middleName);
        window.location.href = "/home";
    } else if (!result.success) {
        failedNotif(result.message, api.status);
    }
});

signUpForm.addEventListener('submit', async function(event) {
    event.preventDefault();

    const pw = document.getElementById("suPw").value;
    const confirmPw = document.getElementById("suConfirmPw").value;

    if (pw === confirmPw) {
        const user = {
        lastName: document.getElementById("suLastName").value,
        firstName: document.getElementById("suFirstName").value,
        middleName: document.getElementById("suMiddleName").value || null,
        username: document.getElementById("suUsername").value,
        password: document.getElementById("suPw").value
        }

        const api = await fetch("/user/signUp", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user)
        });

        const result = await api.json();

        if (result.success) {
            signUpTrigger();
            successNotif(result.message, api.status);
        } else if (!result.success) {
            failedNotif(result.message, api.status);
        } else {
            alert("Something's wrong!");
        }
    } else {
        wrongPw();
    }
});