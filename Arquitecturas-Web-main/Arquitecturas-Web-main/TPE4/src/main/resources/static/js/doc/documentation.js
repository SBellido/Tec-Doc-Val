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
 * @prop {number} size
 * @prop {number} maxPage
 */