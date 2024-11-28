const urlBase = 'http://localhost:8080/AxolotZarape/api/alimento';

document.addEventListener('DOMContentLoaded', () => {
    cargarAlimentos();

    document.getElementById('formAlimento').addEventListener('submit', (e) => {
        e.preventDefault();
        const idAlimento = document.getElementById('idAlimento').value;

        if (idAlimento == 0) {
            insertarAlimento();
        } else {
            actualizarAlimento(idAlimento);
        }
    });
});

function cargarAlimentos() {
    fetch(`${urlBase}/getAll`)
        .then(response => response.json())
        .then(alimentos => {
            const tabla = document.getElementById('tablaAlimentos');
            tabla.innerHTML = '';
            alimentos.forEach(alimento => {
                tabla.innerHTML += `
                    <tr>
                        <td>${alimento.id}</td>
                        <td>${alimento.nombre}</td>
                        <td>${alimento.descripcion}</td>
                        <td><img src="${alimento.foto}" width="50"></td>
                        <td>${alimento.precio}</td>
                        <td>${alimento.categoria}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="editarAlimento(${alimento.id}, '${alimento.nombre}', '${alimento.descripcion}', '${alimento.foto}', ${alimento.precio}, ${alimento.idCategoria})">Editar</button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarAlimento(${alimento.id})">Eliminar</button>
                        </td>
                    </tr>`;
            });
        })
        .catch(error => console.error('Error al cargar los alimentos:', error));
}

function insertarAlimento() {
    const alimento = obtenerDatosFormulario();

    fetch(`${urlBase}/insert`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams(alimento)
    })
    .then(response => response.json())
    .then(data => {
        alert('Alimento agregado con éxito');
        cargarAlimentos();
        limpiarFormulario();
    })
    .catch(error => console.error('Error al insertar alimento:', error));
}

function actualizarAlimento(id) {
    const alimento = obtenerDatosFormulario();
    alimento.idAlimento = id;

    fetch(`${urlBase}/update`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams(alimento)
    })
    .then(response => response.json())
    .then(data => {
        alert('Alimento actualizado con éxito');
        cargarAlimentos();
        limpiarFormulario();
    })
    .catch(error => console.error('Error al actualizar alimento:', error));
}

function eliminarAlimento(id) {
    if (!confirm('¿Seguro que deseas eliminar este alimento?')) return;

    fetch(`${urlBase}/delete`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ idAlimento: id })
    })
    .then(response => response.json())
    .then(data => {
        alert('Alimento eliminado con éxito');
        cargarAlimentos();
    })
    .catch(error => console.error('Error al eliminar alimento:', error));
}

function editarAlimento(id, nombre, descripcion, foto, precio, idCategoria) {
    document.getElementById('idAlimento').value = id;
    document.getElementById('nombre').value = nombre;
    document.getElementById('descripcion').value = descripcion;
    document.getElementById('foto').value = foto;
    document.getElementById('precio').value = precio;
    document.getElementById('idCategoria').value = idCategoria;
}

function obtenerDatosFormulario() {
    return {
        nombre: document.getElementById('nombre').value,
        descripcion: document.getElementById('descripcion').value,
        foto: document.getElementById('foto').value,
        precio: document.getElementById('precio').value,
        idCategoria: document.getElementById('idCategoria').value
    };
}

function limpiarFormulario() {
    document.getElementById('formAlimento').reset();
    document.getElementById('idAlimento').value = 0;
}
