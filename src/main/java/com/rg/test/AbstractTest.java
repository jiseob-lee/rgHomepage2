package com.rg.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/* 모든 테스트 파일이 상속할 추상클래스
 * - 어노테이션 자동 추가
 * - Logger 
 * */
//@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@ContextConfiguration(locations = "/spring/context-*.xml")
//classpath:/com/example/MyTest-context.xml
public class AbstractTest {

	protected static final Logger logger = 
			LoggerFactory.getLogger(AbstractTest.class);
}
