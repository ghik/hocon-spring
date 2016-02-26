hocon-spring
============

**NOTE: `hocon-spring` has been moved to [AVSystem commons library](https://github.com/AVSystem/scala-commons)**

An implementation of [BeanDefinitionReader](http://docs.spring.io/spring/docs/4.0.2.RELEASE/javadoc-api/org/springframework/beans/factory/support/BeanDefinitionReader.html)
that is able to parse [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) (Typesafe Config) files.

This effectively allows you to define your Spring application context entirely in HOCON, which gives you following advantages over XML:

* easy customization and overriding thanks to HOCON merging features
* overall much easier reuse of configuration fragments
* cleaner, more concise syntax

Example:

    beans {
      someAbstractBean {
        %abstract = true
    
        settings {
          inherited = 32
        }
        characters.%set = [a, g, o]
      }
    
      someBean {
        %class = com.example.SomeBeanClass
        %parent = someAbstractBean
    
        someNumberProperty = 42
        someStringProperty = someStringValue
        settings {
          %merge = true
          someSetting = 5
          anotherSetting = 20
        }
        numbers = [1, 2, 3, 4, 5]
        characters {
          %merge = true
          %set = [a, b, c, d]
        }
        nestedBean {
          %class = com.example.SomeOtherBeanClass
    
          otherNestedBean.%ref = otherBean
        }
      }
    
      otherBean {
        %class = com.example.YetAnotherBeanClass
    
        someRandomProperty = someRandomString
      }
    }

An equivalent XML would be:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd">
    
      <bean id="someAbstractBean" abstract="true">
        <property name="settings">
          <map>
            <entry key="inherited" value="32"/>
          </map>
        </property>
    
        <property name="characters">
          <set>
            <value>a</value>
            <value>g</value>
            <value>o</value>
          </set>
        </property>
      </bean>
    
      <bean id="someBean" class="com.example.SomeBeanClass" parent="someAbstractBaen">
        <property name="someNumberProperty" value="42"/>
        <property name="someStringProperty" value="someStringValue"/>
    
        <property name="settings">
          <map merge="true">
            <entry key="someSetting" value="5"/>
            <entry key="anotherSetting" value="20"/>
          </map>
        </property>
    
        <property name="numbers">
          <list>
            <value>1</value>
            <value>2</value>
            <value>3</value>
            <value>4</value>
            <value>5</value>
          </list>
        </property>
    
        <property name="characters">
          <set merge="true">
            <value>a</value>
            <value>b</value>
            <value>c</value>
            <value>d</value>
          </set>
        </property>
    
        <property name="nestedBean">
          <bean class="com.example.SomeOtherBeanClass">
            <property name="otherNestedBean" ref="otherBean"/>
          </bean>
        </property>
    
      </bean>
    
      <bean id="otherBean" class="com.example.YetAnotherBeanClass">
        <property name="someRandomProperty" value="someRandomString"/>
      </bean>
    
    </beans>

How to create application context from HOCON files in Java:

    GenericApplicationContext ctx = new GenericApplicationContext();
    HoconBeanDefinitionReader bdr = new HoconBeanDefinitionReader(ctx);
    bdr.loadBeanDefinitions("/beans.conf");
    ctx.refresh();
