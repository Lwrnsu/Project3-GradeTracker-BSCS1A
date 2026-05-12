const htmlStudentID = document.getElementById("userID");
const lastName = document.getElementById("lastName");
const firstName = document.getElementById("firstName");
const middleName = document.getElementById("middleName");
const editBtn = document.getElementById("editBtn");
const changePwBtn = document.getElementById("changePw");
const changePwInput = document.getElementById("changePwInput");

document.addEventListener("DOMContentLoaded", () => {

    htmlStudentID.innerText = sessionStorage.getItem("username");
    lastName.innerText = sessionStorage.getItem("lastName");
    firstName.innerText = sessionStorage.getItem("firstName");
    if (sessionStorage.getItem("middleName") === "null") {
        sessionStorage.setItem("middleName", "");
    }
    middleName.innerText = sessionStorage.getItem("middleName");

});


editBtn.addEventListener("click", () => {
    const userIDInput = document.createElement("input");
    userIDInput.type = "text";
    userIDInput.value = sessionStorage.getItem("username");
    userIDInput.required = true;
    htmlStudentID.replaceWith(userIDInput);

    const lastNameInput = document.createElement("input");
    lastNameInput.type = "text";
    lastNameInput.value = sessionStorage.getItem("lastName");
    lastNameInput.required = true;
    lastName.replaceWith(lastNameInput);

    const firstNameInput = document.createElement("input");
    firstNameInput.type = "text";
    firstNameInput.value = sessionStorage.getItem("firstName");
    firstNameInput.required = true;
    firstName.replaceWith(firstNameInput);

    const middleNameInput = document.createElement("input");
    middleNameInput.type = "text";
    middleNameInput.value = sessionStorage.getItem("middleName");
    middleName.replaceWith(middleNameInput);

    editBtn.innerText = "Save Changes";
    editBtn.addEventListener("click", async function() {
        const data = {
            oldUserID: sessionStorage.getItem("username"),
            userID: userIDInput.value,
            lastName: lastNameInput.value,
            firstName: firstNameInput.value,
            middleName: middleNameInput.value
        }

        const api = await fetch("/user/update", {
            method:  "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        const result = await api.json();

        if (result.success) {
            console.log(result.message);
        } else if (!result.success) {
            console.log(result.message)
        }

        sessionStorage.setItem("username", data.userID);
        sessionStorage.setItem("lastName", data.lastName);
        sessionStorage.setItem("firstName", data.firstName);
        sessionStorage.setItem("middleName", data.middleName);

        userIDInput.replaceWith(htmlStudentID);
        lastNameInput.replaceWith(lastName);
        firstNameInput.replaceWith(firstName);
        middleNameInput.replaceWith(middleName);
        editBtn.innerText = "Edit Account";
        window.location.reload();
    });
});

changePwBtn.addEventListener("click", () => {
    const html =
    `
        <input
            type="password"
            id="oldPw"
            placeholder="Old Password"
            required
        >
        <input
            type="password"
            id="newPw"
            placeholder="New Password"
            required
        >
        <input
            type="password"
            id="confirmNewPw"
            placeholder="Confirm Password"
            required
        >
        <button id="saveUpdateBtn">Save Password.</button>
    `;
    changePwInput.innerHTML = html;
    const saveBtn = document.getElementById("saveUpdateBtn");
    saveBtn.addEventListener("click", async function() {
        const oldPw = document.getElementById("oldPw").value;
        const newPw = document.getElementById("newPw").value;
        const confirmNewPw = document.getElementById("confirmNewPw").value;
        if (newPw === confirmNewPw) {

            const data = {
                userData: sessionStorage.getItem("username"),
                oldPw: oldPw,
                newPw: newPw
            }

            const api = await fetch("/user/update/password", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            const result = await api.json();

            if (result.success) {
                console.log("Changed Password.");
            } else if (!result.success) {
                console.log("Error in changing the password.");
            }

        } else {
            console.log("Password didn't matched.");
        }
    });
});