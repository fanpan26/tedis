## HASH

#### HSET key field value  :1 新添加 :0 已存在，覆盖值
#### HEXISTS key field      :1 存在 :0 不存在
#### HDEL key field1 field2 ...  :1 删除成功  :0 field 不存在
#### HSETNX key field value :1(添加成功) :0 已经存在，不覆盖  
#### HGETALL key   *6\r\n$2\r\nf1\r\n$2...
#### HINCRBY key field value
#### HINCRYFLOAT key field value
#### HKEYS key   *5 返回所有的字段数
#### HLEN key   :5 字段个数
#### HMGET key field1,field2 获取多个字段的值
#### HMSET key field1,value1,filed2,value2 设置多个值
#### HVALS key 获取所有的value
#### HSETNX key field value 如果已经存在，不覆盖

