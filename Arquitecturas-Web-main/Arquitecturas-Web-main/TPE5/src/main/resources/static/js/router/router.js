import { ComponentManager } from '../component/component-manager.js';
export { Router };

class Router {
     constructor(container, links) {
        links.forEach(link => {
            link.addEventListener("click", ()=>{
                ComponentManager.loadComponents(link.getAttribute("data-component-route"), container)
            });
        });
        // Default page
        ComponentManager.loadComponents(links[0].getAttribute("data-component-route"), container)
    }
}