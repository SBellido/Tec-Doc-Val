
import { TemplateComponent } from "./template-component.js";
import { Util } from "../util/util.js";
import { PaginationComponent } from "./pagination-component.js";
export { ProductComponent };

class ProductComponent extends TemplateComponent{
    constructor(component) {
        super("product-component", "../../templates/products.html", component)
    }
    
    loadJs() {
        function getHtml(p) {
            return `
            <li clas="list-item" id="product-${p.idProduct}">
                ${getProduct(p)}
                ${getModals(p)}
            </li>`;
        }
        function getProduct(p) {
            return `
                <div class="product">
                    <h4>${p.name}</h4>
                    <p class="txt-bold">Precio: $${p.price}</p>
                    <p class="txt-bold">Stock: ${p.stock}</p>
                    <p class="txt-italic">Descripcion:</p>
                    <p> ${p.description} </p>
                    <div class="product-buttons">
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#modal-edit-id-${p.idProduct}">Editar</button>
                        <button type="button" class="btn btn-danger"  data-method="DELETE" data-id="${p.idProduct}">Borrar</button>
                    </div>
                </div>
            `;
        }

        function getModals(p) {
            return `
            <div class="collapse" id="modal-edit-id-${p.idProduct}">
                ${geForm(p)}
            </div>`;
        }

        function geForm(data) {
            return `
            <form class="form-product" action="#" data-method="POST">
                <h6>Crear producto</h6>
                <div class="form-group">
                    <label for="idProduct" class="${(data!=undefined)? '"':'d-none"'}>ID</label>
                    <input type="text" class="form-control class="${(data!=undefined)? '"disabled':'d-none"'} name="idProduct" value="${(data!=undefined)?data.idProduct:''}"/>
                    <label for="name">Nombre</label>
                    <input type="text" class="form-control" name="name" value="${(data!=undefined)?data.name:''}"/>
                    <label for="price">Precio</label>
                    <input type="text" class="form-control" name="price" value="${(data!=undefined)?data.price:''}"/>
                    <label for="description">Descripcion</label>
                    <textarea class="form-control" rows="3" name="description" value="${(data!=undefined)?data.description:''}"></textarea>
                    <button type="submit" class="btn btn-primary">Guardar cambios</button>
                </div>
            </form> `;
        }


        function getMostSelled(p, cant) {
            return `
            <div class="product">
                <h4>${p.name}</h4>
                <p class="txt-bold">Precio: $${p.price}</p>
                <p class="txt-bold">Stock: ${p.stock}</p>
                <p class="txt-italic">Descripcion:</p>
                <p class="txt-italic">Cant veces vendido: ${cant}</p>
                <p> ${p.description} </p>
            </div>`;
        }

        function getSelledByDay(r) {
            return `
            <div class="product">
                <h4>${(r.date).substring(0, 10)}</h4>
                <p class="txt-bold">Cant: $${r.quantitySold}</p>
                <p class="txt-bold">Ganacia: ${r.amountCollected}</p>
                <p class="txt-italic">Descripcion:</p>
            </div>`;
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
                    btn .addEventListener('click', (e) => {
                        btn.dispatchEvent(new CustomEvent(
                            'submit-request',
                            {detail:{
                                method: btn.getAttribute('data-method'),
                                formData: {idProduct: btn.getAttribute("data-id")}
                            }}
                        ));
                    });
                }
            });
        }
        async function render(page="", size="") {
            const url = "products" + ((page!=""||size!="") ? `?page=${page}&size=${size}` : '');
            let products = await Util.fetchJson(url);
            let productMostSelled = await Util.fetchJson("products/most-selled");
            let productsSelledByDay = await Util.fetchJson("products/report");

            const container = document.querySelector("#target");
            const containerMostSelled = document.querySelector("#most-selled");
            const containerSelledByDay = document.querySelector("#product-selled-by-day");
    
            container.innerHTML = "";
            products.elements.forEach(product => { 
                container.innerHTML += getHtml(product);
            });

            containerMostSelled.innerHTML += getMostSelled(...(productMostSelled[0].reverse()));
            
            productsSelledByDay.elements.forEach(report => { 
                containerSelledByDay.innerHTML += getSelledByDay(report);
            });

            addFormEvents(document);
            let clicked = false;
            let pagination = new PaginationComponent(products);
            pagination.addEventListener("pagination-click", (e) => {
                pagination.component.innerHTML = "";
                if(!clicked) {
                    clicked = true;
                    render(e.detail.page, e.detail.size);
                }
            });
            await document.querySelectorAll("[data-method]")
            .forEach( async el => {
                let clicked = false;
                await el.addEventListener("submit-request", async (e)=> {
                    clicked = true;
                    switch(e.detail.method) {
                        case 'POST':
                            await Util.post(`products`,e.detail.formData);
                            break;
                        case 'PUT':
                            await Util.put(`products`,e.detail.formData);
                            break;
                        case 'DELETE':
                            await Util.remove(`products/${e.detail.formData.idProduct}`);
                            break;          
                        default:
                            break;
                    }
                    location.reload();
                });
            });
        }
        render();
    }

}