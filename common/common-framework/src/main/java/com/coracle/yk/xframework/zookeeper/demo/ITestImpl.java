package com.coracle.yk.xframework.zookeeper.demo;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * 定义了服务实例的基本信息,实际中可能会定义更详细的信息。
 * @author Xiruo.Jiang
 *
 */
@JsonRootName("test")
public class ITestImpl implements ITest {
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 4874386081151635679L;
	
	private String myName = "";
	public ITestImpl() {}
	public ITestImpl(String myName) {
		this.myName = myName;
	}

	@Override
	public String test(String name) {
		System.out.println("Hello, " + name + "!");
		if(!"".equals(myName)) {
			System.out.println("I'm " + myName + ", welcome.");
		}
		return "Hi, " + name + ", this is zookeeper response.";
	}

}
