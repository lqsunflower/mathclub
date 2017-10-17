/**
 * 
 */
package com.mathclub.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.mathclub.admin.LoginController;
import com.mathclub.controller.AdminController;
import com.mathclub.controller.CommentController;
import com.mathclub.controller.KeyController;
import com.mathclub.controller.MajorController;
import com.mathclub.controller.MsgController;
import com.mathclub.controller.OauthWeixinController;
import com.mathclub.controller.SubjectAdminController;
import com.mathclub.controller.TestController;
import com.mathclub.controller.UploadController;
import com.mathclub.controller.UserController;
import com.mathclub.model.Account;
import com.mathclub.model.Comment;
import com.mathclub.model.KeyPoint;
import com.mathclub.model.Like;
import com.mathclub.model.Major;
import com.mathclub.model.Session;
import com.mathclub.model.Subject;
import com.mathclub.model.TestSubject;
import com.mathclub.model.User;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法 详见 JFinal 俱乐部:
 * http://jfinal.com/club
 * 
 * API引导式配置
 */
public class MathClubConfig extends JFinalConfig {

	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为： -XX:PermSize=64M
	 * -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		JFinal.start("src/main/webapp", 80, "/", 5);

		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		// JFinal.start("src/main/webapp", 80, "/");
	}

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		//me.add(new FrontRoutes());
		//me.add(new AdminRoutes());
		// 微信接收信息
		me.add("/msg", MsgController.class);
		me.add("/oauth", OauthWeixinController.class);
		me.add("/admin", AdminController.class);
		me.add("/adminSubject", SubjectAdminController.class);
		me.add("/upload", UploadController.class);
		me.add("/math", MajorController.class);
		me.add("/kk", KeyController.class);
		me.add("/ddd", UserController.class);
		me.add("/test", TestController.class);
		me.add("/comment", CommentController.class);
		me.add("/login", LoginController.class);

	}

	/*
	 * public void configEngine(Engine me) {
	 * me.addSharedFunction("/common/_layout.html");
	 * me.addSharedFunction("/common/_paginate.html"); }
	 */

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
				PropKit.get("password").trim());
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		// _MappingKit.mapping(arp);
		me.add(arp);
		arp.addMapping("user", "userId", User.class);
		arp.addMapping("major", "majorId", Major.class);
		arp.addMapping("keypoint", "keyId", KeyPoint.class);
		arp.addMapping("subject", "subjectId", Subject.class);
		arp.addMapping("subject_like", "id", Like.class);
		arp.addMapping("session", "id", Session.class);
		arp.addMapping("test", "id", TestSubject.class);
		arp.addMapping("comment", "commentId", Comment.class);
		arp.addMapping("account", "id", Account.class);

		me.add(new EhCachePlugin());// 配置缓存插件
	}

	/*
	 * public static DruidPlugin createDruidPlugin() { return new
	 * DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
	 * PropKit.get("password").trim()); }
	 */

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		//me.add(new LoginSessionInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {

	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
    public void afterJFinalStart() {
        ApiConfigKit.putApiConfig(getApiConfig());
    }
	
	/**
     * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
     * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
     */
    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();
        
        // 配置微信 API 相关常量
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));
        
        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }
}
