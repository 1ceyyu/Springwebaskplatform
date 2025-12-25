🚀 SpringWeb 问答交互平台 (SpringWebAskPlatform)
这是一个基于 Spring Boot 3 和 Thymeleaf 开发的轻量级问答社区项目。项目实现了全异步的实时数据更新、多行文本格式保留以及动态时间戳展示，旨在提供流畅的用户交互体验。

🌐 访问地址
登录入口：http://10.100.164.26:8080/login

平台首页：http://10.100.164.26:8080/questions

✨ 核心功能
实时交互：首页列表与详情页回复均采用 JavaScript Fetch API 结合定时器实现异步轮询，无需刷新页面即可实时获取最新动态。

格式自适应：

换行保留：通过 CSS white-space: pre-wrap 确保用户发布的复杂内容格式不丢失。

时间戳：后端自动处理 long 类型时间戳并映射为人类可读的字符串。

权限控制：基于 HttpSession 的简单登录机制，确保仅授权用户可发布问题或回复。

🛠️ 技术栈
后端：Java 17 / Spring Boot 3.x / Maven

前端：Thymeleaf 模板引擎 / 原生 JavaScript (ES6+)

服务器：Linux (CentOS/Ubuntu) / OpenJDK 17

工具：Lombok / WindTerm

📂 项目部署说明
1. 打包阶段
在本地开发环境执行 Maven 任务：

Bash

mvn clean package -DskipTests
生成的可执行 JAR 包位于 target/Springwebaskplatform.jar。

2. 服务器部署
上传文件：将 JAR 包上传至服务器 /root 目录。

清理端口（如 8080 已被占用）：

Bash

# 查找并结束旧进程
netstat -tunlp | grep 8080
kill -9 <PID>
持久化运行： 使用 nohup 确保在断开 SSH 连接后程序持续运行：

Bash

nohup java -jar /root/Springwebaskplatform.jar > output.log 2>&1 &
📝 开发者备注
数据模型：Reply 对象包含 getFormattedTime() 方法，由 Spring Boot 的 Jackson 库自动序列化为 JSON 字段供前端调用。

动态渲染：详情页脚本具备 DOM 自愈能力，当第一条评论产生时，会自动从“暂无回复”文案切换为标准的列表展示模式。
