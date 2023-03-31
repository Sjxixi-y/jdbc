# JDBC

java数据库连接规范。

Java提供了一套规范，而各个数据库厂商提供了对应接口的实现（所以需要导包），程序员不需要知道具体的实现，只要使用对应的类（面向接口编程）。



![](.\img\1.png)





**包：mysql-connector-java-5.1.X 或 mysql-connector-java-8.0.X**



## 一、开发步骤

### 1. 注册驱动

```java
Class.forName(全类名);
```



### 2. 连接数据库

```java
String url = "jdbc:mysql://localhost:3306/cs2301";
String username = "root";
String password = "123456";
// 连接，传入 三个参数。1. 连接 2. 数据库用户名 3. 数据库密码
Connection root = DriverManager.getConnection(url, username, password);
```

如果出现：

```java
Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set.
```

则在 统一资源定位符 后添加 `?useSSL=false`



### 3. 获取发送对象

```java
Statement s1 = root.createStatement();
```



### 4. 执行SQL语句

```java
// 准备 SQL
String sql = "";

// 返回一个 int 类型，代表影响的 行 数。
int r = statement.executeUpdate(sql);
// 返回一个 结果集，代表的是查询结果。
ResultSet r1 = s1.executeQuery(str);
```



### 5. 处理结果

```java
// 返回
return r;
// 遍历
while (r1.next()) {
    r1.getObject();
    // 如果知道了 列号，则可以直接使用。
    re.getInt(1);
    // 如果知道了 列名称，则可以直接使用字段名。
    re.getInt("id");
    // 其他具体处理
}
```



### 6. 关闭

先开后关，后开先关。

```java
对象.close();
```



### 7. 综合案例

Main1:

```java
public class Main1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 建立连接，面向接口编程
        String url = "jdbc:mysql://localhost:3306/cs2301";
        String username = "root";
        String password = "123456";
        Connection root = DriverManager.getConnection(url, username, password);
        System.out.println(root);

        // 创建SQL语句
        String str = "select deptno, dname, loc from dept";

        Statement s1 = root.createStatement();

        // 执行，使用结果集接收
        ResultSet r1 = s1.executeQuery(str);

        // 处理结果
        while (r1.next()) { // 判断结果集中是否有数据
            System.out.println(r1.getObject(1) + ", " + r1.getObject(2) + ", " + r1.getObject(3));
        }

        // 释放资源
        r1.close();
        s1.close();
        root.close();
    }
}
```



## 二、综合案例

### 1. 增删改查

```java
public class Main2 {
    public static void main(String[] args) {
        // 添加
        // System.out.println(insert(new Dept(0, "tom", "123456", "19967297087")));
        // 删除
        // System.out.println(delete(6));
        // 修改
        // System.out.println(update(new Dept(0, "tom", "100101", "11312341236"), 1));
        // 查询
        for (Dept d : select(1)) {
            System.out.println(d);
        }
    }

    // 增加
    public static int insert(Dept d1) {
        Connection root = null;
        Statement s1 = null;
        int a = -1;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
            String username = "root";
            String password = "123456";
            root = DriverManager.getConnection(url, username, password);
            // 获取发送 SQL 对象
            String sql = "insert into user values(null , '" + d1.getUsername() + "', '" + d1.getPassword() + "', '" + d1.getPhone() + "');";
            s1 = root.createStatement();
            // 发送 SQL，返回结果集
            a = s1.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s1 != null)
                    s1.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (root != null)
                        root.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return a;
    }

    // 删除
    public static int delete(int id) {
        Connection connection = null;
        Statement statement = null;
        int i = -1;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
            String username = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, username, password);
            // 获取发送对象
            statement = connection.createStatement();
            // 执行
            String sql = "delete from user where id = " + id + ";";
            i = statement.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return i;
    }

    // 修改
    public static int update(Dept d1, int id) {
        Connection connection = null;
        Statement statement = null;
        int i = -1;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
            String username = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, username, password);
            // 获取发送对象
            statement = connection.createStatement();
            // 执行
            String sql = "update user set username = '" +
                    d1.getUsername() + "', password = '" +
                    d1.getPassword() + "',phone = '" +
                    d1.getPhone() + "' where id = " +
                    id + ";";
            i = statement.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return i;
    }

    // 查询
    public static List<Dept> select(int ids) {
        List<Dept> list = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
            String username = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, username, password);
            // 获取发送对象
            statement = connection.createStatement();
            // 发送
            String sql;
            if (ids <= 0) {
                sql = "select * from user";

            } else {
                sql = "SELECT * from user where id = " + ids;
            }
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(new Dept(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("phone")));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    try {
                        if (connection != null)
                            connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return list;
    }
}
```



### 2. 注册

```java
public class Main3 {
    public static void main(String[] args) {
        String username = "to";
        String password = "100101";

        List<Dept> list= select(username, password);

        if (list.size() != 0) {
            for (Dept d : list) {
                System.out.println(d);
            }
        } else {
            System.out.println("密码错误");
        }
    }

    // 查询
    public static List<Dept> select(String u, String p) {
        List<Dept> list = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
            String username = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, username, password);
            // 获取发送对象
            statement = connection.createStatement();
            // 发送
            String sql = "select * from user where username = '" + u + "' and password = '" + p + "'";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(new Dept(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("phone")));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    try {
                        if (connection != null)
                            connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return list;
    }
}
```



## 三、SQL注入

### 1. 介绍

输入的数据中含有SQL关键字并且参与了编译，导致条件为true。以至于得到正确的结果。

如 `注册` 例子中，密用户或密码输入中：

```java
String username = "' or 1=1 or 1= '";
String password = "wlhnmhi";
```



结果

```java
Dept{id=1, username='tom', password='100101', phone='11312341236'}
Dept{id=2, username='mark', password='111111', phone='15329687531'}
Dept{id=3, username='lucy', password='111111', phone='15329687531'}
```

数据库搜索始终为 true。



### 2. PreparedStatement 预编译

#### 1. 作用

（1）提高效率

（2）安全，避免SQL注入

（3）动态填入数据，执行多个同构的SQL语句



**Statement会使数据库频繁编译SQL，可能造成数据库缓冲区溢出。**



#### 2. 使用

参数标记（占位符）

```java
// 其中 ？ 为占位符
String sql = "select * from user where username = ? and password = ?";
```



动态参数绑定

```sql
PreparedStatement pstmt = conn.prepareStatement(sql)
// 其中，第一个入参表示 占位符次序。
pstmt.setString(1, "");
pstmt.setString(2, "");
```



修改后

```java
public class Main5 {
    public static void main(String[] args) {
        // String username = "' or 1=1 or 1= '";
        String username = "tom";
        String password = "100101";

        List<Dept> list = select(username, password);

        if (list.size() != 0) {
            for (Dept d : list) {
                System.out.println(d);
            }
        } else {
            System.out.println("密码错误");
        }
    }

    // 查询
    public static List<Dept> select(String u, String p) {
        List<Dept> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
            String username = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, username, password);
            // 获取发送对象
            String sql = "select * from user where username = ? and password = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, u);
            ps.setString(2, p);
            // 发送
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                list.add(new Dept(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("phone")));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (ps != null)
                        ps.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    try {
                        if (connection != null)
                            connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return list;
    }
}
```



## 四、封装工具类

**util**

```
public class DaoUtil {
    // 初始化
    static {
        try {
            Class.forName("");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // 获取 连接
    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/cs2301?useSSL=false";
        String username = "root";
        String password = "123456";
        try {
            connection =  DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void CloseAll(Connection c, PreparedStatement p, ResultSet r) {
        try {
            if (r != null)
                r.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (p != null)
                    p.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (c != null)
                        c.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
```

```java
public class Main5 {
    // 增加
    public static int insert(Dept d1) {
        Connection connection = null;
        PreparedStatement ps = null;
        int a = -1;
        try {
            connection = DaoUtil.getConnection();
            // 获取发送 SQL 对象
            String sql = "insert into user values(null, ?, ?, ?)";
            ps = connection.prepareStatement(sql);
            //
            ps.setString(1, d1.getUsername());
            ps.setString(2, d1.getPassword());
            ps.setString(3, d1.getPhone());
            // 发送 SQL，返回结果集
            a = ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, null);
        }
        return a;
    }

    // 删除
    public static int delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int i = -1;
        try {
            connection = DaoUtil.getConnection();
            // 预编译
            String sql = "delete from user where id = ?";
            ps = connection.prepareStatement(sql);
            // 修改占位符
            ps.setInt(1, id);
            // 执行
            i = ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, null);
        }
        return i;
    }

    // 修改
    public static int update(Dept d1, int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int i = -1;
        try {
            connection = DaoUtil.getConnection();
            // 预编译
            String sql = "update user set username = ?, password = ?, phone = ? where id = ?";
            ps = connection.prepareStatement(sql);
            // 编译后文件 修改占位符
            ps.setString(1, d1.getUsername());
            ps.setString(2, d1.getPassword());
            ps.setString(3, d1.getPhone());
            ps.setInt(4, id);
            // 执行
            i = ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, null);
        }
        return i;
    }

    // 查询
    public static List<Dept> select(int ids) {
        List<Dept> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DaoUtil.getConnection();
            // 预编译
            String sql;
            if (ids <= 0) {
                sql = "select * from user";

            } else {
                sql = "SELECT * from user where id = ?";
            }
            ps = connection.prepareStatement(sql);
            // 修改占位符
            ps.setInt(1, ids);
            // 发送
            resultSet = ps.executeQuery(sql);
            while (resultSet.next()) {
                list.add(new Dept(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DaoUtil.CloseAll(connection, ps, resultSet);
        }
        return list;
    }
}
```



### 对象关系映射

```java
while (resultSet.next()) {
list.add(new Dept(resultSet.getInt("id"), resultSet.getString("username"),
resultSet.getString("password"), resultSet.getString("phone")));
}
```



如上代码，其中 Dept 类中的属性与数据库中的属性相对应。



**结果集：**

```hava
ResultSet r1 = s1.executeQuery(str);
```

当使用 `executeQuery`  方法执行后会返回一个结果集。

```java
// 使用 next 方法判断是否有数据
r1.next()
// 使用 set 方法取出数据
r1.getXXX();
// 使用 列数
getXXX(1);
// 使用 列名
getXXX("id");
```











































