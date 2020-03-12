export const state = () => ({
    allPosts: [],
    tradePosts: [],
    friendPosts: [],
    tags: [],
    address: "127.0.0.1"
});

export const getters = {
    linkTo: () => (name, id) => {
        return { name: `${name}-id`, params: { id: id } }
    },

    postById: state => (paramId) => {
        for (let i = 0; i < state.allPosts.length; i++) {
            const post = state.allPosts[i];
            const id = post.id
            if (id !== null && id !== undefined) {
                if (id === paramId) {
                    return post;
                }
            }
        }
    },
    postsByTag: state => (currentTag) => {
        const posts = []
        for (let i = 0; i < state.allPosts.length; i++) {
            const post = state.allPosts[i]
            const tags = post.tags.split(',')

            if (tags.length) {
                const tag = tags.find(tag => tag === currentTag)
                if (tag) posts.push(post)
            }
        }
        return posts
    },
}


export const mutations = {
    setAddress(state, payload){
        state.address = payload;
    },
    setAllPosts(state, payload) {
        state.allPosts = []
        state.allPosts = payload
    },
    setTradePosts(state, payload) {
        state.tradePosts = []
        state.tradePosts = payload
    },
    setFriendPosts(state, payload) {
        state.friendPosts = []
        state.friendPosts = payload
    },
    async setTags(state, posts) {
        state.tags = [];

        //タグと投稿数を連想配列にぶちこむ
        for (let i = 0; i < posts.length; i++) {
            const post = posts[i];

            const tags = post.tags.split(',')
            if (tags.length) {
                tags.forEach(tag => {
                    if (tag !== null && tag !== undefined && tag !== "") {
                        if (!state.tags.find((v) => v.name === tag)) {
                            state.tags.push({ name: tag, count: 0 })
                        }
                        if (state.tags.find((v) => v.name === tag)) {
                            const count = state.tags[state.tags.findIndex(el => el.name === tag)].count
                            state.tags[state.tags.findIndex(el => el.name === tag)] = { name: tag, count: count + 1 }
                        }
                    }
                });
            }
        }

        //タグの投稿数を多い順にソート
        state.tags.sort(function(a,b){
            if(a.count<b.count) return 1;
            if(a.count > b.count) return -1;
            return 0;
        });
    }

}

export const actions = {
    async getPosts({ commit }) {
        await Promise.all([
            this.$axios.get(process.env.AXIOS_URL + '/posts'),
            this.$axios.get(process.env.AXIOS_URL + '/posts/trade'),
            this.$axios.get(process.env.AXIOS_URL + '/posts/friend')
        ]).then(async (res) => {
            await commit('setAllPosts', res[0].data)
            await commit('setTradePosts', res[1].data)
            await commit('setFriendPosts', res[2].data)
            await commit('setTags', res[0].data)
        })
    }
}
