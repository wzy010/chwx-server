package cn.net.easyinfo.common.util;

import cn.net.easyinfo.entity.LoginUser;

import java.util.HashMap;
import java.util.Map;

public class ConfigInfo {
	
	public static Configur config = new Configur("config/properties/params.properties");
	public static Configur dbconfig = new Configur("config/properties/jdbc.properties");
	
	public static Map<String,LoginUser> sToken = new HashMap<String,LoginUser>();
	public static Map<String,String> appcode = new HashMap<String,String>();
	
	//系统不支持的类型
	public static Map<String,String> notCanDoTpyeMap;
	static  
    {  
		notCanDoTpyeMap = new HashMap<String,String>();
		//notCanDoTpyeMap.put("163.reply.status", "1"); //163新闻评论不好用
    }  
	//request.getSession().getServletContext().getRealPath("")
	public static String APPTOUXIANG_PATH = "/pages/download/headerpic/";
	public static String SESSION_USER = "loginUser";
	//public static String DEFAULT_URL = "pages/longriver/taskIssued/issued.jsp";
	//任务状态,0未执行 1正在执行 2已经执行
	public static int STATUS_TASK_0 = 0;
	public static int STATUS_TASK_1 = 1;
	public static int STATUS_TASK_2 = 2;
	//短信,采集状态为启用
	public static int STATUS_SMS = 1;
	public static String USER_RIGHT = "rightids";
	
	//查看短信,采集启用标识
	public static String MARK_SMS = "sms";
	public static String MARK_CJ = "cj";
	//是否是下发给部门还是网评员
	public static int MARK_WPY = 0;
	public static int MARK_DEPT = 1;
	//是否是管理员
	public static int IS_MANAGER = 1;
	//website_son_type 对应的网站类型
	public static int TYPE_1 = 1; //微博直发
	public static int TYPE_2 = 2; //论坛直发
	public static int TYPE_3 = 3; //输入哪个网站
	//评论 入库 区分
	public static int DBType_DISCUSS = 1; //是评论 对应类别表
	public static int DBType_ZHIFA = 2; //直发不入对方库
	public static int DBType_WB_ZHUANFA = 4;//微博转发
	public static int DBType_BBS = 3; //论坛直发
	//存入中间表 带url的不单独入库
	public static int CID_ZHUANFA = 4;//微博直发
	//对方库中id对应task_preview表中id   webId对应本地taskid,webname对应categoryid
	public static String DEFAULT_LOGIN_PASSWORD = "e10adc3949ba59abbe56e057f20f883e"; //用户默认密码123456
	public static String SINA_WEIBO = "weibo.com"; //根据链接判断是不是新浪微博
	//支持论坛数组 网易 新浪 天涯 搜狐 百度贴吧 凤凰网 论坛
	public static String[] BBS = new String[]{
									"http://www.tianya","http://bbs.tianya","http://3g.tianya",
									"http://tieba.baidu",
									"http://bbs.sina","http://bbs.163","http://bbs.ifeng",
									"http://club.sohu"};
	//role_right权限设置  1 admin用户 2:管理员权限1 ; 3:2级管理员权限2  10:网评员权限
	public static int right_1 = 2;
	public static int right_2 = 3;
	public static int right_3 = 10;
	
	//图片上传路径
	public static String fileDir = "/pages/images/upload/";
	//Excel导入路径
	public static String ExcelDir = "/pages/upload/excel/";
	//导出word文档路径
	public static String NOTICEFILE = "pages/download/noticefile/";
	//Excel模板路径
	public static String EXCELMODULE = "pages/download/excel/demo.xlsx";
	//Excel模板路径
	public static String EXCELMODULE_ACCOUNT = "pages/download/excel/demo_account.xlsx";
	//超级管理员ID
	public static int ADMINID = 1;
	//补录状态
	public static int FLAG_BULU = 1;
	//短信内容
	public static String header = "尊敬的用户,你有新的待办任务: ";
	public static String content = ",内容: ";
	public static String stime = ",时间: ";
	public static String etime = "至 ";
	//任务暂停
	public static String ZTYW = "尊敬的用户,业务暂停通知,业务: ";
	public static String REASON = ",理由;";
	//登录验证码
	public static String DENGLU_CODE = "尊敬的用户,登录短信验证码为: ";
	public static String DENGLU_CODE_LAST = " ,切勿向任何人泄露!";
	//邮件内容
	public static String URL = " 链接: ";
	public static String SUBJECT = "新任务";
	public static String PUBLISHER = " 发布人: ";
	
	public static String getTable(){
		return "ztaskResult_"+DateUtil.getCurrentDate();
	}
	public static String USER_EXPORT_ERROR = "网评员导入错误信息";
	public static String EXCEL_SUFFIX = ".xls";//xlsx
	//发送到partner那边时间
	public static int TIMER_EXEC = 0;
	
	public static String TOP_MENU_URL = "task/getLeftTree.do?menuid=";
	//过滤直发转发
	public static String FZHIFA = "转发";
	//导出word文档
	public static String PIC_PATH = "pages/images/upload/nopage.png";
	//是否是管理员
	public static String ISMANAGERE = "ismanager";
	//
	public static String receiveNotice = "pages/longriver/notice/noticeReceive.jsp";
	//
	public static String receiveTask = "pages/longriver/taskIssued/receive.jsp";
	//账号维护 账号日志表名
	public static String getMATableName(){
		return "maintain_account_log_"+DateUtil.getCurrentDate();
	}
	public static String ACCOUNT_EXPORT_ERROR = "账号导入错误信息";
	public static String MALOG_TABLENAME_PRE = "maintain_account_log_";
	
	//引导每隔多少条发送到MQ
	public static int toMQEveryNumber=1;

	public static final String APPID = "wx88ecf4b90b53cae5";
	public static final String SECRET = "03ae97aba8c90aeca140240a8b404df4";
	public static final String GRANT_TYPE = "client_credential";
	//public static final String TEMPLATE_ID_VOLIDATE = "wmrNHxMmNhOBLXA9oy6QCj-vxpzbLPWgzBFDKP7MNHQ";
	public static final String TEMPLATE_ID_NOTICE = "mDKCKt3KnMCu_2H8ANWzzBvNBR6RRHmR5cxIVH0pnrk";//USdXru0hh3JMtlkL5o17r20htbVwCPKsKycFkYv46og
	public static final String TEMPLATE_ID_TASK = "AJk6FPRwUlzbvXVk7AhB43K74SXhysWCSUhQl1dcQj0";
	
	public static final String ADMIN_SHOWNAME = "管理员";
	public static final String USER_SHOWNAME = "网评员";
	//第一次采集页数
	public static final String COUNT_100 = "100";
	public static final String COUNT_5 = "5";
	//客户发送引导任务时是否发送通知给公司相关负责人
	public static final int GUIDE_IS_SEND = 2;
	//是否设置值班账号即某管理员下发的任务其他管理员是否能看到--浙江需求
	public static final int SET_ADMIN = 1;
	
	//public static String guideSucEhcache = "guide.suc.";
	public static String guideSinaNewsEhcache = "guide.sinanew.";
	public static String guideChangeStatus = "guide.status.";
	public static String MARK_IMAGE_PATH = "pages/img/";
	public static String IMAGE_TYPE = ".jpg";
	//默认发送引导所到的服务器
	public static String DEFAULT_REGION = "22,23,24,35";
	public static int[] DEFAULT_REGION_ARRAY = {22,23,24};
	
	public final static Map<Integer,Double> map_money = new HashMap<Integer,Double>();  
	static {  
		map_money.put(1, 0.4);  
		map_money.put(3, 0.4);
		map_money.put(10, 0.4);
		map_money.put(8, 0.4);
		
		map_money.put(2, 0.8);  
		map_money.put(4, 0.8);
		map_money.put(5, 0.8);
		map_money.put(6, 0.8);
		map_money.put(7, 0.8);
	}
	public static int BEIJING_ID = 388; //388
	public static String DEPT_TOP_ID_STR = "topDeptid";
	
	public static String UNAME = "13433605584"; //新浪微博采集用户名密码
	public static String UPWD = "lx1314";
	
	public static String AI = "ai.easyinfo";//ai单独做的
//	public static String AI = "127.0";//ai单独做的
}
