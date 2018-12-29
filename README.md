# mmorpg-linxinda
模拟mmorpg项目

# 游戏简介
###  *背景：*
&ensp;&ensp;&ensp;&ensp;这个游戏项目是mmorpg的学习模拟项目，模仿冒险岛（曾经很火，到现在还在运营的一款游戏，也是本人从小玩到大的游戏，虽然现在没玩了。）的基本玩法，因为冒险岛的战斗还算比较简单，这里我模仿了现在在玩的LOL的一些战斗技能（如奶妈的W治疗，R群体治疗。安妮的R熊，阿木木的R群体晕，卡尔玛的群体护盾，露露的单体护盾。青钢影Q刷出护盾，点燃持续掉血等等），增加了游戏的复杂度和可玩性。

### *项目：*
1. 使用netty作为通讯框架。
2. 使用spring框架简化开发，提高可测试性。
3. 使用spring data jpa作为持久层框架，简化对数据库的操作及开发量。
4. 使用谷歌的Guava Cache及map作为缓存，减少数据库IO。
5. AAA测试规范，使用Mockito提高单元测试的可测试性。（比较懒，前期写了一点后面没写了。）
6. 游戏数据导表使用POI读取Excel封装成JavaBean，业务层用接口隔开，导表支持公式计算，配置方便导表结构修改。
7. 使用事件机制和自己封装的一套Behavior简化开发，解耦合和提高导表的可配置性。
8. 客户端使用简单的Java GUI界面，用命令及字符串打印的方式进行交互。

### *使用说明：*
1. 服务端：运行com.wan37.server.ServerStarter
2. 客户端：运行com.wan37.client.ClientStarter
3. 导表目录：\mmorpg-linxinda\server\docs
4. 下面命令手册<br/>
    ↓↓↓    ↓↓↓
### *命令手册：*
**###################################################**

**-> 门派显示：** faction_Info

**-> 注册：** player_Register factionId playerName

**-> 登录：** player_Login playerUid

**-> 下线：** player_Logout

**####################  场景  ###########################**

**-> 打印当前场景实体：** scene_Aoi

**-> 场景跳转：** scene_Switch sceneId

**-> 查看可达场景：** scene_Neighbor

**-> 物品一键拾取：** scene_PickAll

**################### 背包系统 #########################**

**-> 加物品：** item_Add itemCfgId amount

**-> 查看背包物品：** backpack_Info

**-> 背包整理：** backpack_Clean

**-> 查看物品具体信息：** backpack_ItemInfo uid

**-> 物品使用：** item_Use index

**#################### 装备系统 ##########################**

**-> 装备栏信息：** equip_Info

**-> 装备脱：** equip_Takeoff partId

**################### 属性系统  ###########################**

**-> 人物属性：** attr_Info

**################### 技能系统  ############################**

**-> 人物技能：** skill_Info

**-> 技能施放：** skill_Cast skillId targetUid

**################### 商店系统 #############################**

**-> 显示商店菜单：** shop_Info

**-> 购买某种物品：** shop_Buy id amount  

**################### 聊天系统 #############################**

**-> 当前场景聊天：** chat_Scene msg

**-> 私聊：** chat_Private toUid msg

**-> 世界：** chat_World msg

**#################### 邮件系统 ############################**

**-> 邮件信息：** mail_Info

**-> 领取邮件附件：** mail_Receive id

**-> 发送邮件 (不发道具附件则在对应位置发送null)：** mail_Send uid title content index:amount,index:amount

**#################### 副本系统 #############################**

**-> 挑战副本：** dungeon_Enter dungeonId

**-> 副本信息：** dungeon_Info

**-> 离开副本：** dungeon_Leave

**##################### 好友系统 ############################**

**-> 添加好友：** friend_Add playerUid

**-> 好友列表：** friend_Info

**-> 接受好友请求：** friend_Accept playerUid

**-> 拒绝好友请求：** friend_Reject playerUid

**#################### 公会系统 ############################**

**-> 创建公会：** league_Create name

**-> 查看公会：** league_Info 

**-> 公会解散：** league_Dissolve

**-> 公会加人：** league_Add playerUid

**-> 公会踢人：** league_Kick playerUid

**-> 公会修改权限：** league_Change playerUid

**-> 退出公会：** league_Quit

**-> 公会捐献物品：** league_DonateItem index:amount,index:amount...

**-> 公会捐献钱：** league_DonateMoney cfgId amount

**-> 公会仓库信息：** league_Warehouse

**-> 公会仓库领取物品：** league_GetItem index:amount,index:amount....

**-> 公会仓库领钱：** league_GetMoney cfgId amount

**##################### 交易系统 ############################**

**-> 发起交易请求：** trade_Request playerUid

**-> 接受交易请求：** trade_Accept tradeUid

**-> 拒绝交易请求：** trade_Reject tradeUid

**-> 加入背包物品：** trade_AddItem  index amount

**-> 加入钱：** trade_AddMoney cfgId amount

**-> 交易取消：** trade_Close 

**-> 确认交易：** trade_Commit 

**####################### 组队系统 #############################**

**-> 创建组队：** team_Create 

**-> 显示队伍信息：** team_Info

**-> 解散组队：** team_Dissolve

**-> 退出组队：** team_Quit

**-> 组队踢人：** team_Kick playerUid

**-> 组队邀请：** team_Invite playerUid

**-> 同意加入组队：** team_Accept teamUid

**-> 拒绝加入组队：** team_Reject teamUid

**######################## 任务系统 #############################**

**-> 任务列表：** mission_Info 

**-> 接任务：** mission_Accept missionId

**-> 提交任务：** mission_Commit missionId

**-> Npc对话：** Npc_Talk npcId

**########################  pk  ##################################**

**-> 决斗邀请：** pk_Invite playerUid

**-> 拒绝决斗：** pk_Reject playerUid 

**-> 同意决斗：** pk_Accept playerUid

---
### 阶段4扩展问题回答：
1. 任务系统用观察者模式，监听各种事件，有新的任务类型只要写新的事件类型及对应的任务处理方法就行了。
2. 对背包的操作设置锁或弄个对背包的操作队列来处理，应该就可以了（现在代码里还没对背包上锁）
3. 面对面交易的流程是：A向B发起交易请求，A会进入交易界面等待B的回答。若B拒绝，则A发起了交易关闭。若B同意，则进入交易界面。A,B彼此放入物品和钱会通知显示，若中途有一方关闭则会交易结束。当2方同时确认交易会进入交易的处理判断。这里整个交易过程是用锁来实现。就是相同的一场交易的每一步操作都是加锁的。
4. 公会成员对公会仓库的存和取操作是加锁来确保安全的。 
5. 最后一击是在怪物死亡处理逻辑里锁住这只怪物，执行经验结算和爆物，确保不会重复奖励和爆物。
6. 登录有做判断是否在线的处理。