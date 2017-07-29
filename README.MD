# Hello, Netty

本喵学习 Netty 的 HelloWorld 集合

终端下执行 `mvn package` 在各个文件夹的 target 里就会产生对应的 jar-with-dependency 的可执行 jar 包

教程在这里 : [Netty 4.x 用户指南](http://wiki.jikexueyuan.com/project/netty-4-user-guide/)

[TOC]

## hello-discard-server

- 其实不是个完全丢弃任何东西的服务器，如果在 telnet 里输入 "ping" ，它会回应 "pong"
- 默认运行在 2333 端口

## hello-time-server

- 是一个会发送 System.currentTimeMillis 的服务器，一建立连接就发送 8 字节的 long 然后关掉链接
- 默认运行在 2323 端口
- 需要搭配客户端食用

## hello-time-client

- hello-time-server 对应的客户端，会把服务端发送的 8 字节 long 还原成 Date 对象并 toString() 之
- 默认连接本地 2323 端口
- 请配合服务端食用

## hello-time-echo-server

- telnet 链接上之后会显示一段欢迎语
- 会返回任何往服务端发送的文字
- 如果长达 2 秒不跟服务器交互，服务器就会开始跟你谈时间
- 客户端新建或者断开连接，服务端会有反应
- 默认运行在 2324 端口

## hello-chat-server

- 一个很简单的聊天室服务器
- 可 telnet ，但建议配合客户端食用
- 服务器会通知是否在线或者是否离线
- 默认启动于端口 2325

## hello-chat-client

- hello-chat-server 对应的客户端
- 启动参数填上 IP 和端口能够链接到别处相同协议的服务端
- 功能就是简单地发送文本出去
- 默认链接本地 2325 端口