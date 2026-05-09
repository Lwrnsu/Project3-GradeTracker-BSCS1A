const displayName = document.getElementById("name");
const enrollStudentForm = document.getElementById("enrollStudentForm");
const displayEnrolledStudents = document.getElementById("displayEnrolledStudents");
const updateSubjectForm = document.getElementById("updateSubjectForm");
const deleteSubjectBtn = document.getElementById("subjectDeleteBtn");
const studentEnrolled = [];
const subjectInfo = {};

const url = window.location.pathname;
const urlSubjectCode = window.location.pathname.split("/")[3];

function showStudentEnrolled() {
    studentEnrolled.forEach((e) => {
       const student = document.createElement("div");
       student.classList.add("student");
       student.dataset.id = e.studentId;
       const html =
           `
                <p>${e.studentId}</p>
                <p>${e.lastName}</p>
                <p>${e.firstName}</p>
                <p>${e.middleName}</p>
                <p>${e.yearLevel}</p>
                <button class="deleteStudentBtn">Delete student from subject</button>
           `;
       student.innerHTML = html;
       displayEnrolledStudents.appendChild(student);
    });
}

document.addEventListener("DOMContentLoaded", async function() {
   const api = await fetch(`/user/get/subject/${sessionStorage.getItem("username")}/${urlSubjectCode}`);
   const result = await api.json();

   if (result.success) {
       result.data.studentEnrolledList.forEach((e) => {
          studentEnrolled.push(e);
       });
       console.log(studentEnrolled);
       subjectInfo.subjectCode = result.data.subjectCode;
       subjectInfo.subjectName = result.data.subjectName;
       displayName.innerText = `${result.data.subjectCode}: ${result.data.subjectName}.`;
       document.getElementById("subjectCode").value = result.data.subjectCode;
       document.getElementById("subjectName").value = result.data.subjectName;
       showStudentEnrolled();
   } else if (!result.success) {
       window.location.href = "/subject";
   }
});

enrollStudentForm.addEventListener("submit", async function(event) {
   event.preventDefault();

    const data = {
        studentID: document.getElementById("studentID").value,
        subjectCode: subjectInfo.subjectCode,
        userData: sessionStorage.getItem("username")
    }

   const api = await fetch("/user/add/subject/student", {
       method: "POST",
       headers: { "Content-Type": "application/json" },
       body: JSON.stringify(data)
   });

    const result = await api.json();

    if (result.success) {
        console.log(result.message);
        window.location.reload();
    } else if (!result.success) {
        console.log(result.message);
    }
});

displayEnrolledStudents.addEventListener("click", async function(event) {
    if (event.target.matches(".deleteStudentBtn")) {
        const parent = event.target.closest(".student");
        const getData = parent.dataset.id;
        const studentData = studentEnrolled.find(e => e.studentId === getData);

        const data = {
            studentID: studentData.studentId,
            subjectCode: subjectInfo.subjectCode,
            userData: sessionStorage.getItem("username")
        }

        const api = await fetch(`/user/delete/subject/student?studentID=${data.studentID}&subjectCode=${data.subjectCode}&userData=${data.userData}`, {
            method: "DELETE"
        });

        const result = await api.json();

        if (result.success) {
            alert(result.message);
            window.location.reload();
        } else if (!result.success) {
            alert(result.message);
        }

    }
});

updateSubjectForm.addEventListener("submit", async function(event) {
    event.preventDefault();

    const data = {
        oldSubjectCode: subjectInfo.subjectCode,
        oldSubjectName: subjectInfo.subjectName,
        userData: sessionStorage.getItem("username"),
        newSubjectCode: document.getElementById("subjectCode").value.trim(),
        newSubjectName: document.getElementById("subjectName").value.trim()
    }

    const api = await fetch("/user/update/subject", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    const result = await api.json();

    if (result.success) {
        alert(result.message);
        window.location.reload();
    } else if (!result.success) {
        alert(result.message);
    }
});

deleteSubjectBtn.addEventListener("click", async function() {
    const api = await fetch(`/user/delete/subject?userData=${sessionStorage.getItem("username")}&subjectCode=${urlSubjectCode}`, {
        method: "DELETE"
    });
    const result = await api.json();

    if(result.success) {
        alert(result.message);
        window.location.href = "/subject";
    } else if (!result.success) {
        alert(result.message);
    }
});