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
    .then(res => res.json())
    .then(data => {
        if (data.mensaje) {
            alert(data.mensaje);
        } else {
            alert("Registro exitoso");
        }
    })
    .catch(() => alert("Error al registrar usuario"));
}

function login() {
    const correo = document.getElementById("loginCorreo").value;
    const contraseña = document.getElementById("loginPass").value;

    fetch(`${API}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ correo, contraseña })
    })
    .then(res => {
        if (!res.ok) throw new Error("Credenciales incorrectas");
        return res.json();
    })
    .then(data => {
        alert(data.mensaje);

        // Guardar datos del usuario logueado (puedes guardar más si necesitas)
        sessionStorage.setItem("usuarioId", data.usuario.id);
        sessionStorage.setItem("usuarioNombre", data.usuario.nombre);
        sessionStorage.setItem("usuarioCorreo", data.usuario.correo);
        sessionStorage.setItem("usuarioRol", data.usuario.rol);

        window.location.href = "dashboard.html";
    })
    .catch(err => alert(err.message));
}
