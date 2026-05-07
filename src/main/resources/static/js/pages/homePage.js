const displayName = document.getElementById("name");
const totalStudents = document.getElementById("totalStudents");
const totalSubjects = document.getElementById("totalSubjects");
const totalPassing = document.getElementById("totalPassing");
const totalFailing = document.getElementById("totalFailing");

let middleName = sessionStorage.getItem('middleName');

if (middleName === "null") {
    middleName = "";
}

displayName.innerText = `Welcome! ${sessionStorage.getItem('lastName')}, ${sessionStorage.getItem('firstName')} ${middleName}`;

document.addEventListener("DOMContentLoaded", async function() {
    const api = await fetch(`/user/stats?username=${sessionStorage.getItem("username")}`);
    const result = await api.json();
    
    if (result.success) {
        totalStudents.innerText = result.data.totalStudents;
        totalSubjects.innerText = result.data.totalSubjects;
        totalPassing.innerText = result.data.totalPassing;
        totalFailing.innerText = result.data.totalFailing;
    } else if (!result.success) {
        alert(result.message);
    }
});
