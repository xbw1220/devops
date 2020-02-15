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