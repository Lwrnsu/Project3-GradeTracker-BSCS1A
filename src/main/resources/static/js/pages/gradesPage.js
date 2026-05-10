const displaySubjectContainer = document.getElementById("displaySubject");
const subjectData = [];

function displaySubject() {
    subjectData.forEach((e) => {
        const subject = document.createElement("div");
        subject.classList.add("subject");
        subject.dataset.id = e.subjectCode;

        const html =
            `
            <p>${e.subjectCode}</p>
            <p>${e.subjectName}</p>
            <button class="subjectBtn">Enter</button>
        `;

        subject.innerHTML = html;
        displaySubjectContainer.appendChild(subject);
    });
}

document.addEventListener("DOMContentLoaded", async function() {
    const api = await fetch(`/user/get/subject?username=${sessionStorage.getItem("username")}`);
    const result = await api.json();

    if(result.success) {
        result.data.forEach((e) => {
            subjectData.push(e);
        });
        displaySubject();
    } else if(!result.success) {
        console.log(result.message);
    }
});

document.addEventListener("click", (event) => {
   if (event.target.matches(".subjectBtn")) {
       const parent = event.target.closest(".subject");
       const getDataset = parent.dataset.id;
       window.location.href = `/grades/${sessionStorage.getItem("username")}/${getDataset}`;
   }
});

