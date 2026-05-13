//adding student
const overlay = document.getElementById('modalOverlay');
const addBtn  = document.getElementById('addstudent');
let editingRow = null; // tracks which row is being updated

function openModal(mode = 'add', row = null) {
  editingRow = row;
  document.getElementById('modal-title').textContent = mode === 'edit' ? 'Update Student' : 'Add Student';
  document.getElementById('modalSaveBtn').textContent = mode === 'edit' ? 'Save Changes' : 'Add Student';

  if (mode === 'edit' && row) {
    const cells = row.querySelectorAll('td');
    document.getElementById('modal-yearl').value = cells[1].textContent;
    document.getElementById('modal-lname').value = cells[2].textContent;
    document.getElementById('modal-fname').value = cells[3].textContent;
    document.getElementById('modal-mname').value = cells[4].textContent;
  }

  overlay.classList.add('active');
}

function closeModal() {
  overlay.classList.remove('active');
  editingRow = null;
  document.getElementById('modal-lname').value = '';
  document.getElementById('modal-fname').value = '';
  document.getElementById('modal-mname').value = '';
  document.getElementById('modal-yearl').value = '';
}
//updating students
function wireRowButtons(row) {
  row.querySelector('.delete').addEventListener('click', () => row.remove());
  row.querySelector('.update').addEventListener('click', () => openModal('edit', row));
}
document.querySelectorAll('tr.down').forEach(row => wireRowButtons(row));

addBtn.addEventListener('click', () => openModal('add'));
document.getElementById('modalClose').addEventListener('click', closeModal);
document.getElementById('modalCancel').addEventListener('click', closeModal);
overlay.addEventListener('click', e => { if (e.target === overlay) closeModal(); });

document.getElementById('modalSaveBtn').addEventListener('click', () => {
  const lname = document.getElementById('modal-lname').value.trim();
  const fname = document.getElementById('modal-fname').value.trim();
  const mname = document.getElementById('modal-mname').value.trim();
  const yearl = document.getElementById('modal-yearl').value.trim();

  if (!lname || !fname || !yearl) {
    alert('Last name, first name, and year level are required.');
    return;
  }

  if (editingRow) {
    //Updating row
    const cells = editingRow.querySelectorAll('td');
    cells[1].textContent = yearl;
    cells[2].textContent = lname;
    cells[3].textContent = fname;
    cells[4].textContent = mname;
  } else {
    //Adding row
    const newId = Date.now().toString().slice(-9);
    const table = document.querySelector('table');
    const newRow = document.createElement('tr');
    newRow.classList.add('down');
    newRow.innerHTML = `
      <td>${newId}</td>
      <td>${yearl}</td>
      <td>${lname}</td>
      <td>${fname}</td>
      <td>${mname}</td>
      <td>
        <button class="update">Update</button>
        <button class="delete">Delete</button>
      </td>
    `;
    table.appendChild(newRow);
    wireRowButtons(newRow);
  }
  closeModal();
});