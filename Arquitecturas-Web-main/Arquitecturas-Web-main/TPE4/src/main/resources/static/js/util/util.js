export { Util };

const base = "http://localhost:8080/api/";

async function fetchPage(route) {
    const response = await fetch(base + route);
    const t = await response.text();
    return t;
}

async function fetchJson(route) {
    const response = await fetch(base + route);
    const t = await response.json();
    return t;
}

async function post(route, body) {
    const response = await fetch(base + route, {
        method: 'POST',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json",
            "Content-Length": JSON.stringify(body).length
        },
        body: JSON.stringify(body)
    });
    const ok = await response.ok;
    return ok;
}
async function put(route, body) {
    const response = await fetch(base + route, {
        method: 'PUT',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json",
            "Content-Length": JSON.stringify(body).length
        },
        body: JSON.stringify(body)
    });
    const ok = await response.ok;
    return ok;
}
async function remove(route) {
    const response = await fetch(base + route, {
        method: 'DELETE',
    });
    const ok = await response.ok;
    return ok;
}
const Util = { fetchPage, fetchJson, post, put, remove }