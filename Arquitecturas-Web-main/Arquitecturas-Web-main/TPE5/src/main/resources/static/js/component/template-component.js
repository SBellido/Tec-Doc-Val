import { Component } from "./component.js";
import { Util } from "../util/util.js";
export { TemplateComponent };

class TemplateComponent extends Component{
    #template;
    constructor(selector, template, component) {
        super(selector, false, undefined, component)
        this.#template = template;
        this.#loadComponent(this.component)
    }

    async #loadComponent(component) {
        component.innerHTML = await Util.fetchPage(this.#template);
        this.loadJs();
    }
}