const modal = document.getElementById('modal');
const notification = document.getElementById('notification');
let notifTitle = document.getElementById('notifTitle');
let notifMsg = document.getElementById('notifMsg');
let notifCode = document.getElementById('notifCode');

function signUpTrigger() {
    modal.classList.toggle("active");
}

document.addEventListener('click', (event) => {
    if (event.target.matches('#modal_trigger') || event.target.matches('#modal_return')) {
        signUpTrigger();
    }
});

function successNotif(msg, codeStatus) {
    setTimeout(() => {
        notification.classList.toggle('active');
        notification.classList.toggle('success');
        notifMsg.innerText = msg;
        notifCode.innerText = `HTTP: ${codeStatus}`;
    }, 2000);
    notification.classList.toggle('active');
    notification.classList.toggle('success');
}

function failedNotif(msg, codeStatus) {
    setTimeout(() => {
        notification.classList.toggle('active');
        notification.classList.toggle('failed');
        notifMsg.innerText = msg;
        notifCode.innerText = `HTTP: ${codeStatus}`;
    }, 2000);
    notification.classList.toggle('active');
    notification.classList.toggle('failed');
}

function wrongPw() {
    setTimeout(() => {
        notification.classList.toggle('active');
        notification.classList.toggle('failed');
        notifMsg.innerText = "Password didn't matched.";
        notifCode.innerText = '';
    }, 2000);
    notification.classList.toggle('active');
    notification.classList.toggle('failed');
}
