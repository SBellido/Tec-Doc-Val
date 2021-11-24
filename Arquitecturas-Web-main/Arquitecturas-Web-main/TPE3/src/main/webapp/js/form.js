window.addEventListener('DOMContentLoaded', (event) => {
    const base = "http://localhost:8080/TPE3/api/"

    let register_form = document.getElementById('register_form');
    register_form.onsubmit = function(e) {
        e.preventDefault();
        let formData = new FormData(this);
        let jsonData = {};
        for (let [k, v] of formData) {
            jsonData[k] = v;
        }
        console.log(JSON.stringify(jsonData));
        fetch(base +"estudiante", 
            {
                method: 'POST', 
                headers: {
                  'Content-Type': 'application/json',
                },
                body: JSON.stringify(jsonData),
            })
          .then(response => response.json())
          .then(json => console.log(json))
    }
});
