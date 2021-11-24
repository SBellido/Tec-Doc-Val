import { Component } from "./component.js";
export { PaginationComponent };

/**
 * @typedef Pagination
 * @prop {Object[]} elements
 * @prop {number} page
 * @prop {number} size
 * @prop {number} maxPage
 */
class PaginationComponent extends Component{
    #pagination;

    constructor(pagination) {
        super("pagination-component", false)
        this.#pagination = pagination;
        this.loadJs();
    }
    
    loadJs() {
        function getHtml(p) {
            return `
            <nav aria-label="Page navigation example" class="${(p.maxPage>0)?'none': ''}">
                <ul class="pagination">
                    <li class="page-item ${(p.prev<0)?'disabled': ''}"><a class="page-link" data-page="${p.prev}" data-size="${p.size}" href="#">Prev       </a></li>
                    <li class="page-item ${(p.prev<0)?'d-none'  : ''}"><a class="page-link" data-page="${p.prev}" data-size="${p.size}" href="#">${p.prev+1}</a></li>
                    <li class="page-item disabled active"             ><a class="page-link" data-page="${p.page}" data-size="${p.size}" href="#">${p.page+1}</a></li>
                    <li class="page-item ${(p.next<0)?'d-none'  : ''}"><a class="page-link" data-page="${p.next}" data-size="${p.size}" href="#">${p.next+1}</a></li>
                    <li class="page-item ${(p.next<0)?'disabled': ''}"><a class="page-link" data-page="${p.next}" data-size="${p.size}" href="#">Next       </a></li>
                </ul>
            </nav>`;
        }
        this.component.innerHTML = getHtml(this.#pagination);
        this.addEventListener("click", (e) => {
            e.preventDefault();
            if(e.target.tagName === "A") {
                this.emmitEvent(
                    new CustomEvent("pagination-click", {
                    detail: {
                        page: e.target.getAttribute("data-page"),
                        size: e.target.getAttribute("data-size"),
                    }}));
            }
        }); 
    }
}