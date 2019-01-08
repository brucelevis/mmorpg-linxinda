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

**-> 门派显示：** factionInfoCmd

**-> 注册：** playerRegisterCmd factionId playerName

**-> 登录：** playerLoginCmd playerUid

**-> 下线：** playerLogoutCmd

**####################  场景  ###########################**

**-> 打印当前场景实体：** sceneAoiCmd

**-> 场景跳转：** sceneSwitchCmd sceneId

**-> 查看可达场景：** sceneNeighborCmd

**-> 物品一键拾取：** scenePickAllCmd

**################### 背包系统 #########################**

**-> 加物品：** itemAddCmd itemCfgId amount

**-> 查看背包物品：** backpackInfoCmd

**-> 背包整理：** backpackCleanCmd

**-> 查看物品具体信息：** backpackItemInfoCmd uid

**-> 物品使用：** itemUseCmd index

**#################### 装备系统 ##########################**

**-> 装备栏信息：** equipInfoCmd

**-> 装备脱：** equipTakeoffCmd partId

**################### 属性系统  ###########################**

**-> 人物属性：** attrInfoCmd

**################### 技能系统  ############################**

**-> 人物技能：** skillInfoCmd

**-> 技能施放：** skillCastCmd skillId targetUid

**################### 商店系统 #############################**

**-> 显示商店菜单：** shopInfoCmd

**-> 购买某种物品：** shopBuyCmd id amount  

**################### 聊天系统 #############################**

**-> 当前场景聊天：** chatSceneCmd msg

**-> 私聊：** chatPrivate toUidCmd msg

**-> 世界：** chatWorldCmd msg

**#################### 邮件系统 ############################**

**-> 邮件信息：** mailInfoCmd

**-> 领取邮件附件：** mailReceiveCmd id

**-> 发送邮件 (不发道具附件则在对应位置发送null)：** mailSendCmd uid title content index:amount,index:amount

**#################### 副本系统 #############################**

**-> 挑战副本：** dungeonEnterCmd dungeonId

**-> 副本信息：** dungeonInfoCmd

**-> 离开副本：** dungeonLeaveCmd

**##################### 好友系统 ############################**

**-> 添加好友：** friendAddCmd playerUid

**-> 好友列表：** friendInfoCmd

**-> 接受好友请求：** friendAcceptCmd playerUid

**-> 拒绝好友请求：** friendRejectCmd playerUid

**#################### 公会系统 ############################**

**-> 创建公会：** guildCreateCmd name

**-> 查看公会：** guildInfoCmd

**-> 公会解散：** guildDissolveCmd

**-> 公会加人：** guildAddCmd playerUid

**-> 公会踢人：** guildKickCmd playerUid

**-> 公会修改权限：** guildChangeCmd playerUid

**-> 退出公会：** guildQuitCmd

**-> 公会捐献物品：** guildDonateItemCmd index:amount,index:amount...

**-> 公会捐献钱：** guildDonateMoneyCmd cfgId amount

**-> 公会仓库信息：** guildWarehouseCmd

**-> 公会仓库领取物品：** guildGetItemCmd index:amount,index:amount....

**-> 公会仓库领钱：** guildGetMoneyCmd cfgId amount

**##################### 交易系统 ############################**

**-> 发起交易请求：** tradeRequestCmd playerUid

**-> 接受交易请求：** tradeAcceptCmd tradeUid

**-> 拒绝交易请求：** tradeRejectCmd tradeUid

**-> 加入背包物品：** tradeAddItemCmd  index amount

**-> 加入钱：** tradeAddMoneyCmd cfgId amount

**-> 交易取消：** tradeCloseCmd

**-> 确认交易：** tradeCommitCmd 

**####################### 组队系统 #############################**

**-> 创建组队：** teamCreateCmd 

**-> 显示队伍信息：** teamInfoCmd

**-> 解散组队：** teamDissolveCmd

**-> 退出组队：** teamQuitCmd

**-> 组队踢人：** teamKickCmd playerUid

**-> 组队邀请：** teamInviteCmd playerUid

**-> 同意加入组队：** teamAcceptCmd teamUid

**-> 拒绝加入组队：** teamRejectCmd teamUid

**######################## 任务系统 #############################**

**-> 任务列表：** missionInfoCmd 

**-> 接任务：** missionAcceptCmd missionId

**-> 提交任务：** missionCommitCmd missionId

**-> Npc对话：** npcTalkCmd npcId

**########################  pk  ##################################**

**-> 决斗邀请：** pkInviteCmd playerUid

**-> 拒绝决斗：** pkRejectCmd playerUid 

**-> 同意决斗：** pkAcceptCmd playerUid

---
### 阶段4扩展问题回答：
1. 任务系统用观察者模式，监听各种事件，有新的任务类型只要写新的事件类型及对应的任务处理方法就行了。
2. 对背包的操作设置锁或弄个对背包的操作队列来处理，应该就可以了（现在代码里还没对背包上锁）
3. 面对面交易的流程是：A向B发起交易请求，A会进入交易界面等待B的回答。若B拒绝，则A发起了交易关闭。若B同意，则进入交易界面。A,B彼此放入物品和钱会通知显示，若中途有一方关闭则会交易结束。当2方同时确认交易会进入交易的处理判断。这里整个交易过程是用锁来实现。就是相同的一场交易的每一步操作都是加锁的。
4. 公会成员对公会仓库的存和取操作是加锁来确保安全的。 
5. 最后一击是在怪物死亡处理逻辑里锁住这只怪物，执行经验结算和爆物，确保不会重复奖励和爆物。
6. 登录有做判断是否在线的处理。