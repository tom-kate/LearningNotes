# JVM虚拟机

## 类加载器子系统

### 类加载的过程

加载->链接->初始化

![类加载的过程](images/2021-02-04_214640.png)

![类加载的子系统](images/2021-02-04_214749.png)

#### 加载

1. 通过一个类的全限定名获取该定义此类的二进制字节流

2. 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构

3. 在内存中生成一个代表这个类的java.lang.Class对象，作为方法区这个类的各种数据的方法问入口

#### 连接

1. 验证（Verify）
   * 目的在于确定class文件的字节流中包含信息符合当前虚拟机的要求，保证被加载的类的正确性，不会危害虚拟机自身安全。
   * 主要包括四种验证：文件格式验证，元数据验证，字节码验证，以及符号验证
2. 准备（Prepare）
   * 为类变量分配内存并设置该类变量的默认初始值，即零值。
   * 这里不包含用final修饰的static，因为final在编译的时候就会分配了，准备阶段会现式初始化；
   * 这里不会为实例变量分配初始化，类变量会分配在方法区中，而实例变量是会随着对象一起分配到java堆中
3. 解析（Resolve）
   * 将常量池内的符号引用转换为直接引用的过程。
   * 事实上，解析操作往往会伴随着jvm在执行完初始化之后再执行。
   * 符号引用就是一组符号来描述引用的目标。符号引用的字面量形式明确定义在《java虚拟机规范》的Class文件格式中。直接引用就是直接指向目标的指针、相对偏移量或者一个间接定位到目标的句柄
   * 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型等。对应常量池中的CONSTANT_Class_info、CONSTANT_Fieldref_info、CONSTANT_Methodref_info等。

#### 初始化

* 注意
  * 初始化阶段就是执行类构造器方法\<clinit\>（）的过程
  * 此方法不许定义，是javac编译器自动收集类中的所有类变量的赋值动作和静态代码块中的语句合并而来。
  * 构造器方法中指令按语句在源文件中出现的顺序执行。
  * \<clinit\>（）不同于类的构造器。（关联：构造器是虚拟机视角下的\<init\>（））
  * 若该类具有父类，JVM会保证子类的\<clinit\>（）执行前，父类的\<clinit\>（）已经执行完毕。
  * 虚拟机必须保证一个类的\<clinit\>（）方法在多线程下被同步加锁

### 类加载器的分类

- JVM支持两种类型的类加载器，分别为**引导类加载器**和**自定义类加载器**

- 从概念上来讲，自定义类加载器一般指的是程序中由开发人员自定义的一类加载器，但是Java虚拟机规范却没有这么定义，而是**将所有派生于抽象类ClassLoader的类加载器都划分为自定义类加载器**

- 无论类加载器的类型如何划分，在程序中我门最常见的类加载器始终只有3个,如下所示

  - **Bootstrap Class Loader（引导类加载器）**

  - **Extension Class Loader（扩展类加载器）**

  - **System Class Loader（系统类加载器、应用程序加载器）**

  - User Defined Class Loader（用户自定义类加载器，默认使用系统类加载器加载）

    ![类加载器的分类](images/2021-02-04_214537.png)

    **这几者之间的关系是包含关系。不是上层下层，也不是子父类的集成关系**
    
    ````java
            //获取系统类加载器
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            System.out.println(systemClassLoader);
            //sun.misc.Launcher$AppClassLoader@18b4aac2
    
            //获取器上层：扩展类加载器
            ClassLoader extClassLoader = systemClassLoader.getParent();
            System.out.println(extClassLoader);
            //sun.misc.Launcher$ExtClassLoader@1b6d3586
    
            //获取器上层：获取不到引导类加载器
            ClassLoader bootstrapClassLoader = extClassLoader.getParent();
            System.out.println(bootstrapClassLoader);
            //null
    
            //对于用户自定义类来说：默认使用系统类加载器加载
            ClassLoader classLoader = ClassLoaderTest01.class.getClassLoader();
            System.out.println(classLoader);
            //sun.misc.Launcher$AppClassLoader@18b4aac2
    
            //Stirng类使用引导类加载器进行加载的 ----> Java的核心类库都是使用引导类加载器加载的
            ClassLoader classLoader1 = String.class.getClassLoader();
            System.out.println(classLoader1);
        //null
    ````
    
    

#### 启动类加载器（引导类加载器）

* Bootstrap ClassLoader为虚拟机自带的加载器

- 这个类加载使用C/C++语言实现的，嵌套在jvm内部

- 它用来加载Java的核心库（JAVA_HOME/jre/lib/tr/jar、resources.jar或sun.boot.class.path路径下的内容），用于提供JVM自身需要的类

- 并不继承自java.lang.ClassLoader，没有父加载器

- 加载扩展类和应用类加载器，并指定为他们的父类加载器

- 处于安全考虑，Bootstrap启动类加载器只加载报名为java、javax、sun等开头的包

  ````java
          //获取BootstrapClassLoader能够加载的api的路径
          URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
          for (URL urL : urLs) {
              System.out.println(urL.toExternalForm());
          }
          //从上面的路径中随意挑选一个类，来看看它的类加载器是什么:引导类加载器
   		//file:/D:/Program%20Files/Java/jdk1.8.0_191/jre/lib/jsse.jar
          ClassLoader classLoader = Provider.class.getClassLoader();
          System.out.println(classLoader);
          //结果为null即为引导类加载器
  ````

  

  #### 扩展类加载器

* Java语言编写，由sun.misc.Launcher$ExtClassLoader实现。

* 派生于ClassLoader类

* 父加载器为启动类加载器

* 从java.ext.dirs系统属性所指定的目录中加载类库，或从JDK的安装目录的jre/lib/ext子目录（扩展目录）下加载类库。如果用户创建的JAR放在此目录下，也会自动由扩展类加载器加载。

  ```` java
          String property = System.getProperty("java.ext.dirs");
          System.out.println(property);
          for (String path:property.split(";")){
              System.out.println(path);
          }
          //从上面的路径中随意挑选一个类，来看看它的类加载器是什么:扩展类加载器
          ClassLoader classLoader1 = CurveDB.class.getClassLoader();
          System.out.println(classLoader1);
          //sun.misc.Launcher$ExtClassLoader@4b67cf4d
  ````

  

#### 应用程序类加载器（系统类加载器）

- java语言编写，由sum.misc.Launcher$AppClassLoader实现
- 派生于ClassLoader类
- 父类加载器为扩展类加载器
- 它负责加载环境变量classpath或者系统属性 java.class.path指定路径下的类库
- **该类加载是程序中默认的类加载器**，一般来说，Java应用的类都是由它来完成加载
- 通过ClassLoader#getSystemClassLoader()方法可以获取到该类加载器。

#### 用户自定义类加载器

1. 为什么要定义自定义类加载器？
   * 隔离加载类
   * 修改类加载的方式
   * 扩展加载源
   * 防止源码泄露

### 关于ClassLoader

ClassLoader类，它是一个抽象类，其后所有的类加载器都继承自ClassLoader（不包括启动类加载器）

![ClassLoader](images/2021-02-04_214422.png)

#### 获取ClassLoader的途径

````java
  			//1.获取当前类的ClassLoader
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);
            //null

            //2.获取当前线程上下文的ClassLoader
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            System.out.println(contextClassLoader);
            //sun.misc.Launcher$AppClassLoader@18b4aac2

            //3.获取系统的ClassLoader
            ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
            System.out.println(classLoader1);
            //sun.misc.Launcher$AppClassLoader@18b4aac2

            //4.获取扩展类加载器
            ClassLoader classLoader2 = classLoader1.getParent();
            System.out.println(classLoader2);
            //sun.misc.Launcher$ExtClassLoader@1b6d3586
````

### 双亲委派机制

​	Java虚拟机对class文件采用的是**按需加载**的方式，也就是说当需要使用该类时才会将它的class文件加载到内存中生成class对象。而且加载某个类的class文件时，Java虚拟机采用的是**双亲委派模式**，即把请求交由父类处理，它是一种任务委派模式。

#### 工作原理

1. 如果一个类加载器收到了类加载的请求，它并不会自己先去加载，而时把这个请求委托给父类加载器去执行；
2. 如果父类加载器还存在其父类加载器，则进一步向上委托，依次递归，请求最终将到达顶层的启动类加载器；
3. 如果父类加载器可以完成类加载任务，就成功返回，倘若父类加载器无法完成此加载任务，子类加载器才会尝试自己去加载，这就是双亲委派模型。

![双亲委派原理](images/2021-02-04_214315.png)

#### 优势

1. 避免类的重复加载
2. 保护程序安全，防止核心API被随意改动
   * 自定义类：java.lang.String
   * 自定义类：java.lang.ShkStart

#### 沙箱安全机制

​	自定义String类，但是在加载自定义String类的时候会率先使用引导类加载器加载，而引导类加载器在加载的过程中会先加载jdk自带的文件（rt.jar包中java\lang\String.class），报错信息说没有main方法，就是因为加载的是rt.jar包中的String类。这样可以保证对java核心源代码的保护，这就是沙箱安全机制。

### 补充内容

#### Class对象是否一致的判断条件

1. 类的完整类名必须一致，包括包名
2. 加载这个类的ClassLoader（指ClasLoader实例对象）必须相同

#### 对类加载器的引用

1. JVM必须知道一个类型是由启动类加载器加载的还是由用户类加载器加载的。如果一个类型是由用户类加载器加载的，那么JVM会**将这个类加载器的一个引用作为类型信息的一部分保存在方法区中**.当解析一个类型到另一个类型的引用的时候，JVM需要保证这两个类型的类加载器是相同的。

#### 类的主动使用和被动使用

- 主动使用，又分为七种情况：

  1. 创建类的实例

  2. 访问某个类或者接口的静态变量，或者对该静态变量赋值

  3. 调用类的静态方法

  4. 反射（比如：Class.forName("lang.String")）

  5. 初始化一个类的子类

  6. Java虚拟机启动时被表明为启动类的类

  7. JDK7开始提供的动态语言支持

     java.lang.invoke.MethodHandle实例的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic句柄对应用的类没有初始化，则初始化

- 除了以上七种情况，其他使用Java类的方式都被看做是对**类的被动使用**，都**不会导致类的初始化**

## 运行时数据区

![运行时数据区](images/2021-02-04_214205.png)

### 程序计数器（PC寄存器）

#### 简介

1. PC寄存器用来存储指向下一条指令的地址，即将要执行的执行代码。由执行引擎读取下一条指令，**JVM中的PC寄存器是对物理PC寄存器的一种抽象模拟**
2. 它是一块很小的内存空间，几乎可以忽略不记。也是运行速度最快的存储区域。
3. 在JVM规范中，每个线程都有它自己的程序计数器，是线程私有的，生命周期与线程的生命周期保持一致。
4. 任何时间一个线程都只有一个方法在执行，也就是所谓的**当前方法**。程序计数器会存储当前线程正在执行的Java方法的JVM指令地址；或者，如果是在执行native方法，则是未指定值（undefned）
5. 它是程序控制流的指示器，分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器完成
6. 字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令。
7. 它是唯一一个在Java虚拟机规范中没有规定任何OutOtMemoryError情况的区域  

<img src="images/2021-02-04_213958.png" alt="PC寄存器" style="zoom:75%;" />

#### 常见问题

1. 使用PC寄存器存储字节码指令地址有什么用？为什么使用PC寄存器来记录当前线程的执行地址呢？

   因为cpu需要不停的切换各个线程，这时候切换回来以后，就得知道接着从哪开始继续执行，JVM的字节码解释器就需要通过改变PC寄存器的值来明确下一条应该执行什么样的字节码指令。

2. PC寄存器为什么会被设定为线程私有？

   多线程在一个特定的时间段内只会执行其中某一个线程的方法，CPU会不停地做任务切换**为了能够准确地记录各个线程正在执行的当前字节码指令地址，最好的办法自然是为每一个线程都分配一个PC寄存器**，这样一来各个线程之间便可以进行独立的计算，从而不会出现互相干扰的情况。

#### CPU时间片

​		CPU时间片即CPU分配给各个程序的时间，每个线程被分配一个时间段，称作它的时间片。

- 宏观上：我们可以同时打开多个应用程序，每个程序并行不悖，同时运行。
- 微观上：由于只有一个cpu，一次只能处理程序要求的一部分，如何处理公平，一种办法就是引入时间片，每个程序轮流执行。

### 虚拟机栈（Java栈）

#### 栈和堆概念

- **栈是运行时的单位，而堆是存储的单位**（内存中）

- Java虚拟机栈，早期也叫java栈，每个线程在创建的时候都会创建一个虚拟机栈，其内部保存一个个的栈帧，对应着一次次的Java方法调用。
- java虚拟机栈式线程私有的

#### 生命周期

生命周期和线程是一致的

#### 作用

主管Java程序的运行，它保存方法的局部变量（8种基本数据类型、对象的引用）、部分结果、并参与方法的调用和返回

* 局部变量 vs 成员变量（或属性）
* 基本数据变量 vs 引用类型变量（类、数组、接口）

#### 特点

先进后出

#### 优点

- 栈是一种快速有效的分配存储方式，访问速度仅次于程序计数器
- JVM直接对Java栈的操作只有两个
  - 每个方法执行，伴随着进栈（入栈、压栈）
  - 执行结束后的出栈工作
- 对于栈来说不存在垃圾回收问题
  - GC（仅有入栈、出栈的简单操作 所以不需要GC）  ;  OOM（可能存在内存溢出） 

#### 栈中可能出现的异常

Java虚拟机规范允许**Java栈的大小是动态的或者是固定不变的**

- 如果采用固定大小的Java虚拟机栈，那每一个线程的Java虚拟机栈容量可以在线程创建的时候独立选定。如果线程请求分配的栈容量超过Java虚拟机栈允许的最大容量；Java虚拟机会抛出一个**StackOverflowError**异常 。

````java
public class StackErrorTest01 {
    public static void main(String[] args) {
        main(args);
    }
}

Exception in thread "main" java.lang.StackOverflowError
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)
	at com.tomkate.java.StackErrorTest01.main(StackErrorTest01.java:10)

````



- 如果Java虚拟机栈可以动态扩展，并且在尝试扩展的时候无法申请到足够的内存，或者在创建新的线程时没有足够的内存区创建对应的虚拟机栈，那Java虚拟机将会抛出一个**OutOfMemoryError**异常。

#### 设置虚拟机栈大小

````java
 /**
     * 模拟设置栈大小
     *
     * 默认情况下：count：11414
     * 设置栈大小： -Xss256k：count：2477
     */
    private static int  a = 1;
    public static void main(String[] args) {
        System.out.println(a);
        a++;
        main(args);
    }
````

#### 栈的存储单位（栈帧）

* 每个线程都已自己的栈，栈中的数据都是以**栈帧（Stack Frame）的格式存在**
* 在这个线程上正在执行的每个方法都各自对应一个栈帧。
* 栈帧是一个内存区块，是一个数据集，维系着方法执行过程中的各种数据信息。

##### 栈的运行原理

* JVM直接对Java栈的操作只有两个，就是对栈帧的**压栈**和**出栈**,**遵循“先进后出” ——“后进先出”原则**
* 在一条活动线程中，一个时间点上，只会有一个活动的栈帧。即只有当前正在执行的方法的栈帧（栈顶栈帧）是有效的，这个栈帧被称为**当前栈帧（Current Frame）**，与当前栈帧相对应的方法就是**当前方法（Current Method）**,定义这个方法的类就是**当前类（Current Class）**
* 执行引擎运行的所有字节码指令只针对当前栈帧进行操作。
* 如果在该方法中调用了其他方法，对应的新的栈帧会被创建出来，放在栈的顶端，成为新的当前帧。
* **不同线程中锁包含的栈帧是不允许互相引用的，即不可能在一个栈帧之中引用另外一个线程的栈帧**。
* 如果当前方法调用了其他方法，方法返回之际，当前栈帧会传回此方法的执行结果给前一个栈帧，接着虚拟机会丢弃当前栈帧，使得前一个栈帧重新成为当前栈帧。
* Java方法有两种返回函数的方式，**一种是正常的函数返回，使用return指令；另外一种是抛出异常。不管使用哪种方式，都会导致栈帧被弹出**。

<img src="images/2021-02-04_163724.png" alt="当前栈帧" style="zoom:75%;" />

````java
	/**
     * 模拟方法进栈出栈，先进后出，后进先出
     *
     * @param args
     */
    public static void main(String[] args) {
        StackFrameTest stackFrameTest = new StackFrameTest();
        stackFrameTest.method01();
    }

    public void method01() {
        System.out.println("method1开始执行......");
        method02();
        System.out.println("method1执行结束......");
    }

    public int method02() {
        int a = 10;
        System.out.println("method2开始执行......");
        int c = (int) method03();
        System.out.println("method2执行结束......");
        return a + c;
    }

    public double method03() {
        double b = 20.0;
        System.out.println("method3开始执行......");
        System.out.println("method3执行结束......");
        return b;
    }

method1开始执行......
method2开始执行......
method3开始执行......
method3执行结束......
method2执行结束......
method1执行结束......
````

##### 栈帧的内部结构

每个栈帧中存储着：

* **局部变量表（Local  Variables）**
* **操作数栈（Operand  Stack）(或表达式栈)**
* 动态连接（或指向运行时常量池的方法以用）
* 方法返回地址（或方法正常退出或异常退出的定义）
* 一些附加信息

<img src="images/2021-02-04_170821.png" alt="栈帧的内部结构" style="zoom:75%;" />

![帧数据区](images/2021-02-05_155500.png)

##### 局部变量表

* 局部变量表也被称之为局部变量数组或本地变量表
* **定义为一个数字数组，主要用于存储方法参数和定义在方法体内的局部变量**,这些数据类型包括基本数据类型、对象引用、以及returnAddress（返回地址）类型
* 由于局部变量表是建立在线程的栈上，是线程的私有数据，因此**不存在数据安全问题**
* **局部变量表所需的容量大小是在编译期间确定下来的**,并保存在方法的Code属性的maximum  local  variables数据项中。在方法的运行期间是不会改变局部变量表的大小的。
* **方法嵌套调用的次数由栈的大小决定**。一般来说，**栈越大，方法嵌套调用次数越多**。对于一个函数而言，它的参数和局部变量越多，是的局部变量表膨胀，它的栈帧就越大，以满足方法调用所传递的信息增大的需求。进而函数调用就会占用更多的栈空间，导致其嵌套调用次数会减少。
* **局部变量表中的变量只在当前方法调用中有效**。在方法执行时，虚拟机通过使用局部变量表完成参数值到参数变量列表的传递过程。**当方法调用结束后，随着方法栈帧的销毁，局部变量表也会随之销毁**。

###### 关于Slot的理解

*  参数值的存放总是在局部变量表的index0开始，到数组长度-1的索引结束。
* 局部变量表，**最基本的存储单元式Slot（变量槽）**
* 局部变量表中存放编译期可知的各种基本数据类型（8种），引用数据类型（reference），returnAddress类型的变量。
* 在局部变量表里，**32位以内的数据只占一个slot（包括returnAddress类型），64位类型（Long和double）占用两个Slot**。
  * byte、short、char在存储前被转换为int，boolean也被转换为int，0表示false，非0表示true
  * long和double 则占用两个Slot
* JVM会为局部变量表中搞得每一个Slot都分配一个访问索引，通过这个索引即可成功访问到局部变量表中指定的局部变量值。
* 当一个实例方法被调用的时候，它的方法参数和内部定义的局部变量会**按照顺序被复制**到局部变量表中的每一个slot上
* **如果需要访问局部变量表中的一个64bit的局部变量值时，只需要使用前一个索引即可**。（比如：访问long或double类型变量）
* 如果当前帧是由构造方法或者实例方法创建的，那么**该对象引用this将会存放inde为0的slot处**，其余的参数按照参数顺序排列。

<img src="images/2021-02-04_180930.png" alt="局部变量表参数index" style="zoom:50%;" />

![slot关键字的理解1](images/2021-02-04_182027.png)

![slot关键字的理解2](images/2021-02-04_182042.png)



![slot关键字的理解3](images/2021-02-04_182056.png)

###### Slot的重复利用

**栈帧中的局部变量表中的槽位是可以重复用的**，如果一个局部变量过了其作用域，那么在其作用域之后申明的新的局部变量就很有可能会复用过期局部变量的槽位，从而**达到节省资源的目的**。

![Slot的复用](images/2021-02-04_183110.png)

###### 静态变量与局部变量的对比

* 参数表分配完毕之后，再根据方法体内的变量的顺序和作用域分配。
* 我们知道类变量表由两次初始化的机会，第一次始在“**准备阶段**”，执行系统初始化，对类变量设置零值，另一次则是在“**初始化**”阶段，赋予程序员在代码中定义的初始值。
* 和类变量初始化不同的是，**局部变量表不存在系统初始化的过程**，这意味着一旦定义了局部变量则必须人为的初始化否则无法使用。

![局部变量表不存在系统初始化的过程](images/2021-02-04_213245.png)

###### 补充说明

* 在栈帧中，与性能调优关系最为密切的部分就是前面提到的局部变量表。在方法执行时，虚拟机使用局部变量表完成方法的传递。
* **局部变量表中的变量也是重要的垃圾回收根节点，只要被局部变量表中直接或间接引用的对象都不会被回收**

##### 操作数栈

栈：可以使用数组或链表实现

* **操作数栈，在方法执行过程中，根据字节码指令，往栈中写数据或提取数据，即入栈（push）/出栈（pop）**。

* 操作数栈，**主要用于保存计算过程的中间结果，同时作为计算过程中变量临时的存储空间**

* 操作数栈就是JVM执行引擎的一个工作区，当一个方法刚开始执行的时候，一个新的栈帧也会随之被创建出来，**这个方法的操作数栈式空的**
* 每一个操作数栈都会拥有一个明确的栈深度用于存储数值，**其所需的最大深度在编译期就定义好了**，保存在方法的code属性中，为max_stack的值。
* 栈中的任何一个元素都是可以为任意的Java数据类型
  * 32bit的类型占用一个栈单位深度
  * 64bit的类型占用两个栈单位深度
* 操作数栈**并非采用访问索引的方式来进行数据访问的**，而是只能通过标准的入栈（push）和出栈（pop）操作来完成一次数据的访问
* **如果被调用的方法带有返回值的话，其返回值会被压入当前栈帧的操作数栈中**，并更新PC寄存器中下一条需要执行的字节码指令

![调用返回值压入当前操作数栈中](images/2021-02-05_124916.png)

* 操作数栈中的元素的数据类型必须与字节码指令的序列严格匹配，这由编译器在编译期间进行验证，同时在类加载的过程中的类验证阶段的数据流分析阶段要再次验证。
* 另外，我们说Java虚拟机的**解释引擎是基于栈的执行引擎**，其中的栈指的就是操作数栈

###### 操作数栈代码追踪

![操作数栈代码追踪](images/2021-02-05_124442.png)

![1](images/2021-02-05_124529.png)

![2](images/2021-02-05_124545.png)

![3](images/2021-02-05_124557.png)

![4](images/2021-02-05_124610.png)

###### 栈顶缓存技术

前面提过，基于栈式架构的虚拟机所使用的零地址指令更加紧凑，单完成一项操作的时候必然需要使用更多的入栈和出栈指令，这同时也就意味着将需要更多的指令分派次数和内存读/写次数。

由于操作数是在存储在内存中的，因此频繁地执行内存读/写操作必然会影响执行速度，为了解决这个问题，HotSpot JVM的设计者们提出了栈顶缓存技术，**将栈顶元素全部缓存在物理CPU的寄存器中，以降低对内存的读/写次数，提升执行效率**

##### 动态链接（或指向运行时常量池的方法引用）

* 每一个栈帧内部都包含一个指向**运行时常量池中该栈帧所属方法的引用**。包含这个引用的目的就是为了支持当前方法的代码能够实现**动态链接（Dynamic Linking）。**比如invokedynamic指令
* 在Java源文件被编译到字节码文件中时，所有的变量和方法引用都作为符号引用保存在class文件的常量池里。比如：描述一个方法调用了另外的其他方法时，就是通过常量池中指向方法的符号引用来表示的，那么**动态连接的作用就是为了将这些符号引用转换为调用方法的直接引用**。

![动态链接](images/2021-02-05_131545.png)

###### 为什么需要常量池？

常量池的作用，就是为了提供一些符号的常量，便于指令的识别。

##### 方法的调用

在JVM中，将符号引用转换为调用方法的直接引用与方法的绑定机制相关。

###### 链接

* 静态链接

当一个字节码文件被装载进JVM内部时，如果被调用的**目标方法在编译期可知**，且运行时保持不变时。这种情况下将调用方法的符号引用转换为直接引用的过程称之为静态链接。

* 动态连接

如果**被调用的方法在编译器无法被确定下来**，也就是说，只能够在程序运行期将调用方法的符号引用转换为直接引用，由于这种引用转换过程具备动态性，因此也被称之为动态连接。

###### 绑定机制

对应的方法的绑定机制为：早期绑定和晚期绑定。**绑定是一个字段、方法或者类在符号引用被替换为直接引用的过程，这仅仅发生一次**。

* 早期绑定

早期绑定就是指被调用的**目标方法如果在编译期可知，且运行期保持不变**时，即可将这个方法与所属类型进行绑定，这样一来，由于明确了被调用的目标方法究竟是哪一个，因此也就可以使用静态连接的方式将符号引用转换为直接引用。

* 晚期绑定

如果**被调用的方法在编译期无法被确定下来，只能够在程序运行期间根据实际的类型绑定的相关方法**，这种绑定方式也被称之为晚期绑定。

###### 早晚期绑定的发展历史

随着高级语言的横空出世，类似于Java一样的基于面向对象的编程语言如今越来越多，尽管这类编程语言在语法风格上存在一定的差别，但是它们彼此之间始终保持着一个共性，那就是都支持封装、继承和多态等面向对象特性，既然这一类的编程语言具备多态特性，那么自然也就具备早期绑定和晚期绑定两种绑定方式。

Java中任何一个普通的方法其实都具备虚函数的特征，它们相当于C++语言中的虚函数（C++中则需要使用关键字virtual来显式定义）。如果在Java程序中不希望某个方法拥有虚函数的特征时，则可以使用关键字final来标记这个方法。

###### 虚方法和非虚方法

* 非虚方法
  * 如果方法在编译期间就确定了具体的调用版本，这个版本在运行时时不可变的。这样的方法就称为**非虚方法**。
  * 静态方法、私有方法、final方法、实例构造器、父类方法都是非虚方法。
* 虚方法
  * 其他方法为**虚方法**。

虚拟机中提供了一下几条方法的调用指令：

* 普通调用指令
  1. **invokestatic：调用静态方法，解析阶段唯一确定方法版本**
  2. **invokespecial：调用\<init\>方法、私有及父类方法，解析阶段确定唯一方法版本**
  3. invokevirtual：调用所有虚方法
  4. invokeinterface：调用接口方法
* 动态调用指令
  5. invokekedynamic：动态解析出需要调用的方法，然后执行

前四条指令固化在虚拟机内部，方法的调用不可人为干预，而invokeKedynamic指令则支持由用户确定方法版本。其中**invokestatic指令和invokespecial指令调用的方法成为非虚方法，其余的（final修饰的除外）成为虚方法。**

###### 关于invokedynamic指令

* JVM字节码指令集一直比较稳定，一直到Java7中才增加了一个invokedynamic指令，这是Java为了实现动态类型语言】支持而做的一种改进。

* 但是在Java7中并没有提供直接生成invokedynamic指令的方法，需要借助ASM这种底层字节码工具来产生invokedynamic指令。直到Java8的Lambda表达式的出现，invokedynamic指令的生成，在Java中才有了直接的生成方式。

* Java7中增加的动态语言类型支持的本质是对Java虚拟机规范的修改，而不是对Java语言规则的修改，这一块相对来讲比较复杂，增加了虚拟机中的方法调用，最直接的受益者就是运行在Java平台的动态语言的编译器。

###### 动态语言和静态语言

动态类型语言和静态类型语言两者的区别就在于对类型的检查是在编译期还是在运行期，满足前者就是静态类型语言，反之是动态类型语言。

说的再直白一点就是，**静态类型语言是判断变量自身的类型信息；动态类型语言是判断变量值的类型信息，变量没有类型信息，变量值才有类型信息**，这是动态语言的一个重要特征。

> Java：String info = "tomkate";     (Java是静态类型语言的，会先编译就进行类型检查)
>
> JS：var name = "tomkate";    var name = 10;    （运行时才进行检查）

###### 方法重写的本质

- 找到操作数栈顶的第一个元素所执行的对象的实际类型，记作C。
- 如果在类型C中找到与常量中的描述符合简单名称都相符的方法，则进行访问权限校验，如果通过则返回这个方法的直接引用，查找过程结束；如果不通过，则返回java.1ang.I1legalAccessError 异常。
- 否则，按照继承关系从下往上依次对C的各个父类进行第2步的搜索和验证过程。
- 如果始终没有找到合适的方法，则抛出java.1ang.AbstractMethodsrror异常。

###### IllegalAccessError介绍

程序试图访问或修改一个属性或调用一个方法，这个属性或方法，你没有权限访问。一般的，这个会引起编译器异常。这个错误如果发生在运行时，就说明一个类发生了不兼容的改变。

###### 虚方法表

* 在面向对象的编程中，会很频繁的使用到动态分派，如果在每次动态分派的过程中都要重新在类的方法元数据中搜索合适的目标的话就可能影响到执行效率。因此，**为了提高性能，JVM采用在类的方法区建立一个虚方法表（virtual method table）（非虚方法不会出现在表中）来实现。使用索引表来代替查找**。
* 每个类中都有一个虚方法表，表中存放着各个方法的实际入口。
* 虚方法表什么时候被创建？

虚方法表会在类加载的连接阶段被创建并开始初始化，类的变量初始化值准备完成之后，JVM会把该类的方法表也初始化完成。

![虚方法表调用](images/2021-02-05_155125.png)

如上图所示：如果类中重写了方法，那么调用的时候，就会直接在虚方法表中查找，否则将会直接连接到Object的方法中。

##### 方法返回地址（return address）

* 存放调用该方法的pc寄存器的值。
* 一个方法的结束，有两种形式
  * 正常执行完成
  * 出现未处理的异常，非正常退出
* 无论通过那种方式的退出，在方法退出后都返回到该方法被调用的位置。方法无法正常退出时，**调用者的pc计数器的值作为返回地址，即调用该方法的指令的下一条指令的地址**。而通过异常退出的，返回地址时要通过异常表来确定，栈帧中一般不会保存这部分信息。

当一个方法开始执行后，只有两种方式可以退出这个方法：

1. 执行引擎遇到任意一个方法返回的字节码指令（return），会有返回值传递给上层的方法调用者，简称**正常完成出口**

   * 一个方法在正常嗲用完成之后究竟需要使用哪一个返回指令还需要根据方法返回值的实际数据类型而定。
   * 在字节码指令中，返回指令包含ireturn（当返回值时boolean、byte、char、short和int类型时使用）、lreturn、freturn、dtrturn以及areturn，另外还有一个return指令供声明为void的方法、实例初始化方法、类和接口的初始化方法使用。

   <img src="images/2021-02-05_163202.png" style="zoom:75%;" />

2. 在方法执行的过程中遇到了异常（Exception）,并且这个异常没有在方法内进行处理，也就是只要在本方法的异常表中没有搜索到匹配的异常处理器，就会导致方法退出。简称**异常完成出口**

方法执行过程中抛出异常时的异常处理，存储在一个异常处理表，方便再发生异常的时候找到处理异常的代码。

![异常处理表](images/2021-02-05_162343.png)

<img src="images/2021-02-05_163622.png" alt="异常表1" style="zoom:75%;" />

![异常表2](images/2021-02-05_163719.png)

* **本质上，方法的退出就是当前栈帧出栈的过程。此时，需要恢复上层方法的局部变量表、操作数栈、将返回值压入调用者栈帧的操作数栈、设置PC寄存器值等，让调用者方法继续执行下去**。
* **正常完成出口和异常完成出口的区别在于：通过异常完成出口退出的不会给它的上层调用者产生任的返回值。**

##### 栈帧中的一些附加信息

栈帧中还允许携带与Java虚拟机实现相关的一些附加信息。例如，对程序条视提供支持的信息。

#### 栈的相关面试题

* 举例出栈溢出的情况？（StackOverflowError）
  * 通过-Xss设置栈的大小；
  * 整个内存空间不足OOM
* 调整栈大小，就能保证不出现溢出吗？
  * 不能，递归调用main方法总是会溢出的
* 分配的栈内存越大越好吗？
  * 不是，一定时间内降低了OOM概率，但是会挤占其它的线程空间，因为整个空间是有限的。
* 垃圾回收是否会涉及到虚拟机栈？
  * 不会
* 方法中定义的局部变量是否线程安全？
  * 具体问题具体分析

````java
package com.tomkate.java;

/**
 * 面试题
 * 方法中定义局部变量是否线程安全？具体情况具体分析
 * 何为线程安全？
 * 如果只有一个线程才可以操作此数据，则必是线程安全的
 * 如果有多个线程操作，则此数据是共享数据，如果不考虑同步机制，则为线程不安全
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/5 17:05
 */
public class StringBuilderTest {

    // s1的声明方式是线程安全的
    public static void method01() {
        // 线程内部创建的，属于局部变量
        //内部消亡
        StringBuilder s1 = new StringBuilder();
        s1.append("a");
        s1.append("b");
    }
    
    // stringBuilder 是线程不安全的，操作的是共享数据
    public static void method02(StringBuilder stringBuilder) {
        stringBuilder.append("a");
        stringBuilder.append("b");
    }
    
    /**
     * 同时并发的执行，会出现线程不安全的问题
     */
    public static void method03() {
        StringBuilder stringBuilder = new StringBuilder();
        
        new Thread(() -> {
            stringBuilder.append("a");
            stringBuilder.append("b");
        }, "t1").start();

        method02(stringBuilder);
    	//new的线程可能与method02同时执行产生并发
    }

    // 这个也是线程不安全的，因为有返回值，有可能被其它的程序所调用
    public static StringBuilder method04() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("a");
        stringBuilder.append("b");
        return stringBuilder;
    }

    // StringBuilder是线程安全的，但是String也可能线程不安全的
    // StringBuilder内部消亡，return的Strin是线程不安全的
    public static String method05() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("a");
        stringBuilder.append("b");
        return stringBuilder.toString();
    }
}
````

总结：**如果对象始在内部产生，并在内部消亡，没有返回值到外部，那么它就是线程安全的，反之则是线程不安全的。**

**运行时数据区，是否存在Error和GC？**

| 运行时数据区 | 是否存在Error | 是否存在GC |
| ------------ | ------------- | ---------- |
| 程序计数器   | 否            | 否         |
| 虚拟机栈     | 是            | 否         |
| 本地方法栈   | 是            | 否         |
| 方法区       | 是（OOM）     | 是         |
| 堆           | 是            | 是         |





