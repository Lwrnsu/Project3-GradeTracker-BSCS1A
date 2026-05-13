//nav buttons
const routes = {
    'dash': 'dash.html',
    'students': 'students.html',
    'courses': 'subjects.html',
    'grades' : 'grades.html',
    'account': 'account.html'
};
Object.keys(routes).forEach(id => {
    const btn = document.getElementById(id);
    if (btn) {
        btn.addEventListener('click', () => {
            window.location.href = `../html/${routes[id]}`;
        });
    }
});