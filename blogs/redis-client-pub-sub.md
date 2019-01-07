## 前言
&nbsp;&nbsp;在[学习T-io框架，从写一个Redis客户端开始](https://www.cnblogs.com/panzi/p/10213821.html)一文中，已经简单介绍了`Redis`客户端的实现思路，并且基础架构已经搭建完成，只不过支持的命令不全，不过后期在加命令就会很简单了。本篇就要实现`Publish/Subscribe`功能。

## Pub/Sub
&nbsp;&nbsp;发布订阅模式在很多场景中用的都很频繁，这里不再赘述。下面看一下`Redis`中的命令。参考资料：[https://redis.io/topics/pubsub](https://redis.io/topics/pubsub)
```
//发布
PUBLISH
//订阅
SUBSCRIBE
//模式匹配订阅
PSUBSCRIBE
//取消订阅
UNSUBSCRIBE
//取消订阅（模式匹配）
PUNSUBSCRIBE
//其他
```

## PUBLISH/SUBSCRIBE
&nbsp;&nbsp;命令使用方式很简单：
```
PUBLISH CHANNEL MESSAGE
```
例如：`publish user  helloworld`

`Client`类中增加代码：
```
    @Override
    public void publish(final String channel, final String message) {
        sendCommand(Command.PUBLISH, channel, message);
    }

    @Override
    public void subscribe(final String... channels) {
        sendCommand(SUBSCRIBE, channels);
    }
```
调试代码如下：
```
 //发布
 Tedis tedisPublish =  new Tedis("192.168.1.225", 6379);
 tedisPublish.publish("channel1","hello world");
```
```
 //订阅
 Tedis tedis = new Tedis("192.168.1.225", 6379);
 tedis.subscribe(new MyPubSub(),"channel1");
```
先订阅，后发布，订阅响应结果：
```
*3
$9
subscribe
$8
channel1
:1
```
通过响应结果可以看出，我们当前的命令是 `subscribe`，然后订阅的是`channel1`，当前共订阅了`:1`个。

发布响应结果：
```
:1
```
总共发给了`:1`个订阅客户端。这个结果就是订阅客户端的个数。

## PSUBSCRIBE
命令格式如下：
```
PSUBSCIRBE  news.*
```
修改一下调试代码：

订阅
```
  tedis.pSubscribe(new MyPubSub(),"news.*");
```
响应结果：
```
*3
$10
psubscribe
$6
news.*
:1
```
发布
```
  tedisPublish.publish("news.sports","welcome to NBA");
  tedisPublish.publish("news.country","this is china news");
```

订阅客户端收到消息：
```
*4
$8
pmessage
$6
news.*
$11
news.sports
$14
welcome to NBA


*4
$8
pmessage
$6
news.*
$12
news.country
$18
this is china news

```

从响应结果可以看出，客户端订阅了 `news.*`，然后收到了`news.sports,news.country`的消息。

## 响应消息解析

&nbsp;&nbsp;上述代码中有一个`MyPubSub`对象，它继承自抽象类`TedisPubSub`.这个类做了发布订阅核心的业务处理。通过对服务端返回的消息格式，我们可以发现，它的消息格式是统一的。

```
    EVENT_NAME --事件

    CHANNEL_NAME --频道
    
    OTHER --其他信息，根据每个事件可能不同
    
```

所以我们在做发布订阅的响应消息解析时，可以返回 List<Object>。这里以`SUBSCRIBE/PSUBSCRIBE`举例
```
 private boolean handleSubscribe(byte[] resp,List<Object> reply){
        //是否普通订阅
        boolean isSubscribe = Arrays.equals(SUBSCRIBE.raw, resp);
        //是否模式匹配订阅
        boolean isPSubscribe = Arrays.equals(Keyword.PSUBSCRIBE.raw, resp);

        if (isSubscribe || isPSubscribe) {
            resetSubscribedChannels(reply);
            //第二个值为 channel 名称
            final byte[] channelBytes = (byte[]) reply.get(1);
            //转化为 string
            final String channel = getString(channelBytes);

            //调用事件 (onSubscribe,onPSubscribe 子类可以重写)
            if (isSubscribe) {
                onSubscribe(channel);
            } else {
                onPSubscribe(channel);
            }
            return true;
        }
        return false;
    }
```
```
    public abstract void onSubscribe(final String channel);
    public abstract void onPSubscribe(final String channelPatterns);
    
```
在 `MyPubSub`中重写上述两个方法。

```
    @Override
    public void onSubscribe(String channel) {
        System.out.println("订阅了："+channel);
    }

    @Override
    public void onPSubscribe(String channelPatterns) {
        System.out.println("订阅了："+channelPatterns);
    }
```

这样，我们就能够收到回调消息了。
```
订阅了：news.*
```
接收到消息同理：
```
   @Override
    public void onMessage(String channel, String message) {
        System.out.println(channel + " 收到了消息："+message);
    }
```
```
channel1 收到了消息：welcome to NBA.
```

&nbsp;&nbsp;不过这里需要<font color=red>注意</font>的一点是,在普通订阅的消息中只有【MESSAGE，CHANNEL，CONTENT】三个值，而模式匹配的订阅消息中，有【PMESSAGE,PATTERN,CHANNEL,CONTENT】四个值，其中就多了一个 `PATTERN`,也就是上文中的`news.*`,所以稍微做一下区分就可以了

```
 private boolean handleMessage(byte[] resp, List<Object> reply) {
        boolean isMessage = Arrays.equals(MESSAGE.raw, resp);
        boolean isPMessage = Arrays.equals(PMESSAGE.raw, resp);
        if (isMessage || isPMessage) {
            final byte[] secondBytes = (byte[]) reply.get(1);
            final byte[] thirdBytes = (byte[]) reply.get(2);
            final String second = getString(secondBytes);
            final String third = getString(thirdBytes);
            if (isMessage) {
                onMessage(second, third);
            } else {
                final byte[] messageBytes = (byte[]) reply.get(3);
                final String message = getString(messageBytes);
                onPMessage(second, third, message);
            }
            return true;
        }
```
调用示例
```
news.country(news.*)收到了消息：this is china news
```

## 总结
&nbsp;&nbsp;本文简单的对`Redis`的`Pub/Sub`模式做了介绍，并且在客户端中做了相应的处理。当然其中也是大量参考了`Jedis`源码。本文就到这里啦，88