export default async ({ store }) => {
    if(store.state.allPosts.length && store.state.tradePosts.length && store.state.friendPosts.length){
        return
    }
    await store.dispatch('getPosts')
}