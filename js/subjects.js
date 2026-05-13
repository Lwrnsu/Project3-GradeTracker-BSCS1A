//adding subjects
const overlay    = document.getElementById('modalOverlay');
const addBtn     = document.getElementById('addsubject');

function openModal() { overlay.classList.add('active'); }

function closeModal() {
  overlay.classList.remove('active');
  document.getElementById('subjectid').value = '';
  document.getElementById('subjectname').value = '';
}

addBtn.addEventListener('click', openModal);
document.getElementById('modalClose').addEventListener('click', closeModal);
document.getElementById('modalCancel').addEventListener('click', closeModal);
overlay.addEventListener('click', e => { if (e.target === overlay) closeModal(); });

document.getElementById('modalSave').addEventListener('click', () => {
  const name = document.getElementById('subjectname').value.trim();

  if (!name) {
    alert('Subject Name is required.');
    return;
  }

//Adding new subject box
  const container = document.querySelector('.listofsub');
  const newCard = document.createElement('div');
  newCard.classList.add('sample1');
  newCard.innerHTML = `
    <header class="sub">${name}</header>
    <div class="sample1enter">
      <button class="entersub">Enter</button>
    </div>
  `;

  container.appendChild(newCard);

  //Enter button
  newCard.querySelector('.entersub').addEventListener('click', () => {
  //Navigation
    console.log('Entering subject:', name);
  });

  closeModal();
});
//Enter subject2
newCard.querySelector('.entersub').addEventListener('click', () => {
  const encoded = encodeURIComponent(name);
  window.location.href = `../html/grades.html?subject=${encoded}`;
});
//Header inside the structure
const params = new URLSearchParams(window.location.search);
const subject = params.get('subject');

if (subject) {
  const titleEl = document.getElementById('subject-title');
  if (titleEl) titleEl.textContent = subject;
}
newCard.innerHTML = `
  <header class="sub">${name}</header>
  <div class="sample1enter">
    <button class="entersub">Enter</button>
  </div>
`;