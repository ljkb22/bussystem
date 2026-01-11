# **公交管理信息系统的系统实现**
1. ## **运行环境准备**
1. ### **python语言**
![](d5c2951cfbef4bdb8feccc3465fb051f.001.png)
1. ### **java语言**
导入java.sql和javax.servlet

![](d5c2951cfbef4bdb8feccc3465fb051f.002.png)
1. ### **Web项目运行**
Web项目可直接在IDEA中运行
1. ### **服务器端配置**
Tomcat

![](d5c2951cfbef4bdb8feccc3465fb051f.003.png)

数据库管理

数据库名：Your\_database

用户名：root

用户密码：123456
1. ### **运行前后端**
HTML,CSS,JavaScript,JSP,MySQL
## **二．主要操作说明**
### **1.主页选择管理或者访客**
两个不同选项后有不同的功能，管理部分为对路线的管理操作，访客部分为查询公司和换乘查询

![](d5c2951cfbef4bdb8feccc3465fb051f.004.png)
### **2.管理：添加路线**
这是管理部分的所有功能

![](d5c2951cfbef4bdb8feccc3465fb051f.005.png)

![](d5c2951cfbef4bdb8feccc3465fb051f.006.png)

这是添加路线的页面，可以把路线的所有信息登记并输入到数据库中

![](d5c2951cfbef4bdb8feccc3465fb051f.007.png)![](d5c2951cfbef4bdb8feccc3465fb051f.008.png)

若信息填写无误，转到上面的页面，并自动跳转到主页

![](d5c2951cfbef4bdb8feccc3465fb051f.009.png)

![](d5c2951cfbef4bdb8feccc3465fb051f.010.png)

若填写站点有误，报错
### **3.管理：修改路线**
这是修该路线的初始界面

![](d5c2951cfbef4bdb8feccc3465fb051f.011.png)

要输入正确的路线名称

![](d5c2951cfbef4bdb8feccc3465fb051f.012.png)

否则会报错![](d5c2951cfbef4bdb8feccc3465fb051f.013.png)

输入正确路线名后，可进行修改

![](d5c2951cfbef4bdb8feccc3465fb051f.014.png)

### **4管理：删除路线**
这是删除路线的初始界面

![](d5c2951cfbef4bdb8feccc3465fb051f.015.png)

输入存在的路线名，方能正确操作，否则网页会报错

![](d5c2951cfbef4bdb8feccc3465fb051f.016.png)

![](d5c2951cfbef4bdb8feccc3465fb051f.017.png)

### **5.管理：搜索路线**
搜索类型中精确指完整的路线名，模糊是指包含搜索值的所有路线

![](d5c2951cfbef4bdb8feccc3465fb051f.018.png)

返回的结果按排序方式指定的方式排列

![](d5c2951cfbef4bdb8feccc3465fb051f.019.png)

![](d5c2951cfbef4bdb8feccc3465fb051f.020.png)

![](d5c2951cfbef4bdb8feccc3465fb051f.021.png)

![](d5c2951cfbef4bdb8feccc3465fb051f.022.png)
1. ### **访客：出行**
这是访客界面的两个功能显示

![](d5c2951cfbef4bdb8feccc3465fb051f.023.png)

点击出行后的界面

![](d5c2951cfbef4bdb8feccc3465fb051f.024.png)

用松江两个校区举例

![](d5c2951cfbef4bdb8feccc3465fb051f.025.png)
1. ### **访客：公司查询**
![](d5c2951cfbef4bdb8feccc3465fb051f.026.png)

只有编纂的一些公司名

![](d5c2951cfbef4bdb8feccc3465fb051f.027.png)

![](d5c2951cfbef4bdb8feccc3465fb051f.028.png)

这是查询结果，只包括随便编的Phone和City

![](d5c2951cfbef4bdb8feccc3465fb051f.029.png)
