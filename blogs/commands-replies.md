
## HASH
* `1	HDEL key field1 field2`
<br/><font size=2>删除一个或多个哈希表字段</font>
* 2	HEXISTS key field 
<br/>查看哈希表 key 中，指定的字段是否存在。
* 3	HGET key field 
<br/>获取存储在哈希表中指定字段的值。
* 4	HGETALL key 
<br/>获取在哈希表中指定 key 的所有字段和值
* 5	HINCRBY key field increment 
<br/>为哈希表 key 中的指定字段的整数值加上增量 increment 。
* 6	HINCRBYFLOAT key field increment 
<br/>为哈希表 key 中的指定字段的浮点数值加上增量 increment 。
* 7	HKEYS key 
<br/>获取所有哈希表中的字段
* 8	HLEN key 
<br/>获取哈希表中字段的数量
* 9	HMGET key field1 field2
<br/>获取所有给定字段的值
* 10	HMSET key field1 value1 field2 value2 
<br/>同时将多个 field-value (域-值)对设置到哈希表 key 中。
* 11	HSET key field value 
<br/>将哈希表 key 中的字段 field 的值设为 value 。
* 12	HSETNX key field value 
<br/>只有在字段 field 不存在时，设置哈希表字段的值。
* 13	HVALS key 
<br/>获取哈希表中所有值
* 14	HSCAN key cursor MATCH pattern COUNT count
<br/>迭代哈希表中的键值对。
## LIST
* 1	BLPOP key1 key2 timeout 
    <br/>移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
* 2	BRPOP key1 key2 timeout 
 <br/>移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
* 3	BRPOPLPUSH source destination timeout 
 <br/>从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
* 4	LINDEX key index 
 <br/>通过索引获取列表中的元素
* 5	LINSERT key BEFORE|AFTER pivot value 
 <br/>在列表的元素前或者后插入元素
* 6	LLEN key 
 <br/>获取列表长度
* 7	LPOP key 
 <br/>移出并获取列表的第一个元素
* 8	LPUSH key value1 value2
 <br/>将一个或多个值插入到列表头部
* 9	LPUSHX key value 
 <br/>将一个值插入到已存在的列表头部
* 10	LRANGE key start stop 
 <br/>获取列表指定范围内的元素
* 11	LREM key count value 
 <br/>移除列表元素
* 12	LSET key index value 
 <br/>通过索引设置列表元素的值
* 13	LTRIM key start stop 
 <br/>对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
* 14	RPOP key 
 <br/>移除列表的最后一个元素，返回值为移除的元素。
* 15	RPOPLPUSH source destination 
 <br/>移除列表的最后一个元素，并将该元素添加到另一个列表并返回
* 16	RPUSH key value1 value2
 <br/>在列表中添加一个或多个值
* 17	RPUSHX key value 
 <br/>为已存在的列表添加值
