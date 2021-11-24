import { Router } from "./router/router.js";
import { ComponentManager } from "./component/component-manager.js";
let container = document.querySelector("component-container");
let links = document.querySelectorAll("[data-component-route]");

new Router(container, links);
