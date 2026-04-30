const modal = document.getElementById('modal');

document.addEventListener('click', (event) => {
    if (event.target.matches('#modal_trigger') || event.target.matches('#modal_return')) {
        modal.classList.toggle('active');
    }
});
