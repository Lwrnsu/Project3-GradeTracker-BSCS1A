const form = document.getElementById("logInForm");


form.addEventListener('submit', async function(event) {
    event.preventDefault();

    const user = {
        user: document.getElementById('logInUser').value,
        pw: document.getElementById('logInPw').value
    }

    const validateCrendentials = await fetch(`/user/auth?user=${user.user}&pw=${user.pw}`);
    const res = await validateCrendentials.json();

    if (res.success === true) {
        window.location.href = "/dashboard";
    } else if (res.success === false) {
        alert("Invalid Credentials!");
    } else {
        alert("Something's wrong.");
    }
});

