import { FormComponent } from './form-component.js';
/** @typedef {import('./interface').*} */
export { ClientFormComponent };

class ClientFormComponent extends FormComponent{
    constructor(parent, data) {
        super('client-form-component', parent, ClientFormComponent.#getHtml(data))
    }
    
    static #getHtml(data) {
        return `
        <form id="form-client-${data.idClient}" action="#" data-method="${((!data) ? 'POST':'PUT')}">
            <div class="form-group">
                <label for="idClient" class="${(data)? '"disabled':'d-none"'}>ID</label>
                <input type="text" class="form-control ${(data)? '"disabled':'d-none"'} name="idClient" value="${(data)?data.idClient:''}"/>
                <label for="name">Nombre</label>
                <input type="text" class="form-control" name="name" value="${(data)?data.name:''}"/>
            </div>
        </form>`;
    }
}