# tedis
基于Tio网络框架的Redis客户端（学习版）

### `Tio`+`Jedis`源码学习=Tedis
## 知识点
* redis协议
* tio客户端的使用方式
* ByteBuffer的使用
* Jedis源码
* 其他...

## 服务器返回示例
* +OK\r\n
* *3\r\n$9\r\nsubscribe\r\n$12\r\ntest_channel\r\n:1\r\n

### 更新日志
* 2019-1-3 GET SET PING 命令。+，$ 包解析
* 2019-1-4 PUBSUB 命令。* 包解析
* 2019-1-7 DECRBY, DECR, INCRBY, INCR 命令，修复解析字符串多余byte（0）的bug
* 2019-1-7 补全所有命令（但未实现）
* 2019-1-9 EVAL 命令
* 2019-1-10 EXPIRE,PEXPIRE,EXPIREAT,PEXPIREAT 命令
* 2019-1-15 String 相关的命令
* 2019-1-16 HASH 相关命令