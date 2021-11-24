import { Util } from "../util/util.js";
import { TemplateComponent } from "./template-component.js";
import { PaginationComponent } from "./pagination-component.js";
export { ClientComponent };

class ClientComponent extends TemplateComponent{
    constructor(component) {
        super("client-component", "../../templates/clients.html", component)
    }
    
    loadJs() {
        function getHtml(c, cp=[undefined]) {
            return `
            <li clas="list-item" id="client-${c.idClient}">
                ${getClients(c, cp)}
                ${getModals(c)}
            </li>`;
        }
        function getClients(c, cp) {
            return `
                <div class="client">
                    <h4>${c.name}</h4>
                    <p class="txt-italic">Informacion de cliente:</p>
                    <p class="txt-bold">Gasto total: $${ 
                            cp.reduce((prev, next) => prev + ((typeof next !== "undefined"&&typeof next.product !== "undefined")?(next.product.price * next.cant):0), 0).toString()
                        }
                    </p>
                    <p class="txt-italic">Productos Comprados:</p>
                    <ul>
                        ${ 
                            cp
                            .map( cp =>`<li class="txt-bold">${((cp!="")?(cp.product.name):"El cliente no ha comprado nada")}</li>`)
                            .join().replace(/^(?![\s\S])/, "El cliente no ha comprado nada")
                        }
                    </ul>
                    <div class="product-buttons">
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#modal-edit-id-${c.idClient}">Editar</button>
                        <button type="button" class="btn btn-danger" data-method="DELETE" data-id="${c.idClient}">Borrar</button>
                    </div>
                </div>`;
        }

        function getModals(c) {
            return `
            <div class="collapse" id="modal-edit-id-${c.idClient}">
                ${getForms(c)} 
            </div>`;
        }
        function getForms(data) {
            return `
            <form action="#" data-method="PUT">
                <div class="form-group">
                    <label for="idClient" disabled>ID</label>
                    <input type="text" name="idClient" disabled value="${(data)?data.idClient:''}"/>
                    <label for="name">Nombre</label>
                    <input type="text" class="form-control" name="name" value="${(data)?data.name:''}"/>
                </div>
                <button type="submit" class="btn btn-primary"">Guardar combios</button>  
            </form>`;
        }
        function addFormEvents(container) {
            container.querySelectorAll("[data-method]")
            .forEach( el => {
                if(el.tagName == "FORM") {
                    const form =  el;
                    form.addEventListener('submit', (e) => {
                        e.preventDefault();
                        let fm = new FormData(form);
                        let data = {};
                        for (let [k, v] of fm) { data[k] = v; }
                        form.dispatchEvent(new CustomEvent(
                            'submit-request',
                            {detail:{
                                method: form.getAttribute('data-method'),
                                formData: data
                            }}
                        ));
                    });
                } else {
                    const btn =  el;
                    btn.addEventListener('click', (e) => {
                        btn.dispatchEvent(new CustomEvent(
                            'submit-request',
                            {detail:{
                                method: btn.getAttribute('data-method'),
                                formData: {idClient: btn.getAttribute("data-id")}
                            }}
                        ));
                    });
                }
            });
        }


        async function render(page="", size="") {
            const clientsEndPoint = "clients" + ((page!=""||size!="") ? `?page=${page}&size=${size}` : '');
            const productsBoughtEndPoint = "products/bought/"; 
            let clients = await Util.fetchJson(clientsEndPoint);

            const container = document.querySelector("#target");

            container.innerHTML = ""
            await clients.elements.forEach(async (client, i) => {
                const products = await Util.fetchJson(productsBoughtEndPoint+ client.idClient); 
                container.innerHTML += getHtml(client, products);
                if(i==clients.elements.length-1) {
                    addFormEvents(container);
                    addHttpRestEvent();
                }
            });

            let clicked = false;
            let pagination = new PaginationComponent(clients);
            pagination.addEventListener("pagination-click", (e) => {
                if(!clicked) {
                    clicked = true;
                    render(e.detail.page, e.detail.size);
                }
            });
            async function addHttpRestEvent() {
                await container.querySelectorAll("[data-method]")
                .forEach( async el => {
                    let clicked = false;
                    await el.addEventListener("submit-request", async (e)=> {
                        clicked = true;
                        switch(e.detail.method) {
                            case 'POST':
                                await Util.post(`clients`,e.detail.formData);
                                break;
                            case 'PUT':
                                await Util.put(`clients`,e.detail.formData);
                                break;
                            case 'DELETE':
                                await Util.remove(`clients/${e.detail.formData.idClients}`);
                                break;          
                            default:
                                break;
                        }
                        location.reload();
                    });
                });
            }
        }

        render();
    }
}