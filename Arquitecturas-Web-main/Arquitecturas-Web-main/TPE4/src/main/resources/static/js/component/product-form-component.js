import { FormComponent } from './form-component.js';
/** @typedef {import('./interface').*} */
export { ProductFormComponent };

class ProductFormComponent extends FormComponent{
    constructor(parent, data) {
        const text = `
        <form class="form-product" id="form-product-${(data!=undefined)?data.idProduct:'insert'}" action="#" data-method="${((!(data!=undefined)) ? 'POST':'PUT')}">
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
            </div>
        </form>`;
        super('product-form-component', parent, text)
    }

}