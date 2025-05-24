const API = "http://localhost:8080/api";


function registrar() {
    const nombre = document.getElementById("regNombre").value;
    const correo = document.getElementById("regCorreo").value;
    const contraseña = document.getElementById("regPass").value;

    fetch(`${API}/auth/registro`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, correo, contraseña })
    })
    .then(res => res.text())
    .then(msg => alert(msg));
}

function login() {
    const correo = document.getElementById("loginCorreo").value;
    const contraseña = document.getElementById("loginPass").value;

    fetch(`${API}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ correo, contraseña })
    })
    .then(res => res.ok ? res.text() : Promise.reject("Login incorrecto"))
    .then(msg => {
        alert(msg);
        window.location.href = "dashboard.html";
    })
    .catch(err => alert(err));
}