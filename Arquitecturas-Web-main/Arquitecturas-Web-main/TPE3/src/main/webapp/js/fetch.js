document.addEventListener("DOMContentLoaded", ()=> {
    const studenList =  document.getElementById("estudiantes");
    const base = "http://localhost:8080/TPE3/api/"
    console.log("ANDA");

    async function getUsers() {
        const response = await fetch(base + "estudiante");
        const json = await response.json();
        return json
    }

    async function getCareer() {
        const response = await fetch(base + "carrera");
        const json = await response.json();
        return json
    }

    function loadList(estudiantes, carreras) {
        let list = "";
        estudiantes.forEach(e => { 
            list += `
            <li class="student">
                <div class="name">
                    ${e.nombre} ${e.apellido}
                </div>
                <div class="form">
                    <form data-id-estudiante="${e.num_libreta}">
                        <label>Carrera
                            <select>`;
                            carreras.forEach(c => {
                                list += `<option data-id-carrera="${c.id}">
                                    ${c.nombre}
                                </option>`;
                            });
                            list += `</select>
                        </label>
                        <button class="mat_button" type="submit">Matricular</button>
                    </form>
                </div>
            </li>`;
            studenList.innerHTML = list;
        });
        addEvents()
    }
    

    function addEvents() {
        document.getElementById("estudiantes").querySelectorAll("form").forEach( form => {
            form.addEventListener("submit", (e)=> {
                e.preventDefault();
                let idCareer = form.querySelector("select").selectedOptions[0].getAttribute("data-id-carrera");  
                let idStudent = form.getAttribute("data-id-estudiante");  
                fetch(base +"carrera/"+idCareer +"/matricular-estudiante/"+idStudent,{method: "POST"});
            });
        });
    }

    async function foo() {
        try {
            let careers = await getCareer();
            let users = await getUsers();
            loadList(users, careers);
        } catch(error) { }
    }
    foo()
    

    const buscarAlmunoDiv = document.getElementById("response-alumno")
    const formBuscarAlumno = document.getElementById("for-buscar-alumno");
    formBuscarAlumno.addEventListener("submit", (e) => {
        e.preventDefault();
        fetch(base+"estudiante/"+formBuscarAlumno.querySelector("input").value).
        then( text => text.json()).
        then( e =>  
                buscarAlmunoDiv.innerHTML = 
                `<p>Nombre: ${e.nombre} ${e.apellido}</p>  
                <p>DNI: ${e.num_doc}</p>
                <p>Número de libreta: ${e.num_libreta}</p>
                <p>Edad: ${e.edad}</p>
                <p>Ciudad de residencia: ${e.ciudad_residencia}</p>
                <p>Genero: ${e.genero}</p>`
            );
    });


    const buscarAlmunoGeneroDiv = document.getElementById("response-alumno-genero")
    const formBuscarAlumnoGenero = document.getElementById("for-buscar-alumno-genero");
    formBuscarAlumnoGenero.addEventListener("submit", (e) => {
        e.preventDefault();
        fetch(base+"estudiante/genero/"+formBuscarAlumnoGenero.querySelector("input").value).
        then( text => text.json()).
        then( estudiantes =>  {
                let estudiantess = "" 
                estudiantes.forEach(e => {
                    estudiantess += 
                    `<li class="student">
                        ${e.nombre} ${e.apellido} - ${e.genero} 
                    </li>`;
                });
                buscarAlmunoGeneroDiv.innerHTML = estudiantess;
            });
    });


    

        fetch(base+"carrera/con-inscriptos").
        then( text => text.json()).
        then( carreras =>  {
            let carrerass = "" 
            carreras.forEach(c => {
            carrerass += 
                `<div class="form">
                    ${c.nombre}
                </div>`;
            });
                document.getElementById("response-carrera-con-alumnos").innerHTML = carrerass;
            });
    

    fetch(base+"carrera/con-inscriptos").
        then( text => text.json()).
        then( carreras =>  {
                let carrerass = "" 
                    carrerass += 
                    `<div class="form">
                        <form>
                            <label>Carrera
                                <select>`;
                                carreras.forEach(c => {
                                    carrerass += `<option data-id-carrera="${c.id}">
                                        ${c.nombre}
                                    </option>`;
                                });
                                carrerass += `</select>
                            </label> <br>
                            <label>Ciudad</label>
                            <input class="input" type="text"  autocomplete="ciudad" required>
                            <button class="mat_button" type="submit">Buscar</button>
                        </form>
                    </div>`;
                document.getElementById("form-alumno-por-carrera-ciudad").innerHTML = carrerass;
                document.getElementById("form-alumno-por-carrera-ciudad").querySelector("form").addEventListener("submit", (e) => {
                    e.preventDefault();
                    let idCareer = e.target.querySelector("select").selectedOptions[0].getAttribute("data-id-carrera");  
                    let city = e.target.querySelector("input").value;  
                    fetch(base+"carrera/"+idCareer+"/estudiante/ciudad/"+city).
                    then( text => text.json()).
                    then( estudiantes =>  {
                            let estudiantess = "" 
                            estudiantes.forEach(e => {
                                estudiantess += 
                                `<li class="student">
                                    ${e.nombre} ${e.apellido}
                                </li>`;
                            });
                            document.getElementById("response-alumno-por-carrera-ciudad").innerHTML = estudiantess;
                        })
                });
            });
    


    fetch(base+"carrera/reporte").
    then( text => text.json()).
    then( reportes =>  {
        let text = "" 
        reportes.forEach(reporte => {
        text += 
        `<h3>Carrera: ${reporte.carrera.nombre}</h3>
        <p>Inscriptos por carrera</p>
        <ul>`;
            Object.keys(reporte.inscriptos).forEach( key => {
                reporte.inscriptos[key].forEach( e => {
                    text += 
                    `<li>
                        ${e.nombre} ${e.apellido} - ${key}
                    </li>`;
                })
            });
        text += `</ul>
        <p>Egresados  por carrera</p>
        <ul>`;
            Object.keys(reporte.egresados).forEach( key => {
                reporte.egresados[key].forEach( e => {
                    text += 
                    `<li>
                        ${e.nombre} ${e.apellido} - ${key}
                    </li>`;
                })
            })
        text += `</ul> <hr>`;
        });
            document.getElementById("response-reporte").innerHTML = text;
        });
});
