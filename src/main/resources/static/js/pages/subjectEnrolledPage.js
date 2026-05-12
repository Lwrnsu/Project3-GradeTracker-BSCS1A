const displayName = document.getElementById("name");
const enrollStudentForm = document.getElementById("enrollStudentForm");
const displayEnrolledStudents = document.getElementById("displayEnrolledStudents");
const updateSubjectForm = document.getElementById("updateSubjectForm");
const deleteSubjectBtn = document.getElementById("subjectDeleteBtn");
const studentList = [];
const subjectInfo = {};

const urlSubjectCode = window.location.pathname.split("/")[3];

function showStudentEnrolled() {
    studentList.forEach(async (e) => {
       const student = document.createElement("div");
       const button = document.createElement("button");

        const data = {
            studentID: e.studentId,
            subjectCode: urlSubjectCode,
            userData: sessionStorage.getItem("username")
        }

       const api = await fetch(`/user/get/isEnrolled?userData=${data.userData}&studentID=${data.studentID}&subjectCode=${data.subjectCode}`);
       student.classList.add("student");
       student.dataset.id = e.studentId;

       const result = await api.json();

       if (result.success) {
           button.classList.add("unEnrollBtn");
           button.innerText = "Un-Enroll";
       } else if (!result.success) {
           button.classList.add("enrollBtn");
           button.innerText = "Enroll";
       }

       const html =
           `
                <p>${e.studentId}</p>
                <p>${e.lastName}</p>
                <p>${e.firstName}</p>
                <p>${e.middleName}</p>
                <p>${e.yearLevel}</p>
           `;

       student.innerHTML = html;
       student.appendChild(button);
       displayEnrolledStudents.appendChild(student);
    });
}

document.addEventListener("DOMContentLoaded", async function() {
   const api = await fetch(`/user/get/student?username=${sessionStorage.getItem("username")}`);
   const result = await api.json();

   const api_2 = await fetch(`/user/get/subject/name/${sessionStorage.getItem("username")}/${urlSubjectCode}`);
   const result_2 = await api_2.json();
   if (result.success && result_2.success) {
       result.data.forEach((e) => {
          studentList.push(e);
       });
       console.log(studentList);
       subjectInfo.subjectCode = urlSubjectCode;
       subjectInfo.subjectName = result_2.data;
       displayName.innerText = `${urlSubjectCode}: ${result_2.data}.`;
       document.getElementById("subjectCode").value = subjectInfo.subjectCode;
       document.getElementById("subjectName").value = subjectInfo.subjectName;
       showStudentEnrolled();
   } else if (!result.success && !result_2.success) {
       window.location.href = "/subject";
   }
});

displayEnrolledStudents.addEventListener("click", async function(event) {
    if (event.target.matches(".deleteStudentBtn")) {
        const parent = event.target.closest(".student");
        const getData = parent.dataset.id;
        const studentData = studentList.find(e => e.studentId === getData);

        const data = {
            studentID: studentData.studentId,
            subjectCode: subjectInfo.subjectCode,
            userData: sessionStorage.getItem("username")
        }

        const api = await fetch(`/user/delete/subject/student?studentID=${data.studentID}&subjectCode=${urlSubjectCode}&userData=${data.userData}`, {
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
    if (event.target.matches(".enrollBtn")) {
        const parent = event.target.closest(".student");
        const value = parent.dataset.id;

        const data = {
            studentID: value,
            subjectCode: urlSubjectCode,
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
    }
    if (event.target.matches(".unEnrollBtn")) {
        const parent = event.target.closest(".student");
        const value = parent.dataset.id;

        const api = await fetch(`/user/delete/subject/student?studentID=${value}&subjectCode=${subjectInfo.subjectCode}&userData=${sessionStorage.getItem("username")}`, {
            method: "DELETE"
        });

        const result = await api.json();

        if (result.success) {
            window.location.reload();
        } else if (!result.success) {
            console.log(result.message);
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