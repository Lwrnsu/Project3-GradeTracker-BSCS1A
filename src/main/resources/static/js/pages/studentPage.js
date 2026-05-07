const addStudentForm = document.getElementById("addStudentTest");

addStudentForm.addEventListener('submit', async function(event) {
    event.preventDefault();

    const data = {
        lastName: document.getElementById("lastName").value,
        firstName: document.getElementById("firstName").value,
        middleName: document.getElementById("middleName").value,
        yearLevel: document.getElementById("yearLevel").value,
        username: sessionStorage.getItem('username')
    }

    const api = await fetch("/user/add/student", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    const result = await api.json();

    if(result.success) {
        alert(result.message);
    } else if (!result.success) {
        alert(result.message);
    } else {
        alert("Something's wrong");
    }
});