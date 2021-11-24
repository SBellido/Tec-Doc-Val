import { ClientComponent  } from "./client-component.js" ;
import { ProductComponent } from "./product-component.js";
export { ComponentManager }


class ComponentManager {
    static #components = [];
    static addComponent(componentSelector) {
        ComponentManager.#components.push(componentSelector);
    }
    static loadComponents(selector, container) {
        this.#components.forEach( component => {
            let key = Object.keys(component)[0];
            if(key === selector) {
                return component[key](container);  
            }
        });
    }
}

ComponentManager.addComponent({"client-component": (component)=> new ClientComponent(component) })
ComponentManager.addComponent({"product-component": (component)=> new ProductComponent(component) })
