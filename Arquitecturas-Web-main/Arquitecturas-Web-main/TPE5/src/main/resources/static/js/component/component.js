export { Component };

class Component {
    #selector;
    #component;
    constructor(selector, autoLoadJs = true, parent = document, component = undefined) {
        this.#selector = selector;
        this.#component = (component) ? component : parent.querySelector(this.#selector);
        if(autoLoadJs) { this.loadJs(); }
    }

    equals(selector) {
        return this.#selector === selector;
    }

    addEventListener(eventName, callback) {
        this.component.addEventListener(eventName, callback);
    }

    emmitEvent(event) {
        this.component.dispatchEvent(event);
    }

    get component() {return this.#component}

    loadJs() { }
}