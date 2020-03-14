process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = '0';

export default {
  mode: 'universal',
  srcDir: 'app',
  /*
  ** Headers of the page
  */
  head: {
    title: 'あつ森ちゃんねる',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: 'あつまれどうぶつの森　交流掲示板' },
      { hid: 'og:description', property: 'og:description', content: 'あつまれどうぶつの森　交流掲示板'  },
      { hid: 'og:url', property: 'og:url', content: 'https://atumori.space/' },
      { hid: 'og:title', property: 'og:title', content: 'atumori.space' },
      { hid: 'og:type', property: 'og:type', content: 'website'},
      { hid: 'og:image', property: 'og:image', content: ' https://i.imgur.com/AwVDuYf.png' },
      { hid: 'twitter:site', name: 'twitter:site', content: '@AtumoriSpace' },
      { hid: 'twitter:card', name: 'twitter:card', content: 'summary' },
      { hid: 'og:site_name', property: 'og:site_name', content: 'atumori.space' },
      { hid: 'og:locale', property: 'og:locale', content: 'ja_JP' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      { rel: 'stylesheet', href: 'https://fonts.googleapis.com/css?family=Noto+Sans+JP&display=swap' },
      { rel: 'stylesheet', href: 'https://fonts.googleapis.com/css?family=Fredoka+One&display=swap' }
    ]
  },
  /*
  ** Customize the progress-bar color
  */
  loading: { color: '#fff' },
  /*
  ** Global CSS
  */
  css: [
    { src: '~/assets/scss/main.scss', lang: 'scss' },
    {src: '~/assets/scss/markdown.scss', lang: 'scss'}
  ],

  fontawesome: {
    imports: [
      {
        set: '@fortawesome/free-solid-svg-icons',
        icons: ['fas']
      },
      {
        set: '@fortawesome/free-brands-svg-icons',
        icons: ['fab']
      }
    ]
  },

  manifest: {
    name: "あつ森ちゃんねる",
    title: "あつ森ちゃんねる",
    'og:title': 'あつ森ちゃんねる',
    description: 'あつまれどうぶつの森　交流掲示板',
    'og:description': 'あつまれどうぶつの森　交流掲示板',
    lang: 'ja',
  },
  /*
  ** Plugins to load before mounting the App
  */
  plugins: [
    { 
      src: '~/plugins/vue-mavon-editor', 
      srr: false 
    },
    { 
      src: '~/plugins/moment-filter', 
    }
  ],
  /*
  ** Nuxt.js dev-modules
  */
  buildModules: [
  ],

  markdownit: {
    preset: 'default',
    injected: true, // $mdを利用してmarkdownをhtmlにレンダリングする
    breaks: true, // 改行コードを<br>に変換する
    html: true, // HTML タグを有効にする
    linkify: true, // URLに似たテキストをリンクに自動変換する
    typography: true,  // 言語に依存しないきれいな 置換 + 引用符 を有効にします。
  },

  /*
  ** Nuxt.js modules
  */
  modules: [
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/pwa',
    'nuxt-fontawesome',
    '@nuxtjs/markdownit',
    ['@nuxtjs/google-analytics', {
      id: 'UA-159980035-1'
    }],
    ['@nuxtjs/google-adsense', {
      id: 'ca-pub-793248329946064',
      pageLevelAds: true,
      analyticsUacct: 'UA-159980035-1',
      analyticsDomainName: 'atumori.space'
    }]
  ],

  router: {
    middleware: [
      'getPosts',
      'getAddress'
    ]
  },

  /*
  ** Axios module configuration
  ** See https://axios.nuxtjs.org/options
  */
  axios: {
    proxy: true
  },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** You can extend webpack config here
    */
    extend(config, { isDev }) {
      if (isDev && process.isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    }
  },

  env: {
    AXIOS_URL: 'https://localhost:8443'
  }
}
