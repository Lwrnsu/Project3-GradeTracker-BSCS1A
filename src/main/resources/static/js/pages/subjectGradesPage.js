const displayHeaderName = document.getElementById("name");
const displayEnrolledStudents = document.getElementById("displayEnrolledStudents");
const editBtn = document.getElementById("editBtn");
const studentData = [];

const urlSubjectCode = window.location.pathname.split("/")[3];
console.log(urlSubjectCode);

function displayStudent() {
    studentData.forEach(e => {
       const container = document.createElement("div");
       container.dataset.id = e.studentID;
       container.classList.add("testRow");

       const html =
       `
            <p>${e.studentID}</p>
            <p>${e.lastName}</p>
            <p>${e.firstName}</p>
            <p>${e.middleName}</p>
            <p class="gradeSection" id=${e.studentID}>${e.grade}</p>
       `;
       container.innerHTML = html;
       displayEnrolledStudents.appendChild(container);
    });
}

document.addEventListener("DOMContentLoaded", async function() {
    const api = await fetch(`/user/get/grades/${sessionStorage.getItem("username")}/${urlSubjectCode}`);
    const result = await api.json();
    if (result.success) {
        console.log(result.message);
        result.data.studentGrade.forEach(e => {
           studentData.push(e);
        });
        displayHeaderName.innerText = `${result.data.subjectCode}: ${result.data.subjectName}`;
        displayStudent();
    } else if (!result.success) {
        console.log(result.message);
    }
});

let isEditing = false;
editBtn.addEventListener("click", async function() {
    if (!isEditing) {
        studentData.forEach(e => {
            const p = document.getElementById(`${e.studentID}`);
            const input = document.createElement("input");
            input.type = "text";
            input.value = e.grade;
            input.id = e.studentID;
            p.replaceWith(input);
        });
        editBtn.innerText = "Save Changes";
        isEditing = true;
    } else if (isEditing) {
        const updateDataArray = [];
        studentData.forEach((e) => {
            const input = document.getElementById(`${e.studentID}`);
            const p = document.createElement("p");
            const value = input.value;

            const updateDataModel = {
                studentID: e.studentID,
                subjectCode: urlSubjectCode,
                userData: sessionStorage.getItem("username"),
                grade: value
            };

            updateDataArray.push(updateDataModel);

            p.innerText = value;
            input.replaceWith(p);
        });

        const api = await fetch("/user/update/grades/student", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updateDataArray)
        });

        const result = await api.json();

        if(result.success) {
            console.log(result.message);
            window.location.reload();
        } else if (!result.success) {
            console.log(result.message);
            window.location.reload();
        }
        isEditing = false;
    }
});