export default async ({ req, store }) => {

    if (req) {

        if (req.headers && req.headers['x-forwarded-for']) {
            await store.commit('setAddress', req.headers['x-forwarded-for'])
        }
        if (req.connection && req.connection.remoteAddress) {
            await store.commit('setAddress', req.connection.remoteAddress);
        }
        if (req.connection.socket && req.connection.socket.remoteAddress) {
            await store.commit('setAddress', req.connection.socket.remoteAddress);
        }
        if (req.socket && req.socket.remoteAddress) {
            await store.commit('setAddress', req.socket.remoteAddress);
        }
    }
};