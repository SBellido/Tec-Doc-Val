import { Util } from "../util/util.js";
import { Component } from "./component.js";
/** @typedef {import('./interface').*} */
export { FormComponent };

class FormComponent extends Component{
    constructor(selector, parent, form) {
        super(selector, false, parent)
        this.component.innerHTML = form;
        this.loadJs();
    }
    
    loadJs() {
        const form = this.component.querySelector("form");
        form.addEventListener('submit', (e) => {
            e.preventDefault();
            let fm = new FormData(form);
            let data = {};
            for (let [k, v] of fm) { data[k] = v; }
            console.log("DATA: ", data)
            form.dispatchEvent(new CustomEvent(
                'submit-request',
                {detail:{
                    method: form.getAttribute('data-method'),
                    formData: data
                }}
            ));
        });
    }
}