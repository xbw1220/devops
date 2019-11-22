## 本项目用于验证在swarm环境下stack发布服务
#### 环境介绍
1.本人测试的时候在自己电脑上搭建了一个四台服务器的docker集群环境，安装了最新的docker-compose，启动了一个nexus作为镜像私有库，另外安装了docker-machine方便操作

2.需要注意的是，swarm需要打开端口:
~~~shell script
[root@node1 ~]# firewall-cmd --list-all
public (active)
  target: default
  icmp-block-inversion: no
  interfaces: eth0
  sources: 
  services: dhcpv6-client ssh
  ports: 2376/tcp 2377/tcp 7946/tcp 7946/udp 4789/udp
  protocols: 
  masquerade: no
  forward-ports: 
  source-ports: 
  icmp-blocks: 
  rich rules: 
~~~

#### 启动顺序
1.init.sh 创建网络和两个存放日志的文件夹

2.build-push.sh 创建镜像并推送到nexus镜像仓库

3.stack-devops.sh 启动服务


#### 遇到的问题:
1.挂载的问题，现在需要挂载文件的服务都放在主节点，并在compos文件中作了如下配置：
~~~
deploy:
      placement:
        constraints: [node.role == manager]
~~~
感觉需要所有节点上配置需要挂载的目录比较麻烦，不知道有没有更好的处理方式。

2.关于client服务的网络注册问题，原计划直接把client的8080端口映射到宿主机上，并作了如下配置：
~~~
inetutils:
      preferred-networks: ['10.0']
      use-only-site-local-interfaces: true
~~~
结果却并不如预期的将10.0网段的IP注册到nacos上，而且将docker默认生成的devops-default网段注册到了nacos(只观察到这是一个概率事件，大概率不会注册10.0网段)
