// Obtener todos los empleados
function fetchEmployees() {
    fetch('http://localhost:8080/employees/getall')
        .then(response => response.json())
        .then(data => {
            let tableBody = document.getElementById('employeeTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // Limpiar la tabla antes de llenarla

            data.forEach(employee => {
                let row = tableBody.insertRow();
                row.innerHTML = `
                    <td>${employee.id}</td>
                    <td><img src="${employee.foto}" alt="Foto del empleado" class="card-img-top"></td>
                    <td>${employee.nombre}</td>
                    <td>${employee.contrasena}</td>
                    <td>${employee.cargo}</td>
                    <td>${employee.estatus}</td>
                    <td>
                        <button onclick="editEmployee(${employee.id})">Editar</button>
                        <button onclick="deleteEmployee(${employee.id})">Eliminar</button>
                    </td>
                `;
            });
        })
        .catch(error => console.error('Error al obtener los empleados:', error));
}

// Buscar en la tabla
function searchTable() {
    let searchInput = document.getElementById('searchInput').value.toLowerCase();
    let rows = document.getElementById('employeeTable').getElementsByTagName('tbody')[0].rows;

    for (let row of rows) {
        let cells = row.getElementsByTagName('td');
        let match = false;
        
        for (let cell of cells) {
            if (cell.textContent.toLowerCase().includes(searchInput)) {
                match = true;
                break;
            }
        }
        row.style.display = match ? '' : 'none';
    }
}

// Abrir el formulario de nuevo empleado
function openEmployeeForm() {
    // Mostrar el formulario de inserción
    let employeeForm = document.getElementById('employeeForm');
    employeeForm.style.display = 'block';
}

// Insertar un nuevo empleado
function insertEmployee() {
    let formData = new FormData(document.getElementById('employeeForm'));
    
    fetch('http://localhost:8080/employees/insert', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Empleado agregado correctamente');
            fetchEmployees(); // Actualizar la lista de empleados
        } else {
            alert('Error al agregar empleado');
        }
    })
    .catch(error => console.error('Error al insertar empleado:', error));
}

// Editar un empleado
function editEmployee(id) {
    fetch(`http://localhost:8080/employees/get/${id}`)
        .then(response => response.json())
        .then(employee => {
            // Llenar el formulario con los datos del empleado
            document.getElementById('employeeId').value = employee.id;
            document.getElementById('employeeNombre').value = employee.nombre;
            document.getElementById('employeeCargo').value = employee.cargo;
            document.getElementById('employeeEstatus').value = employee.estatus;
            document.getElementById('employeeForm').style.display = 'block';
        })
        .catch(error => console.error('Error al obtener el empleado:', error));
}

// Actualizar empleado
function updateEmployee() {
    let formData = new FormData(document.getElementById('employeeForm'));
    
    fetch('http://localhost:8080/employees/update', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Empleado actualizado correctamente');
            fetchEmployees(); // Actualizar la lista de empleados
        } else {
            alert('Error al actualizar empleado');
        }
    })
    .catch(error => console.error('Error al actualizar empleado:', error));
}

// Eliminar un empleado
function deleteEmployee(id) {
    fetch(`http://localhost:8080/employees/delete/${id}`, {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Empleado eliminado correctamente');
            fetchEmployees(); // Actualizar la lista de empleados
        } else {
            alert('Error al eliminar empleado');
        }
    })
    .catch(error => console.error('Error al eliminar empleado:', error));
}

// Inicializar la página
document.addEventListener('DOMContentLoaded', () => {
    fetchEmployees();
});
