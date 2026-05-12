const addStudentForm = document.getElementById("addStudentTest");
const deleteStudentForm = document.getElementById("deleteStudent");
const displayStudentContainer = document.getElementById("displayStudents");
const displayUpdateStudentModal = document.getElementById("modal");
let studentData = [];
addStudentForm.addEventListener('submit', async function(event) {
    event.preventDefault();
    const data = {
        lastName: document.getElementById("lastName").value.trim(),
        firstName: document.getElementById("firstName").value.trim(),
        middleName: document.getElementById("middleName").value.trim(),
        yearLevel: document.getElementById("yearLevel").value.trim(),
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
        window.location.reload();
    } else if (!result.success) {
        alert(result.message);
    }
});

function displayStudents() {
    studentData.forEach((e) => {
        const student = document.createElement("div");
        student.classList.add("testRow");
        student.dataset.id = e.studentId;
        const html =
        `
            <p class="studentID">${e.studentId}</p>
            <p class="lastName">${e.lastName}</p>
            <p class="firstName">${e.firstName}</p>
            <p class="middleName">${e.middleName}</p>
            <p class="yearLevel">${e.yearLevel}</p>
            <div class="action_container">
                <button class="updateStudentBtn">Update</button>
                <button class="deleteStudentBtn">Delete</button>
            </div>
        `;
        student.innerHTML = html;
        displayStudentContainer.appendChild(student);
    });
}

document.addEventListener('DOMContentLoaded', async function() {
    const api = await fetch(`/user/get/student?username=${sessionStorage.getItem('username')}`);
    const result = await api.json();
    if(result.success) {
        result.data.forEach((e) => {
            studentData.push(e);
        });
        displayStudents();
    } else if (!result.success) {
        console.log(result.message);
    }
});

function displayModalContent(parent, id) {
    const student = studentData.find(student => student.studentId === id);
    const html =
    `
        <form class="updateStudentForm">
            <p id="studentName">Updating student information: ${student.lastName}, ${student.firstName} ${student.middleName}</p>
            <div class="input_container">
                <input
                    type="text"
                    value="${student.lastName}"
                    name="updateLastName"
                    id="updateLastName"
                    required
                >
                <label for="updateLastName">Last Name: </label>
            </div>
            <div class="input_container">
                <input
                    type="text"
                    value="${student.firstName}"
                    name="updateFirstName"
                    id="updateFirstName"
                    required
                >
                <label for="updateFirstName">First Name: </label>
            </div>
            <div class="input_container">
                <input
                    type="text"
                    value="${student.middleName}"
                    name="updateMiddleName"
                    id="updateMiddleName"
                >
                <label for="updateMiddleName">Middle Name: </label>
            </div>
            <button type="submit">Update</button>
        </form>
    `;
    return html;
}

displayStudentContainer.addEventListener('click', async function(event) {
    if (event.target.classList.contains("updateStudentBtn")) {
        const parent = event.target.closest(".testRow");
        const getStudentID = parent.dataset.id;
        const getStudentData = studentData.find(student => student.studentId === getStudentID);
        const modalContent = displayUpdateStudentModal.querySelector(".modal_content");
        modalContent.innerHTML = displayModalContent(parent, getStudentID);
        displayUpdateStudentModal.classList.toggle("hideModal");

        modalContent.addEventListener("submit", async function(event) {
            event.preventDefault();

            const data = {
                oldLastName: getStudentData.lastName,
                oldFirstName: getStudentData.firstName,
                oldMiddleName: getStudentData.middleName,
                newLastName: modalContent.querySelector("#updateLastName").value,
                newFirstName: modalContent.querySelector("#updateFirstName").value,
                newMiddleName: modalContent.querySelector("#updateMiddleName").value,
                username: sessionStorage.getItem('username')
            }

            const api = await fetch("/user/update/student", {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            const result = await api.json();

            if (result.success) {
                alert(result.message);
                displayUpdateStudentModal.classList.toggle("hideModal");
                window.location.reload();
            } else if (!result.success) {
                alert(result.message);
            }
        });
    }

    if (event.target.classList.contains("deleteStudentBtn")) {
        const parent = event.target.closest(".testRow");
        const data = parent.dataset.id;

        const api = await fetch(`/user/delete/student?studentID=${data}`, {
            method: "DELETE"
        });

        const result = await api.json();

        if (result.success) {
            alert(result.message);
            window.location.href = "/student";
        } else if (!result.success) {
            alert(result.message);
            window.location.href = "/student";
        }
    }
});