const path = require('path')

function resolve (dir) {
  return path.join(__dirname, './', dir)
}

module.exports = {
  // 基本路径
  publicPath: '/',
  outputDir: 'dist',
  //放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录。
  assetsDir: 'static',
  //默认情况下，生成的静态资源在它们的文件名中包含了 hash 以便更好的控制缓存。如果你无法使用 Vue CLI 生成的 index HTML，你可以通过将这个选项设为 false 来关闭文件名哈希。
  filenameHashing: true,
  pages: {
    index: {
      // page 的入口
      entry: 'src/main.js',
      // 模板来源
      template: 'public/index.html',
      // 在 dist/index.html 的输出
      filename: 'index.html',
      // 当使用 title 选项时，
      // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
      title: 'Index Page',
      // 在这个页面中包含的块，默认情况下会包含
      // 提取出来的通用 chunk 和 vendor chunk。
      chunks: ['chunk-vendors', 'chunk-common', 'index']
    },
  },
  // eslint-loader 是否在保存的时候检查
  lintOnSave: process.env.NODE_ENV !== 'production',
  // 是否使用包含运行时编译器的Vue核心的构建
  runtimeCompiler: undefined,
  // 生产环境 sourceMap
  productionSourceMap: false,
  //该函数及可以修改配置并不返回任何东西，也可以返回一个被克隆或合并过的配置版本。


  configureWebpack: config => {
    if (process.env.NODE_ENV === 'production') {
      // 为生产环境修改配置...
    } else {
      // 为开发环境修改配置...
      /*            var webpack = require('webpack');
                  var webpackConfig = require('./node_modules/@vue/cli-service/lib/config/dev.js');
                  var compiler = webpack(webpackConfig);

                  require('webpack-dev-middleware-hard-disk')(compiler, {
                      publicPath: webpackConfig.output.publicPath,
                      quiet: true
                  })*/
    }
  },
  //会接收一个基于 webpack-chain 的 ChainableConfig 实例。允许对内部的 webpack 配置进行更细粒度的修改。
  chainWebpack: (config) => {
    // svg rule loader
    const svgRule = config.module.rule('svg') // 找到svg-loader
    svgRule.uses.clear() // 清除已有的loader, 如果不这样做会添加在此loader之后
    svgRule.exclude.add(/node_modules/) // 正则匹配排除node_modules目录
    svgRule // 添加svg新的loader处理
      .test(/\.svg$/)
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })

    // 修改images loader 添加svg处理
    const imagesRule = config.module.rule('images')
    imagesRule.exclude.add(resolve('src/icons'))
    config.module
      .rule('images')
      .test(/\.(png|jpe?g|gif|svg)(\?.*)?$/)


    // 链式配置
    // var webpackConfig = require('./node_modules/@vue/cli-service/lib/config/dev.js');
    // //配置插件：添加
    // config
    //     .plugin('webpack-dev-middleware-hard-disk')
    //     .use(require.resolve('webpack-dev-middleware-hard-disk'), {
    //         publicPath:webpackConfig,
    //         quiet: true
    //     });
  },
  // 配置高于chainWebpack中关于 css loader 的配置
  css: {
    sourceMap: true,
    loaderOptions: {
      css: {
        // 这里的选项会传递给 css-loader
      },
      postcss: {
        // 这里的选项会传递给 postcss-loader
      }
    }
  },
  //所有 webpack-dev-server 的选项都支持
  // https://webpack.docschina.org/configuration/dev-server/
  devServer: {
      open: true,

      host: '10.70.2.27',//本机ip地址

      port: 8080,

      https: false,

    hotOnly: true,

  proxy: {
  '/api': {
    // 目标 API 地址
    target: 'http://10.70.2.27',//开发环境
      target: 'http://10.70.2.27/',//域名环境
      //如果要代理 websockets
      // ws: true,
      // 将主机标头的原点更改为目标URL
      changeOrigin: true,
      pathRewrite:{
      '^/api':'/' //这个很重要
    }
  }
},

before: app => {
}
},
// 构建时开启多进程处理 babel 编译
parallel: require('os').cpus().length > 1,

  // https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-pwa
  pwa: {},

  // 第三方插件配置
  pluginOptions: {
    foo: {
      // 插件可以作为 `options.pluginOptions.foo` 访问这些选项。
    }
  }
};

