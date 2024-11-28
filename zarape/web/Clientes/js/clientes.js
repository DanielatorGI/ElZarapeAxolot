// Obtener todos los clientes
function fetchClients() {
    fetch('http://localhost:8080/clients/getall')
        .then(response => response.json())
        .then(data => {
            let tableBody = document.getElementById('clientTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // Limpiar la tabla antes de llenarla

            data.forEach(client => {
                let row = tableBody.insertRow();
                row.innerHTML = `
                    <td>${client.id}</td>
                    <td><img src="${client.foto}" alt="Foto del cliente" class="card-img-top"></td>
                    <td>${client.nombre}</td>
                    <td>${client.contrasena}</td>
                    <td>${client.estatus}</td>
                    <td>
                        <button onclick="editClient(${client.id})">Editar</button>
                        <button onclick="deleteClient(${client.id})">Eliminar</button>
                    </td>
                `;
            });
        })
        .catch(error => console.error('Error al obtener los clientes:', error));
}

// Buscar en la tabla
function searchTable() {
    let searchInput = document.getElementById('searchInput').value.toLowerCase();
    let rows = document.getElementById('clientTable').getElementsByTagName('tbody')[0].rows;

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

// Abrir el formulario de nuevo cliente
function openClientForm() {
    // Mostrar el formulario de inserción
    // Aquí debes agregar tu lógica para mostrar el formulario y capturar los datos
    let clientForm = document.getElementById('clientForm');
    clientForm.style.display = 'block';
}

// Insertar un nuevo cliente
function insertClient() {
    let formData = new FormData(document.getElementById('clientForm'));
    
    fetch('http://localhost:8080/clients/insert', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Cliente agregado correctamente');
            fetchClients(); // Actualizar la lista de clientes
        } else {
            alert('Error al agregar cliente');
        }
    })
    .catch(error => console.error('Error al insertar cliente:', error));
}

// Editar un cliente
function editClient(id) {
    fetch(`http://localhost:8080/clients/get/${id}`)
        .then(response => response.json())
        .then(client => {
            // Llenar el formulario con los datos del cliente
            document.getElementById('clientId').value = client.id;
            document.getElementById('clientNombre').value = client.nombre;
            document.getElementById('clientEstatus').value = client.estatus;
            document.getElementById('clientForm').style.display = 'block';
        })
        .catch(error => console.error('Error al obtener el cliente:', error));
}

// Actualizar cliente
function updateClient() {
    let formData = new FormData(document.getElementById('clientForm'));
    
    fetch('http://localhost:8080/clients/update', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Cliente actualizado correctamente');
            fetchClients(); // Actualizar la lista de clientes
        } else {
            alert('Error al actualizar cliente');
        }
    })
    .catch(error => console.error('Error al actualizar cliente:', error));
}

// Eliminar un cliente
function deleteClient(id) {
    fetch(`http://localhost:8080/clients/delete/${id}`, {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Cliente eliminado correctamente');
            fetchClients(); // Actualizar la lista de clientes
        } else {
            alert('Error al eliminar cliente');
        }
    })
    .catch(error => console.error('Error al eliminar cliente:', error));
}

// Inicializar la página
document.addEventListener('DOMContentLoaded', () => {
    fetchClients();
});
