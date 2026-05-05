const username = document.getElementById("username");
const displayName = document.getElementById("name");
const totalStudents = document.getElementById("totalStudents");
const totalSubjects = document.getElementById("totalSubjects");

let middleName = sessionStorage.getItem('middleName');

if (middleName === "null") {
    middleName = "";
}

username.innerText = sessionStorage.getItem('username');
displayName.innerText = `Welcome! ${sessionStorage.getItem('lastName')}, ${sessionStorage.getItem('firstName')} ${middleName}`;

document.addEventListener("DOMContentLoaded", async function() {
    const api = await fetch(`/user/stats?username=${sessionStorage.getItem("username")}`);
    const result = await api.json();
    
    if (result.success) {
        totalStudents.innerText = result.data.totalStudents;
        totalSubjects.innerText = result.data.totalSubjects;
    } else if (!result.success) {
        alert(result.message);
    }
});
