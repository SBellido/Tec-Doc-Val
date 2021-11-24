/**
 * @typedef Client
 * @prop {number} idClient
 * @prop {string} name
 */

/**
 * @typedef Product
 * @prop {number} idProduct
 * @prop {string} name
 * @prop {string} description
 * @prop {number} price
 * @prop {number} stock
 */

/**
 * @typedef ClientProduct
 * @prop {Client} client
 * @prop {Product} product
 * @prop {date} date
 * @prop {number} cant
 */

/**
 * @typedef Pagination
 * @prop {Object[]} elements
 * @prop {number} page
 * @prop {number} prev
 * @prop {number} next
 * @prop {number} size
 * @prop {number} maxPage
 */

/**
 * @typedef ProductArr
 * @prop {Product[]} elements
 * @typedef ClientArr
 * @prop {Client[]} elements
 * @typedef ClientProductArr
 * @prop {ClientProduct[]} elements
 */

/**
 * @typedef {Pagination & ClientArr} ClientPagination
 * @typedef {Pagination & ProductArr} ProductPagination
 * @typedef {Pagination & ClientProductArr} ClientProductPagination
 */